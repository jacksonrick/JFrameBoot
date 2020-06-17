package com.jf.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created with IntelliJ IDEA.
 * Description: Zookeeper测试
 * User: xujunfei
 * Date: 2019-07-05
 * Time: 11:42
 */
@Service
@FeignClient(value = "ZK-SERVER", fallback = IApiFallback.class)
public interface IApiService {

    @GetMapping("/list")
    String list();

}
