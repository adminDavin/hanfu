package com.hanfu.cancel.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Api
@RequestMapping("/test")
@CrossOrigin
public class testController {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ApiOperation(value = "用户登录", notes = "用户登录")
    public ResponseEntity<JSONObject> login() throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();

        return builder.body(ResponseUtils.getResponseBody("成功"));
    }
}
