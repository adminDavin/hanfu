package com.hanfu.payment.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.payment.center.manual.dao.CouponsDao;
import com.hanfu.payment.center.manual.dao.PrivilegeDao;
import com.hanfu.payment.center.manual.model.Privilege;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Api
@RequestMapping("/Coupons")
@CrossOrigin
public class CouponsController {
    @Autowired
    private CouponsDao couponsDao;
    @Autowired
    private PrivilegeDao privilegeDao;
    @ApiOperation(value = "优惠券", notes = "优惠券")
    @RequestMapping(value = "/selectCoupons", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "status", value = "优惠券状态", required = true, type = "String")
    })
    public ResponseEntity<JSONObject> selectCoupons(@RequestParam Integer userId, String status)
            throws JSONException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(couponsDao.selectCoupons(userId,status)));
    }

    @ApiOperation(value = "特权", notes = "特权")
    @RequestMapping(value = "/selectPrivilege", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "status", value = "特权状态", required = true, type = "String")
    })
    public ResponseEntity<JSONObject> selectPrivilege(@RequestParam Integer userId, String status)
            throws JSONException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(privilegeDao.selectPrivilege(userId,status)));
    }
}
