package com.jf.system.schedule.jobs;

import com.jf.service.job.JobService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-03-02
 * Time: 16:09
 */
public class Job1 extends QuartzJobBean {

    @Resource
    private JobService jobService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        jobService.test("###### Job1 ######");
    }

}
