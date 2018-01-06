package com.jf.system.job;

import com.jf.service.job.JobService;
import com.jf.service.system.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 日任务
 *
 * @author rick
 * @date
 */
@EnableScheduling
@Component
@Lazy(value = false)
public class DayJob {

    @Autowired
    private JobService jobService;
    @Autowired
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

    // @Scheduled(fixedRate = 6000)
    // @Scheduled(fixedDelay = 6000)
    // @Scheduled(initialDelay=1000, fixedRate=6000)

}
