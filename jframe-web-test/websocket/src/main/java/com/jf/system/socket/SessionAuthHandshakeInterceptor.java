package com.jf.system.socket;

import com.jf.system.IConstant;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 定义handshake握手拦截器
 * User: xujunfei
 * Date: 2018-01-10
 * Time: 15:18
 */
public class SessionAuthHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler, Map<String, Object> map) throws Exception {
        HttpSession session = getSession(request);
        if (session == null || session.getAttribute(IConstant.SESSION_USER) == null) {
            System.out.println("【WebSocket】用户未登录");
            return false;
        }
        System.out.println("【WebSocket】握手开始");
        map.put(IConstant.SESSION_USER, session.getAttribute(IConstant.SESSION_USER));
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler, Exception e) {
        System.out.println("【WebSocket】握手结束");
    }

    private HttpSession getSession(ServerHttpRequest request) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
            return serverRequest.getServletRequest().getSession(false);
        }
        return null;
    }
}
