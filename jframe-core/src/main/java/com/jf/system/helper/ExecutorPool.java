package com.jf.system.helper;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * Description: 线程池
 * User: xujunfei
 * Date: 2019-12-19
 * Time: 16:05
 */
public class ExecutorPool {


    public static final ExecutorService SERVICE = new ThreadPoolExecutor(6, 6,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1000),
            new CustomThreadFactory("RP"),
            new ThreadPoolExecutor.CallerRunsPolicy());


    private static class CustomThreadFactory implements ThreadFactory {
        private String name;
        private AtomicInteger count = new AtomicInteger(0);

        public CustomThreadFactory(String name) {
            this.name = name;
        }

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, name + "-" + count.addAndGet(1));
        }
    }

}
