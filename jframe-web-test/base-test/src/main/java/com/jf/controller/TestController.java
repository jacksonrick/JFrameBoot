package com.jf.controller;

import com.jf.common.BaseController;
import com.jf.entity.ResMsg;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description: 服务测试
 * User: xujunfei
 * Date: 2018-03-14
 * Time: 11:35
 */
@Controller
public class TestController extends BaseController {

    @RequestMapping("/test")
    @ResponseBody
    public ResMsg test() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        return new ResMsg(0, "SUCCESS");
    }

    @RequestMapping("/testAsyncRequest")
    public void testAsyncRequest(HttpServletRequest request) {
        AsyncContext context = request.startAsync();
        context.setTimeout(60000L);
        context.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent asyncEvent) throws IOException {
                System.out.println("over");
            }

            @Override
            public void onTimeout(AsyncEvent asyncEvent) throws IOException {

            }

            @Override
            public void onError(AsyncEvent asyncEvent) throws IOException {

            }

            @Override
            public void onStartAsync(AsyncEvent asyncEvent) throws IOException {

            }
        });
        context.start(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    System.out.println("内部线程：" + Thread.currentThread().getName());

                    context.getResponse().setContentType("text/html;charset=UTF-8");
                    context.getResponse().getWriter().write("Hello");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                context.complete();
            }
        });
        System.out.println("主线程：" + Thread.currentThread().getName());
    }

}
