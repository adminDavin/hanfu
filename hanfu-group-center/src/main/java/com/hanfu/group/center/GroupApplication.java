package com.hanfu.group.center;


import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@SpringBootApplication
@EnableScheduling
@MapperScans(@MapperScan("com.hanfu.group.center.manual.dao"))
public class GroupApplication {
	public static void main(String[] args) {
		SpringApplication.run(GroupApplication.class, args);
	}
}
