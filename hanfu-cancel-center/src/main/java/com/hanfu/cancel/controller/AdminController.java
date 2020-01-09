package com.hanfu.cancel.controller;

import com.hanfu.user.center.config.PermissionConstants;
import com.hanfu.user.center.service.RequiredPermission;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @RequiredPermission(PermissionConstants.ADMIN_PRODUCT_LIST)
    @GetMapping("/greeting")
    public String greeting() {
        return "Hello,World!";
    }

    @GetMapping("/login")
    public String login() {

        return "login sucess";
    }
}
