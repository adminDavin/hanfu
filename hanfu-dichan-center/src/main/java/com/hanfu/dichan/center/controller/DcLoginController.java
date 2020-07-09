package com.hanfu.dichan.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.dichan.center.config.WxLoginConfig;
import com.hanfu.dichan.center.dao.DcAuthMapper;
import com.hanfu.dichan.center.dao.DcCompanyMapper;
import com.hanfu.dichan.center.dao.DcUserMapper;
import com.hanfu.dichan.center.model.*;
import com.hanfu.dichan.center.utils.Encrypt;
import com.hanfu.dichan.center.utils.Message;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidParameterSpecException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@Api
@RequestMapping("/login")
@CrossOrigin
public class DcLoginController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
@Autowired
private DcUserMapper dcUserMapper;
@Autowired
private DcAuthMapper dcAuthMapper;
@Autowired
private DcCompanyMapper dcCompanyMapper;
    @Autowired
    JedisPool jedisPool;

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

        DcAuthExample authAxample = new DcAuthExample();
        authAxample.createCriteria().andAuthKeyEqualTo(openid)
                .andAuthTypeEqualTo(WxLoginConfig.AuthType.WECHART.getAuthType());
        List<DcAuth> auths = dcAuthMapper.selectByExample(authAxample);
        DcUser dcUser = CollectionUtils.isEmpty(auths) ? register(openid, sessionKey, encryptedData, iv, Integer.valueOf((String) bossId)): dcUserMapper.selectByPrimaryKey(auths.get(0).getUserId());

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
        String token = encrypt.getToken(true, dcUser.getId(), "user",Integer.valueOf((String)bossId));
        System.out.println(token);
        response.addHeader("token", token);

        map.put("skey", skey);
        map.put("userId", dcUser.getId());
        map.put("user", dcUser);
        map.put("token", token);
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        return builder.body(ResponseUtils.getResponseBody(map));
    }
    public DcUser register(String openid, String sessionKey, String encryptedData, String iv,Integer bossId)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException,
            InvalidParameterSpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException, UnsupportedEncodingException {
        JSONObject userInfo = WxLoginConfig.parseWechart(encryptedData, sessionKey, iv);
        DcUser hfUser = new DcUser();

        DcUserExample example = new DcUserExample();
        example.createCriteria().andUsernameEqualTo(openid);
        List<DcUser> list = dcUserMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            hfUser.setAddress(userInfo.getString("country") + " " + userInfo.getString("province") + " " + userInfo.getString("city"));
            hfUser.setUsername(openid);
            hfUser.setCreateDate(LocalDateTime.now());
            hfUser.setModifyDate(LocalDateTime.now());
            hfUser.setIdDeleted((byte) 0);
            hfUser.setCancelId(0);
            hfUser.setOwnInvitationCode(create());
            hfUser.setRegion(userInfo.getString("province"));
            hfUser.setUserStatus((byte) 0);
            hfUser.setNickName(userInfo.getString("nickName"));
            hfUser.setBossId(bossId);
            dcUserMapper.insert(hfUser);
        } else {
            hfUser = list.get(0);
        }
        DcAuth record = new DcAuth();
        record.setAuthKey(openid);
        record.setAuthStatus(Byte.valueOf("0"));
        record.setAuthType(WxLoginConfig.AuthType.WECHART.getAuthType());
        record.setCreateDate(LocalDateTime.now());
        record.setModifyDate(LocalDateTime.now());
        record.setUserId(hfUser.getId());
        dcAuthMapper.insert(record);
        return hfUser;
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
    @ApiOperation(value = "公司编号", notes = "公司编号")
    @RequestMapping(value = "/getCode", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> getCode(String code,HttpServletResponse httpServletResponse,Integer companyId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        DcCompany dcCompany = dcCompanyMapper.selectByPrimaryKey(companyId);
        if (!code.equals(dcCompany.getCompanyCode())){
            httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "无权限");
            return builder.body(ResponseUtils.getResponseBody("编号错误"));
        }
        return builder.body(ResponseUtils.getResponseBody("成功"));
    }
    @ApiOperation(value = "修改公司编号", notes = "修改公司编号")
    @RequestMapping(value = "/updateCode", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> updateCode(String code,HttpServletResponse httpServletResponse,Integer companyId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        DcCompany dcCompany = new DcCompany();
        dcCompany.setId(companyId);
        dcCompany.setCompanyCode(code);
        dcCompanyMapper.updateByPrimaryKeySelective(dcCompany);
        return builder.body(ResponseUtils.getResponseBody("成功"));
    }
    @ApiOperation(value = "添加公司", notes = "添加公司")
    @RequestMapping(value = "/addCompanyId", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addCompanyId(DcCompany dcCompany,HttpServletResponse httpServletResponse) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(dcCompanyMapper.insertSelective(dcCompany)));
    }
    @ApiOperation(value = "我的", notes = "我的")
    @RequestMapping(value = "/getMy", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> getMy(Integer companyId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        DcCompany dcCompany = dcCompanyMapper.selectByPrimaryKey(companyId);
        return builder.body(ResponseUtils.getResponseBody(dcCompany));
    }
    
    
    @RequestMapping(path = "/code", method = RequestMethod.POST)
	@ApiOperation(value = "发送验证码", notes = "发送验证码")
	public ResponseEntity<JSONObject> code(@RequestParam String phone) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		if (!StringUtils.isEmpty(phone)) {
			String s2 = "^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$";
			Pattern p = Pattern.compile(s2);
			Matcher m = p.matcher(phone);
			boolean b = m.matches();
			if (b) {
				Integer code = Message.sendSms(phone);
				redisTemplate.opsForValue().set(phone+"dichan", String.valueOf(code));
				return builder.body(ResponseUtils.getResponseBody(code));
			}
			return builder.body(ResponseUtils.getResponseBody("手机号有误"));
		} else {
			return builder.body(ResponseUtils.getResponseBody("请输入手机号"));
		}
	}
    @RequestMapping(path = "/loginCode", method = RequestMethod.POST)
    @ApiOperation(value = "登陆", notes = "登陆")
    public ResponseEntity<JSONObject> loginCode(@RequestParam String phone,String passwd,HttpServletResponse httpServletResponse) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        Jedis jedis = jedisPool.getResource();
        if (passwd == null) {
            return builder.body(ResponseUtils.getResponseBody("还未填写验证码"));
        }
        System.out.println(redisTemplate.opsForValue().get(phone+"dichan"));
        if (!String.valueOf(passwd).equals(redisTemplate.opsForValue().get(phone+"dichan"))) {
            return builder.body(ResponseUtils.getResponseBody("验证码不正确"));
        }
        if(jedis != null) {
            jedis.close();
        }
        DcUserExample example = new DcUserExample();
        example.createCriteria().andPhoneEqualTo(phone).andIdDeletedEqualTo((byte) 0);
        List<DcUser> list = dcUserMapper.selectByExample(example);
        if (list.size()==0){
            httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "无权限");
            return builder.body(ResponseUtils.getResponseBody("您不是此公司的人"));
        }
        return builder.body(ResponseUtils.getResponseBody(list));
    }
}
