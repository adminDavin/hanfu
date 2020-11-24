package com.hanfu.user.center.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hanfu.user.center.utils.Encrypt;
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
import com.hanfu.user.center.dao.HfAuthMapper;
import com.hanfu.user.center.dao.HfUserMapper;
import com.hanfu.user.center.model.HfAuth;
import com.hanfu.user.center.model.HfAuthExample;
import com.hanfu.user.center.model.HfUser;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RestController
@Api
@RequestMapping("/login")
@CrossOrigin
public class LoginController extends HfUserController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
	JedisPool jedisPool;
    
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    HfAuthMapper hfAuthMapper;

    @Autowired
    private HfUserMapper hfUserMapper;

    @RequestMapping(path = "/wechart", method = RequestMethod.GET)
    @ApiOperation(value = "微信登录", notes = "微信登录")
    public ResponseEntity<JSONObject> wxLogin(@RequestParam(value = "code", required = false) String code,
                                              @RequestParam(value = "appName", required = false) String appName,
                                              @RequestParam(value = "encryptedData", required = false) String encryptedData,
                                              @RequestParam(value = "iv", required = false) String iv, HttpServletResponse response, HttpServletRequest request) throws Exception {
        Object bossId= request.getHeader("bossId");
        System.out.println(bossId+"我是boss");
        Map<String, Object> map = new HashMap<String, Object>();
        JSONObject SessionKeyOpenId = WxLoginConfig.getSessionKeyOrOpenId(code, appName, Integer.valueOf((String) bossId));
        String openid = (String) SessionKeyOpenId.get("openid");
        String sessionKey = String.valueOf(SessionKeyOpenId.get("session_key"));

        HfAuthExample authAxample = new HfAuthExample();
        authAxample.createCriteria().andAuthKeyEqualTo(openid)
                .andAuthTypeEqualTo(WxLoginConfig.AuthType.WECHART.getAuthType());
        List<HfAuth> auths = hfAuthMapper.selectByExample(authAxample);
        HfUser hfUser = CollectionUtils.isEmpty(auths) ? register(openid, sessionKey, encryptedData, iv, Integer.valueOf((String) bossId)): hfUserMapper.selectByPrimaryKey(auths.get(0).getUserId());

        String skey = UUID.randomUUID().toString();
        String skey_redis = String.valueOf(redisTemplate.opsForValue().get(openid));
        if (!StringUtils.isEmpty(skey_redis)) {
            redisTemplate.delete(skey_redis);
            skey = UUID.randomUUID().toString();
        }

        JSONObject sessionObj = new JSONObject();
        sessionObj.put("openId", openid);
        sessionObj.put("sessionKey", sessionKey);
        redisTemplate.opsForValue().set(skey, sessionObj.toJSONString());
        redisTemplate.opsForValue().set(openid.toString(), skey);

        Encrypt encrypt = new Encrypt();
        String token = encrypt.getToken(true, hfUser.getId(), "user",Integer.valueOf((String)bossId));
        System.out.println(token);
        response.addHeader("token", token);

        map.put("skey", skey);
        map.put("userId", hfUser.getId());
        map.put("user", hfUser);
        map.put("token", token);
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        return builder.body(ResponseUtils.getResponseBody(map));
    }
    
    @RequestMapping(path = "/loginPhone", method = RequestMethod.GET)
    @ApiOperation(value = "手机号验证码登录", notes = "手机号验证码登录")
    public ResponseEntity<JSONObject> loginPhone(String authKey, String passwd, Integer bossId, HttpServletResponse response) throws Exception {
    	Integer userId = null;
    	Map<String, Object> map = new HashMap<String, Object>();
    	Jedis jedis = jedisPool.getResource();
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		if (passwd == null) {
			return builder.body(ResponseUtils.getResponseBody("还未填写验证码"));
		}
		if (!String.valueOf(passwd).equals(jedis.get(authKey))) {
			return builder.body(ResponseUtils.getResponseBody("验证码不正确"));
		}
		if(jedis != null) {
			jedis.close();
		}
		HfUser user = new HfUser();
		HfAuthExample example = new HfAuthExample();
		example.createCriteria().andAuthKeyEqualTo(authKey);
		List<HfAuth> list = hfAuthMapper.selectByExample(example);
		if (list.isEmpty()) {
//			user.setSourceType(authType);
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
			auth.setAuthType(String.valueOf(2));
			auth.setUserId(user.getId());
			auth.setAuthStatus((byte) 0);
			auth.setIdDeleted((byte) 0);
			auth.setEncodeType("0");
			auth.setCreateDate(LocalDateTime.now());
			auth.setModifyDate(LocalDateTime.now());
			auth.setIdDeleted((byte) 0);
			hfAuthMapper.insert(auth);
			userId = user.getId();
		} else {
			userId = list.get(0).getUserId();
			user = hfUserMapper.selectByPrimaryKey(userId);
		}
        Encrypt encrypt = new Encrypt();
        String token = encrypt.getToken(true, userId, "user",bossId);
        System.out.println(token);
        response.addHeader("token", token);

//        map.put("skey", skey);
        map.put("userId", userId);
        map.put("user", user);
        map.put("token", token);
        return builder.body(ResponseUtils.getResponseBody(map));
    }
    
}
