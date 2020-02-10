package com.hanfu.cancel.controller;

import org.springframework.web.bind.annotation.*;

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