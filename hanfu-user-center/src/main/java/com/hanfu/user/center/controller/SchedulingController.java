package com.hanfu.user.center.controller;

import io.swagger.annotations.Api;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName SchedulingController
 * @Date 2020/1/13 18:10
 * @Author CONSAK
 **/
@Api
@CrossOrigin
@RestController
@RequestMapping("/test")
public class SchedulingController {
    @Scheduled(cron = "*/5 * * * * ?")
    public void test(){
        System.out.println("每隔五秒钟输出一次");
    }
}