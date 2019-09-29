package com.jf.system.async;

import freemarker.template.Template;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.Arrays;
import java.util.Map;

/**
 * 短信平台
 *
 * @author rick
 * @version 1.1
 */
@Component
public class SMService {

    private static Log log = LogFactory.getLog(SMService.class);

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    private String getText(Map<String, String> root, String templateName) {
        String text = "";
        try {
            // 通过指定模板名获取FreeMarker模板实例
            Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate("sms/" + templateName + ".ftl");
            text = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, root);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }

    /**
     * 通过模板发送
     *
     * @param root         数据
     * @param templateName 模板名称
     * @param phones       手机号码
     */
    @Async
    public void sendSms(Map<String, String> root, String templateName, String... phones) {
        String content = getText(root, templateName);
        if (!"".equals(content)) {
            try {
                int i = 0;
                if (i == 0) {
                    log.info(Arrays.toString(phones) + ":" + content);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 普通发送短信【阿里云】
     *
     * @param content
     * @param phone
     */
    @Async
    public void send(String content, String phone) {
        try {
            log.info("phone:" + phone + ",content:" + content);
            //SmsApi.send(phone, "SMS_111111", "{\"code\":\"" + content + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
