package com.jf.system.conf.annotation;

import com.jf.system.conf.CacheConfig;
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
@Import({CacheConfig.class})
public @interface EnableCache {
}
