package com.hanfu.cancel.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.hanfu.cancel.dao.CancelsMapper;
import com.hanfu.cancel.dao.HfGoodsMapper;
import com.hanfu.cancel.dao.HfUserMapper;
import com.hanfu.cancel.model.*;
import com.hanfu.cancel.service.CancelService;
import com.hanfu.cancel.tool.PageTool;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Api
@RequestMapping("/test")
@CrossOrigin
public class testController {
    @Autowired
    private StringRedisTemplate redisClient;
    @Autowired
    private CancelService cancelService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private HfUserMapper hfUserMapper;
    @Autowired
    private HfGoodsMapper hfGoodsMapper;
    @Autowired
    private CancelsMapper cancelsMapper;
    protected final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/login123", method = RequestMethod.GET)
    @ApiOperation(value = "分页查询查询123", notes = "分页查询查询123")
    public ResponseEntity<JSONObject> login123(Paging paging) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        PageTool.num(paging);
        PageInfo<HfUser> pageInfo = new PageInfo<>(hfUserMapper.selectAll());
        return builder.body(ResponseUtils.getResponseBody(pageInfo));
    }

    //转换时间格式
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        //转换日期
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ApiOperation(value = "查询查询", notes = "查询查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "createData", value = "开始时间", required = false, type = "Date"),
            @ApiImplicitParam(paramType = "query", name = "site", value = "核销地点", required = false, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "createDate1", value = "结束时间", required = false, type = "Date"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "第几页", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页的数量", required = false, type = "Integer")
    })
    public ResponseEntity<JSONObject> login(String site, Date createData, Date createDate1, Paging paging) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        PageTool.num(paging);
        PageInfo<record> pageInfo = new PageInfo<>(cancelService.Test(site, createData, createDate1));
        System.out.println(site + "-------" + createData + "---------" + createDate1);
        return builder.body(ResponseUtils.getResponseBody(pageInfo));
    }

    // @Scheduled(cron = "0/5 * * * * ? ")
    @RequestMapping(value = "/qqq", method = RequestMethod.GET)
    @ApiOperation(value = "时间检查查询没有核销员的物品", notes = "时间检查查询没有核销员的物品")
    public ResponseEntity<JSONObject> qqq() throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        List<HfGoods> list = new ArrayList<>();
        List<HfGoods> hfGoodsList = hfGoodsMapper.selectAll();
        for (int i = 0; i < hfGoodsList.size(); i++) {
            if (hfGoodsList.get(i).getClaim() != 0) {
                if (cancelsMapper.selectByPrimaryKey(hfGoodsList.get(i).getCancelId()) == null) {
                    list.add(hfGoodsList.get(i));
                }
            }
        }
        logger.info("时间检查" + LocalDateTime.now());
        logger.info("没有核销员的物品Id:" + list);
        return builder.body(ResponseUtils.getResponseBody(list));
    }


//    @RequestMapping(value = "setAndsave", method = RequestMethod.GET)
//    @ResponseBody
//    public String test(String para,Integer orderId,Integer goodsId) throws Exception {
//        String Order = String.valueOf(orderId);
//        String Goods = String.valueOf(goodsId);
//        JSONObject sessionObj = new JSONObject();
//        sessionObj.put(Order, orderId);
//        sessionObj.put(Goods, goodsId);
//        redisTemplate.opsForValue().set(Order, sessionObj.toString());
//
//        redisTemplate.opsForValue().set(para, "151230");
//        redisClient.opsForValue().set("test", para);
//          String str = redisClient.opsForValue().get(Order);
//        String str2 = redisTemplate.opsForValue().get(para);
//        String str3 = redisTemplate.opsForValue().get("test123");
//        System.out.println(str);
//        System.out.println(str2);
//        System.out.println(str3 + "session");
//        return str;
//    }

    //    @RequestMapping(value = "setAndsave2", method = RequestMethod.GET)
//    @ResponseBody
//    public String test2() throws Exception {
//        String str = redisClient.opsForValue().get("test");
//        System.out.println(str);
//        return str;
//    }
    @GetMapping(value = "/jiema")
    @ApiOperation("解码")
    public ResponseEntity<JSONObject> getCode(String goodsId, String ordersId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        String key = "MIGfMA0GCSqGSIb3";
        goodsId = goodsId.replace("思维创造", "");
        ordersId = ordersId.replace("思维创造", "");
        String decrypt = PageTool.decrypt(ordersId, key);
        String decrypt1 = PageTool.decrypt(goodsId, key);
        test ttt = new test();
        ttt.setOrderId(Integer.valueOf(decrypt));
        ttt.setGoodsId(Integer.valueOf(decrypt1));
        List<test> list = new ArrayList<test>();
        list.add(ttt);
        return builder.body(ResponseUtils.getResponseBody(list));
    }

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
        List<test111> list = new ArrayList<>();
        list.add(t);
        String str1 = JSON.toJSONString(list);
        System.out.println(str1);
//        String content ="http://192.168.1.125:9901/testCancel?goodsId%E5%95%86%E5%93%81Id="+goodsId+"&orderId%E8%AE%A2%E5%8D%95Id="+orderId+"&%E7%94%A8%E6%88%B7%E5%94%AF%E4%B8%80%E6%A0%87%E8%AF%86=1";
        String content = str1;
        //            //获取一个二维码图片
        BitMatrix bitMatrix = com.hanfu.cancel.controller.QRCodeUtils.createCode(content);
//                BitMatrix bitMatrix = com.hanfu.cancel.controller.QRCodeUtils.createCode(content);
        //以流的形式输出到前端
        MatrixToImageWriter.writeToStream(bitMatrix, "jpg", stream);
    }
}