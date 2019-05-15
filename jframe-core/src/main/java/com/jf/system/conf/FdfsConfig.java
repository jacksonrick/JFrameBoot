package com.jf.system.conf;

import com.jf.sdk.fdfs.conn.*;
import com.jf.sdk.fdfs.service.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: FastDFS
 * User: xujunfei
 * Date: 2018-01-19
 * Time: 14:20
 */
@Configuration
@ConditionalOnProperty(name = "system.upload.fdfs", havingValue = "true")
@ConfigurationProperties(prefix = "fdfs")
public class FdfsConfig {

    // 读取时间，默认1000
    private int soTimeout = 2000;
    // 连接超时时间
    private int connectTimeout = 2000;
    // Tracker 地址列表
    private List<String> trackerList = new ArrayList<>();

    @Bean
    public PooledConnectionFactory pooledConnectionFactory() {
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
        pooledConnectionFactory.setSoTimeout(soTimeout);
        pooledConnectionFactory.setConnectTimeout(connectTimeout);
        return pooledConnectionFactory;
    }

    @Bean
    @ConfigurationProperties(prefix = "fdfs.pool")
    public ConnectionPoolConfig connectionPoolConfig() {
        ConnectionPoolConfig connectionPoolConfig = new ConnectionPoolConfig();
        return connectionPoolConfig;
    }

    @Bean
    public FdfsConnectionPool fdfsConnectionPool(PooledConnectionFactory pooledConnectionFactory, ConnectionPoolConfig connectionPoolConfig) {
        FdfsConnectionPool pool = new FdfsConnectionPool(pooledConnectionFactory, connectionPoolConfig);
        return pool;
    }

    @Bean
    public TrackerConnectionManager trackerConnectionManager(FdfsConnectionPool fdfsConnectionPool) {
        return new TrackerConnectionManager(fdfsConnectionPool, trackerList);
    }

    @Bean
    public TrackerClient trackerClient(TrackerConnectionManager trackerConnectionManager) {
        return new DefaultTrackerClient(trackerConnectionManager);
    }

    @Bean
    public ConnectionManager connectionManager(FdfsConnectionPool fdfsConnectionPool) {
        return new ConnectionManager(fdfsConnectionPool);
    }

    @Bean
    public FastFileStorageClient fastFileStorageClient(TrackerClient trackerClient, ConnectionManager connectionManager) {
        return new DefaultFastFileStorageClient(trackerClient, connectionManager);
    }

    @Bean
    public AppendFileStorageClient appendFileStorageClient(TrackerClient trackerClient, ConnectionManager connectionManager) {
        return new DefaultAppendFileStorageClient(trackerClient, connectionManager);
    }

    public int getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public List<String> getTrackerList() {
        return trackerList;
    }

    public void setTrackerList(List<String> trackerList) {
        this.trackerList = trackerList;
    }

}
