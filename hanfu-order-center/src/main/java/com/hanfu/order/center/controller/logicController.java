package com.hanfu.order.center.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.hanfu.order.center.cancel.dao.*;
import com.hanfu.order.center.cancel.model.*;
import com.hanfu.order.center.tool.PageTool;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Api
@RequestMapping("/cancel")
@CrossOrigin
public class logicController {
    protected final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private HfUserMapper hfUserMapper;
    @Autowired
    private HfGoodsMapper hfGoodsMapper;
    @Autowired
    private CancelsMapper cancelsMapper;
    @Autowired
    private HfOrdersCancelDetailMapper hfOrdersCancelDetailMapper;
    @Autowired
    private HfLogMapper hfLogMapper;
    @Autowired
    private HfPriceMapper hfPriceMapper;
    @Autowired
    private CancelProductMapper cancelProductMapper;
    @Autowired
    private ProductMapper productMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private HfOrdersCancelMapper hfOrdersCancelMapper;
    //转换时间格式
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        //转换日期
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }

    @ResponseBody
    @RequestMapping(value = "/testCancel", method = RequestMethod.GET)
    @ApiOperation(value = "核销逻辑", notes = "核销逻辑")
    public ResponseEntity<JSONObject> testCancel(
            @RequestParam(value = "userId", required = true) Integer userId,
            @RequestParam(value = "UgoodsId", required = true) String UgoodsId,
            @RequestParam(value = "UorderId", required = true) String UorderId
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        String key = "MIGfMA0GCSqGSIb3";
        String goodId = String.valueOf(UgoodsId).replace("思维创造", "");
        String ordersId = String.valueOf(UorderId).replace("思维创造", "");
        String decrypt = PageTool.decrypt(ordersId, key);
        String decrypt1 = PageTool.decrypt(goodId, key);
        Integer goodsId=Integer.valueOf(decrypt1);
        Integer orderId=Integer.valueOf(decrypt);

        Integer goodsid= (Integer) redisTemplate.opsForValue().get(decrypt);
        if (redisTemplate.opsForValue().get(decrypt)==null){
            return builder.body(ResponseUtils.getResponseBody("二维码已失效"));
        }
        //核销员查询
        Example example1 = new Example(cancel.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("userId", userId);
        List<cancel> cancelList = cancelsMapper.selectByExample(example1);
        //
        HfUser hfUser = hfUserMapper.selectByPrimaryKey(userId);
        String unionId = hfUser.getUsername();
        if (goodsId == null) {
            return builder.body(ResponseUtils.getResponseBody("goodsId为空"));
        }
        if (orderId == null) {
            return builder.body(ResponseUtils.getResponseBody("orderId为空"));
        }
        Example example = new Example(HfUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", unionId);
        List<HfUser> hfUserList = hfUserMapper.selectByExample(example);
        if (hfUserList.size() == 0) {
            return builder.body(ResponseUtils.getResponseBody("请登录后操作"));
        }
//        HfUser hfUser1 = hfUserMapper.selectByPrimaryKey(cancelList.get(0));
//        System.out.println(hfUser1);
        if (cancelList.size() == 0) {
            return builder.body(ResponseUtils.getResponseBody("对不起你不是核销员无法核销商品"));
        }
//        System.out.println(hfUser1.getCancelId() + "hfUser1.getCancelId()");
        //判断核销的商品是否为自提商品
        int productId=hfGoodsMapper.selectByPrimaryKey(goodsId).getProductId();
        System.out.println(productId);
        if (productMapper.selectByPrimaryKey(productId).getClaim()!=1) {
            return builder.body(ResponseUtils.getResponseBody("该商品不是自提商品"));
        }

        //判断核销员是否为该商品的核销员
        cancel cancel1 = cancelsMapper.selectByPrimaryKey(cancelList.get(0).getId());
//        CancelProduct cancelProduct = cancelProductMapper.selectByExample(examplecancel).get(0);
        Example examplecancel = new Example(CancelProduct.class);
        Example.Criteria criteriacancel = examplecancel.createCriteria();
        criteriacancel.andEqualTo("productId",productId).andEqualTo("cancelId",cancelList.get(0).getId());
        if (cancelProductMapper.selectByExample(examplecancel).size()==0) {
            return builder.body(ResponseUtils.getResponseBody("你不是该商品的核销员"));
        }
        //判断订单的商品与核销商品是否一致
        //价格，根据订单id，设置订单状态
        Example example2 = new Example(HfOrderDetail.class);
        Example.Criteria criteria2 = example2.createCriteria();
        criteria2.andEqualTo("orderId", orderId).andEqualTo("goodsId",goodsId);
        List<HfOrderDetail> hfPriceList = hfOrdersCancelDetailMapper.selectByExample(example2);
        HfOrder hfOrder=hfOrdersCancelMapper.selectByPrimaryKey(orderId);
        if (hfPriceList.size() == 0||hfOrder==null) {
            return builder.body(ResponseUtils.getResponseBody("订单不不存在"));
        }

        if (hfOrder.getOrderStatus().equals("complete")) {
            return builder.body(ResponseUtils.getResponseBody("该订单已被核销"));
        }
        HfOrderDetail hfPrice = hfOrdersCancelDetailMapper.selectByPrimaryKey(hfPriceList.get(0).getId());
        if (hfPrice.getHfStatus().equals("已完成")) {
            return builder.body(ResponseUtils.getResponseBody("该订单已被核销"));
        }
        if (!hfPrice.getGoodsId().equals(goodsId)) {
            return builder.body(ResponseUtils.getResponseBody("订单核销的商品与实际不符合"));
        }
        HfOrderDetail hfOrdersDetail = new HfOrderDetail();
        hfOrdersDetail.setModifyTime(LocalDateTime.now());
        hfOrdersDetail.setId(hfPrice.getId());
        hfOrdersDetail.setHfStatus("已完成");
        hfOrdersCancelDetailMapper.updateByPrimaryKeySelective(hfOrdersDetail);
        HfOrder hfOrder1 = new HfOrder();
        hfOrder1.setId(orderId);
        hfOrder1.setOrderStatus("complete");
        hfOrder1.setModifyTime(LocalDateTime.now());
        hfOrdersCancelMapper.updateByPrimaryKeySelective(hfOrder1);
        //添加核销记录
        CancelRecord cancelRecord = new CancelRecord();
        cancelRecord.setCreateDate(LocalDateTime.now());
        cancelRecord.setModifyDate(LocalDateTime.now());
        cancelRecord.setGoodsId(goodsId);
        cancelRecord.setCancelId(cancel1.getId());
        cancelRecord.setOrderId(orderId);
        Example example3 = new Example(HfPrice.class);
        Example.Criteria criteria3 = example3.createCriteria();
        criteria3.andEqualTo("googsId", goodsId);
        List<HfPrice> hfPriceList1 = hfPriceMapper.selectByExample(example3);
        cancelRecord.setAmount(hfPriceList1.get(0).getSellPrice() * hfPrice.getQuantity());
        hfLogMapper.insert(cancelRecord);
        //添加核销员核销额记录
        cancel cancel = new cancel();
        cancel.setId(cancel1.getId());
        cancel.setMoney(hfPriceList1.get(0).getSellPrice() * hfPrice.getQuantity() + cancel1.getMoney());
        cancel.setPresentMoney(hfPriceList1.get(0).getSellPrice() * hfPrice.getQuantity() + cancel1.getPresentMoney());
        cancelsMapper.updateByPrimaryKeySelective(cancel);
        redisTemplate.delete(decrypt);
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    @ApiOperation(value = "时间检查查询没有核销员的商品", notes = "时间检查查询没有核销员的商品")
    public ResponseEntity<JSONObject> product() throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        List<Product> list = new ArrayList<>();
        List<Product> productList = productMapper.selectAll();
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getClaim() != 0) {
                Example example = new Example(CancelProduct.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("productId",productList.get(i).getId());
                if (cancelProductMapper.selectByExample(example).size()==0){
                    list.add(productList.get(i));
                }
            }
        }
        logger.info("时间检查" + LocalDateTime.now());
        logger.info("没有核销员的物品Id:" + list);
        return builder.body(ResponseUtils.getResponseBody(list));
    }
//    @GetMapping(value = "/decode")
//    @ApiOperation("解码")
//    public ResponseEntity<JSONObject> getCode(String goodsId, String ordersId) throws Exception {
//        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
//        String key = "MIGfMA0GCSqGSIb3";
//        goodsId = goodsId.replace("思维创造", "");
//        ordersId = ordersId.replace("思维创造", "");
//        String decrypt = PageTool.decrypt(ordersId, key);
//        String decrypt1 = PageTool.decrypt(goodsId, key);
//        test ttt = new test();
//        ttt.setOrderId(Integer.valueOf(decrypt));
//        ttt.setGoodsId(Integer.valueOf(decrypt1));
//        List<test> list = new ArrayList<test>();
//        list.add(ttt);
//        return builder.body(ResponseUtils.getResponseBody(list));
//    }

    /**
     * 生成二维码
     */
    @GetMapping(value = "/activity/create/activity-code")
    @ApiOperation("生成活动详情二维码")
    public void getCode(HttpServletResponse response, Integer orderId, Integer goodsId) throws Exception {
        //uuid生成不重复主键
//        String uuid1=UUID.randomUUID().toString();
//        String uuid=UUID.randomUUID().toString().replace("-", "");
//        System.out.println(uuid1);
//        System.out.println(uuid);
        redisTemplate.opsForValue().set(String.valueOf(orderId), goodsId);
        redisTemplate.expire(String.valueOf(orderId),300 , TimeUnit.SECONDS);
        System.out.println("hahha"+redisTemplate.opsForValue().get(String.valueOf(orderId)));
        System.out.println(redisTemplate.getExpire(String.valueOf(String.valueOf(orderId)),TimeUnit.SECONDS));
        //16位
        String key = "MIGfMA0GCSqGSIb3";

        //字符串
        String orderId123 = String.valueOf(orderId);
        String goodsId123 = String.valueOf(goodsId);

        //加密
        String encrypt = PageTool.encrypt(orderId123, key);
        String encrypt1 = PageTool.encrypt(goodsId123, key);
        //解密
//            String decrypt = PageTool.decrypt(encrypt, key);
//
//            System.out.println("加密前：" + orderId123);
//            System.out.println("加密后：" + encrypt);
//            System.out.println("解密后：" + decrypt);
        System.out.println("goodsId:" + encrypt1);
        System.out.println("orderId:" + encrypt);

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
        test111 t = new test111();
        t.setGoodsId(encrypt1 + "思维创造");
        t.setOrderId(encrypt + "思维创造");
        t.setQrCodeType("CancelQrCode");
        List<test111> list = new ArrayList<>();
        list.add(t);
        String str1 = JSON.toJSONString(list);
        System.out.println(str1);
//        String content ="http://192.168.1.125:9901/testCancel?goodsId%E5%95%86%E5%93%81Id="+goodsId+"&orderId%E8%AE%A2%E5%8D%95Id="+orderId+"&%E7%94%A8%E6%88%B7%E5%94%AF%E4%B8%80%E6%A0%87%E8%AF%86=1";
        String content = str1;
        //            //获取一个二维码图片
        BitMatrix bitMatrix = com.hanfu.order.center.controller.QRCodeUtils.createCode(content);
//                BitMatrix bitMatrix = com.hanfu.cancel.controller.QRCodeUtils.createCode(content);
        //以流的形式输出到前端
        MatrixToImageWriter.writeToStream(bitMatrix, "jpg", stream);
    }
}
