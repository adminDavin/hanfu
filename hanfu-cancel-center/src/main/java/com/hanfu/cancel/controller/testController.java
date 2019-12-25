package com.hanfu.cancel.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.hanfu.cancel.dao.HfUserMapper;
import com.hanfu.cancel.model.HfUser;
import com.hanfu.cancel.model.Paging;
import com.hanfu.cancel.service.CancelService;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
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
    @RequestMapping(value = "/login123", method = RequestMethod.GET)
    @ApiOperation(value = "分页查询查询123", notes = "分页查询查询123")
    public ResponseEntity<JSONObject> login123(Paging paging) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        PageTool.num(paging);
        System.out.println(HfUser.class);
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
    public ResponseEntity<JSONObject> login(String site, Date createData, Date createDate1) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        System.out.println(site + "-------" + createData + "---------" + createDate1);
        return builder.body(ResponseUtils.getResponseBody(cancelService.Test(site, createData, createDate1)));
    }
// @Scheduled(cron = "0/5 * * * * ? ")
//    @RequestMapping(value = "/qqq", method = RequestMethod.GET)
//    @ApiOperation(value = "时间检查查询查询", notes = "时间检查查询查询")
//    public ResponseEntity<JSONObject> qqq() throws Exception {
//        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
//
//        return builder.body(ResponseUtils.getResponseBody("123456"));
//    }


    @RequestMapping(value = "setAndsave", method = RequestMethod.GET)
    @ResponseBody
    public String test(String para) throws Exception {
        JSONObject sessionObj = new JSONObject();
        sessionObj.put("openId", "openId");
        sessionObj.put("sessionKey", "sessionKey");
        redisTemplate.opsForValue().set("test123", sessionObj.toString());


        redisTemplate.opsForValue().set(para, "151230");
        redisClient.opsForValue().set("test", para);
        String str = redisClient.opsForValue().get("test");
        String str2 = redisTemplate.opsForValue().get(para);
        String str3 = redisTemplate.opsForValue().get("test123");
        System.out.println(str);
        System.out.println(str2);
        System.out.println(str3 + "session");
        return str;
    }

    @RequestMapping(value = "setAndsave2", method = RequestMethod.GET)
    @ResponseBody
    public String test2() throws Exception {
        String str = redisClient.opsForValue().get("test");
        System.out.println(str);
        return "http://www.baidu.com";
    }
    /**
     *  生成二维码
     * @param type 二维码的类型，

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