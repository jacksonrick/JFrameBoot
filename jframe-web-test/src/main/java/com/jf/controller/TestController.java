package com.jf.controller;

import com.jf.entity.ResMsg;
import com.jf.model.User;
import com.jf.restapi.OrderRestService;
import com.jf.service.user.UserService;
import com.jf.string.StringUtil;
import com.jf.system.conf.LogManager;
import com.jf.system.jms.activemq.MQService;
import com.jf.system.schedule.QuartzManager;
import com.jf.system.schedule.jobs.Job1;
import com.jf.system.socket.SocketMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description: 服务测试
 * User: xujunfei
 * Date: 2018-03-14
 * Time: 11:35
 */
@Controller
@RefreshScope // SpringCloud Config Refresh
public class TestController extends BaseController {

    @Resource
    private UserService userService;

    @Resource
    private OrderRestService orderRestService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private Scheduler scheduler;

    @Autowired(required = false)
    private MQService producer;

    @Autowired(required = false)
    private SimpMessagingTemplate messagingTemplate;
    @Autowired(required = false)
    private WebSocketMessageBrokerStats brokerStats;

    @Value("${s1:0}")
    public String a;

    /**
     * ↑↓ SpringCloud Config
     *
     * @return
     */
    @RequestMapping("/test")
    @ResponseBody
    public ResMsg test() {
        return new ResMsg(0, SUCCESS, a);
    }

    /**
     * 测试服务消费-Feign
     *
     * @return
     */
    @RequestMapping("/order")
    @ResponseBody
    public String order() {
        return orderRestService.order(10000l, 100l);
    }

    @RequestMapping("/testError")
    @ResponseBody
    public ResMsg testError() {
        System.out.println(1 / 0);
        return new ResMsg(0, SUCCESS);
    }

    @RequestMapping("/testLog")
    @ResponseBody
    public ResMsg testLog() {
        LogManager.info("1");
        LogManager.info("2");
        LogManager.info("3");
        LogManager.info("4");
        LogManager.error("11");
        LogManager.error("22");
        LogManager.error("33");
        return new ResMsg(0, SUCCESS);
    }

    /**
     * mysql cluster
     *
     * @return
     */
    @RequestMapping("/testMysqlCluster")
    @ResponseBody
    public ResMsg testMysqlCluster() {
        return new ResMsg(0, SUCCESS, userService.testMysqlCluster());
    }

    /**
     * 多数据源回滚
     *
     * @param source
     * @return
     */
    @RequestMapping("/testRollback")
    @ResponseBody
    public ResMsg testRollback(String source) {
        if ("db1".equals(source)) {
            userService.testRollbackA();
        }
        if ("db2".equals(source)) {
            userService.testRollbackB();
        }
        return new ResMsg(0, "");
    }

    /**
     * 多数据源
     *
     * @param source
     * @return
     */
    @RequestMapping("/testMutilSource")
    @ResponseBody
    public ResMsg testMutilSource(String source) {
        if (StringUtil.isBlank(source)) {
            return new ResMsg(-1, "source is empty");
        }
        User user = userService.testMutilSource(source);
        return new ResMsg(0, SUCCESS, user);
    }

    @RequestMapping("/ws")
    public String ws(String name, HttpSession session) {
        if (StringUtil.isBlank(name)) {
            System.out.println("name is empty!");
            return "error/400";
        }
        User user = new User();
        user.setNickname(name);
        session.setAttribute("user", user);
        return "demo/ws";
    }

    /**
     * spring websocket & redis session
     *
     * @param message
     * @param principal
     */
    @MessageMapping("/say")
    @SendTo("/topic/test01")
    public void say(SocketMessage message, Principal principal) {
        message.setDate(new Date());
        message.setUsername(principal.getName());
        System.out.println(message.toString());
        System.out.println("principal name:" + principal.getName());
        messagingTemplate.convertAndSendToUser(message.getTarget(), "/chat", message);
        //return new ResMsg(0, "welcome," + message.getUsername() + " !");
    }

    @RequestMapping("/getWsInfo")
    @ResponseBody
    public void getWsInfo() {
        System.out.println(brokerStats.getWebSocketSessionStatsInfo());
        System.out.println(brokerStats.getSockJsTaskSchedulerStatsInfo());
    }


