package com.jar.storeLedger.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static com.jar.storeLedger.config.SpringConstants.PACKAGE_STORE_LEDGER;
import static com.jar.storeLedger.config.SpringConstants.PACKAGE_STORE_LEDGER_ENTITY;

@Configuration
@ComponentScan(basePackages = {PACKAGE_STORE_LEDGER, PACKAGE_STORE_LEDGER_ENTITY})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, ErrorMvcAutoConfiguration.class,
        UserDetailsServiceAutoConfiguration.class, SecurityAutoConfiguration.class})
public class BaseApplication {

}
