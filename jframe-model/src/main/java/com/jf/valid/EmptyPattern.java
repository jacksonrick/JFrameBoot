package com.jf.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * BootstrapValidator 扩展
 * <p>正则验证器<b>value可空</b></p>
 * Created by xujunfei on 2017/1/19.
 */
@Documented
@Constraint(validatedBy = {EmptyPatternValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmptyPattern {

    String message() default "格式错误";

    String regexp() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
