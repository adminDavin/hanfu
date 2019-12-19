package com.hanfu.payment.center.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName PayReturnController
 * @Date 2019/12/19 16:04
 * @Author CONSAK
 **/
@Api
@CrossOrigin
@RestController
@RequestMapping("/payReturn")
public class PayReturnController {

    @ApiOperation(value = "申请退款",notes = "申请退款")
    @RequestMapping(value = "/refund",method = RequestMethod.GET)
    public Map refund(){

        return null;

    }
}