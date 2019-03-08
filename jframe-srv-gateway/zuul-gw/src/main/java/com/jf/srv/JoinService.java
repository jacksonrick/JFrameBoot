package com.jf.srv;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-07-06
 * Time: 17:34
 */
@Service
public class JoinService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallback")
    public Observable<Object> getOne(String id) {
        return Observable.create(observer -> {
            Object object = restTemplate.getForObject("http://JFRAME-SRV-ORDER-01/order?userId=1&productId={id}", Object.class, id);
            observer.onNext(object);
            observer.onCompleted();
        });
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public Observable<Object> getTwo(String id) {
        return Observable.create(observer -> {
            Object object = restTemplate.getForObject("http://JFRAME-SRV-ORDER-02/order?userId=2&productId={id}", Object.class, id);
            observer.onNext(object);
            observer.onCompleted();
        });
    }

    public Object fallback(String id) {
        return "error";
    }

}
