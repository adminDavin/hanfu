package com.hanfu.shejiao.center.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanfu.shejiao.center.config.TenpayConfig;
import com.hanfu.shejiao.center.manual.model.PayTypeEnum;
import com.hanfu.shejiao.center.manual.model.TenPayVO;
import com.hanfu.shejiao.center.service.PayService;
import com.hanfu.shejiao.center.utils.TenPayUtils;

@Service
public class PayServiceImpl implements PayService{
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private TenpayConfig tenpayConfig;
	
	/**
     * @Description: 财付通回调
     * @Param: [request]
     * @return: TenPayVO
     * @Author: zengXianKang
     * @Date: 2019/7/28
     */
    @Override
    public TenPayVO tenPayCallBack(HttpServletRequest request) throws Exception {
        InputStream inputStream = request.getInputStream();
        StringBuffer resXml = new StringBuffer();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            resXml.append(str);
        }
        bufferedReader.close();
        inputStream.close();
        //logger.info("微信回调报文: " + resXml);
        TenPayVO tenPayVO = this.tenPayCallBackInfo(resXml.toString(), "xml", "");
        return tenPayVO;
    }
    
    /**
     * @Description: 微信支付回调返回结果
     * @Param: [xml, rootName, rowName]
     * @return: com.huaku.ecom.system.model.vo.TenPayVO
     * @Author: zengXianKang
     * @Date: 2019/7/28
     */
    private TenPayVO tenPayCallBackInfo(String xml, String rootName, String rowName) throws Exception {
        Map<String, Object> resHashMap = (Map<String, Object>) TenPayUtils.readXml(xml, "xml", "");
        SortedMap<String, Object> resMap = new TreeMap<String, Object>(resHashMap);
        //微信返回状态码
        if (!resMap.get("return_code").equals("SUCCESS")) {
            logger.error("微信支付回调连接失败: " + resMap.get("return_msg"));
//            throw new RRException(AppWSConstant.RS_MSG_TENPAY_FALL);
        }
        //业务结果
        if (!resMap.get("result_code").equals("SUCCESS")) {
            logger.error("err_code: " + resMap.get("err_code"), "err_code_des: " + resMap.get("err_code_des"));
//            throw new RRException(AppWSConstant.RS_MSG_TENPAY_FALL);
        }
        TenPayUtils tenPayUtils = new TenPayUtils();
        //校验签名
        String sign = tenPayUtils.createSign(resMap, "UTF-8");
        if (!sign.equals(resMap.get("sign"))) {
            logger.error("微信支付回调签名不正确");
//            throw new RRException(AppWSConstant.RS_MSG_TENPAY_FALL);
        }
        TenPayVO tenPayVO = new TenPayVO();
        //商户订单号
        tenPayVO.setOutTradeNo((String) resMap.get("out_trade_no"));
        //业务结果
        tenPayVO.setResultCode((String) resMap.get("result_code"));
        //签名方式
        tenPayVO.setSignType("ASCII");
        //签名
        tenPayVO.setSign((String) resMap.get("sign"));
        //交易类型
        tenPayVO.setTradeType("APP");
        //交易状态
        tenPayVO.setTradeState((String) resMap.get("trade_state"));
        //商户号
        tenPayVO.setMchId((String) resMap.get("mch_id"));
        //付款银行
        tenPayVO.setBankType((String) resMap.get("bank_type"));
        //交易金额
        BigDecimal totalFee = new BigDecimal((String) resMap.get("total_fee"));
        totalFee = totalFee.divide(new BigDecimal(100));
        tenPayVO.setTotalFee(totalFee);
        //币种
        if (resMap.containsKey("fee_type")) {
            tenPayVO.setFeeType((String) resMap.get("fee_type"));
        }
        //微信支付订单号
        tenPayVO.setTransactionId((String) resMap.get("transaction_id"));
        //支付完成时间
        tenPayVO.setTimeEnd((String) resMap.get("time_end"));
        return tenPayVO;
    }
    
    
