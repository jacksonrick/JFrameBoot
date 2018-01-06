package com.jf.system.flume;

import ch.qos.logback.core.spi.ContextAware;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.api.RpcClient;
import org.apache.flume.api.RpcClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2017-12-20
 * Time: 14:00
 */
public class EventReporter {

    private RpcClient client;

    private final ContextAware loggingContext;

    private final ExecutorService es;

    private final Properties connectionProps;

    public EventReporter(final Properties properties, final ContextAware context, final int maximumThreadPoolSize, final int maxQueueSize) {
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(maxQueueSize);
        this.connectionProps = properties;
        this.loggingContext = context;

        int corePoolSize = 1;
        TimeUnit threadKeepAliveUnits = TimeUnit.SECONDS;
        int threadKeepAliveTime = 30;
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

        this.es = new ThreadPoolExecutor(corePoolSize, maximumThreadPoolSize, threadKeepAliveTime,
                threadKeepAliveUnits, blockingQueue, handler);
    }

    public void report(final Event[] events) {
        es.submit(new ReportingJob(events));
    }

    private synchronized RpcClient createClient() {
        if (client == null) {
            loggingContext.addInfo("Creating a new Flume Client with properties: " + connectionProps);
            try {
                client = RpcClientFactory.getInstance(connectionProps);
            } catch (Exception e) {
                loggingContext.addError(e.getLocalizedMessage(), e);
            }
        }

        return client;
    }

    public synchronized void close() {
        loggingContext.addInfo("Shutting down Flume client");
        if (client != null) {
            client.close();
            client = null;
        }
    }

    public void shutdown() {
        close();
        es.shutdown();
    }

    private class ReportingJob implements Runnable {

        private final Logger logger = LoggerFactory.getLogger(getClass());

        private static final int retries = 3;

        private final Event[] events;

        public ReportingJob(final Event[] events) {
            this.events = events;
            logger.debug("Created a job containing {} events", events.length);
        }


        @Override
        public void run() {
            boolean success = false;
            int count = 0;
            try {
                while (!success && count < retries) {
                    count++;
                    try {
                        logger.debug("Reporting a batch of {} events, try {}", events.length, count);
                        createClient().appendBatch(Arrays.asList(events));
                        success = true;
                        logger.debug("Successfully reported a batch of {} events", events.length);
                    } catch (EventDeliveryException e) {
                        logger.warn(e.getLocalizedMessage(), e);
                        logger.warn("Will retry " + (retries - count) + " times");
                    }
                }
            } finally {
                if (!success) {
                    logger.error("Could not submit events to Flume");
                    close();
                }
            }
        }
    }

}
