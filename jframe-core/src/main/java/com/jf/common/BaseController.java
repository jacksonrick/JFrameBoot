package com.jf.common;

import com.jf.string.StringUtil;
import com.jf.system.conf.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Base Controller
 *
 * @author rick
 * @version 2.0
 */
public class BaseController {

    @Autowired(required = false)
    private RedisTemplate redisTemplate;

    /**
     * 绑定token
     *
     * @param userId
     */
    public String bindToken(Integer userId) {
        String newToken = StringUtil.getTokenId();
        String oldToken = (String) redisTemplate.opsForValue().getAndSet(SysConfig.PREFIX + userId, newToken);
        if (oldToken != null) {
            redisTemplate.delete(oldToken); // 删除旧token
        }
        redisTemplate.opsForValue().set(newToken, userId); // 绑定用户唯一token
        return newToken;
    }

    /**
     * 从SESSION获取登录信息
     *
     * @param request
     * @param sessionName
     * @return
     */
    protected <T> T getSession(HttpServletRequest request, String sessionName) {
        Object object = WebUtils.getSessionAttribute(request, sessionName);
        if (object != null) {
            return (T) object;
        } else {
            return null;
        }
    }

}
