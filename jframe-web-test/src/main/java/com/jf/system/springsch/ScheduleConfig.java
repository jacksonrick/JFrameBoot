package com.jf.system.springsch;

import com.jf.commons.LogManager;
import com.jf.service.JobService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description: Spring定时器
 * User: xujunfei
 * Date: 2017-11-28
 * Time: 11:03
 */
@Configuration
@ConditionalOnProperty(name = "app.springsch.enabled", havingValue = "true")
public class ScheduleConfig {

    @Resource
    private JobService jobService;

    @Scheduled(cron = "0 0 23 * * ?")
    protected void test() {
        long start = System.currentTimeMillis();
        try {
            jobService.test("Spring Schedule ");
            LogManager.info("任务计划已执行完毕【日任务】，用时 : " + (System.currentTimeMillis() - start) + "毫秒");
        } catch (Exception e) {
            LogManager.info("任务计划【日任务】执行异常");
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 */1 * * * ?")
    protected void test2() {
        long start = System.currentTimeMillis();
        try {
            jobService.test("Spring Schedule ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // @Scheduled(fixedRate = 6000)
    // @Scheduled(fixedDelay = 6000)
    // @Scheduled(initialDelay=1000, fixedRate=6000)

}
