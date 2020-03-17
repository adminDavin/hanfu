package com.hanfu.user.center.controller;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hanfu.user.center.dao.FileDescMapper;
import com.hanfu.user.center.dao.HUserBalanceMapper;
import com.hanfu.user.center.dao.HfAuthMapper;
import com.hanfu.user.center.dao.HfUserMapper;
import com.hanfu.user.center.manual.dao.UserDao;
import com.hanfu.user.center.manual.model.UserInfo;
import com.hanfu.user.center.model.HUserBalanceExample;
import com.hanfu.user.center.model.HfAuth;
import com.hanfu.user.center.model.HfAuthExample;
import com.hanfu.user.center.model.HfUser;
import com.hanfu.user.center.model.HfUserExample;
import com.hanfu.user.center.request.UserInfoRequest;
import com.hanfu.user.center.response.handler.UserNotExistException;
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
    private HUserBalanceMapper hUserBalanceMapper;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录", notes = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "authType", value = "鉴权方式,  1:用户登录, 2:手机号登录 ", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "authKey", value = "鉴权key", required = false, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "passwd", value = "密码", required = false, type = "String"),
    })
    public ResponseEntity<JSONObject> login(HttpServletRequest request, HttpServletResponse response, @RequestParam(name = "authType") String authType, @RequestParam(name = "authKey") String authKey, @RequestParam(name = "passwd",required = false) Integer passwd) throws Exception {
    	Integer userId = null;
        Cookie cookie = new Cookie("autologin", authKey);
        response.addCookie(cookie);
        
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        if(passwd==null) {
            return builder.body(ResponseUtils.getResponseBody("还未填写验证码"));
        }
        if (!String.valueOf(passwd).equals(redisTemplate.opsForValue().get(authKey))) {
            return builder.body(ResponseUtils.getResponseBody("验证码不正确"));
        }
       
        HfAuthExample example = new HfAuthExample();
        example.createCriteria().andAuthKeyEqualTo(authKey);
        List<HfAuth> list = hfAuthMapper.selectByExample(example);
        if(list.isEmpty()) {
        	HfUser user = new HfUser();
            user.setSourceType(authType);
            user.setPhone(authKey);
            user.setUserStatus("0".getBytes()[0]);
            user.setLastAuthTime(LocalDateTime.now());
            user.setCreateDate(LocalDateTime.now());
            user.setModifyDate(LocalDateTime.now());
            user.setIdDeleted((byte) 0);
            user.setOwnInvitationCode(create());
            hfUserMapper.insert(user);
            HfAuth auth = new HfAuth();
            auth.setAuthKey(authKey);
            auth.setAuthType(authType);
            auth.setUserId(user.getId());
            auth.setAuthStatus((byte) 0);
            auth.setIdDeleted((byte) 0);
            auth.setEncodeType("0");
            auth.setCreateDate(LocalDateTime.now());
            auth.setModifyDate(LocalDateTime.now());
            auth.setIdDeleted((byte) 0);
            hfAuthMapper.insert(auth);
            userId = user.getId();
        }else {
        	userId = list.get(0).getUserId();
        }
        
        HfUser user = hfUserMapper.selectByPrimaryKey(userId);
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

    public ResponseEntity<JSONObject> addAdminUser(Integer pageNum,Integer pageSize) throws Exception {

        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        if(pageNum==null) {
        	pageNum=0;
        }if(pageSize==null) {
        	pageSize=0;
        }
        
        PageHelper.startPage(pageNum,pageSize);
        List<HfUser> list = hfUserMapper.selectByExample(null);
        List<UserInfo> result = new ArrayList<UserInfo>();
        for (int i = 0; i < list.size(); i++) {
            HfUser hfUser = list.get(i);
            UserInfo info = new UserInfo();
            info.setCreateDate(hfUser.getCreateDate());
            info.setAddress(hfUser.getAddress());
            info.setSex(hfUser.getSex());
            info.setBirthDay(hfUser.getBirthDay());
            info.setFileId(hfUser.getFileId());
            info.setEmail(hfUser.getEmail());
            info.setInvitationCode(hfUser.getInvitationCode());
            info.setOwnInvitationCode(hfUser.getOwnInvitationCode());
            info.setNickName(hfUser.getNickName());
            info.setPhone(hfUser.getPhone());
            info.setId(hfUser.getId());
            HUserBalanceExample example2 = new HUserBalanceExample();
            example2.createCriteria().andUserIdEqualTo(hfUser.getId());
            if(hUserBalanceMapper.selectByExample(example2).isEmpty()) {
            	info.setHfBalance(0);
            }else {
            	hUserBalanceMapper.selectByExample(example2).get(0).getHfBalance();
            }
            
            result.add(info);
        }
        PageInfo<UserInfo> page = new PageInfo<UserInfo>(result);
        return builder.body(ResponseUtils.getResponseBody(page));
    }@RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新用户信息", notes = "更新用户信息")
    public ResponseEntity<JSONObject> update(UserInfoRequest request) throws Exception {
    	
    	BodyBuilder builder = ResponseUtils.getBodyBuilder();
        HfUser user = hfUserMapper.selectByPrimaryKey(request.getUserId());
        if (user == null) {
            throw new UserNotExistException(String.valueOf(request.getUserId()));
        }
        if (!StringUtils.isEmpty(request.getInvitationCode())) {
        	user.setInvitationCode(request.getInvitationCode());
        }
  
        if (!StringUtils.isEmpty(request.getPhone())) {
            user.setPhone(request.getPhone());
        }
        if (!StringUtils.isEmpty(request.getAddress())) {
            user.setAddress(request.getAddress());
        }
        if (!StringUtils.isEmpty(request.getUsername())) {
            user.setUsername(request.getUsername());
        }
		if(!StringUtils.isEmpty(request.getBirthDay())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    	Date date = sdf.parse(request.getBirthDay());
	    	Instant instant = date.toInstant();
	        ZoneId zoneId = ZoneId.systemDefault();
			LocalDateTime ldt = instant.atZone(zoneId).toLocalDateTime();
			user.setBirthDay(ldt);
		}
        if (!StringUtils.isEmpty(request.getEmail())) {
            user.setEmail(request.getEmail());
        }
        if (!StringUtils.isEmpty(request.getNickName())) {
            user.setNickName(request.getNickName());
        }
        if (!StringUtils.isEmpty(request.getRealName())) {
            user.setRealName(request.getRealName());
        }
        if (!StringUtils.isEmpty(request.getRegion())) {
            user.setRegion(request.getRegion());
        }
        if (!StringUtils.isEmpty(request.getSex())) {
            user.setSex(request.getSex());
        }
        if (!StringUtils.isEmpty(request.getUserStatus())) {
            user.setUserStatus(request.getUserStatus());
        }
        if (!StringUtils.isEmpty(request.getCancelId())) {
            user.setCancelId(request.getCancelId());
        }
        user.setModifyDate(LocalDateTime.now());
        user.setIdDeleted((byte) 0);
        
        return builder.body(ResponseUtils.getResponseBody(hfUserMapper.updateByPrimaryKeySelective(user)));
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
    
    public static String create() {
        String code = "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIZXCVBNM";
        String str = "";
        for (int i = 1; i <= 6; i++) {
            String num = String.valueOf(code.charAt((int) Math.floor(Math.random() * code.length())));
            str += num;
            code = code.replaceAll(num, "");
        }
        return str;
    }
}
