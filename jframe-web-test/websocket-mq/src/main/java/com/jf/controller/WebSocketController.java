package com.jf.controller;

import com.alibaba.fastjson.JSON;
import com.jf.model.SocketMessage;
import com.jf.model.User;
import com.jf.system.IConstant;
import com.jf.system.socket.RedisSessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;

import javax.servlet.http.HttpSession;
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
public class WebSocketController {

    private static final Logger log = LoggerFactory.getLogger(WebSocketController.class);

    //@Autowired
    //private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private RedisSessionService redisSessionService;

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
     * 向执行用户发送请求
     *
     * @param name
     * @param msg
     * @return
     */
    @RequestMapping(value = "/send")
    @ResponseBody
    public String send(String name, String msg, HttpSession session) {
        // 根据用户名称获取用户对应的session id值
        String wsSessionId = redisSessionService.get(name);
        // 组装消息对象
        SocketMessage message = new SocketMessage();
        message.setDate(new Date());
        message.setMessage(msg);
        User user = (User) session.getAttribute(IConstant.SESSION_USER);
        message.setUsername(user.getNickname());

        // 生成路由键值，生成规则如下: websocket订阅的目的地 + "-user" + websocket的sessionId值
        String routingKey = getTopicRoutingKey("demo", wsSessionId);
        // 向amq.topic交换机发送消息，路由键为routingKey
        log.info("向用户[{}]sessionId=[{}]，发送消息[{}]，路由键[{}]", name, wsSessionId, wsSessionId, routingKey);
        amqpTemplate.convertAndSend("amq.topic", routingKey, JSON.toJSONString(message));
        return "发送成功";
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

    /**
     * 获取Topic的生成的路由键
     *
     * @param actualDestination
     * @param sessionId
     * @return
     */
    private String getTopicRoutingKey(String actualDestination, String sessionId) {
        return actualDestination + "-user" + sessionId;
    }

}
