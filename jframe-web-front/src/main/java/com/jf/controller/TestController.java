package com.jf.controller;

import com.github.pagehelper.PageInfo;
import com.jf.entity.ResMsg;
import com.jf.model.User;
import com.jf.model.custom.IdText;
import com.jf.service.user.UserService;
import com.jf.sms.SMService;
import com.jf.string.StringUtil;
import com.jf.system.conf.SysConfig;
import com.jf.system.job.QuartzManager;
import com.jf.system.job.TestQuartz;
import com.jf.system.ws.SocketMessage;
import com.jf.view.PDFService;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.*;

/**
 * Created by xujunfei on 2016/12/21.
 */
@Controller
public class TestController extends BaseController {

    @Resource
    private UserService userService;
    @Resource
    private SMService smService;
    @Resource
    private PDFService pdfService;
    @Resource
    private SysConfig sysConfig;

    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private WebSocketMessageBrokerStats stats;

    @RequestMapping("/ws")
    public String ws(String name, HttpSession session) {
        if (StringUtil.isBlank(name)) {
            return "error/404";
        }
        User user = new User();
        user.setNickname(name);
        session.setAttribute("user", user);
        return "demo/ws";
    }

    @MessageMapping("/say")
    //@SendTo("/topic/test01")
    public void say(SocketMessage message, Principal principal) {
        message.setDate(new Date());
        message.setUsername(principal.getName());
        System.out.println(message.toString());
        System.out.println("principal name:" + principal.getName());
        template.convertAndSendToUser(message.getTarget(), "/chat", message);
        //return new ResMsg(0, "welcome," + message.getUsername() + " !");
    }

    @RequestMapping("/getWsInfo")
    @ResponseBody
    public void getWsInfo() {
        System.out.println(stats.getWebSocketSessionStatsInfo());
        System.out.println(stats.getSockJsTaskSchedulerStatsInfo());
    }


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

    @RequestMapping("/testChart")
    public String testChart() {
        return "demo/chart";
    }

    @RequestMapping("/chartData")
    @ResponseBody
    public List<Object> chartData() {
        /*List<KChart> list = new ArrayList<KChart>();
        KChart chart0 = new KChart("2013/1/24", 2320.26,2320.26,2287.3,2362.94);
        KChart chart1 = new KChart("2013/1/25", 2300.2,2291.3,2288.26,2308.38);
        KChart chart2 = new KChart("2013/1/30", 2360.75,2382.48,2347.89,2383.76);
        KChart chart3 = new KChart("2013/2/1", 2377.41,2419.02,2369.57,2421.15);
        KChart chart4 = new KChart("2013/2/4", 2425.92,2428.15,2417.58,2440.38);
        KChart chart5 = new KChart("2013/2/5", 2411.3,2433.13,2403.3,2437.42);
        KChart chart6 = new KChart("2013/3/1", 2364.54,2359.51,2330.86,2369.65);
        list.add(chart0);
        list.add(chart1);
        list.add(chart2);
        list.add(chart3);
        list.add(chart4);
        list.add(chart5);
        list.add(chart6);*/

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

    @RequestMapping("/sms")
    @ResponseBody
    public ResMsg sms() {
        smService.send("123456", "17730215423");
        return new ResMsg(0, "SUCCESS");
    }

    @RequestMapping("/htmltopdf")
    public @ResponseBody
    ResMsg htmltopdf() {
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
            QuartzManager.addJob(sche, "Print", TestQuartz.class, "0/5 * * * * ?");
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

}
