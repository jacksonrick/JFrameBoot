package com.jf.controller;

import com.jf.model.SocketPrincipal;
import com.jf.model.User;
import com.jf.system.IConstant;
import com.jf.model.SocketMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
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
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-06-21
 * Time: 13:58
 */
@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private WebSocketMessageBrokerStats brokerStats;
    @Autowired
    private SimpUserRegistry simpUserRegistry;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 模拟登录
     *
     * @param id      登录用户名，这里使用用户ID
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
        session.setAttribute(IConstant.SESSION_USER, user);
        return "ws";
    }

    /**
     * 发送消息
     * spring websocket & redis session
     *
     * @param message   自定义的接收对象
     * @param principal
     */
    @MessageMapping("/send")
    // @SendoTo对应前台的订阅地址，使用messagingTemplate最为灵活 如：@SendTo("/topic/2/chat")
    public void send(SocketMessage message, Principal principal) {
        User user = ((SocketPrincipal) principal).getUser();
        message.setId(user.getId());
        message.setUsername(user.getNickname());
        message.setDate(new Date());
        System.out.println(message.toString());

        // 发送消息到目标 /topic/username/chat
        messagingTemplate.convertAndSendToUser(message.getTarget(), "/chat", message);
    }

    /**
     * 群发
     *
     * @return
     */
    @MessageMapping("/sendall")
    public void sendall() {
        SocketMessage message = new SocketMessage();
        message.setDate(new Date());
        message.setUsername("SYSTEM");
        message.setMessage("Hello Everyone!");
        Set<SimpUser> sets = simpUserRegistry.getUsers();
        for (SimpUser user : sets) {
            System.out.println(user.getName());
            messagingTemplate.convertAndSendToUser(user.getName(), "/chat", message);
        }
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
