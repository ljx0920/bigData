package com.tscloud.common.tool.jobtool;

import com.tscloud.common.framework.Exception.JobException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.quartz.Trigger.TriggerState;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;
import java.util.Map;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * 通用的任务调度管理工具类
 *
 * @author vain@ccuu.me
 * @date 14-10-30
 */
public class JobTool {

    private static final Logger log = LogManager.getLogger(JobTool.class);

    private static class JobToolHelper {
        private static final JobTool INSTANCE = new JobTool();
    }

    public static JobTool getInstance() {
        return JobToolHelper.INSTANCE;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    private Scheduler scheduler;

    JobTool() {
        //Scheduler quartzScheduler = ServiceBeanContext.getInstance().getBean("quartzScheduler");
        try {
            if (scheduler == null) {
                scheduler = StdSchedulerFactory.getDefaultScheduler();
            }
            scheduler.start();
            log.info("scheduler init success.");
        } catch (SchedulerException e) {
            log.error("scheduler 初始化失败.", e);
        }
    }

    /**
     * 使用cron表达式增加一个调度任务
     *
     * @param jobName 任务名称
     * @param cron    cron表达式
     * @param clazz   Job
     * @throws JobException
     */
    public void addJob(String jobName, String jonGroup, String cron, Class<? extends Job> clazz) throws JobException {
        JobDetail job;
        CronTrigger trigger;
        try {
            job = newJob(clazz).withIdentity(jobName, jonGroup).build();
            trigger = newTrigger().withIdentity(jobName, jonGroup).withSchedule(cronSchedule(cron)).build();
        } catch (Exception e) {
            throw new JobException("build JobDetail or CronTrigger exception," + e.getMessage(), e);
        }
        scheduleJob(jobName, job, trigger);
    }

    /**
     * 使用cron表达式增加一个调度任务，并携带任务执行参数
     *
     * @param jobName  任务名称
     * @param jonGroup 任务名称
     * @param cron     cron表达式
     * @param clazz    Job
     * @param params   任务执行参数
     * @throws JobException
     */
    public void addJob(String jobName, String jonGroup, String cron, Class<? extends Job> clazz, Map<String, String> params) throws JobException {
        JobDetail job;
        CronTrigger trigger;
        try {
            job = newJob(clazz)
                    .withIdentity(jobName, jonGroup)
                    .usingJobData(new JobDataMap(params))
                    .build();
            trigger = newTrigger()
                    .withIdentity(jobName, jobName)
                    .withSchedule(cronSchedule(cron))
                    .build();
        } catch (Exception e) {
            throw new JobException("build JobDetail or CronTrigger exception," + e.getMessage(), e);
        }

        scheduleJob(jobName, job, trigger);
    }

    /**
     * 使用cron表达式增加一个调度任务，设置任务的开始执行时间和结束执行时间，并携带任务执行参数
     *
     * @param jobName    任务名称
     * @param jobGroup   任务组
     * @param cron       cron表达式
     * @param startTime  任务起始时间
     * @param finishTime 任务结束时间
     * @param clazz      Job
     * @param params     任务执行参数
     * @throws JobException
     */
    public void addJob(String jobName, String jobGroup, String cron, Date startTime, Date finishTime, Class<? extends Job> clazz, Map<String, String> params) throws JobException {
        JobDetail job;
        CronTrigger trigger;
        try {
            job = newJob(clazz).withIdentity(jobName, jobGroup).build();
            job.getJobDataMap().putAll(params);
            trigger = newTrigger().withIdentity(jobName, jobGroup)
                    .startAt(startTime)
                    .withSchedule(cronSchedule(cron))
                    .endAt(finishTime)
                    .build();
        } catch (Exception e) {
            throw new JobException("build JobDetail or CronTrigger exception," + e.getMessage(), e);
        }
        scheduleJob(jobName, job, trigger);
    }

    /**
     * 使用任务间隔毫秒数增加一个调度任务
     *
     * @param jobName  任务名称
     * @param jobGroup 任务组
     * @param interval 任务执行间隔
     * @param clazz    Job
     * @throws JobException
     */
    public void addJob(String jobName, String jobGroup, long interval, Class<? extends Job> clazz) throws JobException {
        JobDetail job;
        SimpleTrigger trigger;
        try {
            job = newJob(clazz).withIdentity(jobName, jobGroup).build();
            trigger = newTrigger().withIdentity(jobName, jobGroup)
                    .withSchedule(simpleSchedule().withIntervalInMilliseconds(interval).repeatForever())
                    .build();
        } catch (Exception e) {
            throw new JobException("build JobDetail or SimpleTrigger exception," + e.getMessage(), e);
        }
        scheduleJob(jobName, job, trigger);
    }

    /**
     * 使用任务间隔毫秒数增加一个调度任务，并携带任务执行参数
     *
     * @param jobName  任务名称
     * @param jobGroup 任务组
     * @param interval 任务执行间隔
     * @param clazz    Job
     * @param params   任务执行参数
     * @throws JobException
     */
    public void addJob(String jobName, String jobGroup, long interval, Class<? extends Job> clazz, Map<String, String> params) throws JobException {
        JobDetail job;
        SimpleTrigger trigger;
        try {
            job = newJob(clazz).withIdentity(jobName, jobGroup).build();
            job.getJobDataMap().putAll(params);
            trigger = newTrigger().withIdentity(jobName, jobGroup)
                    .withSchedule(simpleSchedule().withIntervalInMilliseconds(interval).repeatForever())
                    .build();
        } catch (Exception e) {
            throw new JobException("build JobDetail or SimpleTrigger exception," + e.getMessage(), e);
        }
        scheduleJob(jobName, job, trigger);
    }

    /**
     * 使用任务间隔毫秒数增加一个调度任务，设置任务的开始执行时间和结束执行时间，并携带任务执行参数
     *
     * @param jobName    任务名称
     * @param jobGroup   任务组
     * @param interval   任务执行间隔
     * @param startTime  任务起始时间
     * @param finishTime 任务结束时间
     * @param clazz      Job
     * @param params     任务执行参数
     * @throws JobException
     */
    public void addJob(String jobName, String jobGroup, long interval, Date startTime, Date finishTime, Class<? extends Job> clazz, Map<String, String> params) throws JobException {
        JobDetail job;
        SimpleTrigger trigger;
        try {
            job = newJob(clazz).withIdentity(jobName, jobGroup).build();
            job.getJobDataMap().putAll(params);
            trigger = newTrigger().withIdentity(jobName, jobGroup)
                    .startAt(startTime)
                    .withSchedule(simpleSchedule().withIntervalInMilliseconds(interval).repeatForever())
                    .endAt(finishTime)
                    .build();
        } catch (Exception e) {
            throw new JobException("build JobDetail or SimpleTrigger exception," + e.getMessage(), e);
        }
        scheduleJob(jobName, job, trigger);
    }

    /**
     * 对任务进行调度
     *
     * @param jobName 任务名称
     * @param job     任务内容
     * @param trigger 调度方案
     * @throws JobException 可能出现的异常
     */
    private void scheduleJob(String jobName, JobDetail job, Trigger trigger) throws JobException {
        try {
            if (scheduler.isShutdown()) {
                scheduler.start();
            }
            try {
                if (!scheduler.checkExists(job.getKey())) {
                    scheduler.scheduleJob(job, trigger);
                    log.info("The " + jobName + " job has been started.{jobName:" + jobName + "}");
                }
            } catch (SchedulerException e) {
                throw new JobException("Add new job scheduler exception,{jobCode:" + jobName + "}," + e.getMessage(), e);
            }
        } catch (SchedulerException e) {
            throw new JobException("Restart scheduler exception," + e.getMessage(), e);
        }
    }

    /**
     * 删除一个job
     *
     * @param jobName
     * @param jobGroup
     * @throws JobException
     */
    public void deleteJob(String jobName, String jobGroup) throws JobException {
        try {
            if (scheduler.isShutdown()) {
                scheduler.start();
            }
            JobKey key = new JobKey(jobName, jobGroup);
            if (scheduler.checkExists(key)) {
                scheduler.deleteJob(key);
                log.info("the " + jobName + " job has been removed.");
            }
        } catch (SchedulerException e) {
            throw new JobException("Delete job by jobCode failed,{jobName: " + jobName + ",jobGroup: " + jobGroup + "}", e);
        }
    }

    /**
     * 暂停一个job
     *
     * @param jobName
     * @param jobGroup
     * @throws JobException
     */
    public void pauseJob(String jobName, String jobGroup) throws JobException {
        try {
            if (scheduler.isShutdown()) {
                scheduler.start();
            }
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            if (scheduler.checkExists(jobKey)) {
                scheduler.pauseJob(jobKey);
                log.info("the " + jobName + " job has been paused.");
            }
        } catch (SchedulerException e) {
            throw new JobException("Pause job by jobCode failed, {jobName: " + jobName + ",jobGroup: " + jobGroup + "}", e);
        }
    }

    /**
     * 恢复一个job
     *
     * @param jobName
     * @param jobGroup
     * @throws JobException
     */
    public void resumeJob(String jobName, String jobGroup) throws JobException {
        try {
            if (scheduler.isShutdown()) {
                scheduler.start();
            }
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            if (scheduler.checkExists(jobKey)) {
                scheduler.resumeJob(jobKey);
                log.info("the " + jobName + " job has been resumed.");
            }
        } catch (SchedulerException e) {
            throw new JobException("Resume job by jobCode failed, {jobName: " + jobName + ",jobGroup: " + jobGroup + "}", e);
        }
    }

    /**
     * 获取指定任务的状态
     *
     * @param jobName
     * @param jobGroup
     * @return
     * @throws JobException
     */
    public TriggerState getJobState(String jobName, String jobGroup) throws JobException {
        TriggerState state;
        try {
            if (scheduler.isShutdown()) {
                scheduler.start();
            }
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            state = scheduler.getTriggerState(triggerKey);
        } catch (SchedulerException e) {
            throw new JobException("getJobState failed, {jobName: " + jobName + ",jobGroup: " + jobGroup + "}", e);
        }
        return state;
    }
}

