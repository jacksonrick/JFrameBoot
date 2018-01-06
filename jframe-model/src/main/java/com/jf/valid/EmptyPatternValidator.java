package com.jf.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * BootstrapValidator 扩展
 * Created by xujunfei on 2017/1/19.
 */
public class EmptyPatternValidator implements ConstraintValidator<EmptyPattern, String> {

    private String regexp;

    @Override
    public void initialize(EmptyPattern emptyPattern) {
        this.regexp = emptyPattern.regexp();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value == "") {
            return true;
        }
        if (value.matches(regexp)) {
            return true;
        }
        return false;
    }

}
