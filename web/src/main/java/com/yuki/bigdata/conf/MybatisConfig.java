package com.yuki.bigdata.conf;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@MapperScan("com.yuki.bigdata.mapper")
public class MybatisConfig {

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;


    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
//        Resource[] resources = new PathMatchingResourcePatternResolver()
//                .getResources("classpath:mybatis/mapper/*.xml");
//        factoryBean.setMapperLocations(resources);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // 加载全局的配置文件
        factoryBean.setConfigLocation(
                resolver.getResource("classpath:mybatis/mybatis-config.xml"));
        factoryBean.setMapperLocations(
                resolver.getResources("classpath:mybatis/mapper/*.xml")
        );
        factoryBean.setDataSource(dataSource);
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory()); // 使用上面配置的Factory
        return template;
    }


}
