package com.jf.system.schedule.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-03-02
 * Time: 16:08
 */
public interface BaseJob extends Job {

    public void execute(JobExecutionContext context) throws JobExecutionException;

}
