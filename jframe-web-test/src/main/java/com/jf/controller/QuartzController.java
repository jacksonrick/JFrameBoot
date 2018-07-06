package com.jf.controller;

import com.github.pagehelper.PageInfo;
import com.jf.common.BaseController;
import com.jf.database.model.manage.Jobs;
import com.jf.entity.ResMsg;
import com.jf.entity.enums.ResCode;
import com.jf.service.JobService;
import org.quartz.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-06-21
 * Time: 14:00
 */
@Controller
@RequestMapping("/job")
public class QuartzController extends BaseController {

    @Resource
    private Scheduler scheduler;
    @Resource
    private JobService jobService;

    @RequestMapping("/index")
    public String index() {
        return "quartz/index";
    }

    /**
     * 查询任务
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/queryJob")
    @ResponseBody
    public Map<String, Object> queryJob(Integer pageNum, Integer pageSize) {
        PageInfo<Jobs> pageInfo = jobService.findJobAndTriggerDetails(pageNum, pageSize);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", pageInfo);
        map.put("number", pageInfo.getTotal());
        return map;
    }

    /**
     * Quartz 添加任务
     *
     * @param className      com.jf.system.quartz.jobs.Job1
     * @param groupName      g1
     * @param cronExpression 0/5 * * * * ?
     * @return
     */
    @RequestMapping("/addJob")
    @ResponseBody
    public ResMsg addJob(String className, String groupName, String cronExpression) {
        try {
            // 启动调度器
            scheduler.start();
            // 构建job信息
            JobDetail jobDetail = JobBuilder.newJob(getClass(className).getClass()).withIdentity(className, groupName).build();
            // 表达式调度构建器(即任务执行的时间)
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            // 按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(className, groupName).withSchedule(scheduleBuilder).build();

            Date date = scheduler.scheduleJob(jobDetail, trigger);
            return new ResMsg(ResCode.SUCCESS.code(), ResCode.SUCCESS.msg(), date);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResMsg(ResCode.ERROR.code(), ResCode.ERROR.msg());
        }
    }

    /**
     * 暂停任务
     *
     * @param className
     * @param groupName
     * @return
     */
    @RequestMapping("/pauseJob")
    @ResponseBody
    public ResMsg pauseJob(String className, String groupName) {
        try {
            scheduler.pauseJob(JobKey.jobKey(className, groupName));
            return new ResMsg(ResCode.SUCCESS.code(), ResCode.SUCCESS.msg());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResMsg(ResCode.ERROR.code(), ResCode.ERROR.msg());
        }
    }

    /**
     * 恢复任务
     *
     * @param className
     * @param groupName
     * @return
     */
    @RequestMapping("/resumeJob")
    @ResponseBody
    public ResMsg resumeJob(String className, String groupName) {
        try {
            scheduler.resumeJob(JobKey.jobKey(className, groupName));
            return new ResMsg(ResCode.SUCCESS.code(), ResCode.SUCCESS.msg());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResMsg(ResCode.ERROR.code(), ResCode.ERROR.msg());
        }
    }

    /**
     * 更新任务
     *
     * @param className
     * @param groupName
     * @param cronExpression
     * @return
     */
    @RequestMapping("/rescheduleJob")
    @ResponseBody
    public ResMsg rescheduleJob(String className, String groupName, String cronExpression) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(className, groupName);
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            // 按新的trigger重新设置job执行
            Date date = scheduler.rescheduleJob(triggerKey, trigger);
            return new ResMsg(ResCode.SUCCESS.code(), ResCode.SUCCESS.msg(), date);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResMsg(ResCode.ERROR.code(), ResCode.ERROR.msg());
        }
    }

    /**
     * 删除任务
     *
     * @param className
     * @param groupName
     * @return
     */
    @RequestMapping("/delJob")
    @ResponseBody
    public ResMsg delJob(String className, String groupName) {
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(className, groupName));
            scheduler.unscheduleJob(TriggerKey.triggerKey(className, groupName));
            scheduler.deleteJob(JobKey.jobKey(className, groupName));
            return new ResMsg(ResCode.SUCCESS.code(), ResCode.SUCCESS.msg());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResMsg(ResCode.ERROR.code(), ResCode.ERROR.msg());
        }
    }

    public static Job getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (Job) class1.newInstance();
    }

    /*@RequestMapping("/startQ")
    @ResponseBody
    public ResMsg startQ() {
        SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
        try {
            Scheduler sche = gSchedulerFactory.getScheduler();
            QuartzManager.addJob(sche, "Print", Job1.class, "0/5 * * * * ?");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return new ResMsg(ResCode.SUCCESS.code(), ResCode.SUCCESS.msg());
    }

    @RequestMapping("/stopQ")
    @ResponseBody
    public ResMsg stopQ() {
        SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
        try {
            Scheduler sche = gSchedulerFactory.getScheduler();
            QuartzManager.removeJob(sche, "Print");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return new ResMsg(ResCode.SUCCESS.code(), ResCode.SUCCESS.msg());
    }*/
}
