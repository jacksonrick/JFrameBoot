package com.jf.controller;

import com.jf.common.BaseController;
import com.jf.database.model.User;
import com.jf.string.StringUtil;
import com.jf.system.socket.SocketMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-06-21
 * Time: 13:58
 */
@Controller
public class WebSocketController extends BaseController {

    // spring websocket
    @Autowired(required = false)
    private SimpMessagingTemplate messagingTemplate;
    @Autowired(required = false)
    private WebSocketMessageBrokerStats brokerStats;

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

}
