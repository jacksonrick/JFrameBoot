package com.jf.system.socket;

import com.jf.model.SocketPrincipal;
import com.jf.model.User;
import com.jf.system.IConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
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
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractSessionWebSocketMessageBrokerConfigurer {

    @Autowired
    private SessionAuthHandshakeInterceptor sessionAuthHandshakeInterceptor;
    @Autowired
    private AuthWebSocketHandlerDecoratorFactory authWebSocketHandlerDecoratorFactory;

    /**
     * 配置消息代理
     * 使用RabbitMQ做为消息代理，替换默认的Simple Broker
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/mq") // 指服务端接收地址的前缀，意思就是说客户端给服务端发消息的地址的前缀
                .setUserDestinationPrefix("/user") // subscribe前缀
                .enableStompBrokerRelay("/exchange", "/topic", "/queue", "/amq/queue") // 处理所有消息将消息发送到外部的消息代理
                .setRelayHost("dev") // mq地址
                //.setRelayPort() // stomp端口,default:61613
                .setVirtualHost("/rmq-ws") // 虚拟主机
                .setClientLogin("rabbitmq") // 用户名密码
                .setClientPasscode("12345678")
                .setSystemLogin("rabbitmq")
                .setSystemPasscode("12345678")
                .setSystemHeartbeatReceiveInterval(4000)
                .setSystemHeartbeatSendInterval(5000)
        ;
    }

    /**
     * 注册配置
     * Stomp.js
     *
     * @param registry
     */
    @Override
    protected void configureStompEndpoints(StompEndpointRegistry registry) {
        // websocket连接地址后缀，如ws:ip/websocket
        registry.addEndpoint("/websocket")
                .addInterceptors(sessionAuthHandshakeInterceptor)
                .setHandshakeHandler(new DefaultHandshakeHandler() {
                    @Override
                    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                        // 定义用户
                        // 从Session获取，Session名称为SysConfig.SESSION_USER
                        return new SocketPrincipal((User) attributes.get(IConstant.SESSION_USER));
                    }
                })
        ;
    }

    /**
     * 用于获取建立websocket时获取对应的sessionid值
     *
     * @param registration
     */
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.addDecoratorFactory(authWebSocketHandlerDecoratorFactory);
        super.configureWebSocketTransport(registration);
    }

}