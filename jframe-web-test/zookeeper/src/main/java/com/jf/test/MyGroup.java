package com.jf.test;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-05-06
 * Time: 17:09
 */
public class MyGroup implements Watcher {

    private static final int SESSION_TIMEOUT = 1000; //会话延时
    private ZooKeeper zk = null;
    private CountDownLatch latch = new CountDownLatch(1);

    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.SyncConnected) {
            latch.countDown();
        }
    }

    public void connect(String host) throws IOException, InterruptedException {
        zk = new ZooKeeper(host, SESSION_TIMEOUT, this);
        latch.await();
    }

    public void create(String groupName) throws KeeperException, InterruptedException {
        String path = "/" + groupName;
        String createPath = zk.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("created: " + createPath);
    }

    public void set(String groupName, String data, Integer version) throws KeeperException, InterruptedException {
        String path = "/" + groupName;
        Stat stat = zk.setData(path, data.getBytes(), version);
        System.out.println("seted: " + stat);
    }

    public void children() throws KeeperException, InterruptedException {
        List<String> list = zk.getChildren("/", false);
        for (String str : list) {
            System.out.println("child: " + str);
        }
    }

    public void close() {
        if (zk != null) {
            try {
                zk.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                zk = null;
            }
        }
    }
}