//    /**
//     * @Description: 微信支付订单回调
//     * @Param: [tenPayVO]
//     * @return: void
//     * @Author: zengXianKang
//     * @Date: 2019/7/28
//     */
//    @Override
//    public void tenPayOrderCallBack(TenPayVO tenPayVO) throws Exception {
//        if (tenPayVO != null && tenPayVO.getResultCode().equals("SUCCESS") && tenPayVO.getTradeState().equals("SUCCESS")) {
//            //根据交易编号加锁，处理高并发
//            synchronized (tenPayVO.getOutTradeNo()) {
//                TOrder order = orderMapper.getOneOrderByPayNo(tenPayVO.getOutTradeNo());
//                if (order.getOrderStatus().equals(OrderStatusEnum.PENDING_PAYMENT.toString())) {
//                    //订单需支付金额总和
//                    BigDecimal payNumSum = this.getPayNumSumByPayNo(tenPayVO.getOutTradeNo());
//                    String orderStatus = "";
//                    //以防万一，再次校验金额
//                    if (payNumSum.compareTo(tenPayVO.getTotalFee()) != 0) {
//                        logger.error("***订单号: " + tenPayVO.getOutTradeNo() + "***微信支付支付金额与订单需支付金额总和不一致***微信支付金额为:" + tenPayVO.getTotalFee() + " ***订单需支付金额总为:" + payNumSum + "***日期:" + new Date());
//                        //金额异常，订单状态为支付金额异常
//                        orderStatus = OrderStatusEnum.ABNORMAL_PAYMENT_AMOUNT.toString();
//                    } else {
//                        //金额正常，订单状态为已付款（待发货）
//                        orderStatus = OrderStatusEnum.WAIT_FOR_DELIVERY.toString();
//                    }
//                    //修改订单状态
//                    int orderFlag = orderMapper.updatePayOrderStatusByPayNo(orderStatus, tenPayVO.getOutTradeNo());
//                    //微信支付交易记录表
//                    TTenpayTradeLog tenpayTradeLog = new TTenpayTradeLog();
//                    tenpayTradeLog.setTradeLogId(Convert.createUniqueId(idWorker));
//                    //签名方式
//                    tenpayTradeLog.setSignType(tenPayVO.getSignType());
//                    //交易方式
//                    tenpayTradeLog.setTradeMode(tenPayVO.getTradeType());
//                    //交易状态
//                    tenpayTradeLog.setTradeStatus(tenPayVO.getResultCode());
//                    //商户号
//                    tenpayTradeLog.setPartner(tenPayVO.getMchId());
//                    //银行类型
//                    tenpayTradeLog.setBankType(tenPayVO.getBankType());
//                    //交易金额
//                    tenpayTradeLog.setTotalFee(tenPayVO.getTotalFee());
//                    //币种
//                    tenpayTradeLog.setFeeType(tenPayVO.getFeeType());
//                    //微信支付订单号
//                    tenpayTradeLog.setTransactionId(tenPayVO.getTransactionId());
//                    //商户订单号
//                    tenpayTradeLog.setOutTradeNo(tenPayVO.getOutTradeNo());
//                    //支付完成时间
//                    tenpayTradeLog.setTimeEnd(tenPayVO.getTimeEnd());
//
//                    int payFlag = tenpayTradeLogMapper.insertSelective(tenpayTradeLog);
//
//                    //若有一个操作出错，抛错回滚
//                    if (!(orderFlag > 0 && payFlag == 1)) {
//                        logger.error("微信支付订单回调失败");
//                        throw new RRException(AppWSConstant.RS_MSG_TENPAY_FALL);
//                    }
//                } else {
//                    logger.info("该订单已支付处理,交易编号为: " + tenPayVO.getOutTradeNo());
//                    throw new RRException(AppWSConstant.RS_MSG_ORDER_PAY_ERROR);
//                }
//            }
//        }
//    }
   
    
    /**
     * 主动调用订单支付回调
     *
     * @throws Exception
     */
    @Override
    public void initiativeOrderPayCallBack() throws Exception {
        //查询订单状态为orderStatus的支付编号
//        List<Map<String, String>> payNoList = orderMapper.getPayNoByStatus(OrderStatusEnum.PENDING_PAYMENT.toString());
        List<Map<String, String>> payNoList = new ArrayList<Map<String,String>>();
        for (Map<String, String> map : payNoList) {
            try {
                switch (PayTypeEnum.valueOf(map.get("payType"))) {
                    case TENPAY://财付通
                        TenPayVO tenPayVO = this.tenPayQueryCallBack(map.get("payNo"));
                        //订单回调处理
//                        this.tenPayOrderCallBack(tenPayVO);
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    
    
    /**
     * @Description: 微信支付主动查询回调
     * @Param: [payNo]
     * @return: com.huaku.ecom.system.model.vo.TenPayVO
     * @Author: zengXianKang
     * @Date: 2019/5/30
     */
    @Override
    public TenPayVO tenPayQueryCallBack(String payNo) throws Exception {
        SortedMap<String, Object> paramsMap = new TreeMap<String, Object>();
        //应用APPID
        paramsMap.put("appid", tenpayConfig.getAppId());
        //商户号
        paramsMap.put("mch_id", tenpayConfig.getMchId());
        //商户订单号
        paramsMap.put("out_trade_no", payNo);
        //随机字符串
        paramsMap.put("nonce_str", UUID.randomUUID().toString().replaceAll("-", ""));
        TenPayUtils tenPayUtils = new TenPayUtils();
        //签名
        String sign = tenPayUtils.createSign(paramsMap, "UTF-8");
        paramsMap.put("sign", sign);
        //请求报文
        String requestXml = TenPayUtils.tenPayXmlInfo(paramsMap);
        //发送微信查询post请求
        String tenQueryPost = TenPayUtils.httpsRequest(tenpayConfig.getQueryUrl(), "POST", requestXml);

        TenPayVO tenPayVO = this.tenPayCallBackInfo(tenQueryPost, "xml", "");
        return tenPayVO;
    }
}
