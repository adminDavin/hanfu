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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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


        LocalDateTime time = LocalDateTime.now();
        LocalDateTime thirtyTime = LocalDateTime.now();
        LocalDateTime seasonTime = LocalDateTime.now();
        LocalDateTime yearTime = LocalDateTime.now();
        thirtyTime = time.minus(-1, ChronoUnit.MONTHS);
        seasonTime = time.minus(-3, ChronoUnit.MONTHS);
        yearTime = time.minus(-12, ChronoUnit.MONTHS);


        //先判断用户是不是第一次充值会员
        HUserBalance hUserBalance = hfUserMemberService.itExistUserById(userId);

        //如果用户不是第一次充值会员  直接修改用户表和余额表就可以了
        if(hUserBalance!=null){
            //如果不是第一次开会员 想看用户到期没有
            String str = hfUserMemberService.getModifyTime(userId);
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime parse = LocalDateTime.parse(str, df);//这个是用户的过期时间
            System.out.println("这是用户的过期日期："+parse);
            System.out.println("这是当前时间:"+time);
            boolean parseBefore = parse.isBefore(time);

            if (parseBefore){//如果过期时间在当前时间前面  则已经过期
                System.out.println("已经过期");
                hfUserMemberService.memberTime(userId,time,thirtyTime,seasonTime,yearTime,total); //到期的话 就重新设置开通会员的开始和过期时间
                hfUserMemberService.updateBalance(userId,total);//但是余额表不用新增  修改余额就可以
                return builder.body(ResponseUtils.getResponseBody("充值会员成功"));
            }else{
                System.out.println("没有过期");
                //没有过期的话  先修改用户的过期时间
                hfUserMemberService.updateModifyTime(userId,time,thirtyTime,seasonTime,yearTime,total);
                //如何在添加用户余额就可以了
                hfUserMemberService.updateBalance(userId,total);
                return builder.body(ResponseUtils.getResponseBody("充值会员成功"));
            }

        }else{
            hfUserMemberService.memberTime(userId,time,thirtyTime,seasonTime,yearTime,total);//如果是第一次充值  则要新增数据 先给用户表添加成为会员的时间，并改变用户的状态
            hfUserMemberService.insertBalance(userId,total);//然后再把用户冲的钱放到用户的余额表里面
            return builder.body(ResponseUtils.getResponseBody("充值会员成功"));
        }
    }


    @ApiOperation(value = "购买会员",notes = "购买会员")
    @RequestMapping(value = "buyMember",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用戶id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "total", value = "充值的金额", required = true, type = "Integer")
    })
    public ResponseEntity<JSONObject> buyMember(@RequestParam(required = true,defaultValue = "") Integer userId,
                                                @RequestParam(required = true,defaultValue = "") Integer total) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();

        LocalDateTime time = LocalDateTime.now();
        LocalDateTime thirtyTime = LocalDateTime.now();
        LocalDateTime seasonTime = LocalDateTime.now();
        LocalDateTime yearTime = LocalDateTime.now();
        thirtyTime = time.minus(-1, ChronoUnit.MONTHS);
        seasonTime = time.minus(-3, ChronoUnit.MONTHS);
        yearTime = time.minus(-12, ChronoUnit.MONTHS);


        //先判断是不是第一次购买会员
        String createTime = hfUserMemberService.getCreateTime(userId);

        System.out.println(createTime);

        if (createTime!=null){
            System.out.println("不是第一次购买会员");
            //先判断他过期没有
            String str = hfUserMemberService.getModifyTime(userId);
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime parse = LocalDateTime.parse(str, df);//这个是用户的过期时间
            System.out.println("这是用户的过期日期："+parse);
            System.out.println("这是当前时间:"+time);
            boolean parseBefore = parse.isBefore(time);

            if (parseBefore){//如果过期日期在当前日期前面 则已经过期
                System.out.println("已经过期");
                //购买会员  相当于重新设置开通，过期时间和状态
                //直接调用第一次购买会员的方法就可以了
                hfUserMemberService.buymemberTime(userId,time,thirtyTime,seasonTime,yearTime,total);
                return builder.body(ResponseUtils.getResponseBody("购买会员成功"));
            }else {
                System.out.println("没有过期");
                //没有过期  在过期时间上面加时间
                hfUserMemberService.updateTime(userId,time,thirtyTime,seasonTime,yearTime,total);
                return builder.body(ResponseUtils.getResponseBody("购买会员成功"));
            }

        }else {
            System.out.println("第一次购买会员");
            //第一次购买会员  只需要改变用户的状态、开通时间、过期时间
            hfUserMemberService.buymemberTime(userId,time,thirtyTime,seasonTime,yearTime,total);
            return builder.body(ResponseUtils.getResponseBody("购买会员成功"));
        }
    }
}