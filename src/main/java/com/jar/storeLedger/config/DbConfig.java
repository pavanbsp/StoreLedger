package com.jar.storeLedger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

import static com.jar.storeLedger.config.SpringConstants.PACKAGE_STORE_LEDGER_ENTITY;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager")
public class DbConfig {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Bean
    //TODO define min, max connection values later
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(applicationProperties.getJdbcDriver());
        dataSourceBuilder.url(applicationProperties.getJdbcUrl());
        dataSourceBuilder.username(applicationProperties.getJdbcUsername());
        dataSourceBuilder.password(applicationProperties.getJdbcPassword());
        return dataSourceBuilder.build();
    }

    @Autowired
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(DataSource dataSource) {
        Properties props = new Properties();
        props.put("hibernate.dialect", applicationProperties.getHibernateDialect());
        props.put("hibernate.show_sql", applicationProperties.getHibernateShowSql());
        props.put("hibernate.hbm2ddl.auto", applicationProperties.getHibernateHbm2ddlAuto());
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaProperties(props);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setPackagesToScan(PACKAGE_STORE_LEDGER_ENTITY);
        return entityManagerFactoryBean;
    }

    @Autowired
    @Bean(name = "transactionManager")
    public JpaTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        JpaTransactionManager bean = new JpaTransactionManager();
        bean.setEntityManagerFactory(entityManagerFactoryBean.getObject());
        return bean;
    }

}
