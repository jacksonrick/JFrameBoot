package com.jf.system.quartz;

import com.jf.system.quartz.jobs.Job1;
import org.quartz.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * Description: Quartz
 * User: xujunfei
 * Date: 2018-03-02
 * Time: 15:28
 */
@Configuration
@ConditionalOnProperty(name = "app.quartz.enabled", havingValue = "true")
public class QuartzConfig {

    @Bean
    public JobDetail dayJobDetail() {
        return JobBuilder.newJob(Job1.class).withIdentity("job1").storeDurably().build();
    }

    @Bean
    public Trigger dayTrigger() {
        // 设置时间周期单位秒
        //SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever();
        // 设置表达式
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0/1 * * * ?");

        return TriggerBuilder.newTrigger().forJob(dayJobDetail()).withIdentity("job1").withSchedule(cronScheduleBuilder).build();
    }

}
