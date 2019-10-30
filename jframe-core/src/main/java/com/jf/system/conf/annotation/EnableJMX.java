package com.jf.system.conf.annotation;

import com.jf.system.conf.JMXConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-10-29
 * Time: 17:21
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({JMXConfig.class})
public @interface EnableJMX {
}
