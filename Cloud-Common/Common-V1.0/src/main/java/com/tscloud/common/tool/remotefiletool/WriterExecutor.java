package com.tscloud.common.tool.remotefiletool;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 用于本地文件生成
 * 模式： 本地
 * Created by jiancai.wang on 2017/1/12.
 */
public class WriterExecutor {

    private final Logger log = LogManager.getLogger(WriterExecutor.class);
    // 本地文件存储目录
    private String localDir;
    // 文件格式
    private String fileFormat;
    // 文件切割周期
    private Integer fileCuttingPeriod;
    // 数据缓存池
    private BlockingQueue<byte[]> dataQueue;
    // 文件数据块d
    private ByteArrayOutputStream buffer;
    // 文件写工具
    private FileWriter fileWriter;
    // 定时器 用来定时切分文件
    private ScheduledExecutorService scheduler;
    // 线程池 用来异步存储文件的
    private ExecutorService executorService;
    // 缺省队列长度 （用于多辆车同时写入，被写文件写出 队列的大小需要保证队列不能溢出）
    private final static int DEFAULT_QUEUE_CAPACITY = 2048;
    // 队却缺省刷新周期（单位：ms）
    private final static int DEFAULT_FLUSH_PERIOD = 100;
    // "毒丸" 用于停止执行
    private final static byte[] POISON_PILL = "POISON_PILL".getBytes();
    // "剪刀" 用于文件切割
    private final static byte[] SHEARS_PILL = "SHEARS_PILL".getBytes();
    // 文件名
    private volatile String fileName;
    // 文件输出流
    private volatile FileOutputStream outputStream = null;
    // 写文件计数器
    private AtomicLong writerMsgCnt = new AtomicLong(0);


    public WriterExecutor(String localDir, String fileFormat, Integer fileMaxCapacity, Integer fileCuttingPeriod) {
        Objects.requireNonNull(localDir);
        Objects.requireNonNull(fileFormat);
        this.localDir = localDir;
        this.fileFormat = fileFormat;
        this.fileCuttingPeriod = fileCuttingPeriod;
        this.dataQueue = new LinkedBlockingDeque<>(DEFAULT_QUEUE_CAPACITY);
        this.buffer = new ByteArrayOutputStream(fileMaxCapacity);
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.executorService = Executors.newSingleThreadExecutor();
        this.fileWriter = FileWriter.getInstance(executorService, null);
        this.fileName = fileWriter.allocateFileName();
    }

    /**
     * 往消息队列发数据
     *
     * @param msgData
     * @throws InterruptedException
     */
    public void passByteArrayToWriter(byte[] msgData) throws InterruptedException {
        this.dataQueue.put(msgData);
    }

    /**
     * 停止队列
     *
     * @throws InterruptedException
     */
    public void close() throws InterruptedException {
        this.dataQueue.put(POISON_PILL);
    }

    /**
     * 定时切割数据
     * 共享文件名
     */
    public void startPeriodTask() {
        // 切割文件 （刷新文件名）
        scheduler.scheduleWithFixedDelay(() -> {
            fileName = fileWriter.allocateFileName();
        }, 0, fileCuttingPeriod, TimeUnit.SECONDS);

        scheduler.scheduleWithFixedDelay(() -> {
            try {
                flushBuffer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, DEFAULT_FLUSH_PERIOD, TimeUnit.MILLISECONDS);
        log.info("[WriterExecutor start]- fileDir：" + localDir + " 文件类型：" + fileFormat);
    }

    /**
     * 定时切割数据
     */
    public void startExclusivePeriodTask() {
        // 1. 定时切割文件。一个周期后开始，确保文件切割标识在队首。
        scheduler.scheduleWithFixedDelay(() -> {
            try {
                passByteArrayToWriter(SHEARS_PILL);
            } catch (InterruptedException e) {
                log.error("Failed in passing SHEARS_PILL. ", e);
            }
        }, fileCuttingPeriod, fileCuttingPeriod, TimeUnit.SECONDS);
        // 2. 在队首添加一个切割文件标记
        try {
            passByteArrayToWriter(SHEARS_PILL);
        } catch (InterruptedException e) {
            log.error("Failed in passing SHEARS_PILL. ", e);
        }
        // 3. 写数据到文件任务
        Runnable writerTask = () -> {
            while (true) {
                byte[] msgData;
                try {
                    msgData = dataQueue.take();
                    if (Objects.equals(msgData, POISON_PILL)) {
                        if (outputStream != null) {
                            outputStream.flush();
                            outputStream.close();
                        }
                        break;
                    } else if (Objects.equals(msgData, SHEARS_PILL)) {
                        // 1. 先关闭上次的文件流
                        if (outputStream != null) {
                            outputStream.flush();
                            outputStream.close();
                            outputStream = null;
                            log.info("[WriterExecutor cutting file]- fileDir：" + localDir + " 文件类型：" + fileFormat);
                        }
                        // 2. 生成新的文件名
                        FileUtils.forceMkdir(new File(localDir));
                        fileName = fileWriter.allocateFileName();
                    } else {
                        if (outputStream == null) {
                            outputStream = new FileOutputStream(new File(localDir + "/" + fileName + "." + fileFormat));
                            // 确保第一次文件创建的时候flush一次
                            outputStream.write(msgData);
                            outputStream.flush();
                        } else {
                            outputStream.write(msgData);
                        }
                    }
                } catch (Exception e) {
                    log.error("Failed in writing data. ", e);
                    if (outputStream != null) {
                        try {
                            outputStream.flush();
                            outputStream.close();
                        } catch (IOException exception) {
                            log.error("[FATAL]Failed in closing outputStream. ", e);
                        } finally {
                            outputStream = null;
                        }
                    }
                }
            }
        };
        // 4. 启动写文件任务
        executorService.submit(writerTask);
        log.info("[WriterExecutor start]- fileDir：" + localDir + " 文件类型：" + fileFormat);
    }

    private void flushBuffer() throws IOException {
        if (dataQueue.isEmpty()) {
            return;
        }
        try {
            int queueSize = dataQueue.size();
            for (int i = 0; i < queueSize; i++) {
                byte[] msgData = dataQueue.take();
                buffer.write(msgData);
                long wtMsgCnt = writerMsgCnt.incrementAndGet();
                if (wtMsgCnt % 10000 == 0) {
                    log.info("WriterExecutor: write {}", wtMsgCnt);
                }
            }
            if (buffer.size() > 0) {
                fileWriter.appendByteArray(buffer.toByteArray(), localDir, fileName, fileFormat);
                buffer.reset();
            }
        } catch (Exception e) {
            log.error("Error in WriterExecutor.flushBuffer: exception! cause = ", e);
        }
    }

}
