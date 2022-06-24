package com.lzk.originalregister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class OriginalRegisterApplication{

    public static void main(String[] args) {
        SpringApplication.run(OriginalRegisterApplication.class, args);
    }

}
