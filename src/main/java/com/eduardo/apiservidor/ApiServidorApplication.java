package com.eduardo.apiservidor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ApiServidorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiServidorApplication.class, args);
    }

}
