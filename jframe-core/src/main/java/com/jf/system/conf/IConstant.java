package com.jf.system.conf;

/**
 * Created with IntelliJ IDEA.
 * Description: 系统静态常量
 * User: xujunfei
 * Date: 2019-03-07
 * Time: 17:53
 */
public class IConstant {

    // redis token prefix
    public final static String TOKEN_PREFIX = "token:";
    public final static String TOKEN_UID_PREFIX = TOKEN_PREFIX + "uid:";
    // exclude path
    public final static String[] excludePathPatterns = {"/static/**", "/images/**", "/js/**", "/css/**", "/error/**"};

    // session name
    public final static String SESSION_USER = "user";
    public final static String SESSION_ADMIN = "admin";
    public final static String SESSION_RAND = "rand";
    public final static String SESSION_SMS = "sms";

    // token name
    public final static String TOKEN = "token";
    public final static String TOKEN_HEADER = "header";
    public final static String TOKEN_COOKIE = "cookie";
    public final static String TOKEN_PARAM = "param";

    // rabbitmq topic
    public final static String QUEUE_MSGA = "TOPIC.MSG.A";
    public final static String QUEUE_MSGB = "TOPIC.MSG.B";
    public final static String QUEUE_MSGS = "TOPIC.MSGS";
    public final static String QUEUE_DELAY = "QUEUE_DELAY";
    public final static String MY_EXCHANGE = "MY_EXCHANGE";
    public final static String DELAY_EXCHANGE = "DELAY_EXCHANGE";
    public final static String DELAY_ROUTING_KEY = "DELAY_ROUTING_KEY";

}
