package com.jf.executor;

import sun.jvm.hotspot.utilities.WorkerThread;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created with IntelliJ IDEA.
 * Description: 自定义线程池
 * User: xujunfei
 * Date: 2018-04-10
 * Time: 14:39
 */
public class TaskPoolImpl implements ITaskPool {

    // 默认开启线程个数
    static int WORKER_NUMBER = 5;
    // 完成任务线程数
    volatile int totalCount = 0;
    // 任务队列
    List<Runnable> taskQueue = Collections.synchronizedList(new LinkedList<>());
    // 线程工作组
    WorkerThread[] workerThreads;

    static AtomicLong threadNum = new AtomicLong();

    static TaskPoolImpl taskPool;

    public TaskPoolImpl() {
        this(WORKER_NUMBER);
    }

    public TaskPoolImpl(int workerNum) {
        this.WORKER_NUMBER = workerNum;
        workerThreads = new WorkerThread[WORKER_NUMBER];

        for (int i = 0; i < WORKER_NUMBER; i++) {
            workerThreads[i] = new WorkerThread();
            Thread thread = new Thread(workerThreads[i], "ThreadPool-worker-" + threadNum.incrementAndGet());
            System.out.println("初始化线程数" + (i + 1) + "---线程名称:" + thread.getName());
            thread.start();
        }
    }

    public static ITaskPool getTaskPool(int workerNum) {
        if (workerNum <= 0) {
            workerNum = WORKER_NUMBER;
        }
        if (taskPool == null) {
            taskPool = new TaskPoolImpl(workerNum);
        }
        return taskPool;
    }

    @Override
    public void execute(Runnable task) {
        taskQueue.add(task);
    }

    @Override
    public void execute(List<Runnable> task) {
        for (Runnable runnable : task) {
            taskQueue.add(runnable);
        }
    }

    @Override
    public String toString() {
        return "工作线程数量为" + WORKER_NUMBER
                + "\n已完成的任务数" + totalCount +
                "\n等待任务数量" + taskQueue.size();
    }

    @Override
    public void destroy() {
        // 循环是否还存在任务，如果存在等待20毫秒处理时间
        while (!taskQueue.isEmpty()) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < WORKER_NUMBER; i++) {
            workerThreads[i].destroy();
            workerThreads[i] = null;
        }
        taskPool = null;
        taskQueue.clear();
    }

    class WorkerThread extends Thread {
        private Boolean isRunning = true;

        @Override
        public void run() {
            Runnable runnable = null;
            while (isRunning) {
                if (!taskQueue.isEmpty()) {
                    runnable = taskQueue.remove(0);
                }
                if (runnable != null) {
                    runnable.run();
                }
                totalCount++;
                runnable = null;
            }
        }

        // 销毁线程
        public void destroy() {
            isRunning = false;
        }
    }
}
