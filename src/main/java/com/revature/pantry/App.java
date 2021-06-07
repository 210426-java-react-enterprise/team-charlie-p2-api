package com.revature.pantry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
import org.springframework.web.client.*;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class App {

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
    
}