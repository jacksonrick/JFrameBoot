package com.jf.system.aspect;

import com.jf.entity.ResMsg;
import com.jf.json.JacksonUtil;
import com.jf.system.conf.SysConfig;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description: 全局系统日志处理
 * User: xujunfei
 * Date: 2018-05-23
 * Time: 12:00
 */
@Aspect
@Component
@Order(99)
public class AspectLog {

    private final static Logger log = LoggerFactory.getLogger(AspectLog.class);

    @Resource
    private SysConfig config;

    // 针对所有controller(包含子包)
    @Pointcut("execution(public * com.jf.controller..*.*(..))")
    public void ctl() {
    }
    // 固定包
    // @Pointcut("execution(public * com.jf.controller..*.*(..))")

    // 所有controller
    // @Pointcut("execution(public * com.jf..*.*(..))&&@within(org.springframework.stereotype.Controller)")

    /**
     * 记录请求参数，客户端IP等
     *
     * @param point
     */
    @Before("ctl()")
    public void log(JoinPoint point) {
        try {
            // 记录时间
            MDC.put("time", String.valueOf(System.currentTimeMillis()));
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                log.info("Can't read request, Class: {}, Method: {}()", point.getSignature().getDeclaringTypeName(), point.getSignature().getName());
                return;
            }
            HttpServletRequest request = attributes.getRequest();

            // 请求ID
            String requestId = String.valueOf(System.nanoTime());
            MDC.put("rid", requestId);
            // 服务器名称
            MDC.put("server", config.getServerId());
            // 客户端IP
            String remote = request.getHeader("x-forwarded-for") == null ? request.getRemoteAddr() : request.getHeader("x-forwarded-for");
            // GET/POST参数
            Map<String, String[]> parameters = request.getParameterMap();
            String param = "";
            if (!parameters.isEmpty()) {
                Set<String> keys = parameters.keySet();
                for (String key : keys) {
                    String[] params = parameters.get(key);
                    if (params.length > 0) {
                        param += "|" + key + "=" + StringUtils.join(params, '&');
                    }
                }
                param += "|";
            }

            MethodSignature signature = (MethodSignature) point.getSignature();
            // 获取类和方法
            // String method = signature.getDeclaringTypeName() + "." + point.getSignature().getName();
            Annotation[][] annotation = signature.getMethod().getParameterAnnotations();
            int anno = -1;
            for (int i = 0; i < annotation.length; i++) {
                for (Annotation annotation2 : annotation[i]) {
                    if (annotation2 instanceof RequestBody) {
                        anno = i;
                        break;
                    }
                }
                if (anno != -1) {
                    break;
                }
            }
            if (anno != -1) {
                Object obj = point.getArgs()[anno];
                log.info("{} - {} {} {} {}", remote, request.getMethod(), request.getRequestURI(), param, JacksonUtil.objectToJson(obj));
            } else {
                log.info("{} - {} {} {}", remote, request.getMethod(), request.getRequestURI(), param);
            }
        } catch (Exception e) {
            log.error("参数解析错误", e);
            MDC.clear();
        }
    }

    @AfterReturning(pointcut = "ctl()", returning = "ret")
    public void ret(Object ret) {
        try {
            long time = 0;
            long timeStart = Long.parseLong(MDC.get("time"));
            long timeEnd = System.currentTimeMillis();
            time = timeEnd - timeStart;
            if (ret != null) {
                // 开发环境下打印具体信息
                if (config.dev()) {
                    log.info("Returns: {}, Time: {}", ret, time);
                } else {
                    if (ret instanceof ResMsg) {
                        log.info("Returns: {}, Msg: {}, Time: {}", ((ResMsg) ret).getCode(), ((ResMsg) ret).getMsg(), time);
                    } else if (ret instanceof ModelAndView) {
                        log.info("Returns: {}, Time: {}", ((ModelAndView) ret).getView(), time);
                    } else {
                        log.info("Returns: {}, Time: {}", ret, time);
                    }
                }
            }
        } catch (Exception e) {
            log.error("方法返回值解析错误", e);
        } finally {
            MDC.clear();
        }
    }

    /* 环绕方法
    @Around("log()")
    public Object around(ProceedingJoinPoint pjp) {
        //Signature signature = point.getSignature();
        //MethodSignature methodSignature = (MethodSignature) signature;
        //String[] strings = methodSignature.getParameterNames();
        //System.out.println(Arrays.toString(strings));


        Object[] objects = pjp.getArgs();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Annotation[][] annotations = method.getParameterAnnotations();
        for (int i = 0; i < objects.length; i++) {
            if (annotations.length > 0) {
                for (Annotation annotation : annotations[i]) {
                    if (annotation.annotationType() == Token.class) {
                        objects[i] = "1234";
                    }
                }
            }
        }

        Object result = null;
        try {
            result = pjp.proceed(objects);
        } catch (Throwable throwable) {
            throw new SysException(e.getMessage(), e);
        }
        return result;
    }*/
}