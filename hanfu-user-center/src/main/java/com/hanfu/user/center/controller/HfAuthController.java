package com.hanfu.user.center.controller;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.user.center.dao.FileDescMapper;
import com.hanfu.user.center.dao.HfAuthMapper;
import com.hanfu.user.center.dao.HfUserMapper;
import com.hanfu.user.center.manual.dao.UserDao;
import com.hanfu.user.center.model.HfAuth;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@Api
@RequestMapping("/hf-auth")
@CrossOrigin
public class HfAuthController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private FileDescMapper fileDescMapper;
    
    @Autowired
    private HfUserMapper hfUserMapper;
    
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    
    @Autowired
    private HfAuthMapper hfAuthMapper;
    
    @Autowired
    private UserDao userDao;
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ApiOperation(value = "用户登录", notes = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "authType", value = "鉴权方式,  1:用户登录, 2:手机号登录 ", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "authKey", value = "鉴权key", required = false, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "passwd", value = "密码", required = false, type = "String"),
    })
    public ResponseEntity<JSONObject> login(HttpServletRequest request, HttpServletResponse response, @RequestParam(name = "authType") String authType, @RequestParam(name = "authKey") String authKey, @RequestParam(name = "passwd") Integer passwd) throws Exception {
        Cookie cookie = new Cookie("autologin", authKey);
        response.addCookie(cookie);

        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        HfAuth hfAuth = userDao.selectAuthList(authKey);
        
        if (hfAuth == null) {
            return builder.body(ResponseUtils.getResponseBody("还未注册"));
        }
        
        if (redisTemplate.opsForValue().get(String.valueOf(hfAuth.getUserId())) == null) {
        
            String token = "_" + UUID.randomUUID().toString().replaceAll("-", "");
            redisTemplate.opsForValue().set(String.valueOf(hfAuth.getUserId()), token);
        } else {
            
            return builder.body(ResponseUtils.getResponseBody("1"));
        }
        if (!passwd.equals(redisTemplate.opsForValue().get(authKey))) {
            
            return builder.body(ResponseUtils.getResponseBody("成功"));
        } else {

            return builder.body(ResponseUtils.getResponseBody("验证码不正确"));
        }       
    }

}
