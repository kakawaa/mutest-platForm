package com.mutest.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import javax.sql.DataSource;

/**
 * @author guozhengMu
 * @version 1.0
 * @date 2019/1/2 18:38
 * @description 数据源配置
 * @modify
 */
@Configuration
public class DataSourceConfig {

    /**
     * 主数据源:192.168.112.36
     *
     * @return
     */
    @Bean(name = "baseDataSource")
    @Qualifier("baseDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.base.datasource")
    public DataSource base() {

        return DataSourceBuilder.create().build();
    }
}


