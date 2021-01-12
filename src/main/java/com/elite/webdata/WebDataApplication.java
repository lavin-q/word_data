package com.elite.webdata;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan(basePackages = "com.elite.webdata.elasticjob")
@EnableConfigurationProperties
@EnableScheduling
public class WebDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebDataApplication.class, args);
    }

}
