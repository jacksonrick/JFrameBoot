package com.jf.executor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-04-10
 * Time: 14:31
 */
public class TaskQueue {

    public static void main(String[] args) {
        ITaskPool taskPool = TaskPoolImpl.getTaskPool(10);
        List<Runnable> taskList = new ArrayList<Runnable>();
        for (int i = 0; i < 100; i++) {
            taskList.add(new Task());
        }
        // 执行
        taskPool.execute(taskList);

        try {
            Thread.sleep(3000);
            System.out.println(taskPool);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 销毁
        //taskPool.destroy();
        //System.out.println("销毁后：" + taskPool);
    }

    static class Task implements Runnable {

        private static volatile int i = 1;

        @Override
        public void run() {
            System.out.println("当前处理的线程:" + Thread.currentThread().getName() + " 执行任务" + (i++) + " 完成");
        }
    }

}
