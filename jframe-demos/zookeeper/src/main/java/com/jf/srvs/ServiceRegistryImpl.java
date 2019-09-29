package com.jf.srvs;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created with IntelliJ IDEA.
 * Description: 服务注册事件
 * User: xujunfei
 * Date: 2019-05-07
 * Time: 10:54
 */
@Component
public class ServiceRegistryImpl implements ServiceRegistry, Watcher {

    private static Logger log = LoggerFactory.getLogger(ServiceRegistryImpl.class);
    private static CountDownLatch latch = new CountDownLatch(1);
    private static final int SESSION_TIMEOUT = 5000;
    private ZooKeeper zk;

    @Value("${zookeeper.registry-path}")
    private String registryPath;

    public ServiceRegistryImpl() {
    }

    public ServiceRegistryImpl(String zkServers) {
        try {
            log.info("##########连接Zookeeper");
            zk = new ZooKeeper(zkServers, SESSION_TIMEOUT, this);
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        // 事件 NodeChildrenChanged | NodeDeleted
        log.info("event: " + watchedEvent);
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            latch.countDown();
        }
    }

    /**
     * 注册服务
     *
     * @param serviceName    服务名称
     * @param serviceAddress 服务地址
     */
    @Override
    public void register(String serviceName, String serviceAddress) {
        try {
            if (zk.exists(registryPath, false) == null) {
                zk.create(registryPath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            //创建服务节点（持久节点）
            String servicePath = registryPath + "/" + serviceName;
            if (zk.exists(servicePath, false) == null) {
                zk.create(servicePath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            //创建地址节点
            String addressPath = servicePath + "/address-";
            String addressNode = zk.create(addressPath, serviceAddress.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取已发现服务
     *
     * @param path
     * @return
     */
    @Override
    public ArrayList<String> getValue(String path) {
        String data = null;
        ArrayList<String> array = new ArrayList<String>();
        try {
            List<String> children = zk.getChildren(path, this);
            for (String child : children) {
                data = new String(zk.getData(path + "/" + child, this, null));
                array.add(data);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("##########" + array);
        return array;
    }
}
