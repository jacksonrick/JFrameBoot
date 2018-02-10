package com.jf.system.job;

import com.jf.service.job.JobService;
import com.jf.service.system.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 日任务
 *
 * @author rick
 * @date
 */
@Configuration
//@Lazy(value = false)
@Conditional(SchedulerCondition.class)
public class DayJob {

    @Resource
    private JobService jobService;
    @Resource
    private AdminService adminService;

    @Scheduled(cron = "0 0 23 * * ?")
    protected void test() {
        long start = System.currentTimeMillis();
        try {
            jobService.test();
            adminService.addMsg(new Long(10000), "任务计划已执行完毕【日任务】，用时 : " + (System.currentTimeMillis() - start) + "毫秒");
        } catch (Exception e) {
            adminService.addMsg(new Long(10000), "任务计划【日任务】执行异常");
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 */1 * * * ?")
    protected void test2() {
        long start = System.currentTimeMillis();
        try {
            jobService.test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // @Scheduled(fixedRate = 6000)
    // @Scheduled(fixedDelay = 6000)
    // @Scheduled(initialDelay=1000, fixedRate=6000)

}
