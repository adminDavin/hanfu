package com.hanfu.payment.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.hanfu.payment.center.AppPaymentService.AlipayService;
import com.hanfu.payment.center.config.AlipayConfig;
import com.hanfu.payment.center.config.AppProgramConfig;
import com.hanfu.payment.center.config.MiniProgramConfig;
import com.hanfu.payment.center.dao.*;
import com.hanfu.payment.center.manual.dao.HfOrderDao;
import com.hanfu.payment.center.manual.model.*;
import com.hanfu.payment.center.model.*;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author shihao
 * @Title: PaymentVipOrderController
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
@CrossOrigin
@RestController
@RequestMapping("/vip-payment/")
@Api
public class PaymentVipOrderController {
    @Autowired
    private MiniProgramConfig miniProgramConfig;
    @Autowired
    private HfOrderDao hfOrderDao;
    @Autowired
    private PayOrderMapper payOrderMapper;
    @Autowired
    private HfVipMapper hfVipMapper;
    @Autowired
    private HfVipPrivilegeMapper hfVipPrivilegeMapper;
    @Autowired
    private HfVipUserMapper hfVipUserMapper;
    @Autowired
    private HfVipOrderMapper hfVipOrderMapper;
    @Autowired
    HttpServletRequest req;
    @Autowired
    private HfTansactionFlowMapper hfTansactionFlowMapper;
    @Autowired
    private HfBalanceDetailMapper hfBalanceDetailMapper;
    @Autowired
    private HfUserBalanceMapper hfUserBalanceMapper;
    //app微信支付
    @Autowired
    private AppProgramConfig appProgramConfig;
    //支付宝app
    @Autowired
    private AlipayConfig alipayConfig;
    @Autowired
    private HfBossMapper hfBossMapper;
    @Autowired
    private AlipayService alipayService;
    @ApiOperation(value = "支付订单", notes = "")
    @RequestMapping(value = "/order", method = RequestMethod.GET)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer") })
    public ResponseEntity<JSONObject> payment(Integer userId, Integer payOrderId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		HfOrderDisplay hfOrder = hfOrderDao.selectHfOrderbyCode(outTradeNo);
//		Integer.valueOf((String) req.getServletContext().getAttribute("bossId"))

        HfUser hfUser = hfOrderDao.selectHfUser(userId);
        PayOrder payOrder= payOrderMapper.selectByPrimaryKey(payOrderId);
        HfVipOrderExample hfVipOrderExample = new HfVipOrderExample();
        hfVipOrderExample.createCriteria().andPayOrderIdEqualTo(payOrder.getId());
        String payName = hfVipOrderMapper.selectByExample(hfVipOrderExample).get(0).getPaymentName();
        Map<String, String> resp = null;
        if (PaymentTypeEnum.getPaymentTypeEnum(payName).equals(PaymentTypeEnum.WECHART)) {
            miniProgramConfig.setBossId(Integer.valueOf((String) req.getServletContext().getAttribute("bossId")));
            resp = wxPay(hfUser, payOrder);
        } else if(PaymentTypeEnum.getPaymentTypeEnum(payName).equals(PaymentTypeEnum.BALANCE)){
            resp = balancePay(hfUser, payOrder);
        } else if (PaymentTypeEnum.getPaymentTypeEnum(payName).equals(PaymentTypeEnum.APPCHART)){
//			resp = AppCHPay(hfUser, payOrder);
            appProgramConfig.setBossId(Integer.valueOf((String) req.getServletContext().getAttribute("bossId")));
            resp = wxAppPay(hfUser, payOrder);
        } else if(PaymentTypeEnum.getPaymentTypeEnum(payName).equals(PaymentTypeEnum.APPALIPAY)){
            Integer boss = Integer.valueOf((String) req.getServletContext().getAttribute("bossId"));
            alipayConfig.setBossId(boss);
            resp = appalipay(hfUser, payOrder,boss);
            System.out.println("app");
        }
        System.out.println(resp);
        return builder.body(ResponseUtils.getResponseBody(resp));
    }
    private Map<String, String> wxPay(HfUser hfUser, PayOrder payOrder) throws Exception {
//		MiniProgramConfig config = new MiniProgramConfig();
        Map<String, String> data = getWxPayData(miniProgramConfig, hfUser.getAuthKey(), String.valueOf(payOrder.getId()),payOrder.getAmount());
        WXPay wxpay = new WXPay(miniProgramConfig);
        Map<String, String> resp = wxpay.unifiedOrder(data);
        if ("SUCCESS".equals(resp.get("return_code"))) {
            Map<String, String> reData = new HashMap<>();
            reData.put("appId", miniProgramConfig.getAppID());
            reData.put("nonceStr", resp.get("nonce_str"));
            String newPackage = "prepay_id=" + resp.get("prepay_id");
            reData.put("package", newPackage);
            reData.put("signType", "MD5");
            reData.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));

