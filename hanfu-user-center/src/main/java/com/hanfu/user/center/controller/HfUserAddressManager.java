package com.hanfu.user.center.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.user.center.dao.HfUserAddresseMapper;
import com.hanfu.user.center.model.HfUserAddresseExample;
import com.hanfu.user.center.response.handler.ParamInvalidException;
import com.hanfu.user.center.utils.GetMessageCode;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@Api
@RequestMapping("/user/address")
public class HfUserAddressManager {
    @Autowired
    private HfUserAddresseMapper hfUserAddresseMapper;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value = "獲取用戶地址列表", notes = "獲取用戶地址列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用戶id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "token", value = "token", required = false, type = "String")
            })
    public ResponseEntity<JSONObject> login(@RequestParam Integer userId, @RequestParam String token) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        HfUserAddresseExample e = new HfUserAddresseExample();
        e.createCriteria().andUserIdEqualTo(userId);
        return builder.body(ResponseUtils.getResponseBody(hfUserAddresseMapper.selectByExample(e)));
    }


}
