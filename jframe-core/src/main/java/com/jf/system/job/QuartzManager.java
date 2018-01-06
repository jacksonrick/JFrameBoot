package com.jf.system.job;

import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;

public class QuartzManager {

    private static String JOB_GROUP_NAME = "JOBGROUP_NAME";
    private static String TRIGGER_GROUP_NAME = "TRIGGERGROUP_NAME";

    /**
     * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     *
     * @param sched    调度器
     * @param jobName  任务名
     * @param jobClass 任务类
     * @param time     时间设置，参考quartz说明文档
     */
    public static void addJob(Scheduler sched, String jobName, Class jobClass, String time) {
        try {
            JobDetailImpl jobDetail = new JobDetailImpl(jobName, JOB_GROUP_NAME, jobClass);// 任务名，任务组，任务执行类
            // 触发器
            CronTriggerImpl trigger = new CronTriggerImpl(jobName, TRIGGER_GROUP_NAME);// 触发器名,触发器组
            trigger.setCronExpression(time);// 触发器时间设定
            sched.scheduleJob(jobDetail, trigger);
            // 启动
            if (!sched.isShutdown()) {
                sched.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加一个定时任务
     *
     * @param sched            调度器
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param jobClass         任务类
     * @param time             时间设置，参考quartz说明文档
     */
    public static void addJob(Scheduler sched, String jobName, String jobGroupName, String triggerName, String triggerGroupName,
                              Class jobClass, String time) {
        try {
            JobDetailImpl jobDetail = new JobDetailImpl(jobName, jobGroupName, jobClass);// 任务名，任务组，任务执行类
            // 触发器
            CronTriggerImpl trigger = new CronTriggerImpl(triggerName, triggerGroupName);// 触发器名,触发器组
            trigger.setCronExpression(time);// 触发器时间设定
            sched.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
     *
     * @param sched   调度器
     * @param jobName
     * @param time
     */
    public static void modifyJobTime(Scheduler sched, String jobName, String time) {
        try {
            TriggerKey triggerKey = new TriggerKey(jobName, TRIGGER_GROUP_NAME);
            CronTriggerImpl trigger = (CronTriggerImpl) sched.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(time)) {
                JobKey jobKey = new JobKey(jobName, JOB_GROUP_NAME);
                JobDetail jobDetail = sched.getJobDetail(jobKey);
                Class objJobClass = jobDetail.getJobClass();
                removeJob(sched, jobName);
                addJob(sched, jobName, objJobClass, time);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改一个任务的触发时间
     *
     * @param sched            调度器
     * @param triggerName
     * @param triggerGroupName
     * @param time
     */
    public static void modifyJobTime(Scheduler sched, String triggerName, String triggerGroupName, String time) {
        try {
            TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroupName);
            CronTriggerImpl trigger = (CronTriggerImpl) sched.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(time)) {
                CronTriggerImpl ct = (CronTriggerImpl) trigger;
                // 修改时间
                ct.setCronExpression(time);
                // 重启触发器
                sched.resumeTrigger(triggerKey);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
     *
     * @param sched   调度器
     * @param jobName
     */
    public static void removeJob(Scheduler sched, String jobName) {
        try {
            TriggerKey triggerKey = new TriggerKey(jobName, TRIGGER_GROUP_NAME);
            sched.pauseTrigger(triggerKey);// 停止触发器
            sched.unscheduleJob(triggerKey);// 移除触发器
            JobKey jobKey = new JobKey(jobName, JOB_GROUP_NAME);
            sched.deleteJob(jobKey);// 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 移除一个任务
     *
     * @param sched            调度器
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     */
    public static void removeJob(Scheduler sched, String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
        try {
            TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroupName);
            sched.pauseTrigger(triggerKey);// 停止触发器
            sched.unscheduleJob(triggerKey);// 移除触发器
            JobKey jobKey = new JobKey(jobName, jobGroupName);
            sched.deleteJob(jobKey);// 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 启动所有定时任务
     *
     * @param sched 调度器
     */
    public static void startJobs(Scheduler sched) {
        try {
            sched.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭所有定时任务
     */
    public static void shutdownJobs(Scheduler sched) {
        try {
            if (!sched.isShutdown()) {
                sched.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
