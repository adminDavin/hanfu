package com.hanfu.payment.center;

import com.hanfu.payment.center.dao.HfUserMemberMapper;
//import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableScheduling
@MapperScan("com.hanfu.payment.center.dao")

public class AppletNewPayApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppletNewPayApplication.class, args);
    }
}