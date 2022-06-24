package com.lzk.originaluserservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.lzk.*.mapper")
public class OriginalUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OriginalUserServiceApplication.class, args);
    }

}
