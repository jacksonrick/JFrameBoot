package com.jf.controller;

import com.github.pagehelper.PageInfo;
import com.jf.entity.ResMsg;
import com.jf.model.User;
import com.jf.model.custom.IdText;
import com.jf.restapi.OrderRestService;
import com.jf.service.user.UserService;
import com.jf.string.StringUtil;
import com.jf.system.conf.SysConfig;
import com.jf.system.schedule.QuartzManager;
import com.jf.system.schedule.jobs.BaseJob;
import com.jf.system.schedule.jobs.Job1;
import com.jf.system.service.EmailService;
import com.jf.system.service.PDFService;
import com.jf.system.service.SMService;
import com.jf.system.socket.SocketMessage;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.*;

/**
 * 测试DEMO
 * <p>
 * 服务消费
 * SpringCloud分布式配置
 * 多数据源
 * 分布式事务
 * websocket
 * redis session
 * cache
 * activemq
 * email + sms
 * js demo
 * <p>
 * Created by xujunfei on 2016/12/21.
 */
@Controller
@RefreshScope
public class TestController extends BaseController {

    @Resource
    private UserService userService;
    @Resource
    private SMService smService;
    @Resource
    private EmailService emailService;
    @Resource
    private PDFService pdfService;
    @Resource
    private SysConfig sysConfig;

    @Resource
    private OrderRestService orderRestService;

    @Autowired(required = false)
    @Qualifier("Scheduler")
    private Scheduler scheduler;

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
     * 测试服务消费
     *
     * @return
     */
    @RequestMapping("/order")
    @ResponseBody
    public String order() {
        return orderRestService.order(10000l, 100l);
    }

    @RequestMapping("/testRollback")
    @ResponseBody
    public ResMsg testRollback(String source) {
        if ("master".equals(source)) {
            userService.testRollbackA();
        }
        if ("cluster".equals(source)) {
            userService.testRollbackB();
        }
        return new ResMsg(0, "");
    }

    @RequestMapping("/testMutilSource")
    @ResponseBody
    public ResMsg testMutilSource(String source) {
        if (StringUtil.isBlank(source)) {
            return new ResMsg(-1, "source is empty");
        }
        User user = userService.testMutilSource(source);
        return new ResMsg(0, SUCCESS, user);
    }

    @Autowired(required = false)
    private SimpMessagingTemplate messagingTemplate;
    @Autowired(required = false)
    private WebSocketMessageBrokerStats brokerStats;

    @RequestMapping("/ws")
    public String ws(String name, HttpSession session) {
        if (StringUtil.isBlank(name)) {
            System.out.println("name is empty!");
            return "error/500";
        }
        User user = new User();
        user.setNickname(name);
        session.setAttribute("user", user);
        return "demo/ws";
    }

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


    @GetMapping("/testSetSession")
    @ResponseBody
    public ResMsg testSetSession(HttpSession session) {
        session.setAttribute("name", "xujunfei");
        return new ResMsg(0, SUCCESS);
    }

    @GetMapping("/testGetSession")
    @ResponseBody
    public ResMsg testGetSession(HttpSession session) {
        return new ResMsg(0, SUCCESS, session.getAttribute("name"));
    }

    @GetMapping("/testLock")
    @ResponseBody
    public ResMsg testLock() {
        //userService.testLock(10000l);
        return new ResMsg(0, SUCCESS);
    }

    @GetMapping("/testCache1")
    @ResponseBody
    public ResMsg testCache1() {
        User user = userService.findUserById(10000l);
        return new ResMsg(0, SUCCESS, user);
    }

    @GetMapping("/testCache2")
    @ResponseBody
    public ResMsg testCache2() {
        User user = new User(10000l);
        user.setMoney(1000d);
        userService.updateUser(user);
        return new ResMsg(0, SUCCESS);
    }


    ////////////////////////

    @RequestMapping("/demo/{path}")
    public String demo(@PathVariable("path") String path) {
        return "demo/" + path;
    }

    @RequestMapping("/login")
    @ResponseBody
    public ResMsg login(String account) {
        return new ResMsg(0, SUCCESS, account);
    }

    @RequestMapping("/users")
    @ResponseBody
    public PageInfo users(Integer pageNo, String pageSort, String keywords) {
        User condition = new User();
        condition.setKeywords(keywords);
        condition.setPageNo(pageNo);
        if (StringUtil.isBlank(pageSort)) {
            condition.setPageSort("id DESC");
        } else {
            condition.setPageSort(pageSort);
        }
        return userService.findUserByPage(condition);
    }

    @RequestMapping("/findUsers")
    @ResponseBody
    public List<IdText> findUsers(String phone) {
        return userService.findUserLikePhone(phone);
    }

    @RequestMapping("/chartData")
    @ResponseBody
    public List<Object> chartData() {
        List<Object> list = new ArrayList<Object>();
        Object[] obj = new Object[]{"2013/1/24", 2320.26, 2320.26, 2287.3, 2362.94};
        Object[] obj1 = new Object[]{"2013/1/25", 2300.2, 2291.3, 2288.26, 2308.38};
        Object[] obj2 = new Object[]{"2013/1/30", 2360.75, 2382.48, 2347.89, 2383.76};
        Object[] obj3 = new Object[]{"2013/2/1", 2377.41, 2419.02, 2369.57, 2421.15};
        Object[] obj4 = new Object[]{"2013/2/4", 2425.92, 2428.15, 2417.58, 2440.38};
        Object[] obj5 = new Object[]{"2013/2/5", 2411.3, 2433.13, 2403.3, 2437.42};
        Object[] obj6 = new Object[]{"2013/3/1", 2364.54, 2359.51, 2330.86, 2369.65};
        list.add(obj);
        list.add(obj1);
        list.add(obj2);
        list.add(obj3);
        list.add(obj4);
        list.add(obj5);
        list.add(obj6);
        return list;
    }

    @GetMapping("/testSms")
    @ResponseBody
    public ResMsg testSms() {
        smService.send("1", "17730215423");
        smService.send("2", "17730215423");
        smService.send("3", "17730215423");
        smService.send("4", "17730215423");
        smService.send("5", "17730215423");
        return new ResMsg(0, SUCCESS);
    }

    @GetMapping("/testEmail")
    @ResponseBody
    public ResMsg testEmail() {
        emailService.sendTemplateMail(null, "80222@qq.com", "123", "email_register");
        return new ResMsg(0, SUCCESS);
    }

    @RequestMapping("/htmltopdf")
    @ResponseBody
    public ResMsg htmltopdf() {
        try {
            // 导入数据
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", "xujunfei");
            String str = pdfService.pdfCreate("pdf_user", map, sysConfig.getStaticPath());
            if ("error".equals(str)) {
                return new ResMsg(0, "转换失败");
            }
            return new ResMsg(1, str);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResMsg(0, "error");
        }
    }

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

    public static BaseJob getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (BaseJob) class1.newInstance();
    }

}
