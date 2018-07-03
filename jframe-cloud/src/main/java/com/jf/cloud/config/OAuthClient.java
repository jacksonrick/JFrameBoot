package com.jf.cloud.config;

import com.jf.cloud.common.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
 * Description: OAuthClient
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
    private final String error = "error";

    @Value("${oauth.token_url}")
    private String token_url;

    @PostConstruct
    public synchronized void init() {
        log.info("正在获取Token...");
        try {
            ResponseEntity<AccessToken> response = restTemplate.exchange(token_url, HttpMethod.POST, null, AccessToken.class);
            log.info("获取Token成功：" + response.getBody());
            token = response.getBody().getAccess_token();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("获取Token失败");
        }
    }

    /**
     * @param url
     * @param method
     * @return
     */
    public String request(String url, HttpMethod method) {
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, method, null, String.class);
            log.info("【" + method + "】【" + url + "】返回: " + response.getBody());
            return response.getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
            return error;
        }
    }

    /**
     * @param url
     * @param method
     * @return
     */
    public String requestAuth(String url, HttpMethod method) {
        if ("".equals(token)) {
            init();
        }
        if ("".equals(token)) {
            return error;
        }
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(null);
            headers.add("Authorization", "Bearer " + token);
            HttpEntity<String> entity = new HttpEntity<String>(null, headers);
            ResponseEntity<String> response = restTemplate.exchange(url, method, entity, String.class);
            log.info("【OAuth】【" + method + "】【" + url + "】返回: " + response.getBody());
            return response.getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
            return error;
        }
    }

}
