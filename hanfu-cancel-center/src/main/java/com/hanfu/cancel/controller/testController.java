package com.hanfu.cancel.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.cancel.service.CancelService;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@Api
@RequestMapping("/test")
@CrossOrigin
public class testController {
    @Autowired
    private CancelService cancelService;
    //转换时间格式
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        //转换日期
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ApiOperation(value = "查询查询", notes = "查询查询")
    public ResponseEntity<JSONObject> login(String site,Date createData,Date createDate1) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();

        return builder.body(ResponseUtils.getResponseBody(cancelService.Test(site,createData,createDate1)));
    }
// @Scheduled(cron = "0/5 * * * * ? ")
//    @RequestMapping(value = "/qqq", method = RequestMethod.GET)
//    @ApiOperation(value = "查询查询", notes = "查询查询")
//    public ResponseEntity<JSONObject> qqq() throws Exception {
//        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
//
//        return builder.body(ResponseUtils.getResponseBody("123456"));
//    }
}
