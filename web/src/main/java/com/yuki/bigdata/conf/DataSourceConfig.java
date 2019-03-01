package com.yuki.bigdata.conf;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Autowired
    private DataSourceProperties properties;

    @Bean(name = "dataSource")
    public DataSource dataSource1() {
        System.setProperty("hikaricp.configurationFile","/hikaripoll.properties");
        DataSourceBuilder factory = DataSourceBuilder
                .create(properties.getClassLoader())
                .driverClassName(properties.getDriverClassName())
                .url(properties.getUrl()).username(properties.getUsername())
                .password(properties.getPassword());
        if (properties.getType() != null) {
            factory.type(properties.getType());
        }
        return factory.build();
    }

}
