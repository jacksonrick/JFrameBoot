package com.jf.system.socket;

import com.jf.database.model.User;
import com.jf.system.conf.SysConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: Spring WebSocket配置
 * <p>后端:spring socket,redis session；前端:stomp.js</p>
 * User: xujunfei
 * Date: 2018-01-09
 * Time: 14:40
 */
@Configuration
@ConditionalOnProperty(name = "app.socket.enabled", havingValue = "true")
@ConditionalOnClass(WebSocketMessageBrokerConfigurer.class)
@EnableWebSocketMessageBroker // 开启使用STOMP协议来传输基于代理(message broker)的消息,此时浏览器支持使用@MessageMapping 就像支持@RequestMapping一样
public class WebSocketConfig extends AbstractSessionWebSocketMessageBrokerConfigurer {
    // 亦可以使用WebSocketMessageBrokerConfigurer

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/ws"); // 客户端发送地址的前缀
        registry.setUserDestinationPrefix("/topic"); // 客户端订阅地址的前缀
        registry.enableSimpleBroker("/chat"); // 客户端订阅地址(后缀),可以设置多个
    }

    /**
     * Stomp配置
     *
     * @param registry
     */
    @Override
    protected void configureStompEndpoints(StompEndpointRegistry registry) {
        // websocket连接地址后缀，如ws:ip/websocket
        registry.addEndpoint("/websocket").addInterceptors(new SessionAuthHandshakeInterceptor()).setHandshakeHandler(new DefaultHandshakeHandler() {
            @Override
            protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                // 定义用户从Session获取，Session名称为SysConfig.SESSION_USER
                return new SocketPrincipal((User) attributes.get(SysConfig.SESSION_USER));
            }
        });
    }

    // 注册的另一种方式，使用SockJS
    // registerStompEndpoints()
    //      registry.addEndpoint("/websocket").addInterceptors(...)..setAllowedOrigins("*").withSockJS();

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
                System.out.println("recived : " + message.getHeaders().get("simpSessionAttributes"));
                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
                // 可以从SESSION获取到用户信息
                User user = (User) accessor.getSessionAttributes().get(SysConfig.SESSION_USER);
                System.out.println("recived-user:" + user.getNickname());
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
                System.out.println("sended: " + message);
                // CONNECT_ACK表示连接，DISCONNECT_ACK连接断开
                System.out.println("simpMessageType: " + message.getHeaders().get("simpMessageType"));
                return super.preSend(message, channel);
            }
        });
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit(500 * 1024 * 1024);
        registration.setSendBufferSizeLimit(1024 * 1024 * 1024);
        registration.setSendTimeLimit(200000);
    }

    class SocketPrincipal implements Principal {

        private User user;

        public SocketPrincipal(User user) {
            this.user = user;
        }

        @Override
        public String getName() {
            return String.valueOf(user.getNickname());
        }

    }

}
