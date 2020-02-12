package com.hanfu.payment.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.payment.center.dao.BalanceMapper;
import com.hanfu.payment.center.model.HfUserBalance;
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
import tk.mybatis.mapper.entity.Example;

@RestController
@Api
@RequestMapping("/UserBalance")
@CrossOrigin
public class UserBalanceController {
    @Autowired
    private BalanceMapper balanceMapper;

    @ApiOperation(value = "用户balance查询", notes = "用户balance查询")
    @RequestMapping(value = "/UserBalance", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "balanceType", value = "余额类型", required = true, type = "String")
    })
    public ResponseEntity<JSONObject> payment(@RequestParam Integer userId, String balanceType)
            throws JSONException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        Example example = new Example(HfUserBalance.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId).andEqualTo("balanceType",balanceType).andEqualTo("isDeleted",0);
        return builder.body(ResponseUtils.getResponseBody(balanceMapper.selectByExample(example)));
    }
}
