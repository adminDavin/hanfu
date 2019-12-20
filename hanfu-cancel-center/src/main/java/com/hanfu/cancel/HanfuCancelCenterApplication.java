package com.hanfu.cancel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableScheduling
@MapperScan(value="com.hanfu.cancel.dao")
public class HanfuCancelCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(HanfuCancelCenterApplication.class, args);
    }

}
