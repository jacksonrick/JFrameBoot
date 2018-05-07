package com.jf.system.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created with IntelliJ IDEA.
 * Description: RestTemplate
 * User: xujunfei
 * Date: 2018-05-04
 * Time: 14:05
 * <blockquote><pre>
 *     HttpHeaders headers = new HttpHeaders();
 *     headers.add("name", value);
 *     HttpEntity requestEntity = new HttpEntity(null, headers);
 *     ResponseEntity response1 = restTemplate.exchange("http://ip:port/add?param={value}", HttpMethod.GET, requestEntity, String.class, value);
 * </pre></blockquote>
 */
//@Configuration
public class RestConfig {

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(15000);
        factory.setReadTimeout(5000);
        return factory;
    }

}
