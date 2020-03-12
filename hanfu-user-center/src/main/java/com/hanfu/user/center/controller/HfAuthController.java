package com.hanfu.user.center.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import com.hanfu.user.center.manual.model.UserInfo;
import com.hanfu.user.center.model.HfAuth;
import com.hanfu.user.center.model.HfAuthExample;
import com.hanfu.user.center.model.HfUser;
import com.hanfu.user.center.model.HfUserExample;
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

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录", notes = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "authType", value = "鉴权方式,  1:用户登录, 2:手机号登录 ", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "authKey", value = "鉴权key", required = false, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "passwd", value = "密码", required = false, type = "String"),
    })
    public ResponseEntity<JSONObject> login(HttpServletRequest request, HttpServletResponse response, @RequestParam(name = "authType") String authType, @RequestParam(name = "authKey") String authKey, @RequestParam(name = "passwd",required = false) Integer passwd) throws Exception {
        Cookie cookie = new Cookie("autologin", authKey);
        response.addCookie(cookie);

        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        HfAuthExample example = new HfAuthExample();
        example.createCriteria().andAuthKeyEqualTo(authKey);
        List<HfAuth> list = hfAuthMapper.selectByExample(example);

        if (list.isEmpty()) {
            return builder.body(ResponseUtils.getResponseBody("还未注册"));
        }
        HfAuth hfAuth = list.get(0);

        HfUser user = hfUserMapper.selectByPrimaryKey(hfAuth.getUserId());
        if (user.getUserLevel() != 1 || user.getUserLevel()==null) {
            return builder.body(ResponseUtils.getResponseBody("没有权限"));
        }
        if (redisTemplate.opsForValue().get(String.valueOf(hfAuth.getUserId())) == null) {
            String token = "_" + UUID.randomUUID().toString().replaceAll("-", "");
            redisTemplate.opsForValue().set(String.valueOf(hfAuth.getUserId()), token);
        }
        if(passwd==null) {
            return builder.body(ResponseUtils.getResponseBody("还未填写验证码"));
        }
        if (!String.valueOf(passwd).equals(redisTemplate.opsForValue().get(authKey))) {
            return builder.body(ResponseUtils.getResponseBody("验证码不正确"));
        }

        return builder.body(ResponseUtils.getResponseBody(user));
    }


    @RequestMapping(value = "/addAdminUser", method = RequestMethod.POST)
    @ApiOperation(value = "添加管理后台用户", notes = "添加管理后台用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "phone", value = "手机号", required = false, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "name", value = "用户名", required = false, type = "String"),
    })
    public ResponseEntity<JSONObject> addAdminUser(HttpServletRequest request,
                                                   HttpServletResponse response,
                                                   @RequestParam(name = "phone",required = false) String phone,
                                                   @RequestParam(name = "name",required = false) String name) throws Exception {

        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        HfAuthExample example = new HfAuthExample();
        example.createCriteria().andAuthKeyEqualTo(phone);
        List<HfAuth> list= hfAuthMapper.selectByExample(example);
        if(!list.isEmpty()) {
            return builder.body(ResponseUtils.getResponseBody("该用户已经存在"));
        }
        HfUser hfUser = new HfUser();
        hfUser.setRealName(name);
        hfUser.setPhone(phone);
        hfUser.setUserLevel((byte) 1);
        hfUser.setCreateDate(LocalDateTime.now());
        hfUser.setModifyDate(LocalDateTime.now());
        hfUser.setIdDeleted((byte) 0);
        hfUserMapper.insert(hfUser);
        HfAuth auth = new HfAuth();
        auth.setAuthKey(phone);
        auth.setAuthStatus((byte) 0);
        auth.setAuthType("2");
        auth.setUserId(hfUser.getId());
        auth.setIdDeleted((byte) 0);
        auth.setCreateDate(LocalDateTime.now());
        auth.setModifyDate(LocalDateTime.now());
        hfAuthMapper.insert(auth);
        return builder.body(ResponseUtils.getResponseBody("注册成功"));
    }


    @RequestMapping(value = "/findAdminUser", method = RequestMethod.GET)
    @ApiOperation(value = "查询后台用户", notes = "查询后台用户")

    public ResponseEntity<JSONObject> addAdminUser() throws Exception {

        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        HfUserExample example = new HfUserExample();
        example.createCriteria().andUserLevelEqualTo((byte) 1);
        List<HfUser> list = hfUserMapper.selectByExample(example);
        List<UserInfo> result = new ArrayList<UserInfo>();
        for (int i = 0; i < list.size(); i++) {
            HfUser hfUser = list.get(i);
            UserInfo info = new UserInfo();
            info.setCreateDate(hfUser.getCreateDate());
            info.setNickName(hfUser.getNickName());
            info.setPhone(hfUser.getPhone());
            info.setId(hfUser.getId());
            result.add(info);
        }
        return builder.body(ResponseUtils.getResponseBody(result));
    }

    @RequestMapping(value = "/deleteAdminUser", method = RequestMethod.GET)
    @ApiOperation(value = "删除后台用户", notes = "删除后台用户")

    public ResponseEntity<JSONObject> deleteAdminUser(@RequestParam(name = "userId",required = false) Integer userId) throws Exception {

        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        HfUser hfUser = hfUserMapper.selectByPrimaryKey(userId);
        if(hfUser == null) {
            return builder.body(ResponseUtils.getResponseBody("此用户不存在"));
        }
        hfUserMapper.deleteByPrimaryKey(userId);
        HfAuthExample example = new HfAuthExample();
        example.createCriteria().andUserIdEqualTo(userId);
        hfAuthMapper.deleteByExample(example);
        return builder.body(ResponseUtils.getResponseBody("删除成功"));
    }
}
