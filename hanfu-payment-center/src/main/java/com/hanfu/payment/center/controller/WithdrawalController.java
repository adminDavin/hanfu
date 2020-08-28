package com.hanfu.payment.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.payment.center.dao.WithdrawalMapper;
import com.hanfu.payment.center.manual.model.WithdrawalType;
import com.hanfu.payment.center.model.HfUserBalance;
import com.hanfu.payment.center.model.Withdrawal;
import com.hanfu.payment.center.model.WithdrawalExample;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.core.util.datetime.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@Api
@RequestMapping("/Withdrawal")
@CrossOrigin
public class WithdrawalController {
    @Autowired
    private WithdrawalMapper withdrawalMapper;



    @ApiOperation(value = "取款申请", notes = "取款申请")
    @RequestMapping(value = "/withdrawalApply", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "money", value = "申请金额", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "account", value = "申请账号", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "申请用户id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "stoneId", value = "申请店铺id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "stoneId", value = "申请类型", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "name", value = "名称", required = true, type = "String"),
    })
    public ResponseEntity<JSONObject> withdrawalApply(HttpServletRequest request, Integer money, String account , Integer userId, Integer stoneId, String type, String name)
            throws JSONException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        Integer bossId = (Integer)request.getServletContext().getAttribute("bossId");
        if (bossId!=null){
            Withdrawal withdrawal = new Withdrawal();
            withdrawal.setBossId(bossId);
            withdrawal.setUserId(userId);
            withdrawal.setStoneId(stoneId);
            withdrawal.setWithdrawalType(WithdrawalType.DiscoverTypeEnum.getDiscoverTypeEnum(type).getDiscoverType());
            withdrawal.setWithdrawalAccount(account);
            withdrawal.setWithdrawalName(name);
            withdrawal.setWithdrawalState(WithdrawalType.DiscoverStateEnum.PENDING.getDiscoverType());
            withdrawal.setWithdrawalMoney(money);
            withdrawal.setWithdrawalMark(getC(null));
            withdrawal.setIsDeleted(0);
            withdrawal.setCreateDate(LocalDateTime.now());
            withdrawal.setModifyDate(LocalDateTime.now());
            withdrawalMapper.insertSelective(withdrawal);
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "取款申请列表", notes = "取款申请列表")
    @RequestMapping(value = "/withdrawalApplyList", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> withdrawalApplyList(HttpServletRequest request)
            throws JSONException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        Integer bossId = (Integer)request.getServletContext().getAttribute("bossId");
        List<Withdrawal> withdrawalList = new ArrayList<>();
        if (bossId!=null){
            WithdrawalExample withdrawalExample = new WithdrawalExample();
            withdrawalExample.createCriteria().andBossIdEqualTo(bossId).andIsDeletedEqualTo(0);
            withdrawalList = withdrawalMapper.selectByExample(withdrawalExample);
        }
        return builder.body(ResponseUtils.getResponseBody(withdrawalList));
    }
    private static final FastDateFormat pattern = FastDateFormat.getInstance("yyyyMMddHHmmss");
    private static final AtomicInteger atomicInteger = new AtomicInteger(1);
    private static ThreadLocal<StringBuilder> threadLocal = new ThreadLocal<StringBuilder>();
    public static String getC(String lock) {
        lock = Objects.isNull(lock) ? UUID.randomUUID().toString() : lock;
        StringBuilder builder = new StringBuilder(pattern.format(Instant.now().toEpochMilli()));// 取系统当前时间作为订单号前半部分
        builder.append(Math.abs(lock.hashCode()));// HASH-CODE
        builder.append(atomicInteger.getAndIncrement());// 自增顺序
        threadLocal.set(builder);
        return threadLocal.get().toString();
    }
}
