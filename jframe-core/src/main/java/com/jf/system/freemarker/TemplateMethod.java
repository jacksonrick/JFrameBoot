package com.jf.system.freemarker;

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
            if (value.length() == 11) {
                return value.substring(0, 3) + "****" + value.substring(7, 11);
            }
            return value.substring(0, 3) + "********";
        } else if ("idcard".equals(key)) { // 身份证号码
            if (value.length() == 18) {
                return value.substring(0, 6) + "********" + value.substring(14, 18);
            }
            if (value.length() == 15) {
                return value.substring(0, 6) + "*****" + value.substring(11, 15);
            }
            return value.substring(0, 6) + "*********";
        } else {
            return "doesn't support this key.";
        }
        // 更多详见com.jf.string.JFunction
    }
}
