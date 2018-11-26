package com.jf.controller;

import com.jf.common.BaseController;
import com.jf.database.model.User;
import com.jf.system.conf.SysConfig;
import com.jf.system.socket.SocketMessage;
import com.jf.system.socket.WebSocketConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-06-21
 * Time: 13:58
 */
@Controller
public class WebSocketController extends BaseController {

    @Autowired(required = false)
    private SimpMessagingTemplate messagingTemplate;
    @Autowired(required = false)
    private WebSocketMessageBrokerStats brokerStats;
    @Autowired(required = false)
    private SimpUserRegistry simpUserRegistry;

    @RequestMapping("/wst")
    @ResponseBody
    public String wst(HttpSession session) {
        User user = new User(1);
        user.setNickname("USER" + 1);
        session.setAttribute("A", user);
        return "OK";
    }

    /**
     * 模拟登录
     *
     * @param name    登录用户名
     * @param session Redis Session
     * @return
     */
    @RequestMapping("/ws")
    public String ws(Integer id, HttpSession session) {
        if (id == null) {
            System.out.println("id is empty!");
            return "error/400";
        }
        User user = new User(id);
        user.setNickname("USER" + id);
        session.setAttribute(SysConfig.SESSION_USER, user);
        return "demo/ws";
    }

    /**
     * 发送消息
     * spring websocket & redis session
     *
     * @param message   自定义的接收对象
     * @param principal
     */
    @MessageMapping("/send")
    // @SendoTo对应前台的订阅地址，使用messagingTemplate最为灵活
    public void send(SocketMessage message, Principal principal) {
        User user = ((WebSocketConfig.SocketPrincipal) principal).getUser();
        message.setId(user.getId());
        message.setUsername(user.getNickname());
        message.setDate(new Date());
        System.out.println(message.toString());

        // 发送消息到目标 /topic/username/chat
        messagingTemplate.convertAndSendToUser(message.getTarget(), "/chat", message);
    }

    /**
     * 获取Socket统计信息
     *
     * @return
     */
    @RequestMapping("/getWsInfo")
    @ResponseBody
    public Object getWsInfo() {
        Map<String, Object> map = new HashMap<String, Object>();
        //
        map.put("sessionIngo", brokerStats.getWebSocketSessionStatsInfo());
        //
        map.put("statInfo", brokerStats.getSockJsTaskSchedulerStatsInfo());
        // 在线人数
        map.put("online", simpUserRegistry.getUserCount());
        return map;
    }

}
