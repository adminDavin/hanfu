package com.hanfu.user.center.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidParameterSpecException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.user.center.config.WxLoginConfig;
import com.hanfu.user.center.dao.FileDescMapper;
import com.hanfu.user.center.dao.HfAuthMapper;
import com.hanfu.user.center.dao.HfUserMapper;
import com.hanfu.user.center.manual.dao.UserDao;
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
@RequestMapping("/hf-user")
@CrossOrigin
public class HfUserController {

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
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "authType", value = "鉴权方式,  1:用户登录, 2:手机号登录 ",
//                    required = true, type = "String"),
//            @ApiImplicitParam(paramType = "query", name = "authKey", value = "鉴权key", required = false,
//                    type = "String"),
//            @ApiImplicitParam(paramType = "query", name = "passwd", value = "密码", required = false, type = "String"), })
    public ResponseEntity<JSONObject> login(
            String username,  String password) throws Exception {
//        Cookie cookie = new Cookie("autologin", authKey);
//        response.addCookie(cookie);

        BodyBuilder builder = ResponseUtils.getBodyBuilder();
//        HfAuth hfAuth = userDao.selectAuthList(authKey);
//
//        if (hfAuth == null) {
//            return builder.body(ResponseUtils.getResponseBody("还未注册"));
//        }
//
//        if (redisTemplate.opsForValue().get(String.valueOf(hfAuth.getUserId())) == null) {
//
//            String token = "_" + UUID.randomUUID().toString().replaceAll("-", "");
//            redisTemplate.opsForValue().set(String.valueOf(hfAuth.getUserId()), token);
//        } else {
//
//            return builder.body(ResponseUtils.getResponseBody("1"));
//        }
//        if (!passwd.equals(redisTemplate.opsForValue().get(authKey))) {
//
//            return builder.body(ResponseUtils.getResponseBody("成功"));
//        } else {
//
//            return builder.body(ResponseUtils.getResponseBody("验证码不正确"));
//        }
        return builder.body(ResponseUtils.getResponseBody("调通"));
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新用户信息", notes = "更新用户信息")
    public ResponseEntity<JSONObject> update(UserInfoRequest request) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        HfUser user = hfUserMapper.selectByPrimaryKey(request.getUserId());
        if (user == null) {
            throw new UserNotExistException(String.valueOf(request.getUserId()));
        }
        if (!StringUtils.isEmpty(request.getPhone())) {
            user.setPhone(request.getPhone());
        }
        if (!StringUtils.isEmpty(request.getAddress())) {
            user.setAddress(request.getAddress());
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
        hfUserMapper.updateByPrimaryKeySelective(user);

        return builder.body(ResponseUtils.getResponseBody(user));
    }

    @RequestMapping(value = "/update/wechart", method = RequestMethod.GET)
    @ApiOperation(value = "更新用户信息", notes = "更新用户信息")
    public ResponseEntity<JSONObject> updateWechartPhone(@RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "appName", required = false) String appName,
            @RequestParam(value = "encryptedData", required = false) String encryptedData,
            @RequestParam(value = "iv", required = false) String iv) throws Exception {
         
        JSONObject SessionKeyOpenId = WxLoginConfig.getSessionKeyOrOpenId(code, appName);
        String openid = SessionKeyOpenId.getString("openid");
        String sessionKey = SessionKeyOpenId.getString("session_key");
        
        HfAuthExample authAxample = new HfAuthExample();
        authAxample.createCriteria().andAuthKeyEqualTo(openid).andAuthTypeEqualTo(WxLoginConfig.AuthType.WECHART.getAuthType());
        List<HfAuth> auths = hfAuthMapper.selectByExample(authAxample);
        
        if (CollectionUtils.isEmpty(auths)) {
            throw new Exception("please login first.");
        }
        
        JSONObject parseResult = WxLoginConfig.parseWechart(encryptedData, sessionKey, iv);
        System.out.println(parseResult);
        HfUser hfUser = hfUserMapper.selectByPrimaryKey(auths.get(0).getUserId());
        UserInfoRequest request = new UserInfoRequest();
        request.setUserId(hfUser.getId());
        request.setPhone(parseResult.getString("phoneNumber"));
        return update(request);
    }

    @RequestMapping(value = "/create/wechart", method = RequestMethod.GET)
    @ApiOperation(value = "更新用户信息", notes = "更新用户信息")
    public ResponseEntity<JSONObject> createWechart(@RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "appName", required = false) String appName,
            @RequestParam(value = "encryptedData", required = false) String encryptedData,
            @RequestParam(value = "iv", required = false) String iv) throws Exception {
         
        JSONObject SessionKeyOpenId = WxLoginConfig.getSessionKeyOrOpenId(code, appName);
        String openid = SessionKeyOpenId.getString("openid");
        String sessionKey = SessionKeyOpenId.getString("session_key");
        HfAuthExample authAxample = new HfAuthExample();
        authAxample.createCriteria().andAuthKeyEqualTo(openid).andAuthTypeEqualTo(WxLoginConfig.AuthType.WECHART.getAuthType());
        List<HfAuth> auths = hfAuthMapper.selectByExample(authAxample);
        HfUser hfUser = CollectionUtils.isEmpty(auths) ? register(openid, sessionKey, encryptedData, iv): hfUserMapper.selectByPrimaryKey(auths.get(0).getUserId());

        UserInfoRequest request = new UserInfoRequest();
        request.setUserId(hfUser.getId());
        return update(request);
    }
    
    public HfUser register(String openid, String sessionKey, String encryptedData, String iv)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException,
            InvalidParameterSpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException, UnsupportedEncodingException {
        JSONObject userInfo = WxLoginConfig.parseWechart(encryptedData, sessionKey, iv);
        HfUser hfUser = new HfUser();
        
        HfUserExample example = new HfUserExample();
        example.createCriteria().andUsernameEqualTo(openid);
        List<HfUser> list = hfUserMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            hfUser.setAddress(userInfo.getString("country") + " " + userInfo.getString("province") + " " + userInfo.getString("city"));
            hfUser.setUsername(openid);
            hfUser.setCreateDate(LocalDateTime.now());
            hfUser.setModifyDate(LocalDateTime.now());
            hfUser.setIdDeleted((byte) 0);
            hfUser.setCancelId(0);
            hfUser.setRegion(userInfo.getString("province"));
            hfUser.setUserStatus((byte) 0);
            hfUserMapper.insert(hfUser);
        } else {
            hfUser = list.get(0);
        }
        HfAuth record = new HfAuth();
        record.setAuthKey(openid);
        record.setAuthStatus(Byte.valueOf("0"));
        record.setAuthType(WxLoginConfig.AuthType.WECHART.getAuthType());
        record.setCreateDate(LocalDateTime.now());
        record.setModifyDate(LocalDateTime.now());
        record.setUserId(hfUser.getId());
        hfAuthMapper.insert(record);
        return hfUser;
    }

}
