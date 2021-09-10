package com.restkeeper.operator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"com.restkeeper"})
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.restkeeper.operator.mapper"})
public class OperatorCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(OperatorCenterApplication.class, args);
    }
}
