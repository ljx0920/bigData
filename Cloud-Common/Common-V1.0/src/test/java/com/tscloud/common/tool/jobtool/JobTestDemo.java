package com.tscloud.common.tool.jobtool;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 2015/4/5.
 */
public class JobTestDemo implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(context.getJobDetail().getKey().getName() + " : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
