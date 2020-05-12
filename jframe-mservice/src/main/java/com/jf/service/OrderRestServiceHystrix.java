package com.jf.service;

import com.jf.model.User;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 断路器
 * User: xujunfei
 * Date: 2018-02-27
 * Time: 10:57
 */
@Component
public class OrderRestServiceHystrix implements OrderRestService {

    @Override
    public String order(Integer userId, Integer productId) {
        return "sorry!service[order] is Unavailable!";
    }

    @Override
    public String get(Map<String, Object> map) {
        return "sorry!service[get] is Unavailable!";
    }

    @Override
    public String post(User user) {
        return "sorry!service[post] is Unavailable!";
    }
}
