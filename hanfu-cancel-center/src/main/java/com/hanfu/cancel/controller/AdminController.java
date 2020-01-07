package com.hanfu.cancel.controller;

import com.hanfu.cancel.config.PermissionConstants;
import com.hanfu.cancel.service.RequiredPermission;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {


    @RequiredPermission(PermissionConstants.ADMIN_PRODUCT_LIST) // 权限注解
    @GetMapping("/greeting")
    public String greeting(String name) {
        return "Hello,World!";
    }
    @GetMapping("/login")
    public String login() {

        return "login sucess";
    }
}
