package com.jf.system.freemarker;

import com.jf.string.JFunction;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

import java.util.List;

/**
 * 格式化标签
 * create by jfxu
 */
public class TemplateMethod implements TemplateMethodModel {

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        String key = arguments.get(0).toString();
        String value = arguments.get(1).toString();
        if (key == null || "".equals(key)) {
            return "key is null";
        }
        if (value == null || "".equals(value)) {
            return "value is null";
        }

        if ("phone".equals(key)) { // 手机号格式化
            return JFunction.formatPhone(value);
        } else if ("idcard".equals(key)) { // 身份证号码
            return JFunction.formatIdcard(value);
        } else {
            return "doesn't support this key.";
        }
        // 更多详见com.jf.string.JFunction
    }
}
