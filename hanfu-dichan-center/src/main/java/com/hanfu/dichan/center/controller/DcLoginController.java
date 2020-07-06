//package com.hanfu.dichan.center.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import com.hanfu.dichan.center.config.WxLoginConfig;
//import com.hanfu.dichan.center.dao.DcUserMapper;
//import com.hanfu.dichan.center.model.DcUser;
//import com.hanfu.utils.response.handler.ResponseEntity;
//import com.hanfu.utils.response.handler.ResponseUtils;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//@RestController
//@Api
//@RequestMapping("/login")
//@CrossOrigin
//public class DcLoginController {
//
//    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Resource
//    private RedisTemplate<String, Object> redisTemplate;
//@Autowired
//private DcUserMapper dcUserMapper;
//
//    @RequestMapping(path = "/wechart", method = RequestMethod.GET)
//    @ApiOperation(value = "微信登录", notes = "微信登录")
//    public ResponseEntity<JSONObject> wxLogin(@RequestParam(value = "code", required = false) String code,
//                                              @RequestParam(value = "appName", required = false) String appName,
//                                              @RequestParam(value = "encryptedData", required = false) String encryptedData,
//                                              @RequestParam(value = "iv", required = false) String iv, HttpServletResponse response, HttpServletRequest request) throws Exception {
//        Object bossId= request.getHeader("bossId");
//        System.out.println(bossId+"我是boss");
//        Map<String, Object> map = new HashMap<String, Object>();
//        JSONObject SessionKeyOpenId = WxLoginConfig.getSessionKeyOrOpenId(code, appName, Integer.valueOf((String) bossId));
//        String openid = (String) SessionKeyOpenId.get("openid");
//        String sessionKey = String.valueOf(SessionKeyOpenId.get("session_key"));
//
////        HfAuthExample authAxample = new HfAuthExample();
////        authAxample.createCriteria().andAuthKeyEqualTo(openid)
////                .andAuthTypeEqualTo(WxLoginConfig.AuthType.WECHART.getAuthType());
////        List<HfAuth> auths = hfAuthMapper.selectByExample(authAxample);
//        DcUser dcUser = CollectionUtils.isEmpty(auths) ? register(openid, sessionKey, encryptedData, iv, Integer.valueOf((String) bossId)): dcUserMapper.selectByPrimaryKey(auths.get(0).getUserId());
//
//        String skey = UUID.randomUUID().toString();
//        String skey_redis = String.valueOf(redisTemplate.opsForValue().get(openid));
//        if (!StringUtils.isEmpty(skey_redis)) {
//            redisTemplate.delete(skey_redis);
//            skey = UUID.randomUUID().toString();
//        }
//
//        JSONObject sessionObj = new JSONObject();
//        sessionObj.put("openId", openid);
//        sessionObj.put("sessionKey", sessionKey);
//        redisTemplate.opsForValue().set(skey, sessionObj.toJSONString());
//        redisTemplate.opsForValue().set(openid.toString(), skey);
//
//        Encrypt encrypt = new Encrypt();
//        String token = encrypt.getToken(true, hfUser.getId(), "user",Integer.valueOf((String)bossId));
//        System.out.println(token);
//        response.addHeader("token", token);
//
//        map.put("skey", skey);
//        map.put("userId", dcUser.getId());
//        map.put("user", dcUser);
//        map.put("token", token);
//        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
//        return builder.body(ResponseUtils.getResponseBody(map));
//    }
//}
