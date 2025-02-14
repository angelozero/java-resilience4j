package com.angelozero.resilience4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@EnableFeignClients
@SpringBootApplication
public class Resilience4jApplication {

    public static void main(String[] args) {
        SpringApplication.run(Resilience4jApplication.class, args);
    }

}
