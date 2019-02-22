package com.tscloud.common.tool.spark;

import org.junit.Test;

/**
 * Created by lei.yang1 on 2017/5/4.
 */
public class SparkSubmitTest {

    @Test
    public void getShell(){

        SparkSubmit sparkSubmit = new SparkSubmit();
        sparkSubmit.setMaster( "spark://slave6:6066" )
                   .setDeployModel("clustershell")
                   .setName("wcn")
                   .setMainClass("com.hirain.spark.demo.WordCountNew")
                   .setApplicationJar( "hdfs://192.168.5.10:8020/spark/demo/wordcount.jar" );

        String args = "hdfs://192.168.5.10:8020/spark/demo/cdh.txt \\\n" +
                "hdfs://192.168.5.10:8020/spark/demo/result \\\n" +
                "hdfs";
        sparkSubmit.setApplicationArguments( args );
        System.out.println( sparkSubmit.buildShell() );
    }
}
