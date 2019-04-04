package com.jf.common;

import com.jf.database.mapper.TokenMapper;
import com.jf.database.model.Token;
import com.jf.date.DateUtil;
import com.jf.string.StringUtil;
import com.jf.system.conf.IConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Base Controller
 *
 * @author rick
 * @version 2.0
 */
public class BaseController {

    @Autowired(required = false)
    private RedisTemplate redisTemplate;
    @Autowired
    private TokenMapper tokenMapper;

    /**
     * 绑定token [redis]
     *
     * @param userId
     */
    public String bindToken(Integer userId) {
        String newToken = StringUtil.getTokenId();
        String oldToken = (String) redisTemplate.opsForValue().getAndSet(IConstant.TOKEN_UID_PREFIX + userId, newToken);
        if (oldToken != null) {
            redisTemplate.delete(IConstant.TOKEN_PREFIX + oldToken); // 删除旧token
        }
        // 绑定用户唯一token 7天过期
        redisTemplate.opsForValue().set(IConstant.TOKEN_PREFIX + newToken, userId, 7, TimeUnit.DAYS);
        return newToken;
    }

    /**
     * 绑定token [db]
     *
     * @param userId
     * @return
     */
    public String bindTokenToDb(Integer userId) {
        String newToken = StringUtil.getTokenId();
        Token oldToken = tokenMapper.findByUid(String.valueOf(userId));
        if (oldToken == null) {
            Token tk = new Token(String.valueOf(userId), newToken, DateUtil.dateAddDay(new Date(), 7));
            if (tokenMapper.insert(tk) > 0) {
                return newToken;
            } else {
                throw new RuntimeException("插入Token失败");
            }
        } else {
            oldToken.setToken(newToken);
            oldToken.setExpired(DateUtil.dateAddDay(new Date(), 7));
            if (tokenMapper.update(oldToken) > 0) {
                return newToken;
            } else {
                throw new RuntimeException("更新Token失败");
            }
        }
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
