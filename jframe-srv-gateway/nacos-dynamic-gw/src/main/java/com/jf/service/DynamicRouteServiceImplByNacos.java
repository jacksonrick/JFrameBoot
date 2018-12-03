package com.jf.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;

/**
 * Created with IntelliJ IDEA.
 * Description: 监听Nacos Server下发的动态路由配置
 * User: xujunfei
 * Date: 2018-11-26
 * Time: 13:48
 */
@Component
public class DynamicRouteServiceImplByNacos {

    private static final Logger logger = LoggerFactory.getLogger(DynamicRouteServiceImplByNacos.class);

    @Autowired
    private DynamicRouteServiceImpl dynamicRouteService;

    @Value("${spring.cloud.nacos.config.server-addr}")
    private String serverAddr;
    @Value("${gw.data-id}")
    private String dataId;
    @Value("${gw.group}")
    private String group;

    @PostConstruct
    public void init() {
        try {
            ConfigService configService = NacosFactory.createConfigService(serverAddr);
            String content = configService.getConfig(dataId, group, 5000);
            logger.info(content);

            // 添加监听器
            configService.addListener(dataId, group, new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    RouteDefinition definition = JSON.parseObject(configInfo, RouteDefinition.class);
                    dynamicRouteService.update(definition);
                }

                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

}
