package com.hanfu.product.center;


import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScans({ 
	@ComponentScan("com.king.words.controller"), 
	@ComponentScan("com.king.words.manual.dao"), 
	@ComponentScan("com.king.words.schedules"),
	@ComponentScan("com.king.words.tasks"),
	@ComponentScan("com.king.words.service"),
	@ComponentScan("com.king.words.intercepter"),
	@ComponentScan("com.king.words.config"),
	@ComponentScan("com.king.words.response.handler"),
})
@MapperScans({@MapperScan("com.king.words.dao")})
@EnableAutoConfiguration
@EnableTransactionManagement
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);	
	}
}
