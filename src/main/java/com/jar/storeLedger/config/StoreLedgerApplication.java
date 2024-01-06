package com.jar.storeLedger.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
@ComponentScan(basePackages = {SpringConstants.PACKAGE_STORE_LEDGER})
public class StoreLedgerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreLedgerApplication.class, args);
    }

}
