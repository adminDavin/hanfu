package com.hanfu.cancel.controller;

import com.hanfu.user.center.service.impl.Permission;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    Permission permission = new Permission();
    @GetMapping("/greeting")
    public String greeting() {
        permission.test();
        return "Hello,World!";
    }

    @GetMapping("/login")
    public String login() {

        return "login sucess";
    }
}
