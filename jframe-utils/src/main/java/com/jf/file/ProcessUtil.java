package com.jf.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * Description: Shell执行工具
 * User: xujunfei
 * Date: 2019-05-20
 * Time: 17:23
 */
public class ProcessUtil {

    private static Logger log = LoggerFactory.getLogger(ProcessUtil.class);

    /**
     * Buffer size of process input-stream (used for reading the output (sic!) of the process). Currently 64KB.
     */
    public static final int BUFFER_SIZE = 65536;
    public static final int EXEC_TIME_OUT = 2;

    private ExecutorService exec;

    private ProcessUtil() {
        // 初始化线程池
        exec = new ThreadPoolExecutor(2,
                2,
                1,
                TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(10),
                new CustomThreadFactory("cmd-process"),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static ProcessUtil instance() {
        return InputStreamConsumer.instance;
    }

    /**
     * 简单的封装， 执行cmd命令
     *
     * @param cmd 待执行的操作命令
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public boolean process(String cmd) throws Exception {
        log.debug("execute shell: " + cmd);
        Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", cmd});
        waitForProcess(process);
        return true;
    }


    /**
     * 源码参考 im4java 的实现修改而来
     */
    private int waitForProcess(final Process pProcess)
            throws IOException, InterruptedException, TimeoutException, ExecutionException {

        FutureTask<Object> outTask = new FutureTask<Object>(() -> {
            processOutput(pProcess.getInputStream(), InputStreamConsumer.DEFAULT_CONSUMER);
            return null;
        });
        exec.submit(outTask);


        FutureTask<Object> errTask = new FutureTask<Object>(() -> {
            processError(pProcess.getErrorStream(), InputStreamConsumer.DEFAULT_CONSUMER);
            return null;
        });
        exec.submit(errTask);

        // Wait and check IO exceptions (FutureTask.get() blocks).
        try {
            outTask.get();
            errTask.get();
        } catch (ExecutionException e) {
            Throwable t = e.getCause();

            if (t instanceof IOException) {
                throw (IOException) t;
            } else if (t instanceof RuntimeException) {
                throw (RuntimeException) t;
            } else {
                throw new IllegalStateException(e);
            }
        }

        FutureTask<Integer> processTask = new FutureTask<Integer>(() -> {
            pProcess.waitFor();
            return pProcess.exitValue();
        });
        exec.submit(processTask);

        // 设置超时时间，防止死等
        int rc = processTask.get(EXEC_TIME_OUT, TimeUnit.SECONDS);

        try {
            pProcess.getInputStream().close();
            pProcess.getOutputStream().close();
            pProcess.getErrorStream().close();
        } catch (Exception e) {
            log.error("close stream error!", e);
        }

        return rc;
    }


    /**
     * 方便后续对输出流的扩展
     */
    private void processOutput(InputStream pInputStream, InputStreamConsumer pConsumer) throws IOException {
        pConsumer.consume(pInputStream);
    }

    /**
     * 方便对后续异常流的处理
     */
    private void processError(InputStream pInputStream, InputStreamConsumer pConsumer) throws IOException {
        pConsumer.consume(pInputStream);
    }


    // 内部类，消息消费
    private static class InputStreamConsumer {
        static ProcessUtil instance = new ProcessUtil();
        static InputStreamConsumer DEFAULT_CONSUMER = new InputStreamConsumer();

        void consume(InputStream stream) throws IOException {
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream), BUFFER_SIZE);

            String temp;
            while ((temp = reader.readLine()) != null) {
                builder.append(temp);
            }

            log.debug("cmd process input stream: {}", builder.toString());
            reader.close();
        }
    }

    // 创建线程
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
