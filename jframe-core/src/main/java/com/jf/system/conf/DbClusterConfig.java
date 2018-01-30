package com.jf.system.conf;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * Description: DB数据源、Mybatis|Plugins、事务
 * User: xujunfei
 * Date: 2017-11-28
 * Time: 10:43
 *
 * @version 2.0
 */
//@Configuration
//@EnableTransactionManagement
//@MapperScan(basePackages = "com.jf.cluster", sqlSessionFactoryRef = "clusterSqlSessionFactory")
public class DbClusterConfig {

    private Logger logger = LoggerFactory.getLogger(DbClusterConfig.class);

    @Bean
    @ConfigurationProperties("spring.datasource.druid.cluster")
    public DataSource clusterDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "clusterSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("clusterDataSource") DataSource clusterDataSource) {
        try {
            SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
            bean.setDataSource(clusterDataSource);
            // we MUST set the 'VFS' if you use jar
            bean.setVfs(SpringBootVFS.class);
            // 实体类位置
            bean.setTypeAliasesPackage("com.jf.model");
            // 设置mapper.xml文件所在位置
            org.springframework.core.io.Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:com/jf/cluster/xml/*.xml");
            bean.setMapperLocations(resources);

            // 添加分页插件
            PageInterceptor pageHelper = new PageInterceptor();
            Properties p = new Properties();
            p.setProperty("helperDialect", "mysql");
            p.setProperty("supportMethodsArguments", "true");
            p.setProperty("params", "pageNum=pageNo;pageSize=pageSize;");
            pageHelper.setProperties(p);
            Interceptor[] plugins = new Interceptor[]{pageHelper};
            bean.setPlugins(plugins);

            return bean.getObject();
        } catch (Exception e) {
            logger.error("DB Cluster sqlSessionFactory create error!", e);
            return null;
        }
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("clusterSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 事务管理
     *
     * @return
     */
    @Bean
    public PlatformTransactionManager clusterTransactionManager(@Qualifier("clusterDataSource") DataSource clusterDataSource) {
        return new DataSourceTransactionManager(clusterDataSource);
    }

}
