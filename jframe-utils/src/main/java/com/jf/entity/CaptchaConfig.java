package com.jf.entity;

import com.jf.file.CaptchaUtil;

/**
 * Created with IntelliJ IDEA.
 * Description: 验证码配置
 * User: xujunfei
 * Date: 2020-06-19
 * Time: 10:46
 */
public class CaptchaConfig {

    public int len = 5; // 验证码随机字符长度
    public int width = 100; // 验证码显示宽度
    public int height = 35; // 验证码显示高度
    public int fontSize = 24; // 验证码字符字体大小
    public int charType = CaptchaUtil.TYPE_DEFAULT;  // 验证码类型

    public CaptchaConfig() {
    }

    public CaptchaConfig(int len, int width, int height) {
        this.len = len;
        this.width = width;
        this.height = height;
    }

    public CaptchaConfig(int charType) {
        this.charType = charType;
    }

    public CaptchaConfig(int len, int width, int height, int fontSize, int charType) {
        this.len = len;
        this.width = width;
        this.height = height;
        this.fontSize = fontSize;
        this.charType = charType;
    }

}
