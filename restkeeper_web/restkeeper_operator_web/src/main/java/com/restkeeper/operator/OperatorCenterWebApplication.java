package com.restkeeper.operator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class},scanBasePackages = {"com.restkeeper"})
@EnableDiscoveryClient
public class OperatorCenterWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(OperatorCenterWebApplication.class, args);
    }
}
