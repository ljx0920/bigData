package com.tscloud.common.tool.hadooptool;

import com.tscloud.common.tool.hadooptool.mr.MapReduceSubmit;
import org.junit.Test;

/**
 * Created by lei.yang1 on 2017/7/10.
 */
public class MapReduceSubmitTest {
    @Test
    public void test(){
        MapReduceSubmit mapReduceSubmit = new MapReduceSubmit();
        mapReduceSubmit.setJar( "/opt/etl-plugin/demo-1.0-SNAPSHOT.jar" );
        mapReduceSubmit.setMainClass( "com.hirain.yarn.demo.WordCount" );
        mapReduceSubmit.setMapMemory( "1024" );
        mapReduceSubmit.setReduceMemory( "1024" );
        mapReduceSubmit.setInParams( "/mr/a.txt" );
        mapReduceSubmit.setOutParams( "/mr/out2" );
        System.out.println( mapReduceSubmit.buildShell() );
    }
}
