package com.jf.system.async.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import java.util.concurrent.Future;

/**
 * Created with IntelliJ IDEA.
 * Description: 使用Future以及定义超时
 * User: xujunfei
 * Date: 2018-04-25
 * Time: 12:11
 */
//@Component
public class FutureTask {

    @Async
    public Future<String> run() throws Exception {
        Thread.sleep(5000);
        return new AsyncResult<>("test");
    }

    // @Autowired FutureTask task;
    // Future<String> result = task.run();
    // String s = result.get(5, TimeUnit.SECONDS);
    // log.info(s);

}
