package com.hanfu.user.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.user.center.dao.*;
import com.hanfu.user.center.manual.model.VipOrder;
import com.hanfu.user.center.model.*;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.core.util.datetime.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author shihao
 * @Title: HfVipController
 * @ProjectName Second-order-center
 * @Description: 会员-重写部分
 * @date Created in
 * @Version: $
 */
@Api
@CrossOrigin
@RestController
@RequestMapping("/vip")
public class HfVipController {
    @Autowired
    private HfVipMapper hfVipMapper;
    @Autowired
    private HfVipPrivilegeMapper hfVipPrivilegeMapper;
    @Autowired
    private HfVipUserMapper hfVipUserMapper;
    @Autowired
    private HfVipOrderMapper hfVipOrderMapper;
    @Autowired
    private PayOrderMapper payOrderMapper;
    @ApiOperation(value = "添加会员卡",notes = "添加会员卡")
    @RequestMapping(value = "/addVipCard",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "vipName", value = "会员卡名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "vipDay", value = "会员卡天数", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "label", value = "标签", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "fileId", value = "文件id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "price", value = "价格", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "bossId", value = "boss", required = true, type = "Integer"),
    })
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> addVipCard(@RequestParam(required = true,defaultValue = "") String vipName,
                                                        @RequestParam(required = true,defaultValue = "") Integer vipDay,
                                                        @RequestParam(required = true,defaultValue = "") String label,
                                                        @RequestParam(required = true,defaultValue = "") Integer fileId,
                                                        @RequestParam(required = true,defaultValue = "") Integer price,
                                                        @RequestParam(required = true,defaultValue = "") Integer bossId
    ) throws JSONException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        if (bossId !=null){
            HfVip hfVip = new HfVip();
            hfVip.setVipName(vipName);
            hfVip.setBossId(bossId);
            if (!StringUtils.isEmpty(fileId)){
                hfVip.setFileId(fileId);
            }
            if (StringUtils.isEmpty(vipDay)){
                vipDay=1;
            }
            if (StringUtils.isEmpty(price)){
                price=1;
            }
            hfVip.setLable(label);
            hfVip.setVipDay(vipDay);
            hfVip.setVipPrice(price);
            hfVip.setCreateTime(LocalDateTime.now());
            hfVip.setModifyTime(LocalDateTime.now());
            hfVip.setIsDeleted((byte) 0);
            hfVipMapper.insertSelective(hfVip);
            return builder.body(ResponseUtils.getResponseBody("设置成功"));
        }

        return builder.body(ResponseUtils.getResponseBody("设置失败"));
    }
    @ApiOperation(value = "修改会员卡",notes = "修改会员卡")
    @RequestMapping(value = "/updateVipCard",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "vipId", value = "会员卡ID", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "vipName", value = "会员卡名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "vipDay", value = "会员卡天数", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "label", value = "标签", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "fileId", value = "文件id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "price", value = "价格", required = true, type = "Integer"),
    })
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> updateVipCard(
            @RequestParam(required = true,defaultValue = "") Integer vipId,
            @RequestParam(required = true,defaultValue = "") String vipName,
                                                 @RequestParam(required = true,defaultValue = "") Integer vipDay,
                                                 @RequestParam(required = true,defaultValue = "") String label,
                                                 @RequestParam(required = true,defaultValue = "") Integer fileId,
                                                 @RequestParam(required = true,defaultValue = "") Integer price
    ) throws JSONException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
            HfVip hfVip = new HfVip();
            hfVip.setId(vipId);
            hfVip.setVipName(vipName);
            hfVip.setLable(label);
            hfVip.setFileId(fileId);
            hfVip.setVipDay(vipDay);
            hfVip.setVipPrice(price);
            hfVip.setModifyTime(LocalDateTime.now());
            hfVipMapper.updateByPrimaryKeySelective(hfVip);
            return builder.body(ResponseUtils.getResponseBody("修改成功"));

    }
    @ApiOperation(value = "删除会员卡",notes = "删除会员卡")
    @RequestMapping(value = "/deleteVipCard",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "vipId", value = "会员卡ID", required = true, type = "Integer"),
    })
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> deleteVipCard(
            @RequestParam(required = true,defaultValue = "") Integer vipId
    ) throws JSONException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        HfVip hfVip = new HfVip();
        hfVip.setId(vipId);
        hfVip.setModifyTime(LocalDateTime.now());
        hfVip.setIsDeleted((byte) 1);
        hfVipMapper.updateByPrimaryKeySelective(hfVip);
        return builder.body(ResponseUtils.getResponseBody("删除成功"));

    }
    @ApiOperation(value = "查询会员卡",notes = "查询会员卡")
    @RequestMapping(value = "/selectVipCard",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "bossId", value = "boss", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> selectVipCard(
            @RequestParam(required = true,defaultValue = "") Integer bossId
    ) throws JSONException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        HfVipExample hfVipExample = new HfVipExample();
        hfVipExample.createCriteria()
                .andBossIdEqualTo(bossId)
                .andIsDeletedEqualTo((byte) 0);
        return builder.body(ResponseUtils.getResponseBody(hfVipMapper.selectByExample(hfVipExample)));

    }
    @ApiOperation(value = "添加会员特权",notes = "添加会员特权")
    @RequestMapping(value = "/addVipPrivilege",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "privilegeName", value = "特权名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "fileId", value = "文件id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "bossId", value = "boss", required = true, type = "Integer"),
    })
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> addVipPrivilege(
            @RequestParam(required = true,defaultValue = "") String privilegeName,
            @RequestParam(required = true,defaultValue = "") Integer fileId,
            @RequestParam(required = true,defaultValue = "") Integer bossId
    ) throws JSONException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        if (bossId != null){
            HfVipPrivilege hfVipPrivilege = new HfVipPrivilege();
            hfVipPrivilege.setPrivilegeName(privilegeName);
            hfVipPrivilege.setBossId(bossId);
            if (!StringUtils.isEmpty(fileId)){
                hfVipPrivilege.setFileId(fileId);
            }
            hfVipPrivilege.setCreateTime(LocalDateTime.now());
            hfVipPrivilege.setModifyTime(LocalDateTime.now());
            hfVipPrivilege.setIsDeleted((byte) 0);
            hfVipPrivilegeMapper.insertSelective(hfVipPrivilege);
            return builder.body(ResponseUtils.getResponseBody("成功"));
        }
        return builder.body(ResponseUtils.getResponseBody("失败"));
    }
    @ApiOperation(value = "修改会员特权",notes = "修改会员特权")
    @RequestMapping(value = "/updateVipPrivilege",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "privilegeId", value = "特权id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "privilegeName", value = "特权名称", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "fileId", value = "文件id", required = true, type = "Integer"),
    })
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> updateVipPrivilege(
            @RequestParam(required = true,defaultValue = "") Integer privilegeId,
            @RequestParam(required = true,defaultValue = "") String privilegeName,
            @RequestParam(required = true,defaultValue = "") Integer fileId
    ) throws JSONException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
            HfVipPrivilege hfVipPrivilege = new HfVipPrivilege();
            hfVipPrivilege.setId(privilegeId);
            hfVipPrivilege.setPrivilegeName(privilegeName);
            if (!StringUtils.isEmpty(fileId)){
                hfVipPrivilege.setFileId(fileId);
            }
            hfVipPrivilege.setModifyTime(LocalDateTime.now());
            hfVipPrivilege.setIsDeleted((byte) 0);
            hfVipPrivilegeMapper.updateByPrimaryKeySelective(hfVipPrivilege);
        return builder.body(ResponseUtils.getResponseBody("成功"));
    }
    @ApiOperation(value = "删除会员特权",notes = "删除会员特权")
    @RequestMapping(value = "/deleteVipPrivilege",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "privilegeId", value = "特权id", required = true, type = "Integer"),
    })
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> deleteVipPrivilege(
            @RequestParam(required = true,defaultValue = "") Integer privilegeId
    ) throws JSONException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        HfVipPrivilege hfVipPrivilege = new HfVipPrivilege();
        hfVipPrivilege.setId(privilegeId);
        hfVipPrivilege.setModifyTime(LocalDateTime.now());
        hfVipPrivilege.setIsDeleted((byte) 1);
        hfVipPrivilegeMapper.updateByPrimaryKeySelective(hfVipPrivilege);
        return builder.body(ResponseUtils.getResponseBody("成功"));
    }
    @ApiOperation(value = "查询会员特权",notes = "查询会员特权")
    @RequestMapping(value = "/selectVipPrivilege",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "bossId", value = "boss", required = true, type = "Integer"),
    })
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> selectVipPrivilege(
            @RequestParam(required = true,defaultValue = "") Integer bossId
            ) throws JSONException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        HfVipPrivilegeExample hfVipPrivilegeExample = new HfVipPrivilegeExample();
        hfVipPrivilegeExample.createCriteria()
                .andIsDeletedEqualTo((byte) 0)
                .andBossIdEqualTo(bossId);
        return builder.body(ResponseUtils.getResponseBody(hfVipPrivilegeMapper.selectByExample(hfVipPrivilegeExample)));
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
    @ApiOperation(value = "创建会员订单",notes = "创建会员订单")
    @RequestMapping(value = "/addVipOrder",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "bossId", value = "boss", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "vipId", value = "会员卡id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "payName", value = "支付方式", required = true, type = "String"),
    })
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> addVipOrder(
            @RequestParam(required = true,defaultValue = "") Integer userId,
            @RequestParam(required = true,defaultValue = "") Integer bossId,
            @RequestParam(required = true,defaultValue = "") String payName,
            @RequestParam(required = true,defaultValue = "") Integer vipId
    ) throws JSONException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        HfVip hfVip  = hfVipMapper.selectByPrimaryKey(vipId);
        if (hfVip!=null){
            PayOrder payOrder = new PayOrder();
            payOrder.setActualPrice(hfVip.getVipPrice());
            payOrder.setAmount(hfVip.getVipPrice());
            payOrder.setBossId(bossId);
            payOrder.setUserId(userId);
            payOrder.setPayStatus(0);
            payOrder.setPaymentName(payName);
            payOrder.setCreateTime(LocalDateTime.now());
            payOrder.setModifyTime(LocalDateTime.now());
            payOrder.setIsDeleted((byte) 0);
            payOrderMapper.insertSelective(payOrder);
            HfVipOrder hfVipOrder = new HfVipOrder();
            hfVipOrder.setUserId(userId);
            hfVipOrder.setBossId(bossId);
            hfVipOrder.setVipId(vipId);
            hfVipOrder.setAmount(hfVip.getVipPrice());
            hfVipOrder.setOrderCode(getC(UUID.randomUUID().toString()));
            hfVipOrder.setPayStatus(VipOrder.PaymentStatus.UNPAID.getPaymentStatus());
            hfVipOrder.setOrderStatus(VipOrder.OrderStatus.PAYMENT.getOrderStatus());
            hfVipOrder.setPaymentName(payName);
            hfVipOrder.setCreateTime(LocalDateTime.now());
            hfVipOrder.setModifyTime(LocalDateTime.now());
            hfVipOrder.setIsDeleted((byte) 0);
            hfVipOrderMapper.insertSelective(hfVipOrder);
            return builder.body(ResponseUtils.getResponseBody(payOrder));
        }
        return builder.body(ResponseUtils.getResponseBody("失败"));
    }
    @ApiOperation(value = "查询会员订单",notes = "查询会员订单")
    @RequestMapping(value = "/selectVipOrder",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "bossId", value = "boss", required = true, type = "Integer"),
    })
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> selectVipOrder(
            @RequestParam(required = true,defaultValue = "") Integer userId,
            @RequestParam(required = true,defaultValue = "") Integer bossId
    ) throws JSONException {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        HfVipOrderExample hfVipOrderExample = new HfVipOrderExample();
        HfVipOrderExample.Criteria criteria = hfVipOrderExample.createCriteria().andIsDeletedEqualTo((byte) 0);
        if (userId!=null){
            criteria.andUserIdEqualTo(userId);
        }
        if (bossId!=null){
            criteria.andBossIdEqualTo(bossId);
        }
        return builder.body(ResponseUtils.getResponseBody("失败"));
    }
}
