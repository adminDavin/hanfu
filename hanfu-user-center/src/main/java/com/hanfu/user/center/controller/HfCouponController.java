package com.hanfu.user.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.user.center.model.HfCoupon;
import com.hanfu.user.center.model.HfUserCoupon;
import com.hanfu.user.center.service.HfCouponService;
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
import java.util.List;

/**
 * @ClassName HfCouponController
 * @Date 2020/1/14 17:35
 * @Author CONSAK
 **/
@Api
@CrossOrigin
@RestController
@RequestMapping("/coupon")
public class HfCouponController {

    @Autowired
    private HfCouponService hfCouponService;

    @ApiOperation(value = "查询所有优惠券,也可根据id来查找",notes = "查询所有优惠券,也可根据id来查找")
    @RequestMapping(value = "/selectCoupon",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "优惠券id", required = false, type = "Integer")
    })
    public ResponseEntity<JSONObject> selectCoupon(@RequestParam(required = false,defaultValue = "") Integer id) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        List<HfCoupon> hfCoupon = hfCouponService.selectCoupon(id);
        return builder.body(ResponseUtils.getResponseBody(hfCoupon));
    }

    @ApiOperation(value = "后台添加优惠券",notes = "后台添加优惠券")
    @RequestMapping(value = "/insertCoupon",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "money", value = "优惠券金额", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "total", value = "优惠券到达多少价格才能用", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "body", value = "优惠券说明", required = true, type = "String"),
    })
    public ResponseEntity<JSONObject> insertCoupon(@RequestParam(required = true,defaultValue = "") Integer money,
                                                   @RequestParam(required = true,defaultValue = "") Integer total,
                                                   @RequestParam(required = true,defaultValue = "") String body) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        hfCouponService.insertCoupon(money,total,body);
        return builder.body(ResponseUtils.getResponseBody("添加成功"));
    }

    @ApiOperation(value = "后台修改优惠券",notes = "后台修改优惠券")
    @RequestMapping(value = "/updateCoupon",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "优惠券ID", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "money", value = "优惠券金额", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "total", value = "优惠券到达多少价格才能用", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "body", value = "优惠券说明", required = true, type = "String"),
    })
    public ResponseEntity<JSONObject> updateCoupon(@RequestParam(required = true,defaultValue = "") Integer id,
                                                   @RequestParam(required = true,defaultValue = "") Integer money,
                                                   @RequestParam(required = true,defaultValue = "") Integer total,
                                                   @RequestParam(required = true,defaultValue = "") String body) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        hfCouponService.updateCoupon(id,money,total,body);
        return builder.body(ResponseUtils.getResponseBody("修改成功"));
    }

    @ApiOperation(value = "后台删除优惠券",notes = "后台删除优惠券")
    @RequestMapping(value = "/deleteCoupon",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "优惠券ID", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> deleteCoupon(@RequestParam(required = true,defaultValue = "") Integer id) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        hfCouponService.deleteCoupon(id);
        return builder.body(ResponseUtils.getResponseBody("删除成功"));
    }

    @ApiOperation(value = "用户查询自己的优惠券",notes = "用户查询自己的优惠券")
    @RequestMapping(value = "/selectUserCoupon",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户ID", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> selectUserCoupon(@RequestParam(required = true,defaultValue = "") Integer userId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        List<HfUserCoupon> hfUserCoupon = hfCouponService.selectUserCoupon(userId);
        return builder.body(ResponseUtils.getResponseBody(hfUserCoupon));
    }

    @ApiOperation(value = "用户领取优惠券",notes = "用户领取优惠券")
    @RequestMapping(value = "/receiveCoupon",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户ID", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "couponId", value = "优惠券ID", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> receiveCoupon(@RequestParam(required = true,defaultValue = "") Integer userId,
                                                    @RequestParam(required = true,defaultValue = "") Integer couponId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        LocalDateTime time = LocalDateTime.now();
        hfCouponService.receiveCoupon(userId,couponId,time);
        return builder.body(ResponseUtils.getResponseBody("领取成功"));
    }

    @ApiOperation(value = "用户使用优惠券",notes = "用户使用优惠券")
    @RequestMapping(value = "/useCoupon",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户ID", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "couponId", value = "优惠券ID", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "total", value = "当前商品价格", required = true, type = "Integer")
    })
    public ResponseEntity<JSONObject> useCoupon(@RequestParam(required = true,defaultValue = "") Integer userId,
                                                @RequestParam(required = true,defaultValue = "") Integer couponId,
                                                @RequestParam(required = true,defaultValue = "") Integer total) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();

        //首先，先把当前用户使用的优惠券查出来
        HfCoupon hfCoupon = hfCouponService.selectOneCoupon(couponId);

        System.out.println(hfCoupon.toString());

        if(total>=hfCoupon.getTotal()){
            int money = hfCoupon.getMoney();
            total=total-money;
            return builder.body(ResponseUtils.getResponseBody(total));
        }else{
            return builder.body(ResponseUtils.getResponseBody("不满足"));
        }
    }

    @ApiOperation(value = "用户使用优惠券之后删除优惠券",notes = "用户使用优惠券之后删除优惠券")
    @RequestMapping(value = "/deleteUserCoupon",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "用户优惠券中间表id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户ID", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "couponId", value = "优惠券ID", required = true, type = "Integer")
    })
    public ResponseEntity<JSONObject> deleteUserCoupon(@RequestParam(required = true,defaultValue = "") Integer id,
                                                       @RequestParam(required = true,defaultValue = "") Integer userId,
                                                       @RequestParam(required = true,defaultValue = "") Integer couponId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();

        Integer result = hfCouponService.deleteUserCoupon(id,userId,couponId);

        System.out.println(result);

        if(null!=result&&result>0){
            return builder.body(ResponseUtils.getResponseBody("删除用户优惠券成功"));
        }else{
            return builder.body(ResponseUtils.getResponseBody("删除用户优惠券失败"));
        }
    }
}