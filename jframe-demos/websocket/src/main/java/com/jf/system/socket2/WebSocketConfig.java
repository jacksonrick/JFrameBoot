package com.jf.system.socket2;

import com.jf.model.SocketPrincipal;
import com.jf.model.User;
import com.jf.system.IConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: Spring WebSocket配置 【独立服务版本】
 * User: xujunfei
 * Date: 2018-01-09
 * Time: 14:40
 */
//@Configuration
//@EnableWebSocketMessageBroker // 开启使用STOMP协议来传输基于代理(message broker)的消息,此时浏览器支持使用@MessageMapping 就像支持@RequestMapping一样
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    // 亦可以使用WebSocketMessageBrokerConfigurer

    private static Logger log = LoggerFactory.getLogger(WebSocketConfig.class);

    /**
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        ThreadPoolTaskScheduler tp = new ThreadPoolTaskScheduler();
        tp.setPoolSize(1);
        tp.setThreadNamePrefix("ws-heartbeat-thread-");
        tp.initialize();
        registry.setApplicationDestinationPrefixes("/ws") // 客户端发送地址的前缀，如ws/send
                .setUserDestinationPrefix("/topic") // 客户端订阅地址的前缀 subscribe("/topic/chat")
                .enableSimpleBroker("/chat") // 客户端订阅地址(后缀),可以设置多个 "/chat1,/chat2"
                .setHeartbeatValue(new long[]{60000, 60000}).setTaskScheduler(tp) // 心跳60s default10s
        ;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket")
                .setAllowedOrigins("*")
        ;
    }

    /**
     * 收到消息
     *
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(new ChannelInterceptorAdapter() {
            /**
             * @param message Message对象
             * @param channel
             * @return
             */
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                MessageHeaders headers = message.getHeaders();

                LinkedMultiValueMap raw = (LinkedMultiValueMap) headers.get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
                if (raw != null) {
                    List list = raw.get(IConstant.UID);
                    if (list != null && list.size() > 0) {
                        Object uid = list.get(0);
                        log.info("###### Login UID: " + uid);
                        String uidStr = String.valueOf(uid);
                        if ("null".equals(uidStr)) {
                            throw new MessagingException("Token异常");
                        }
                        List names = raw.get(IConstant.UNAME);
                        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                        accessor.setUser(new SocketPrincipal(new User(uidStr, String.valueOf(names.get(0)))));
                    }
                }
                return super.preSend(message, channel);
            }

            /**
             * @param message
             * @param channel
             * @param sent
             */
            @Override
            public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
                super.postSend(message, channel, sent);
            }

            // 还有其他重写方法
        });
    }

    /**
     * 发送消息
     *
     * @param registration
     */
    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(new ChannelInterceptorAdapter() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                // CONNECT_ACK表示连接，DISCONNECT_ACK连接断开
                log.info("MessageType:" + message.getHeaders().get("simpMessageType") + ", message: " + message);
                return super.preSend(message, channel);
            }
        });
    }

    /**
     * @param registration
     */
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration
                .setMessageSizeLimit(500 * 1024 * 1024)
                .setSendBufferSizeLimit(1024 * 1024 * 1024)
                .setSendTimeLimit(200000)
        ;
    }

}
