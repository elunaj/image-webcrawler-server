package com.luna.webcrawlerrestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WebcrawlerRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebcrawlerRestApiApplication.class, args);
    }

}
