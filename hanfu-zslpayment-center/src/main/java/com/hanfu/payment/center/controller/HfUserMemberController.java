package com.hanfu.payment.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.payment.center.service.HfUserMemberService;
import com.hanfu.utils.response.handler.ResponseEntity;
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
 * @Date 2019/12/18 14:03
 * @Author CONSAK
 **/

@Api
@CrossOrigin
@RestController
@RequestMapping("/member")
public class HfUserMemberController {

    @Autowired
    private HfUserMemberService hfUserMemberService;
//
//    @ApiOperation(value = "充值会员",notes = "充值会员")
//    @RequestMapping(value = "/rechargeMember",method = RequestMethod.GET)
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "userId", value = "用戶id", required = true),
//            @ApiImplicitParam(paramType = "query", name = "money", value = "充值的金额", required = true)
//    })
//    public ResponseEntity<JSONObject> rechargeMember(@RequestParam(value = "userId") Integer userId,
//                                                     @RequestParam(value = "money") Integer money) throws JSONException {
//
//        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
//
//        //先把充值的金额 存入该用户的余额表
//        hfUserMemberService.rechargeMember(userId,money);
//
//        return builder.body(ResponseUtils.getResponseBody("充值成功"));
//    }
}