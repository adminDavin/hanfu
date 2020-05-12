package com.hanfu.payment.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.payment.center.dao.StoneBalanceMapper;
import com.hanfu.payment.center.dao.StoneChargeOffMapper;
import com.hanfu.payment.center.model.*;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.xml.crypto.Data;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Api
@RequestMapping("/StoneBalance")
@CrossOrigin
public class StoneBalanceController {
    @Autowired
    private StoneChargeOffMapper stoneChargeOffMapper;
    @Autowired
    private StoneBalanceMapper stoneBalanceMapper;
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
    @ApiOperation(value = "店铺余额变化", notes = "店铺余额变化")
    @RequestMapping(value = "/upStoneBalance", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> upStoneBalance(Integer stoneId,String balanceType,Integer money)
            throws JSONException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        StoneBalanceExample stoneBalanceExample = new StoneBalanceExample();
        stoneBalanceExample.createCriteria().andStoneIdEqualTo(stoneId).andBalanceTypeEqualTo(balanceType);
        List<StoneBalance> stoneBalance= stoneBalanceMapper.selectByExample(stoneBalanceExample);
//        System.out.println(stoneBalance.size());
        synchronized (this) {
            if (stoneBalance.size() == 0) {
                StoneBalance stoneBalance1 = new StoneBalance();
                stoneBalance1.setBalanceType(balanceType);
                stoneBalance1.setCreateTime(LocalDateTime.now());
                stoneBalance1.setModifyTime(LocalDateTime.now());
                stoneBalance1.setIsDeleted((short) 0);
                stoneBalance1.setStoneId(stoneId);
                stoneBalance1.setStoneBalance(money);
                stoneBalanceMapper.insert(stoneBalance1);
            } else {
//                StoneBalance stoneBalance1 = new StoneBalance();
//                stoneBalance1.setStoneBalance(stoneBalance.get(0).getStoneBalance() + money);
                stoneBalance.get(0).setStoneBalance(stoneBalance.get(0).getStoneBalance() + money);
                stoneBalanceMapper.updateByPrimaryKeySelective(stoneBalance.get(0));
            }
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @ApiOperation(value = "余额查询", notes = "余额查询")
    @RequestMapping(value = "/selectStoneBalance", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectStoneBalance(Integer stoneId,String balanceType)
            throws JSONException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        //
        StoneBalanceExample stoneBalanceExample = new StoneBalanceExample();
        stoneBalanceExample.createCriteria().andStoneIdEqualTo(stoneId).andBalanceTypeEqualTo(balanceType);
        List<StoneBalance> stoneBalance= stoneBalanceMapper.selectByExample(stoneBalanceExample);
        //
        StoneChargeOffExample stoneChargeOffExample = new StoneChargeOffExample();
        stoneChargeOffExample.createCriteria().andStoneIdEqualTo(stoneId).andChargeOffStateEqualTo(1);
        List<StoneChargeOff> stoneChargeOffs= stoneChargeOffMapper.selectByExample(stoneChargeOffExample);
        Integer money = stoneChargeOffs.stream().mapToInt(StoneChargeOff::getActualPrice).sum();

        StoneChargeOffExample stoneChargeOffExample1 = new StoneChargeOffExample();
        stoneChargeOffExample1.createCriteria().andStoneIdEqualTo(stoneId).andChargeOffStateEqualTo(2);
        List<StoneChargeOff> stoneChargeOffs1= stoneChargeOffMapper.selectByExample(stoneChargeOffExample1);
        Integer money1 = stoneChargeOffs1.stream().mapToInt(StoneChargeOff::getActualPrice).sum();
        Map<String,Integer> map = new HashMap<>();
        if (stoneBalance.size()!=0){
            map.put("all",money+stoneBalance.get(0).getStoneBalance());
            map.put("use",stoneBalance.get(0).getStoneBalance());
        } else {
            map.put("all",0);
            map.put("use",0);
        }
        map.put("process",money);
        map.put("payment",money1);
        return builder.body(ResponseUtils.getResponseBody(map));
    }
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }
    @ApiOperation(value = "余额明细", notes = "余额明细")
    @RequestMapping(value = "/selectBalanceDetail", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectBalanceDetail(Integer stoneId, Integer today, Integer yesterday, Integer sevenDays, Integer month , @RequestParam(value = "stateTime",required = false) Date stateTime, @RequestParam(value = "endTime",required = false) Date endTime)
            throws JSONException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        StoneChargeOffExample stoneChargeOffExample = new StoneChargeOffExample();
        stoneChargeOffExample.createCriteria().andStoneIdEqualTo(stoneId).andIsDeletedEqualTo((byte) 0);
        List<StoneChargeOff> stoneChargeOff= stoneChargeOffMapper.selectByExample(stoneChargeOffExample);
//        System.out.println(Duration.between(stoneChargeOff.get(0).getCreateTime(),LocalDateTime.now()).toHours());
        if (today!=null){
            stoneChargeOff=stoneChargeOff.stream().filter(i ->Duration.between(i.getCreateTime(),LocalDateTime.now()).toHours()<=24&&Duration.between(i.getCreateTime(),LocalDateTime.now()).toHours()>=0).collect(Collectors.toList());
        }
        if (yesterday!=null){
            stoneChargeOff=stoneChargeOff.stream().filter(i ->Duration.between(i.getCreateTime(),LocalDateTime.now()).toHours()<=48&&Duration.between(i.getCreateTime(),LocalDateTime.now()).toHours()>=24).collect(Collectors.toList());
        }
        if (sevenDays!=null){
            stoneChargeOff=stoneChargeOff.stream().filter(i ->Duration.between(i.getCreateTime(),LocalDateTime.now()).toHours()<=168&&Duration.between(i.getCreateTime(),LocalDateTime.now()).toHours()>=0).collect(Collectors.toList());
        }
        if (month!=null){
            stoneChargeOff=stoneChargeOff.stream().filter(i ->Duration.between(i.getCreateTime(),LocalDateTime.now()).toHours()<720&&Duration.between(i.getCreateTime(),LocalDateTime.now()).toHours()>0).collect(Collectors.toList());
        }
        if (stateTime!=null && endTime!=null){
            LocalDateTime dateTime1 = LocalDateTime.ofInstant(stateTime.toInstant(),ZoneId.systemDefault());
            LocalDateTime dateTime2 = LocalDateTime.ofInstant(endTime.toInstant(),ZoneId.systemDefault());

            StoneChargeOffExample stoneChargeOffExample1 = new StoneChargeOffExample();
            stoneChargeOffExample1.createCriteria().andStoneIdEqualTo(stoneId).andIsDeletedEqualTo((byte) 0).andCreateTimeGreaterThan(dateTime1).andCreateTimeLessThan(dateTime2);
            stoneChargeOff = stoneChargeOffMapper.selectByExample(stoneChargeOffExample1);
        }
        return builder.body(ResponseUtils.getResponseBody(stoneChargeOff));
    }
}
