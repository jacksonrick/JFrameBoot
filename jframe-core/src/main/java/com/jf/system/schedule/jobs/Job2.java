package com.jf.system.schedule.jobs;

import com.jf.service.job.JobService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-03-02
 * Time: 16:09
 */
public class Job2 implements BaseJob {

    @Resource
    private JobService jobService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        jobService.test("###### Job2 ######");
    }

}
