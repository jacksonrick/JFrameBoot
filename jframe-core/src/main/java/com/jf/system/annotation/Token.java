package com.jf.system.annotation;

import com.jf.system.conf.SysConfig;

import java.lang.annotation.*;

/**
 * Created by xujunfei on 2017/8/1.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Token {

    boolean need() default true;

    String name() default SysConfig.TOKEN;

    String type() default SysConfig.TOKEN_HEADER;

}
