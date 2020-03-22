package com.hanfu.payment.center.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.hanfu.payment.center.dao.*;
import com.hanfu.payment.center.manual.model.OrderStatus;
import com.hanfu.payment.center.manual.model.OrderTypeEnum;
import com.hanfu.payment.center.model.*;
import com.hanfu.payment.center.tool.PageTool;
import com.hanfu.payment.center.tool.QRCode;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@Api
@RequestMapping("/balance")
@CrossOrigin
public class BalancePaymentController {
    @Autowired
   private BalanceMapper balanceMapper;
    @Autowired
    private HfOrderMapper hfOrderMapper;
    @Autowired
    private QrCodeMapper qrCodeMapper;
    @Autowired
    private CancelPaymentMapper cancelPaymentMapper;
    @Autowired
    private CancelRecordPaymentMapper cancelRecordPaymentMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @GetMapping(value = "/activity/payment/activity-code")
    @ApiOperation("生成活动详情二维码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "money", value = "金额", required = true,
                    type = "String"),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true,
                    type = "Integer") })
    public void getCode(HttpServletResponse response, Integer money,Integer userId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String key1=String.valueOf(userId)+"BalancePayment";
        redisTemplate.opsForValue().set(key1, money);
        redisTemplate.expire(key1,300 , TimeUnit.SECONDS);
        System.out.println(key1);
        System.out.println(redisTemplate.getExpire(key1, TimeUnit.SECONDS));
        System.out.println(redisTemplate.opsForValue().get(key1));
        QR t = new QR();
//        Calendar now = Calendar.getInstance();
//        System.out.println("年: " + now.get(Calendar.YEAR));
//        t.setYear(now.get(Calendar.YEAR));
//        System.out.println("月: " + (now.get(Calendar.MONTH) + 1) + "");
//        t.setMonth((now.get(Calendar.MONTH) + 1));
//        System.out.println("日: " + now.get(Calendar.DAY_OF_MONTH));
//        t.setDay(now.get(Calendar.DAY_OF_MONTH));
//        System.out.println("时: " + now.get(Calendar.HOUR_OF_DAY));
//        System.out.println(now.get(Calendar.HOUR_OF_DAY));
//        t.setHour(now.get(Calendar.HOUR_OF_DAY));
//        System.out.println("分: " + now.get(Calendar.MINUTE));
//        t.setMinute(now.get(Calendar.MINUTE));
//        System.out.println("秒: " + now.get(Calendar.SECOND));
//        t.setSecond(now.get(Calendar.SECOND));
//        System.out.println("当前时间毫秒数：" + now.getTimeInMillis());
//        System.out.println(now.getTime());

//        Date d = new Date();
//        System.out.println(d);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String dateNowStr = sdf.format(d);
//        System.out.println("格式化后的日期：" + dateNowStr);

//        String str = "2012-1-13 17:26:33";	//要跟上面sdf定义的格式一样
//        Date today = sdf.parse(str);
//        System.out.println("字符串转成日期：" + today);
        //uuid生成不重复主键
        String uuid1=UUID.randomUUID().toString();
        String uuid=UUID.randomUUID().toString().replace("-", "");
        System.out.println(uuid1);
        System.out.println(uuid);

        //16位
        String key = "MIGfMA0GCSqGSIb3";

        //字符串
        String QrCodeType = "BalancePayment";
        String UserId = String.valueOf(userId);
//        String Money= String.valueOf(money);
        //加密
        String encrypt = PageTool.encrypt(QrCodeType, key);
        String encrypt1 = PageTool.encrypt(UserId, key);
//        String encrypt2=PageTool.encrypt(Money, key);
        //解密
//            String decrypt = PageTool.decrypt(encrypt, key);
//
//            System.out.println("加密前：" + orderId123);
//            System.out.println("加密后：" + encrypt);
//            System.out.println("解密后：" + decrypt);

        // 设置响应流信息

//        String resultString = PageTool.stringToMD5(String.valueOf(orderId));
//        String resultString2 = PageTool.stringToMD5(String.valueOf(goodsId));
//        System.out.println(resultString);
//        System.out.println(resultString2);
//        final Base64.Decoder decoder = Base64.getDecoder();
//        final Base64.Encoder encoder = Base64.getEncoder();
//        final String text = String.valueOf(orderId);
//        final String text1 = String.valueOf(goodsId);
//        final byte[] textByte = text.getBytes("UTF-8");
//        final byte[] textByte1 = text1.getBytes("UTF-8");
////编码
//        final String encodedText = encoder.encodeToString(textByte);
//        final String encodedText1 = encoder.encodeToString(textByte1);
//        System.out.println("OrdersId:"+encodedText);
//        System.out.println("GoodsId:"+encodedText1);
        response.setContentType("image/jpg");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        OutputStream stream = response.getOutputStream();
        //type是1，生成活动详情、报名的二维码，type是2，生成活动签到的二维码
        //二维码记录
//        QrCode qrCode = new QrCode();
//        qrCode.setUserId(Integer.valueOf(userId));
//        qrCode.setCode("BalancePayment");
//        qrCode.setCreateTime(LocalDateTime.now());
//        qrCode.setModifyTime(LocalDateTime.now());
//        qrCode.setIdDeleted((byte) 0);
//        qrCode.setQrCode(uuid);
//        qrCodeMapper.insertSelective(qrCode);
        //
        t.setQrCodeType(encrypt + "思维创造");
        t.setUserId(encrypt1+ "思维创造");
//        t.setMoney(encrypt2+ "思维创造");
        t.setQrCode(uuid);
//        t.setQrCodeId(qrCode.getId());
        List<QR> list = new ArrayList<>();
        list.add(t);
        String str1 = JSON.toJSONString(list);
        System.out.println(str1);
//        String content ="http://192.168.1.125:9901/testCancel?goodsId%E5%95%86%E5%93%81Id="+goodsId+"&orderId%E8%AE%A2%E5%8D%95Id="+orderId+"&%E7%94%A8%E6%88%B7%E5%94%AF%E4%B8%80%E6%A0%87%E8%AF%86=1";
        String content = str1;
        //            //获取一个二维码图片
        BitMatrix bitMatrix = QRCode.createCode(content);
//                BitMatrix bitMatrix = com.hanfu.cancel.controller.QRCodeUtils.createCode(content);
        //以流的形式输出到前端
        MatrixToImageWriter.writeToStream(bitMatrix, "jpg", stream);

    }

    @GetMapping(value = "/payment")
    @ApiOperation("扫码支付")
    public ResponseEntity<JSONObject> getCode(QR qr,Integer userCancelId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        Example example1 = new Example(cancel.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("userId",userCancelId);
        List<cancel> cancelList = cancelPaymentMapper.selectByExample(example1);
        if (cancelList.size()==0){
            return builder.body(ResponseUtils.getResponseBody("您不是核销人员"));
        }
        Integer DistributorId = cancelList.get(0).getId();
        cancel cancel=cancelPaymentMapper.selectByPrimaryKey(DistributorId);

//        if (qrCodeMapper.selectByPrimaryKey(QRCodeId)==null){
//           return builder.body(ResponseUtils.getResponseBody("二维码已失效,不要重复扫描"));
//        }
//        Calendar now = Calendar.getInstance();
//        Integer year=qr.getYear()-now.get(Calendar.YEAR);
//        Integer month=qr.getMonth()-(now.get(Calendar.MONTH) + 1);
//        Integer day=qr.getDay()-now.get(Calendar.DAY_OF_MONTH);
//            String Hour=String.format("%2d", qr.getHour()).replace(" ", "0");
//            String HourNow=String.format("%2d", now.get(Calendar.HOUR_OF_DAY)).replace(" ", "0");
//            System.out.println(Hour);//输出结果Hour
//        String Minute=String.format("%2d", qr.getMinute()).replace(" ", "0");
//        String MinuteNow=String.format("%2d", now.get(Calendar.MINUTE)).replace(" ", "0");
//        System.out.println(Minute);//输出结果Minute
//        String Second=String.format("%2d", qr.getSecond()).replace(" ", "0");
//        String SecondNow=String.format("%2d", now.get(Calendar.SECOND)).replace(" ", "0");
//        System.out.println(Second);//输出结果Second
//        String time = Hour+Minute+Second;
//        System.out.println("time"+time);
//        String timeNow = HourNow+MinuteNow+SecondNow;
//        System.out.println("timeNow"+timeNow);
//        boolean a=true;
//        if (year!=0||month!=0||day!=0){
//              return   builder.body(ResponseUtils.getResponseBody("二维码已失效"));
//        }
//            if (now.get(Calendar.HOUR_OF_DAY)-qr.getHour()!=0){
//                System.out.println(Integer.valueOf(timeNow)-Integer.valueOf(time));
//                if ((Integer.valueOf(timeNow)-Integer.valueOf(time))>4500){
//                   return builder.body(ResponseUtils.getResponseBody("二维码已失效"));
//                }else {
//                    a=false;
//                }
//            }
//            if (a){
//                System.out.println(Integer.valueOf(timeNow)-Integer.valueOf(time));
//                if ((Integer.valueOf(timeNow)-Integer.valueOf(time))>500){
//                    return  builder.body(ResponseUtils.getResponseBody("二维码已失效"));
//                }
//            }

        String key = "MIGfMA0GCSqGSIb3";
        String QrCodeType = qr.getQrCodeType().replace("思维创造", "");
        String UserId = qr.getUserId().replace("思维创造", "");
//        String Money = qr.getMoney().replace("思维创造", "");
        String decrypt = PageTool.decrypt(QrCodeType, key);
        String decrypt1 = PageTool.decrypt(UserId, key);
//        String decrypt2=PageTool.decrypt(Money,key);
        String key1=decrypt1+"BalancePayment";
        Integer money= (Integer) redisTemplate.opsForValue().get(key1);
        System.out.println(redisTemplate.getExpire(key1,TimeUnit.SECONDS));
        System.out.println(key1);
        if (redisTemplate.opsForValue().get(decrypt1+"BalancePayment")==null){
            return builder.body(ResponseUtils.getResponseBody("二维码已失效"));
        }
        Example example = new Example(HfUserBalance.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",Integer.valueOf(decrypt1)).andEqualTo("balanceType","rechargeAmount");
        List<HfUserBalance> hfUserBalances=balanceMapper.selectByExample(example);
        if (hfUserBalances.size()==0||hfUserBalances.get(0).getHfBalance()-Integer.valueOf(money)<0){
            return builder.body(ResponseUtils.getResponseBody("余额不足请充值"));
        }
        HfUserBalance hfUserBalance = new HfUserBalance();
        hfUserBalance.setCreateTime(LocalDateTime.now());
        hfUserBalance.setModifyTime(LocalDateTime.now());
        hfUserBalance.setIsDeleted((short) 0);
        hfUserBalance.setHfBalance(hfUserBalances.get(0).getHfBalance()-Integer.valueOf(money));
//        QR ttt = new QR();
//        ttt.setQrCodeType(decrypt);
//        ttt.setUserId(decrypt1);
//        ttt.setMoney(decrypt2);
//        ttt.setYear(qr.getYear());
//        ttt.setMonth(qr.getMonth());
//        ttt.setDay(qr.getDay());
//        ttt.setHour(qr.getHour());
//        ttt.setMinute(qr.getMinute());
//        ttt.setSecond(qr.getSecond());
//        List<QR> list = new ArrayList<QR>();
//        list.add(ttt);
        balanceMapper.updateByExampleSelective(hfUserBalance,example);
        //创建流水
        LocalDateTime timeOrder = LocalDateTime.now();
        HfOrder hfOrder = new HfOrder();
        hfOrder.setCreateTime(timeOrder);
        hfOrder.setModifyTime(timeOrder);

        hfOrder.setAmount(Integer.valueOf(money));
        hfOrder.setHfRemark("余额扫码支付");
        hfOrder.setUserId(Integer.valueOf(decrypt1));
        hfOrder.setOrderType("balancePayment");
        hfOrder.setPaymentName(decrypt);
        hfOrder.setStoneId(0);
        hfOrder.setDistributorId(DistributorId);
        hfOrder.setOrderCode(UUID.randomUUID().toString().replaceAll("-", ""));
        hfOrder.setLastModifier(String.valueOf(hfOrder.getUserId()));
        Integer paymentType = CreateHfOrderRequest.PaymentType.getPaymentTypeEnum(hfOrder.getPaymentName()).getPaymentType();
        hfOrder.setPaymentType(paymentType);
        hfOrder.setOrderStatus(OrderStatus.COMPLETE.getOrderStatus());
        hfOrder.setPayStatus(CreateHfOrderRequest.PaymentStatus.UNPAID.getPaymentStatus());

        hfOrderMapper.insertSelective(hfOrder);
//        qrCodeMapper.deleteByPrimaryKey(QRCodeId);
        //添加核销记录
        cancel cancel1 = new cancel();
        cancel1.setId(DistributorId);
        cancel1.setModifyDate(LocalDateTime.now());
        cancel1.setMoney(cancel.getMoney()+Integer.valueOf(money));
        cancel1.setPresentMoney(cancel.getPresentMoney()+Integer.valueOf(money));
        cancelPaymentMapper.updateByPrimaryKeySelective(cancel1);
        CancelRecord cancelRecord = new CancelRecord();
        cancelRecord.setAmount(Integer.valueOf(money));
        cancelRecord.setCancelId(DistributorId);
        cancelRecord.setGoodsId(0);
        cancelRecord.setCreateDate(LocalDateTime.now());
        cancelRecord.setModifyDate(LocalDateTime.now());
        cancelRecord.setOrderId(hfOrder.getId());
        cancelRecord.setCancelId(cancel1.getId());
        cancelRecordPaymentMapper.insertSelective(cancelRecord);
        redisTemplate.delete(key1);
        return builder.body(ResponseUtils.getResponseBody(hfOrder));
    }
}
