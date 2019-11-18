package com.jf.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {DataConfig.mapperPackage}, sqlSessionFactoryRef = "sqlSessionFactory")
@EnableTransactionManagement
public class DataConfig {

    private Logger logger = LoggerFactory.getLogger(DataConfig.class);

    public final static String mapperPackage = "com.jf.mapper";
    public final static String modelPackage = "com.jf.model";
    public final static String xmlMapperLocation = "classpath*:mapper/*.xml";

    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) {
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
            return bean.getObject();
        } catch (Exception e) {
            logger.error("Database sqlSessionFactory create error!", e);
            return null;
        }
    }

    @Bean(name = "sqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 事务管理
     *
     * @return
     */
    @Bean(name = "platformTransactionManager")
    @Primary
    public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
