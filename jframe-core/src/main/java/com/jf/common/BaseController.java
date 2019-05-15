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
