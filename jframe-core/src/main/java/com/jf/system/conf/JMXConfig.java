package com.jf.system.conf;

import com.jf.commons.LogManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jmx.support.ConnectorServerFactoryBean;
import org.springframework.remoting.rmi.RmiRegistryFactoryBean;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-03-28
 * Time: 10:51
 */
public class JMXConfig {

    @Value("${jmx.host}")
    private String host;
    @Value("${jmx.port}")
    private Integer port;

    @Bean(name = "rmiRegistry")
    public RmiRegistryFactoryBean rmiRegistry() {
        final RmiRegistryFactoryBean rmiRegistryFactoryBean = new RmiRegistryFactoryBean();
        rmiRegistryFactoryBean.setPort(port);
        rmiRegistryFactoryBean.setAlwaysCreate(true);
        return rmiRegistryFactoryBean;
    }

    @Bean
    @DependsOn("rmiRegistry")
    public ConnectorServerFactoryBean connectorServerFactoryBean() throws Exception {
        final ConnectorServerFactoryBean connectorServerFactoryBean = new ConnectorServerFactoryBean();
        connectorServerFactoryBean.setObjectName("connector:name=rmi");
        String url = String.format("service:jmx:rmi://%s:%s/jndi/rmi://%s:%s/jmxrmi", host, port, host, port);
        LogManager.info("JMX started at: " + host + ":" + port + " and serv_url: " + url, getClass());
        connectorServerFactoryBean.setServiceUrl(url);
        return connectorServerFactoryBean;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        // 注意：port是依据Tomcat/Undertow服务器的端口，建议4位
        if (port == -1) {
            this.port = 12121;
        } else {
            this.port = port + 10000;
        }
    }
}