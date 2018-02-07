package com.jf.system.conf.freemarker;

import com.github.pagehelper.PageInfo;
import com.jf.page.DoTag;
import freemarker.core.Environment;
import freemarker.ext.beans.StringModel;
import freemarker.template.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * 分页标签
 * create by jfxu
 */
@Component
public class TemplateDirectivePage implements TemplateDirectiveModel {

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        if (params == null || params.size() != 2) {
            throw new TemplateException("params cann't be empty and must be 2.", env);
        }
        // 分页对象和表单ID
        PageInfo page = null;
        String form = null;
        for (Object key : params.keySet()) {
            String name = (String) key;
            if (name.equals("data")) {
                StringModel model = (StringModel) params.get(key);
                page = (PageInfo) model.getWrappedObject();
            }
            if (name.equals("form")) {
                form = ((TemplateScalarModel) params.get(key)).getAsString();
            }
        }

        // 组装分页标签
        String div = DoTag.tag(page, form);
        // 输出HTML
        Writer out = env.getOut();
        out.write(div);
        if (body != null) {
            body.render(env.getOut());
        } else {
            throw new RuntimeException("body is empty.");
        }
    }

}

