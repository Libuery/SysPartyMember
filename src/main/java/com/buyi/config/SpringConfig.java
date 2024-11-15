package com.buyi.config;

import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:jdbc.properties")
@Import({DataSourceJavaConfig.class, MapperJavaConfig.class})
@ComponentScan({"com.buyi.service","com.buyi.mapper"})
//开启Spring事务管理
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class SpringConfig {
    @Bean
    public TransactionManager transactionManager(DataSource dataSource){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }
}
