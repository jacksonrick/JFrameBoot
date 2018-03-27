package com.jf.restapi;

import org.springframework.stereotype.Component;

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
    public String order(Long userId, Long productId) {
        return "sorry!service[order] is Unavailable!";
    }

}
