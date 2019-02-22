package com.tscloud.common.tool.file;

import com.tscloud.common.framework.Exception.DubboProviderException;
import com.tscloud.common.tool.jobtool.JobTestDemo;
import com.tscloud.common.tool.jobtool.JobTool;
import com.tscloud.common.utils.FileUtil;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static com.tscloud.common.framework.Constants.Env.ECHARTS_HOME;

/**
 * @author wei.wang5
 */
public class Filetest {


    @Test
    public void test() {



            InputStream inputStream = null;
            byte[] data;

            //File iconFile = new File(FileUtils.readFileToString(new File("e:" + filePath + "/" + fileName)));
            //File iconFile = FileUtil.getFile(fileName, "e:"+ filePath +"/" );

            try {
              //  File iconFile = FileUtil.getFile("vertical_tiled_bar.png", "configs/echarts/icon/");
               // File iconFile = FileUtil.getFile("yjx.jpg", "E:/tmp/");
              //  byte[] bytes = FileUtils.readFileToByteArray(new File("E:/tmp/yjx.jpg"));
                File file = new File("E:/tmp/yjx.jpg");
                inputStream = FileUtils.openInputStream(file);
                 data = new byte[inputStream.available()];
                 inputStream.read(data);
                // 加密
                BASE64Encoder encoder = new BASE64Encoder();
                System.out.println("data:image/png;base64," + encoder.encode(data));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
}
