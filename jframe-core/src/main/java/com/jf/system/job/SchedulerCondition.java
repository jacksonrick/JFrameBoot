package com.jf.system.job;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Created with IntelliJ IDEA.
 * Description: Scheduler Quartz Enable/Disable
 * User: xujunfei
 * Date: 2018-02-10
 * Time: 15:54
 */
public class SchedulerCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return Boolean.valueOf(context.getEnvironment().getProperty("app.scheduler.enabled"));
    }

}
