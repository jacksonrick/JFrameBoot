package com.jf.test;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created with IntelliJ IDEA.;
 * Description:
 * User: xujunfei
 * Date: 2019-05-06
 * Time: 17:32
 */
public class MyWatcher {

    private static String path = "/test";

    private ZooKeeper zk;
    private CountDownLatch latch = new CountDownLatch(1);

    private Watcher watcher;

    public MyWatcher(String host) throws IOException, KeeperException, InterruptedException {
        watcher = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("event++");
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    System.out.println("received: " + event.toString());
                    latch.countDown();
                }
            }
        };
        zk = new ZooKeeper(host, 1000, watcher);
        latch.await();
        if (zk.exists(path, false) == null) {
            zk.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        Thread.sleep(Integer.MAX_VALUE);
    }

    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {
        new MyWatcher("192.168.31.200");
    }
}
