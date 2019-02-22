package com.tscloud.common.tool.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * 对zookeeper操作进行封装
 *
 * @author daowan.hu
 */
public class ZookeeperUtil {
    private static Logger log = LogManager.getLogger(ZookeeperUtil.class);

    private static ZookeeperUtil zookeeperUtil = null;

    private CuratorFramework zkClient = null;

    private ZookeeperUtil() {
    }

    public synchronized static ZookeeperUtil getInstance() {
        if (zookeeperUtil == null) {
            zookeeperUtil = new ZookeeperUtil();
        }
        return zookeeperUtil;
    }

    /**
     * 向zookeeper添加节点和数据
     *
     * @param url
     * @param path
     * @param data
     * @throws Exception
     */
    public void addNode(String url, String path, String data) throws Exception {
        try {
            if (zkClient == null) {
                this.getZkClient(url);
            }
            //若创建节点的父节点不存在会先创建父节点再创建子节点
            String pathData = zkClient.create().creatingParentsIfNeeded()
                    //withMode节点类型，
                    .withMode(CreateMode.PERSISTENT)
                    .forPath(path, data.getBytes());
            log.info("[ZookeeperUtil] PathData:[{}]", pathData);
        } catch (Exception e) {
            log.error("[ZookeeperUtil] 向zookeeper添加节点失败，URL:[{}]", path, e);
            throw e;
        }
    }

    /**
     * 得到指定节点数据
     *
     * @param path
     * @return
     * @throws Exception
     */
    public String getNodeData(String url, String path) throws Exception {
        String data;
        try {
            if (zkClient == null) {
                this.getZkClient(url);
            }
            //在获取节点内容的同时把状态信息存入Stat对象
            data = new String(zkClient.getData().forPath(path));
        } catch (Exception e) {
            log.error("[ZookeeperUtil] 向zookeeper节点获取数据失败，URL:[{}]", path, e);
            throw e;
        }
        return data;
    }

    /**
     * 获取子节点
     * @param url
     * @param path
     * @return
     * @throws Exception
     */
    public List<String> getChildNode(String url, String path) throws Exception{
        List<String> list;
        try{
            if(zkClient == null){
                this.getZkClient(url);
            }
            //在获取节点内容的同时把状态信息存入Stat对象
            list = zkClient.getChildren().forPath(path);
        }catch (Exception e){
            log.error("[ZookeeperUtil02] 向zookeeper节点获取子节点失败，URL:["+path+"]",e);
            throw new Exception(e);
        }
        return list;
    }


    /**
     * 判断节点是否存在
     *
     * @param url
     * @param path
     * @return
     * @throws Exception
     */
    public boolean isNodeExists(String url, String path) throws Exception {
        if (zkClient == null) {
            this.getZkClient(url);
        }
        Stat stat = zkClient.checkExists().forPath(path);
        return stat != null;
    }

    /**
     * 修改zookeeper节点数据
     *
     * @param url
     * @param path
     * @param data
     * @throws Exception
     */
    public void updateNode(String url, String path, String data) throws Exception {
        try {
            if (zkClient == null) {
                this.getZkClient(url);
            }
            Stat stat = new Stat();
            //在获取节点内容的同时把状态信息存入Stat对象
            String dataSrc = new String(zkClient.getData().storingStatIn(stat).forPath(path));
            log.info("[ZookeeperUtil] Path:[{}], 修改之前Data:[{}]", path, dataSrc);
            //修改前获取一次节点数据得到版本信息
            zkClient.setData().withVersion(stat.getVersion())
                    .forPath(path, data.getBytes());
        } catch (Exception e) {
            log.error("[ZookeeperUtil] 向zookeeper修改节点数据失败，URL:[{}]", path, e);
            throw e;
        }
    }

    /**
     * 删除zookeeper节点
     *
     * @param url
     * @param path
     * @throws Exception
     */
    public void deleteNode(String url, String path) throws Exception {
        try {
            if (zkClient == null) {
                this.getZkClient(url);
            }
            zkClient.delete()
                    //删除失败，则客户端持续删除，直到节点删除为止
                    .guaranteed()
                    //删除相关子节点
                    .deletingChildrenIfNeeded()
                    //无视版本，直接删除
                    .withVersion(-1)
                    .forPath(path);
        } catch (Exception e) {
            log.error("[ZookeeperUtil] 删除zookeeper节点数据失败，URL:[{}]", path, e);
            throw e;
        }
    }

    /**
     * 关闭zkClient
     */
    public void close() {
        if (this.zkClient != null) {
            this.zkClient.close();
        }
    }

    /**
     * 获取zookeeper客户端连接
     *
     * @param url
     * @return
     * @throws Exception
     */
    public void getZkClient(String url) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000, 3);
        try {
            if (zkClient == null) {
                zkClient = CuratorFrameworkFactory.builder().connectString(url)
                        //会话超时时间
                        .sessionTimeoutMs(5000)
                        //连接超时时间
                        .connectionTimeoutMs(5000)
                        .retryPolicy(retryPolicy)
                        .build();
                zkClient.start();
            }
        } catch (Exception e) {
            log.error("[ZookeeperUtil] 连接zookeeper失败，URL:[]", e);
            throw e;
        }
    }

    public static void main(String[] arg) {
        ZookeeperUtil zookeeperUtil = ZookeeperUtil.getInstance();
        String url = "localhost:2181";
        String path = "/etlworkflow/wf/01";
        try {
            if (zookeeperUtil.isNodeExists(url, path)) {
                zookeeperUtil.deleteNode(url, path);
            }

            zookeeperUtil.addNode(url, path, "running");
            zookeeperUtil.updateNode(url, path, "11111111");
            String data = zookeeperUtil.getNodeData(url, path);
            log.info("Data:" + data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
