package com.etrade.puggo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableAsync
@EnableWebMvc
@EnableFeignClients
@EnableScheduling
@SpringBootApplication
public class PuggoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PuggoServerApplication.class, args);
    }

}
