package com.hanfu.product.center;


import com.github.pagehelper.PageHelper;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;
@SpringBootApplication(scanBasePackages = {"com.hanfu.product.center.*","com.hanfu.user.center.service"} )
@MapperScans({@MapperScan("com.hanfu.user.center.Jurisdiction.dao")})
@MapperScan(value = {"com.hanfu.product.center.dao","com.hanfu.user.center.Jurisdiction.dao"})
@tk.mybatis.spring.annotation.MapperScan({"com.hanfu.product.center.dao"})
@EnableTransactionManagement
@EnableScheduling
@EnableDubbo(scanBasePackages = {"com.hanfu.user.product.*","com.hanfu.user.center.Jurisdiction.service"})
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