    /**
     * session redis
     *
     * @param session
     * @return
     */
    @RequestMapping("/testSetSession")
    @ResponseBody
    public ResMsg testSetSession(HttpSession session) {
        session.setAttribute("name", "xujunfei");
        return new ResMsg(0, SUCCESS);
    }

    @RequestMapping("/testGetSession")
    @ResponseBody
    public ResMsg testGetSession(HttpSession session) {
        return new ResMsg(0, SUCCESS, session.getAttribute("name"));
    }

    /**
     * 分布式锁
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/testLock")
    @ResponseBody
    public ResMsg testLock() throws Exception {
        userService.testLock(10000l);
        return new ResMsg(0, SUCCESS);
    }

    /**
     * 测试Spring缓存
     *
     * @return
     */
    @RequestMapping("/testCache1")
    @ResponseBody
    public ResMsg testCache1() {
        User user = userService.findUserById(10000l);
        return new ResMsg(0, SUCCESS, user);
    }

    @RequestMapping("/testCache2")
    @ResponseBody
    public ResMsg testCache2() {
        User user = new User(10000l);
        user.setMoney(1000d);
        userService.updateUser(user);
        return new ResMsg(0, SUCCESS);
    }

    /**
     * QuartzManager
     *
     * @return
     */
    @RequestMapping("/startQ")
    @ResponseBody
    public ResMsg startQ() {
        SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
        try {
            Scheduler sche = gSchedulerFactory.getScheduler();
            QuartzManager.addJob(sche, "Print", Job1.class, "0/5 * * * * ?");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return new ResMsg(0, SUCCESS);
    }

    @RequestMapping("/stopQ")
    @ResponseBody
    public ResMsg stopQ() {
        SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
        try {
            Scheduler sche = gSchedulerFactory.getScheduler();
            QuartzManager.removeJob(sche, "Print");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return new ResMsg(0, SUCCESS);
    }

    /**
     * Quartz 添加任务
     *
     * @param className      com.jf.system.schedule.jobs.Job1
     * @param groupName      g1
     * @param cronExpression 0/5 * * * * ?
     * @return
     */
    @RequestMapping("/addJob")
    @ResponseBody
    public ResMsg addJob(String className, String groupName, String cronExpression) {
        try {
            // 启动调度器
            scheduler.start();
            //构建job信息
            JobDetail jobDetail = JobBuilder.newJob(getClass(className).getClass()).withIdentity(className, groupName).build();
            //表达式调度构建器(即任务执行的时间)
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(className, groupName).withSchedule(scheduleBuilder).build();

            Date date = scheduler.scheduleJob(jobDetail, trigger);
            return new ResMsg(0, SUCCESS, date);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResMsg(-1, FAIL);
        }
    }

    @RequestMapping("/delJob")
    @ResponseBody
    public ResMsg delJob(String className, String groupName) {
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(className, groupName));
            scheduler.unscheduleJob(TriggerKey.triggerKey(className, groupName));
            scheduler.deleteJob(JobKey.jobKey(className, groupName));
            return new ResMsg(0, SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResMsg(-1, FAIL);
        }
    }

    public static Job getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (Job) class1.newInstance();
    }

    /**
     * activemq send
     */
    @RequestMapping("/mq_send")
    @ResponseBody
    public ResMsg mq_send() {
        Destination destination = new ActiveMQQueue("test.queue");
        User user = new User(1000l);
        user.setNickname("hahha");
        producer.sendObjectMessage(destination, user);
        return new ResMsg(0, SUCCESS);
    }

    /**
     * spring redis template
     *
     * @return
     */
    @RequestMapping("/redisTemplate")
    @ResponseBody
    public ResMsg redisTemplate() {
        redisTemplate.opsForValue().set("name", "xujunfei");
        System.out.println((String) redisTemplate.opsForValue().get("name"));
        //redisTemplate.delete("name");
        //String res = (String) redisTemplate.opsForValue().getAndSet("name", "hahahahah");
        return new ResMsg(0, SUCCESS);
    }

}
