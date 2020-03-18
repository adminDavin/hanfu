package com.hanfu.user.center.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Api
@RequestMapping("/LoginCC")
@CrossOrigin
public class LoginsController {

    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    public String updateOrAddProject() {
        return "redirect:http://39.100.237.144:3001/";
    }
}
