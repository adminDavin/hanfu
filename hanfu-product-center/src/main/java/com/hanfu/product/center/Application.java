package com.hanfu.product.center;


import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
 
@MapperScans({@MapperScan("com.hanfu.product.center.dao")})
@DubboComponentScan(value = "com.hanfu.product.center.service")
@EnableAutoConfiguration
@EnableTransactionManagement
@SpringBootApplication
@EnableDubbo
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);	
	}
}
