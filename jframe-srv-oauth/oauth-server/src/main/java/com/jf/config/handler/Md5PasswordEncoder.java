package com.jf.config.handler;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-01-14
 * Time: 16:27
 */
public class Md5PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        return PasswordUtil.MD5Encode(charSequence.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (encodedPassword != null && encodedPassword.length() != 0) {
            return PasswordUtil.MD5Encode(rawPassword.toString()).equals(encodedPassword);
        }
        return false;
    }
}
