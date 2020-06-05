package com.jf.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限验证 未声明则均验证
 *
 * @author rick
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthPassport {

    /**
     * true-验证登录，false-不验证登录
     */
    boolean login() default true;

    /**
     * true-验证权限，false-不验证权限
     */
    boolean right() default true;

    /**
     * 额外字段
     */
    boolean transform() default false;

}
