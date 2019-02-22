package com.jf.annotation;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-05-24
 * Time: 17:14
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Except {

    /**
     * 是否是Error日志
     */
    boolean error() default true;

    /**
     * 是否打印详细日志
     */
    boolean stack() default true;

}
