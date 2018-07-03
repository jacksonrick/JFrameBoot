package com.jf.thread.lock;

import java.util.concurrent.CountDownLatch;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-04-11
 * Time: 11:50
 */
public class CountDownLatchTest implements Runnable {

    private final CountDownLatch countDownLatch;
    private int number;

    public CountDownLatchTest(int number) {
        this.number = number;
        this.countDownLatch = new CountDownLatch(number); // 初始化其内部的计数器，当初始化完成后，不能再次初始化
    }

    public void arrive(String name) {
        countDownLatch.countDown(); // 内部的计数器减1
        System.out.println("arrive: " + name);

        try {
            // await方法是线程进入休眠，当countDownLatch计数器为0时，将被唤醒
            countDownLatch.await();
            // 线程被唤醒，在这里可以执行一系列任务
            System.out.println("name: " + name + " starting...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("has arrived:" + (number - countDownLatch.getCount()));

        try {
            countDownLatch.await();
            System.out.println("all arrived:" + (number - countDownLatch.getCount()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Meeting implements Runnable{



    @Override
    public void run() {

    }
}