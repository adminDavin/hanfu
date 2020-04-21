package com.hanfu.payment.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.payment.center.dao.StoneChargeOffMapper;
import com.hanfu.payment.center.manual.model.StoneBalanceDis;
import com.hanfu.payment.center.model.HfUserBalance;
import com.hanfu.payment.center.model.StoneChargeOff;
import com.hanfu.payment.center.model.StoneChargeOffExample;
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

import java.time.LocalDateTime;

@RestController
@Api
@RequestMapping("/StoneBalance")
@CrossOrigin
public class StoneBalanceController {
    @Autowired
    private StoneChargeOffMapper stoneChargeOffMapper;
    @ApiOperation(value = "余额流水", notes = "余额流水")
    @RequestMapping(value = "/addStoneBalance", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "stoneId", value = "店铺id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "state", value = "流水状态", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "price", value = "流水金额", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "balanceType", value = "流水类型", required = true, type = "String")
    })
    public ResponseEntity<JSONObject> addStoneBalance(@RequestParam Integer stoneId, String balanceType,Integer state,Integer price,String desc,Integer orderId)
            throws JSONException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        StoneChargeOff stoneChargeOff = new StoneChargeOff();
        stoneChargeOff.setActualPrice(price);
        stoneChargeOff.setChargeOffState(state);
        stoneChargeOff.setChargeOffType(balanceType);
        stoneChargeOff.setStoneDesc(desc);
        stoneChargeOff.setStoneId(stoneId);
        stoneChargeOff.setOrderId(orderId);

        stoneChargeOff.setCreateTime(LocalDateTime.now());
        stoneChargeOff.setModifyTime(LocalDateTime.now());
        stoneChargeOff.setIsDeleted((byte) 0);
        stoneChargeOff.setLastModifier(String.valueOf(stoneId));
        stoneChargeOffMapper.insert(stoneChargeOff);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "改变流水状态", notes = "改变流水状态")
    @RequestMapping(value = "/updateStoneBalance", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> updateStoneBalance(Integer orderId)
            throws JSONException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        StoneChargeOff stoneChargeOff = new StoneChargeOff();
        stoneChargeOff.setChargeOffState(0);
        StoneChargeOffExample stoneChargeOffExample = new StoneChargeOffExample();
        stoneChargeOffExample.createCriteria().andOrderIdEqualTo(orderId);
        stoneChargeOffMapper.updateByExampleSelective(stoneChargeOff,stoneChargeOffExample);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
}
