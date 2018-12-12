package com.jf.annotation.excel;

import com.jf.poi.AbstractCellRender;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-06-27
 * Time: 09:30
 */
@Documented
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Fields {

    String value();

    FieldType type() default FieldType.STRING;

    TypeValue[] typeValues() default @TypeValue(name = "", value = "");

    Class<? extends AbstractCellRender> render() default AbstractCellRender.None.class;

}
