package com.jf.srv;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import rx.Observable;
import rx.Observer;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * Description: 聚合微服务
 * User: xujunfei
 * Date: 2018-07-06
 * Time: 17:32
 */
@RestController
public class JoinController {

    private final static Logger log = LoggerFactory.getLogger(JoinController.class);

    @Resource
    private JoinService joinService;

    @GetMapping("/get/{id}")
    public DeferredResult<HashMap<String, Object>> get(@PathVariable("id") String id) {
        Observable<HashMap<String, Object>> result = this.aggregate(id);
        return this.toDeferredResult(result);
    }

    public Observable<HashMap<String, Object>> aggregate(String id) {
        return Observable.zip(
                this.joinService.getOne(id),
                this.joinService.getTwo(id),
                (one, two) -> {
                    HashMap<String, Object> map = Maps.newHashMap();
                    map.put("one", one);
                    map.put("two", two);
                    return map;
                }

        );
    }

    public DeferredResult<HashMap<String, Object>> toDeferredResult(Observable<HashMap<String, Object>> result) {
        DeferredResult<HashMap<String, Object>> defer = new DeferredResult<>();
        result.subscribe(new Observer<HashMap<String, Object>>() {
            @Override
            public void onCompleted() {
                log.info("Completed...");
            }

            @Override
            public void onError(Throwable throwable) {
                log.info("Error..." + throwable.getMessage());
            }

            @Override
            public void onNext(HashMap<String, Object> map) {
                defer.setResult(map);
            }
        });
        return defer;
    }

}
