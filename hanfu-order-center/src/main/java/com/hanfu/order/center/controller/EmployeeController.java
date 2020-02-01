package com.hanfu.order.center.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/employee")
public class EmployeeController {


    @RequestMapping(value = "/greeting",method = RequestMethod.GET)
    public String greeting(String username,String password) {
        return "Hello,World!";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login() {

        return "login sucess";
    }
}