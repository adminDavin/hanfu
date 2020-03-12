package com.hanfu.activity.center;


import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling
@MapperScans({@MapperScan("com.hanfu.activity.center.dao")})
@EnableAutoConfiguration
@EnableTransactionManagement
@SpringBootApplication
@EnableDubbo(scanBasePackages = "com.hanfu.activity.center.service.impl")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