            String newSign = WXPayUtil.generateSignature(reData, miniProgramConfig.getKey());
            resp.put("paySign", newSign);
            resp.put("package", reData.get("package"));
            resp.put("signType", reData.get("signType"));
            resp.put("timeStamp", reData.get("timeStamp"));
            recordTransactionFlow(hfUser, payOrder, data, reData);
            return resp;
        } else {
            throw new Exception(resp.get("return_msg"));
        }

    }
    private Map<String, String> getWxPayData(MiniProgramConfig config, String openId, String orderCode,Integer Amount)
            throws Exception {
        Map<String, String> data = new HashMap<>();
        data.put("appid", config.getAppID());
        data.put("mch_id", config.getMchID());
        data.put("body", "订单支付");
        data.put("out_trade_no", orderCode);
        data.put("device_info", req.getRemoteHost());
        data.put("fee_type", "CNY");
        data.put("total_fee", String.valueOf(Amount));
        data.put("spbill_create_ip", req.getRemoteAddr());
        data.put("notify_url", "https://swcloud.tjsichuang.cn:1445/api/payment/hf-payment/handleWxpay");
        data.put("trade_type", "JSAPI");
        data.put("openid", openId);
        String sign = WXPayUtil.generateSignature(data, config.getKey());
        data.put("sign", sign);
        return data;
    }

    private void recordTransactionFlow(HfUser hfUser, PayOrder payOrder, Map<String, String> data,
                                       Map<String, String> reData) {
//        HfVipOrderExample hfVipOrderExample = new HfVipOrderExample();
//        hfVipOrderExample.createCriteria().andPayOrderIdEqualTo(payOrder.getId());
//        List<HfVipOrder> hfVipOrders= hfVipOrderMapper.selectByExample(hfVipOrderExample);
        HfTansactionFlowExample e = new HfTansactionFlowExample();
        e.createCriteria().andOutTradeNoEqualTo(data.get("out_trade_no"))
                .andHfStatusEqualTo(TansactionFlowStatusEnum.PROCESS.getStatus());
        List<HfTansactionFlow> hfTansactionFlows = hfTansactionFlowMapper.selectByExample(e);

        if (hfTansactionFlows.isEmpty()) {
            HfTansactionFlow t = completeHfTansactionFlow(new HfTansactionFlow(), hfUser, payOrder, data, reData);
            hfTansactionFlowMapper.insertSelective(t);
        } else {
            HfTansactionFlow t = completeHfTansactionFlow(hfTansactionFlows.get(0), hfUser, payOrder, data, reData);
            hfTansactionFlowMapper.updateByPrimaryKey(t);
        }
    }
    private HfTansactionFlow completeHfTansactionFlow(HfTansactionFlow t, HfUser hfUser, PayOrder payOrder,
                                                      Map<String, String> data, Map<String, String> reData) {
        LocalDateTime current = LocalDateTime.now();
//        HfVipOrderExample hfVipOrderExample = new HfVipOrderExample();
//        hfVipOrderExample.createCriteria().andPayOrderIdEqualTo(payOrder.getId());
//        List<HfVipOrder> hfVipOrders= hfVipOrderMapper.selectByExample(hfVipOrderExample);
        t.setAppId(data.get("appid"));
        t.setCreateDate(current);
        t.setDeviceInfo(data.get("device_info"));
        t.setFeeType(data.get("fee_type"));
        t.setMchId(data.get("mch_id"));
        t.setModifyDate(current);
        t.setNotifyUrl(data.get("notify_url"));
        t.setOpenId(hfUser.getAuthKey());
        t.setOutTradeNo(data.get("out_trade_no"));
        t.setSigntype(reData.get("signType"));
        t.setSpbillCreateIp(data.get("spbill_create_ip"));
        t.setTotalFee(data.get("total_fee"));
        t.setTradeType(data.get("trade_type"));
//        t.setTransactionType(hfVipOrders.get(0).getOrderType());
        t.setHfStatus(TansactionFlowStatusEnum.PROCESS.getStatus());
        t.setUserId(hfUser.getUserId());
        t.setWechartBody(data.get("body"));
        t.setWechartPackage("package");
        return t;
    }
    //余额支付
    private synchronized Map<String, String> balancePay(HfUser hfUser, PayOrder payOrder) throws Exception {
//		MiniProgramConfig config = new MiniProgramConfig();
//		Map<String, String> data = getWxPayData(config, hfUser.getAuthKey(), hfOrder.getOrderCode());
//		WXPay wxpay = new WXPay(config);
//		Map<String, String> resp = wxpay.unifiedOrder(data);


        HfUserBalanceExample hfUserBalanceExample = new HfUserBalanceExample();
        hfUserBalanceExample.createCriteria().andUserIdEqualTo(hfUser.getUserId()).andBalanceTypeEqualTo("rechargeAmount");
        List<HfUserBalance> hfUserBalance= hfUserBalanceMapper.selectByExample(hfUserBalanceExample);

        if (hfUserBalance.size()!=0&&payOrder.getAmount()<hfUserBalance.get(0).getHfBalance()){
            HfUserBalance hfUserBalance1 = new HfUserBalance();
            hfUserBalance1.setId(hfUserBalance.get(0).getId());
            hfUserBalance1.setModifyTime(LocalDateTime.now());
//				hfUserBalance1.setLastModifier(hfUser.getAuthKey());
            hfUserBalance1.setHfBalance(hfUserBalance.get(0).getHfBalance()-payOrder.getAmount());
            hfUserBalanceMapper.updateByPrimaryKeySelective(hfUserBalance1);
            HfBalanceDetail detail = new HfBalanceDetail();
            detail.setUserId(hfUser.getUserId());
            detail.setAmount(String.valueOf(payOrder.getAmount()));
            detail.setPaymentName("会员");
            detail.setCreateTime(LocalDateTime.now());
            detail.setModifyTime(LocalDateTime.now());
            detail.setIsDeleted((byte) 0);
            hfBalanceDetailMapper.insert(detail);
        } else {
            throw new Exception("return_msg");
        }
        // TODO Auto-generated method stub
        return null;
    }
    //
    //微信app支付
    private Map<String, String> wxAppPay(HfUser hfUser, PayOrder payOrder) throws Exception {
//	MiniProgramConfig miniProgramConfig = new MiniProgramConfig();
        Map<String, String> data = getWxPayData1(appProgramConfig, String.valueOf(payOrder.getId()),payOrder.getAmount());
//        logger.info(JSONObject.toJSONString(data));
        WXPay wxpay = new WXPay(appProgramConfig);
        Map<String, String> resp = wxpay.unifiedOrder(data);
        System.out.println(resp);
//        logger.info(JSONObject.toJSONString(resp));
        if ("SUCCESS".equals(resp.get("return_code"))) {
            System.out.println(1);
            resp.put("appid", resp.get("appid"));
            resp.put("noncestr", resp.get("nonce_str"));
            if ("SUCCESS".equals(resp.get("result_code"))) {//resultCode 为SUCCESS，才会返回prepay_id和trade_type
                System.out.println(2);
                Map<String, String> reData = new HashMap<>();
                reData.put("appId", appProgramConfig.getAppID());
                reData.put("nonceStr", resp.get("nonce_str"));
                reData.put("package", "Sign=WXPay");
//                reData.put("signType", "MD5");
                reData.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
                reData.put("partnerid", resp.get("mch_id"));
                reData.put("prepayid", resp.get("prepay_id"));
                String sign = WXPayUtil.generateSignature(reData, appProgramConfig.getKey());// 二次签名
                System.out.println(sign);
                resp.put("sign",sign.substring(0, 30)); //签名
                resp.put("trade_type", resp.get("trade_type"));//获取预支付交易回话标志
                resp.put("package","Sign=WXPay");
                resp.put("partnerid", resp.get("mch_id"));
                resp.put("prepayid", resp.get("prepay_id"));
                resp.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
                recordTransactionFlow(hfUser, payOrder, data, reData);
                return resp;
            } else {
                //此时返回没有预付订单的数据
                return resp;
            }
        } else {
            throw new Exception(resp.get("return_msg"));
        }

    }
    private Map<String, String> getWxPayData1(AppProgramConfig config, String orderCode,Integer Amount)
            throws Exception {
        Map<String, String> data = new HashMap<>();
        data.put("appid", config.getAppID());
        data.put("mch_id", config.getMchID());
        data.put("body", "订单支付");
        data.put("out_trade_no", orderCode);
        data.put("device_info", req.getRemoteHost());
        data.put("fee_type", "CNY");
        data.put("total_fee", String.valueOf(Amount));
        data.put("spbill_create_ip", req.getRemoteAddr());
        data.put("notify_url", "https://swcloud.tjsichuang.cn:1445/api/payment/hf-payment/handleWxpay");
        data.put("trade_type", "APP");
//        data.put("signType", "MD5");
//        data.put("openid", openId);
        String sign = WXPayUtil.generateSignature(data, config.getKey());
        System.out.println(sign);
        data.put("sign", sign);
//        logger.info(JSONObject.toJSONString(data));
        return data;
    }
    //app支付宝支付
    private Map<String ,String> appalipay(HfUser hfUser,PayOrder payOrder,Integer bossId) throws AlipayApiException {
        HfBoss hfBoss = hfBossMapper.selectByPrimaryKey(bossId);
        String orderStr = alipayService.createOrder(String.valueOf(payOrder.getId()), payOrder.getAmount(), hfBoss.getName());
        Map<String, String> resp = new HashMap<>();
        resp.put("data", orderStr);
        return resp;
    }
    @ApiOperation(value = "完成支付", notes = "")
    @RequestMapping(value = "/complete", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer")})
    public synchronized ResponseEntity<JSONObject> completePaymentAfter(
            @RequestParam("userId") Integer userId,
             Integer payOrderId)
            throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfVipOrderExample hfVipOrderExample = new HfVipOrderExample();
        hfVipOrderExample.createCriteria().andPayOrderIdEqualTo(payOrderId);
        List<HfVipOrder> hfVipOrders= hfVipOrderMapper.selectByExample(hfVipOrderExample);
        for (HfVipOrder hfVipOrder:hfVipOrders){
            HfVip hfVip = hfVipMapper.selectByPrimaryKey(hfVipOrder.getVipId());
            hfVipOrder.setModifyTime(LocalDateTime.now());
            hfVipOrder.setOrderStatus(VipOrder.OrderStatus.COMPLETE.getOrderStatus());
            hfVipOrder.setPayStatus(VipOrder.PaymentStatus.PAID.getPaymentStatus());
            hfVipOrderMapper.updateByPrimaryKeySelective(hfVipOrder);
            HfVipUserExample hfVipUserExample = new HfVipUserExample();
            hfVipUserExample.createCriteria()
                    .andUserIdEqualTo(userId)
                    .andIsDeletedEqualTo((byte) 0);
            List<HfVipUser> hfVipUser = hfVipUserMapper.selectByExample(hfVipUserExample);
            if (hfVipUser.size() == 0){
                HfVipUser hfVipUser1 = new HfVipUser();
                hfVipUser1.setBossId(hfVipOrder.getBossId());
                hfVipUser1.setVipDay(hfVip.getVipDay());
                hfVipUser1.setUserId(userId);
                hfVipUser1.setStartTime(LocalDateTime.now());
                //计算结束天数
                hfVipUser1.setEndTime(dateEnd(hfVip.getVipDay(),LocalDateTime.now()));
                hfVipUser1.setVipId(hfVip.getId());
                hfVipUser1.setCreateTime(LocalDateTime.now());
                hfVipUser1.setModifyTime(LocalDateTime.now());
                hfVipUser1.setIsDeleted((byte) 0);
                hfVipUserMapper.insertSelective(hfVipUser1);
            } else {
                HfVipUser hfVipUser1 = new HfVipUser();
                hfVipUser1.setId(hfVipUser.get(0).getId());
                hfVipUser1.setVipDay(hfVipUser.get(0).getVipDay()+hfVip.getVipDay());
                //计算结束天数
                hfVipUser1.setEndTime(dateEnd(hfVip.getVipDay(),hfVipUser.get(0).getEndTime()));
                hfVipUser1.setVipId(hfVip.getId());
                hfVipUser1.setModifyTime(LocalDateTime.now());
                hfVipUserMapper.updateByPrimaryKeySelective(hfVipUser1);
            }
        }

        return builder.body(ResponseUtils.getResponseBody(0));
    }
//
private LocalDateTime dateEnd(Integer num, LocalDateTime localDateTime) throws Exception {
    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String localTime = df.format(localDateTime);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date currdate = format.parse(localTime);
    System.out.println("现在的日期是：" + currdate);
    Calendar ca = Calendar.getInstance();
    ca.setTime(currdate);
    System.out.println(Calendar.DATE);
    ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
    currdate = ca.getTime();
    System.out.println(currdate);
    String enddate = format.format(currdate);
    LocalDateTime ldt = LocalDateTime.parse(enddate,df);
        return ldt;
}
    public static void main(String[] args) throws ParseException {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String localTime = df.format(LocalDateTime.now());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currdate = format.parse("2020-11-30 22:17:01");
        System.out.println("现在的日期是：" + currdate);
        Calendar ca = Calendar.getInstance();
        ca.setTime(currdate);
        System.out.println(Calendar.DATE);
        ca.add(Calendar.DATE, 1);// num为增加的天数，可以改变的
        currdate = ca.getTime();
        System.out.println(currdate);
        String enddate = format.format(currdate);
        System.out.println("增加天数以后的日期：" + enddate);
    }
}
