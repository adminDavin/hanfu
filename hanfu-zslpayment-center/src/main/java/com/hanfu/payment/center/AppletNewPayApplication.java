package com.hanfu.payment.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableScheduling
@ComponentScan("com.hanfu.payment.center.dao")
public class AppletNewPayApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppletNewPayApplication.class, args);
    }
}