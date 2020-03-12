package com.hanfu.user.center.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
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

@RestController
@Api
@RequestMapping("/login")
@CrossOrigin
public class LoginController extends HfUserController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

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
            @RequestParam(value = "iv", required = false) String iv) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        JSONObject SessionKeyOpenId = WxLoginConfig.getSessionKeyOrOpenId(code, appName);
        String openid = (String) SessionKeyOpenId.get("openid");
        String sessionKey = String.valueOf(SessionKeyOpenId.get("session_key"));

        HfAuthExample authAxample = new HfAuthExample();
        authAxample.createCriteria().andAuthKeyEqualTo(openid)
                .andAuthTypeEqualTo(WxLoginConfig.AuthType.WECHART.getAuthType());
        List<HfAuth> auths = hfAuthMapper.selectByExample(authAxample);
        HfUser hfUser = CollectionUtils.isEmpty(auths) ? register(openid, sessionKey, encryptedData, iv): hfUserMapper.selectByPrimaryKey(auths.get(0).getUserId());

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

        map.put("skey", skey);
        map.put("userId", hfUser.getId());
        map.put("user", hfUser);
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        return builder.body(ResponseUtils.getResponseBody(map));
    }

}
