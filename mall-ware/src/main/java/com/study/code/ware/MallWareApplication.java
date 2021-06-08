package com.study.code.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan("com.study.code.ware.mapper")
@SpringBootApplication(scanBasePackages = {"com.study.code.commons.exception", "com.study.code.ware"})
@EnableDiscoveryClient
@EnableFeignClients("com.study.code.commons.feign")
public class MallWareApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallWareApplication.class, args);
    }

}
