package com.study.code.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

// 开启服务注册与发现
@EnableDiscoveryClient
// 排除数据源相关的自动配置
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
// 直接在pom中排除mybatis jar包
@SpringBootApplication(scanBasePackages = {"com.study.code.commons.exception", "com.study.code.gateway"})
public class MallGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallGatewayApplication.class, args);
    }

}
