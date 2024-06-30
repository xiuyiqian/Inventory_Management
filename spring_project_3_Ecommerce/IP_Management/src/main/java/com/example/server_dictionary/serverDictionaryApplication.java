package com.example.server_dictionary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class serverDictionaryApplication {
    public static void main(String[] args) {
        SpringApplication.run(serverDictionaryApplication.class, args);
    }
}
