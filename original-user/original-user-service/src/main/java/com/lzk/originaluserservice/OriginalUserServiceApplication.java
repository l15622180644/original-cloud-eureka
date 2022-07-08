package com.lzk.originaluserservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients(basePackages = {"com.lzk"}) //扫描指定包下的@FeignClient注解
@SpringBootApplication
@EnableEurekaClient //将该服务注册到eureka
@MapperScan("com.lzk.*.mapper") //扫描指定包下的mybatis映射接口
@ComponentScan(basePackages = {"com.lzk"}) //扫描指定包下的springboot注解
public class OriginalUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OriginalUserServiceApplication.class, args);
    }

}
