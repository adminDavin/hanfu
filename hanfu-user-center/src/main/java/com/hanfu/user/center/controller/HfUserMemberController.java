package com.hanfu.user.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.user.center.model.HUserBalance;
import com.hanfu.user.center.service.HfUserMemberService;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName HfUserMemberController
 * @Date 2019/12/19 10:01
 * @Author CONSAK
 **/
@Api
@CrossOrigin
@RestController
@RequestMapping("/member")
public class HfUserMemberController {

    @Autowired
    private HfUserMemberService hfUserMemberService;

    @RequestMapping(value = "/rechargeMember",method = RequestMethod.GET)
    @ApiOperation(value = "充值会员",notes = "充值会员")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用戶id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "total", value = "充值的金额", required = true, type = "Integer")
    })
    public ResponseEntity<JSONObject> rechargeMember(@RequestParam(required = true,defaultValue = "") Integer userId,
                                                     @RequestParam(required = true,defaultValue = "") Integer total) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();

        //先判断用户是不是第一次充值
        HUserBalance hUserBalance = hfUserMemberService.itExistUserById(userId);
        if(hUserBalance!=null){

        }else{

        }
        return builder.body(ResponseUtils.getResponseBody("充值成功"));
    }
}