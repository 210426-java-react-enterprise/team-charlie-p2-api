package com.revature.pantry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}