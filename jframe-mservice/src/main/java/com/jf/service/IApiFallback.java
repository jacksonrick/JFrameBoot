package com.jf.service;

import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description: 断路器
 * User: xujunfei
 * Date: 2019-07-05
 * Time: 15:21
 */
@Component
public class IApiFallback implements IApiService {
    @Override
    public String list() {
        return "service unavaliable!";
    }
}
