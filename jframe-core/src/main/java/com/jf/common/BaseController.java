package com.jf.common;

import com.jf.database.model.Admin;
import com.jf.database.model.User;
import com.jf.string.StringUtil;
import com.jf.system.conf.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller Bases
 * token & user & constant
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
    public String bindToken(Long userId) {
        String newToken = StringUtil.getTokenId();
        String oldToken = (String) redisTemplate.opsForValue().getAndSet(SysConfig.PREFIX + userId, newToken);
        if (oldToken != null) {
            redisTemplate.delete(oldToken); // 删除旧token
        }
        redisTemplate.opsForValue().set(newToken, userId + ""); // 绑定用户唯一token
        return newToken;
    }

    /**
     * 从SESSION获取当前管理员信息
     *
     * @param request
     * @return
     */
    protected Admin getAdmin(HttpServletRequest request) {
        Object object = WebUtils.getSessionAttribute(request, SysConfig.SESSION_ADMIN);
        if (object != null) {
            return (Admin) object;
        } else {
            return null;
        }
    }

    /**
     * 获取当前用户
     *
     * @param request
     * @return
     */
    protected User getUser(HttpServletRequest request) {
        Object object = WebUtils.getSessionAttribute(request, SysConfig.SESSION_USER);
        if (object != null) {
            return (User) object;
        } else {
            return null;
        }
    }

}
