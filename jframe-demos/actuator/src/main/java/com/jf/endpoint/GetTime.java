package com.jf.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 自定义端点
 * User: xujunfei
 * Date: 2019-08-12
 * Time: 18:24
 */
@Endpoint(id = "get-time")
@Component
public class GetTime {

    private final Map<String, Object> message = new HashMap<>();

    private GetTime() {
        this.message.put("当前时间", new Date());
        this.message.put("我是", "Jack");
    }

    @ReadOperation
    public Map<String, Object> getAll() {
        return message;
    }

    @ReadOperation
    public Map<String, Object> getOne(@Selector String name) {
        Object value = message.get(name);
        Map<String, Object> message2 = new HashMap<String, Object>();
        message2.put("name", value);
        return message2;
    }

    @WriteOperation
    public void updatePerson(@Selector String name) {
        this.message.put(name, name);
    }

}
