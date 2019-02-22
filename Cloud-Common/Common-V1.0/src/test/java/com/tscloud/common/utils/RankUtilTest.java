package com.tscloud.common.utils;

import org.junit.Test;

/**
 * Created by lei.yang1 on 2017/7/13.
 */
public class RankUtilTest {

    @Test
    public void rank(){
        Integer[] list={34,3,53,2,23,7,14,10};
        RankUtil qs=new RankUtil();
        qs.quick(list);
        for(int i=0;i<list.length;i++){
            System.out.print(list[i]+" ");
        }
    }
}
