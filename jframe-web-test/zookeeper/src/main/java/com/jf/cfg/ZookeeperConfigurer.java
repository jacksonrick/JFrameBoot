package com.jf.cfg;

import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * Description: 分布式配置
 * User: xujunfei
 * Date: 2019-05-08
 * Time: 11:44
 */
public class ZookeeperConfigurer {

    private static Logger log = LoggerFactory.getLogger(ZookeeperConfigurer.class);

    // curator客户端与监听器
    private CuratorFramework zkClient;
    private TreeCache treeCache;

    // zk服务器ip(:端口)
    private String zkServers;
    // zk监听的路径
    private String zkPath;

    private Properties props;

    public ZookeeperConfigurer(String zkServers, String zkPath) {
        this.zkServers = zkServers;
        this.zkPath = zkPath;
        this.props = new Properties();

        log.info("############初始化BEAN");
        //初始化curator客户端
        initZkClient();
        //从zookeeper的Jdbc节点下获取数据库配置存入props
        getConfigData();
        //对zookeeper上的数据库配置文件所在节点进行监听，如果有改变就动态刷新props
        addZkListener();
    }

    private void initZkClient() {
        zkClient = CuratorFrameworkFactory.builder().connectString(zkServers)
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
        zkClient.start();
    }

    private void getConfigData() {
        log.info("########获取配置########");
        try {
            List<String> list = zkClient.getChildren().forPath(zkPath);
            for (String key : list) {
                String value = new String(zkClient.getData().forPath(zkPath + "/" + key));
                if (StringUtils.isNotBlank(value)) {
                    props.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addZkListener() {
        TreeCacheListener listener = new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                if (event.getType() == TreeCacheEvent.Type.NODE_UPDATED) { // 更新触发
                    getConfigData();
                    log.info("########配置刷新########");
                    log.info(props.toString());
                }
            }
        };
        treeCache = new TreeCache(zkClient, zkPath);
        try {
            treeCache.start();
            treeCache.getListenable().addListener(listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
