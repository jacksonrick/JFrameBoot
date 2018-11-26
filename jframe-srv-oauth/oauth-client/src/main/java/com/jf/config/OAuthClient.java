package com.jf.config;

import com.jf.model.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-06-06
 * Time: 16:10
 */
@Configuration
public class OAuthClient {

    private static Logger log = LoggerFactory.getLogger(OAuthClient.class);

    @Autowired
    private RestTemplate restTemplate;

    private String token = "";

    @PostConstruct
    public synchronized void init() {
        log.info("正在获取Token...");
        String token_url = "http://127.0.0.1:8090/oauth/token?grant_type=client_credentials&scope=all&client_id=client&client_secret=123456";
        try {
            ResponseEntity<AccessToken> response = restTemplate.exchange(token_url, HttpMethod.POST, null, AccessToken.class);
            log.info("获取Token成功：" + response.getBody());
            token = response.getBody().getAccess_token();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("获取Token失败");
        }
    }

    public String getAuth(String url) {
        if ("".equals(token)) {
            init();
        }
        if ("".equals(token)) {
            return "error";
        }
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            HttpEntity<String> entity = new HttpEntity<String>(null, headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            log.info("OAuth Resource返回: " + response.getBody() + ", token: " + token);
            return response.getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String get(String url) {
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            log.info("OAuth Resource返回: " + response.getBody());
            return response.getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
            return "error";
        }
    }

}
