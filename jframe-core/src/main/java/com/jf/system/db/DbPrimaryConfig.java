package com.jf.system.db;

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
import org.springframework.context.annotation.Primary;
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
@Configuration
@MapperScan(basePackages = DbPrimaryConfig.mapperPackage, sqlSessionFactoryRef = "primarySqlSessionFactory")
@EnableTransactionManagement
public class DbPrimaryConfig {

    private Logger logger = LoggerFactory.getLogger(DbPrimaryConfig.class);

    public final static String mapperPackage = "com.jf.database.mapper";
    public final static String modelPackage = "com.jf.database.model";
    public final static String xmlMapperLocation = "classpath:mapper/**/*.xml";

    @Bean(name = "primaryDataSource")
    @Primary
    @ConfigurationProperties("spring.datasource.druid.primary")
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "primarySqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("primaryDataSource") DataSource dataSource) {
        try {
            SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
            bean.setDataSource(dataSource);
            // we MUST set the 'VFS' if you use jar
            bean.setVfs(SpringBootVFS.class);
            // 实体类位置
            bean.setTypeAliasesPackage(modelPackage);
            // 设置mapper.xml文件所在位置
            org.springframework.core.io.Resource[] resources = new PathMatchingResourcePatternResolver().getResources(xmlMapperLocation);
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
            logger.error("DB [primary] sqlSessionFactory create error!", e);
            return null;
        }
    }

    @Bean(name = "primarySqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("primarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 事务管理
     *
     * @return
     */
    @Bean(name = "primaryTransactionManager")
    @Primary
    public PlatformTransactionManager transactionManager(@Qualifier("primaryDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
