package com.jf.controller;

import com.jf.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-06-21
 * Time: 13:54
 */
@RestController
@RequestMapping("/rest")
public class RestTplController extends BaseController {

    @Autowired(required = false)
    private RestTemplate restTemplate;

    @GetMapping("/exchage")
    public String exchage() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("name", "");
        HttpEntity requestEntity = new HttpEntity(null, headers);
        ResponseEntity<String> response = restTemplate.exchange("http://ip:port/add?param={value}", HttpMethod.GET, requestEntity, String.class, "value");
        if (HttpStatus.OK.value() == response.getStatusCodeValue()) {
            return response.getBody();
        }
        return "";
    }

    @GetMapping("/get")
    public String get() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://ip:port/add?param={value}", String.class, "value");
        if (HttpStatus.OK.value() == response.getStatusCodeValue()) {
            return response.getBody();
        }
        return "";
    }

    @GetMapping("/post")
    public String post() {
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("param", "value");
        ResponseEntity<String> response = restTemplate.postForEntity("http://ip:port/add", param, String.class);
        if (HttpStatus.OK.value() == response.getStatusCodeValue()) {
            return response.getBody();
        }
        return "";
    }

}
