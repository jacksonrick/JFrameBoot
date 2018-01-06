package com.jf.system.conf;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2017-11-28
 * Time: 10:43
 */
@Configuration
@AutoConfigureAfter(DruidDBConfig.class)
@EnableTransactionManagement
@MapperScan("com.jf.mapper")
public class MybatisConfig implements TransactionManagementConfigurer {

    private Logger logger = LoggerFactory.getLogger(MybatisConfig.class);

    @Autowired
    private DataSource dataSource;

    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory() {
        try {
            SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
            bean.setDataSource(dataSource);
            // we MUST set the 'VFS' if you use jar
            bean.setVfs(SpringBootVFS.class);
            // 实体类位置
            bean.setTypeAliasesPackage("com.jf.model");
            // 设置mapper.xml文件所在位置
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:com/jf/mapper/xml/*.xml");
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
            logger.error("Mybatis sqlSessionFactoryBean create error", e);
            return null;
        }
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 事务管理
     *
     * @return
     */
    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

}
