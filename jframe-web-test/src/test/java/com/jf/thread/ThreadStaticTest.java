package com.jf.thread;

/**
 * Created with IntelliJ IDEA.
 * Description: 测试全局静态变量线程安全问题
 * User: xujunfei
 * Date: 2018-04-08
 * Time: 13:23
 */
public class StaticVar {

    private static int a = 1;

    public static void main(String[] args) {
        for (int i = 0; i < 5000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    a = 2;
                    System.out.println("1: " + Thread.currentThread().getName() + " -- " + a);
                    a = 5;
                    System.out.println("2: " + Thread.currentThread().getName() + " -- " + (a * 2));
                }
            }).start();
        }
    }

}
