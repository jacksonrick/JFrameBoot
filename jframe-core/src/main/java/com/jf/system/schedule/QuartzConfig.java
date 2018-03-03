package com.jf.system.schedule;

import com.jf.system.schedule.jobs.Job1;
import com.jf.system.schedule.jobs.Job2;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * Description: Quartz
 * User: xujunfei
 * Date: 2018-03-02
 * Time: 15:28
 */
@Configuration
@ConditionalOnProperty(name = "app.scheduler.quartz.enabled", havingValue = "true")
@Lazy
public class QuartzConfig {

    @Resource
    private MyJobFactory myJobFactory;

    @Bean(name = "dayJobDetail")
    public JobDetail dayJobDetail() throws Exception {
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(Job1.class);
        factory.setGroup("Default");
        factory.setName("dayJob");
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean(name = "dayCronTrigger")
    public CronTrigger dayCronTrigger(@Qualifier("dayJobDetail") JobDetail jobDetail) throws Exception {
        CronTriggerFactoryBean factory = new CronTriggerFactoryBean();
        factory.setJobDetail(jobDetail);
        factory.setGroup("Default");
        factory.setName("dayJobTrigger");
        factory.setCronExpression("0/5 * * * * ?");
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean(name = "monthJobDetail")
    public JobDetail monthJobDetail() throws Exception {
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(Job2.class);
        factory.setGroup("Default");
        factory.setName("monthJob");
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean(name = "monthCronTrigger")
    public CronTrigger monthCronTrigger(@Qualifier("monthJobDetail") JobDetail jobDetail) throws Exception {
        CronTriggerFactoryBean factory = new CronTriggerFactoryBean();
        factory.setJobDetail(jobDetail);
        factory.setGroup("Default");
        factory.setName("monthJobTrigger");
        factory.setCronExpression("0/10 * * * * ?");
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    /**
     * @return
     * @throws Exception
     */
    @Bean(name = "Scheduler")
    public Scheduler scheduler(@Qualifier("dayCronTrigger") CronTrigger dayCronTrigger,
                               @Qualifier("monthCronTrigger") CronTrigger monthCronTrigger) throws Exception {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        Properties properties = new Properties();
        properties.setProperty("org.quartz.threadPool.threadCount", "5");
        properties.setProperty("org.quartz.threadPool.threadPriority", "5");
        properties.setProperty("org.quartz.jobStore.misfireThreshold", "5000");
        properties.setProperty("org.quartz.jobStore.class", "org.quartz.simpl.RAMJobStore");
        factory.setQuartzProperties(properties);

        factory.setSchedulerFactoryClass(StdSchedulerFactory.class);
        factory.setTriggers(dayCronTrigger, monthCronTrigger);
        factory.setJobFactory(myJobFactory);

        factory.afterPropertiesSet();

        Scheduler scheduler = factory.getScheduler();
        scheduler.start();
        return scheduler;
    }

}

@Component
class MyJobFactory extends AdaptableJobFactory {

    @Autowired
    private AutowireCapableBeanFactory capableBeanFactory;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        // 调用父类的方法
        Object jobInstance = super.createJobInstance(bundle);
        // 进行注入
        capableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
}
