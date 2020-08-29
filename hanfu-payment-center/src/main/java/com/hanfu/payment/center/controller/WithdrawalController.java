package com.hanfu.payment.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.payment.center.dao.WithdrawalAuditMapper;
import com.hanfu.payment.center.dao.WithdrawalMapper;
import com.hanfu.payment.center.dao.WithdrawalMethodMapper;
import com.hanfu.payment.center.manual.model.WithdrawalType;
import com.hanfu.payment.center.manual.model.Withdrawals;
import com.hanfu.payment.center.model.*;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.core.util.datetime.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@Api
@RequestMapping("/Withdrawal")
@CrossOrigin
public class WithdrawalController {
    @Autowired
    private WithdrawalMapper withdrawalMapper;
    @Autowired
    private WithdrawalMethodMapper withdrawalMethodMapper;
    @Autowired
    private WithdrawalAuditMapper withdrawalAuditMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @ApiOperation(value = "取款申请", notes = "取款申请")
    @RequestMapping(value = "/withdrawalApply", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "money", value = "申请金额", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "account", value = "申请账号", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "申请用户id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "stoneId", value = "申请店铺id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "type", value = "type", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "name", value = "名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "methodId", value = "取款方式id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> withdrawalApply(HttpServletRequest request, Integer money, String account , Integer userId, Integer stoneId, String type, String name, Integer methodId)
            throws JSONException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        Integer bossId = (Integer)request.getServletContext().getAttribute("bossId");
        if (bossId!=null){

            WithdrawalMethod withdrawalMethod = withdrawalMethodMapper.selectByPrimaryKey(methodId);
            Integer realityMoney = 0;
            if (withdrawalMethod.getWithdrawalCommission()!=null){
                realityMoney = (money*withdrawalMethod.getWithdrawalCommission())/100;
            }
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
            withdrawal.setMethodId(methodId);
            withdrawal.setRealityMoney(realityMoney);
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

        List<Withdrawals> withdrawalsList = new ArrayList<>();
        if (bossId!=null){
            List<Withdrawal> withdrawalList = new ArrayList<>();
            WithdrawalExample withdrawalExample = new WithdrawalExample();
            withdrawalExample.createCriteria()
                    .andBossIdEqualTo(bossId)
                    .andIsDeletedEqualTo(0)
                    .andWithdrawalStateEqualTo(WithdrawalType.DiscoverStateEnum.PENDING.getDiscoverType());
            withdrawalList = withdrawalMapper.selectByExample(withdrawalExample);

            withdrawalList.forEach(a->{
                WithdrawalMethod withdrawalMethod = withdrawalMethodMapper.selectByPrimaryKey(a.getMethodId());
                Withdrawals withdrawals = new Withdrawals();
                        if(a.getCreateDate()!=null){
                            withdrawals.setCreateDate(a.getCreateDate());
                        }
                        if (!StringUtils.isEmpty(a.getId())){
                            withdrawals.setId(a.getId());
                        }
                        if (!StringUtils.isEmpty(a.getPhone())){
                            withdrawals.setPhone(a.getPhone());
                        }
                        if (!StringUtils.isEmpty(a.getWithdrawalAccount())){
                            withdrawals.setWithdrawalAccount(a.getWithdrawalAccount());
                        }
                        if (!StringUtils.isEmpty(a.getWithdrawalMoney())){
                            withdrawals.setWithdrawalMoney(a.getWithdrawalMoney());
                        }
                        if (!StringUtils.isEmpty(a.getRealityMoney())){
                            withdrawals.setRealityMoney(a.getRealityMoney());
                        }
                        if (!StringUtils.isEmpty(a.getWithdrawalName())){
                            withdrawals.setWithdrawalName(a.getWithdrawalName());
                        }
                        if (!StringUtils.isEmpty(a.getWithdrawalMark())){
                            withdrawals.setWithdrawalMark(a.getWithdrawalMark());
                        }
                        if (!StringUtils.isEmpty(a.getWithdrawalState())){
                            withdrawals.setWithdrawalState(a.getWithdrawalState());
                        }
                        if (!StringUtils.isEmpty(withdrawalMethod.getCommissionType())){
                            withdrawals.setCommissionType(withdrawalMethod.getCommissionType());
                        }
                        if (!StringUtils.isEmpty(withdrawalMethod.getWithdrawalWayName())){
                            withdrawals.setWithdrawalWayName(withdrawalMethod.getWithdrawalWayName());
                        }
                        if (!StringUtils.isEmpty(withdrawalMethod.getCommissionType())){
                            withdrawals.setCommissionType(withdrawalMethod.getCommissionType());
                        }
                withdrawalsList.add(withdrawals);
            });
        }

        return builder.body(ResponseUtils.getResponseBody(withdrawalsList));
    }

    @ApiOperation(value = "处理取款申请", notes = "处理取款申请")
    @RequestMapping(value = "/disposeWithdrawalApply", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "WithdrawalId", value = "取款申请id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "type", value = "处理类型", required = true, type = "String"),
    })
    public ResponseEntity<JSONObject> disposeWithdrawalApply(Integer WithdrawalId, String type, Integer userId)
            throws JSONException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);

        if (redisTemplate.opsForValue().get(String.valueOf(WithdrawalId) + "disposeWithdrawalApply")!=null){
            WithdrawalExample withdrawalExample = new WithdrawalExample();
            withdrawalExample.createCriteria().andIdEqualTo(WithdrawalId).andWithdrawalStateEqualTo(WithdrawalType.DiscoverStateEnum.PENDING.getDiscoverType());
            List<Withdrawal> withdrawalList = withdrawalMapper.selectByExample(withdrawalExample);
            if (withdrawalList.size()!=0){
                Withdrawal withdrawal = new Withdrawal();
                withdrawal.setId(WithdrawalId);
                withdrawal.setWithdrawalState(WithdrawalType.DiscoverStateEnum.getDiscoverTypeEnum(type).getDiscoverType());
                withdrawalMapper.updateByPrimaryKeySelective(withdrawal);
                WithdrawalAudit withdrawalAudit = new WithdrawalAudit();
                withdrawalAudit.setWithdrawalId(WithdrawalId);
                withdrawalAudit.setAuditState(WithdrawalType.DiscoverStateEnum.getDiscoverTypeEnum(type).getDiscoverType());
                withdrawalAudit.setCreateDate(LocalDateTime.now());
                withdrawalAudit.setModifyDate(LocalDateTime.now());
                withdrawalAudit.setIsDeleted(0);
                withdrawalAudit.setApprover(userId);
                withdrawalAuditMapper.insertSelective(withdrawalAudit);
                redisTemplate.opsForValue().set(String.valueOf(WithdrawalId) + "disposeWithdrawalApply", WithdrawalId);
                redisTemplate.expire(String.valueOf(WithdrawalId) + "disposeWithdrawalApply", 3, TimeUnit.SECONDS);
                return builder.body(ResponseUtils.getResponseBody(0));
            }
        }
        return builder.body(ResponseUtils.getResponseBody(1));
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
