package com.jf.system.helper;

import com.jf.entity.ResMsg;
import com.jf.database.enums.ResCode;
import com.jf.system.exception.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.SocketTimeoutException;

/**
 * Created with IntelliJ IDEA.
 * Description: restTemplate方法【仅供参考】
 * User: xujunfei
 * Date: 2018-07-05
 * Time: 16:31
 */
@Component
public class RestClient {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 普通请求
     *
     * @param url
     * @param method HttpMethod
     * @return 0-OK | 1-ERROR | 2-TIMEOUT
     */
    public ResMsg request(String url, HttpMethod method) {
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, method, null, String.class);
            return new ResMsg(ResCode.HTTP_OK.code(), ResCode.HTTP_OK.msg(), response.getBody());
        } catch (Exception e) {
            if (e.getCause() instanceof SocketTimeoutException) {
                return new ResMsg(ResCode.HTTP_TIMEOUT.code(), ResCode.HTTP_TIMEOUT.msg());
            }
            if (e instanceof RestException) {
                return new ResMsg(ResCode.HTTP_ERROR.code(), ResCode.HTTP_ERROR.msg() + ":" + ((RestException) e).getCode());
            }
            return new ResMsg(ResCode.HTTP_ERROR.code(), ResCode.HTTP_ERROR.msg());
        }
    }

    /**
     * 普通请求
     *
     * @param url
     * @param method
     * @param clz
     * @param <T>
     * @return
     */
    public <T> ResMsg request(String url, HttpMethod method, Class<T> clz) {
        try {
            ResponseEntity<T> response = restTemplate.exchange(url, method, null, clz);
            return new ResMsg(ResCode.HTTP_OK.code(), ResCode.HTTP_OK.msg(), response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getCause() instanceof SocketTimeoutException) {
                return new ResMsg(ResCode.HTTP_TIMEOUT.code(), ResCode.HTTP_TIMEOUT.msg());
            }
            if (e instanceof RestException) {
                return new ResMsg(ResCode.HTTP_ERROR.code(), ResCode.HTTP_ERROR.msg() + ":" + ((RestException) e).getCode());
            }
            return new ResMsg(ResCode.HTTP_ERROR.code(), ResCode.HTTP_ERROR.msg());
        }
    }

    /**
     * requestBody请求
     *
     * @param url
     * @param body   json
     * @param method HttpMethod
     * @return
     */
    public ResMsg request(String url, String body, HttpMethod method) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(body, headers);
            ResponseEntity<String> response = restTemplate.exchange(url, method, entity, String.class);
            return new ResMsg(ResCode.HTTP_OK.code(), ResCode.HTTP_OK.msg(), response.getBody());
        } catch (Exception e) {
            if (e.getCause() instanceof SocketTimeoutException) {
                return new ResMsg(ResCode.HTTP_TIMEOUT.code(), ResCode.HTTP_TIMEOUT.msg());
            }
            if (e instanceof RestException) {
                return new ResMsg(ResCode.HTTP_ERROR.code(), ResCode.HTTP_ERROR.msg() + ":" + ((RestException) e).getCode());
            }
            return new ResMsg(ResCode.HTTP_ERROR.code(), ResCode.HTTP_ERROR.msg());
        }
    }

    /**
     * requestBody请求
     *
     * @param url
     * @param body
     * @param method
     * @param clz
     * @param <T>
     * @return
     */
    public <T> ResMsg request(String url, String body, HttpMethod method, Class<T> clz) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(body, headers);
            ResponseEntity<T> response = restTemplate.exchange(url, method, entity, clz);
            return new ResMsg(ResCode.HTTP_OK.code(), ResCode.HTTP_OK.msg(), response.getBody());
        } catch (Exception e) {
            if (e.getCause() instanceof SocketTimeoutException) {
                return new ResMsg(ResCode.HTTP_TIMEOUT.code(), ResCode.HTTP_TIMEOUT.msg());
            }
            if (e instanceof RestException) {
                return new ResMsg(ResCode.HTTP_ERROR.code(), ResCode.HTTP_ERROR.msg() + ":" + ((RestException) e).getCode());
            }
            return new ResMsg(ResCode.HTTP_ERROR.code(), ResCode.HTTP_ERROR.msg());
        }
    }

}
