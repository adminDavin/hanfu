package com.hanfu.cancel.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.hanfu.cancel.dao.CancelsMapper;
import com.hanfu.cancel.dao.HfGoodsMapper;
import com.hanfu.cancel.dao.HfUserMapper;
import com.hanfu.cancel.model.HfGoods;
import com.hanfu.cancel.model.HfUser;
import com.hanfu.cancel.model.Paging;
import com.hanfu.cancel.model.record;
import com.hanfu.cancel.service.CancelService;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.hibernate.validator.internal.util.logging.Log;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.scheduling.annotation.Scheduled;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hanfu.cancel.tool.PageTool;
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
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "第几页", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页的数量", required = true, type = "Integer")
    })
    public ResponseEntity<JSONObject> login(String site, Date createData, Date createDate1,Paging paging) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        PageTool.num(paging);
        PageInfo<record> pageInfo = new PageInfo<>(cancelService.Test(site, createData, createDate1));
        System.out.println(site + "-------" + createData + "---------" + createDate1);
        return builder.body(ResponseUtils.getResponseBody(pageInfo));
    }
 @Scheduled(cron = "0/5 * * * * ? ")
    @RequestMapping(value = "/qqq", method = RequestMethod.GET)
    @ApiOperation(value = "时间检查查询查询", notes = "时间检查查询查询")
    public ResponseEntity<JSONObject> qqq() throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
     List<Integer> list = new ArrayList<Integer>();
        List<HfGoods> hfGoodsList=hfGoodsMapper.selectAll();
        for (int i=0;i<hfGoodsList.size();i++){
            if (hfGoodsList.get(i).getClaim()!=0){
                if (cancelsMapper.selectByPrimaryKey(hfGoodsList.get(i).getCancelId())==null){
                    list.add(hfGoodsList.get(i).getId());
                }
            }
        }
        logger.info("时间检查"+LocalDateTime.now());
        logger.info("没有核销员的物品Id:"+list);
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
    /**
     *  生成二维码
     * @param ，

     * */
    @GetMapping(value = "/activity/create/activity-code")
    @ApiOperation("生成活动详情二维码")
    public void getCode(HttpServletResponse response) throws Exception {

            int type = 1;
            // 设置响应流信息
            response.setContentType("image/jpg");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            OutputStream stream = response.getOutputStream();
            //type是1，生成活动详情、报名的二维码，type是2，生成活动签到的二维码
            String content = (type == 1 ? "http://www.baidu.com" : "核销失败");
            //获取一个二维码图片
            BitMatrix bitMatrix = com.example.order.Test.QRCodeUtils.createCode(content);
            //以流的形式输出到前端
            MatrixToImageWriter.writeToStream(bitMatrix , "jpg" , stream);
    }
}