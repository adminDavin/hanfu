package com.hanfu.payment.center;


import com.github.pagehelper.PageHelper;

import java.util.Properties;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


@MapperScans({@MapperScan("com.hanfu.payment.center.manual.dao"), @MapperScan("com.hanfu.payment.center.dao")})
@EnableAutoConfiguration
@EnableDubbo(scanBasePackages = "com.hanfu.payment.center.service.impl")
@ComponentScan("com.hanfu.payment.center")
@tk.mybatis.spring.annotation.MapperScan(value = "com.hanfu.payment.center.dao")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "true");
        //配置mysql数据库的方言
        properties.setProperty("dialect", "mysql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}