package com.jf.database.model;

import java.math.BigInteger;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-07-06
 * Time: 11:29
 */
public class Jobs {

    private String JOB_NAME;
    private String JOB_GROUP;
    private String JOB_CLASS_NAME;
    private String TRIGGER_NAME;
    private String TRIGGER_GROUP;
    private String TRIGGER_STATE;
    private BigInteger REPEAT_INTERVAL;
    private BigInteger TIMES_TRIGGERED;
    private String CRON_EXPRESSION;
    private String TIME_ZONE_ID;

    public String getJOB_NAME() {
        return JOB_NAME;
    }

    public void setJOB_NAME(String JOB_NAME) {
        this.JOB_NAME = JOB_NAME;
    }

    public String getJOB_GROUP() {
        return JOB_GROUP;
    }

    public void setJOB_GROUP(String JOB_GROUP) {
        this.JOB_GROUP = JOB_GROUP;
    }

    public String getJOB_CLASS_NAME() {
        return JOB_CLASS_NAME;
    }

    public void setJOB_CLASS_NAME(String JOB_CLASS_NAME) {
        this.JOB_CLASS_NAME = JOB_CLASS_NAME;
    }

    public String getTRIGGER_NAME() {
        return TRIGGER_NAME;
    }

    public void setTRIGGER_NAME(String TRIGGER_NAME) {
        this.TRIGGER_NAME = TRIGGER_NAME;
    }

    public String getTRIGGER_GROUP() {
        return TRIGGER_GROUP;
    }

    public void setTRIGGER_GROUP(String TRIGGER_GROUP) {
        this.TRIGGER_GROUP = TRIGGER_GROUP;
    }

    public String getTRIGGER_STATE() {
        return TRIGGER_STATE;
    }

    public void setTRIGGER_STATE(String TRIGGER_STATE) {
        this.TRIGGER_STATE = TRIGGER_STATE;
    }

    public BigInteger getREPEAT_INTERVAL() {
        return REPEAT_INTERVAL;
    }

    public void setREPEAT_INTERVAL(BigInteger REPEAT_INTERVAL) {
        this.REPEAT_INTERVAL = REPEAT_INTERVAL;
    }

    public BigInteger getTIMES_TRIGGERED() {
        return TIMES_TRIGGERED;
    }

    public void setTIMES_TRIGGERED(BigInteger TIMES_TRIGGERED) {
        this.TIMES_TRIGGERED = TIMES_TRIGGERED;
    }

    public String getCRON_EXPRESSION() {
        return CRON_EXPRESSION;
    }

    public void setCRON_EXPRESSION(String CRON_EXPRESSION) {
        this.CRON_EXPRESSION = CRON_EXPRESSION;
    }

    public String getTIME_ZONE_ID() {
        return TIME_ZONE_ID;
    }

    public void setTIME_ZONE_ID(String TIME_ZONE_ID) {
        this.TIME_ZONE_ID = TIME_ZONE_ID;
    }

    @Override
    public String toString() {
        return "JobAndTrigger{" +
                "JOB_NAME='" + JOB_NAME + '\'' +
                ", JOB_GROUP='" + JOB_GROUP + '\'' +
                ", JOB_CLASS_NAME='" + JOB_CLASS_NAME + '\'' +
                ", TRIGGER_NAME='" + TRIGGER_NAME + '\'' +
                ", TRIGGER_GROUP='" + TRIGGER_GROUP + '\'' +
                ", TRIGGER_STATE='" + TRIGGER_STATE + '\'' +
                ", REPEAT_INTERVAL=" + REPEAT_INTERVAL +
                ", TIMES_TRIGGERED=" + TIMES_TRIGGERED +
                ", CRON_EXPRESSION='" + CRON_EXPRESSION + '\'' +
                ", TIME_ZONE_ID='" + TIME_ZONE_ID + '\'' +
                '}';
    }
}
