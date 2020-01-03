package com.hanfu.payment.center;


import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScans({@MapperScan("com.hanfu.payment.center.manual.dao")})
@EnableTransactionManagement
@SpringBootApplication
@EnableAutoConfiguration
@EnableDubbo(scanBasePackages = "com.hanfu.payment.center.service.impl")
@ComponentScan("com.hanfu.payment.center")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}