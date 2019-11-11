package com.hanfu.user.center.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hanfu.user.center.dao.HfUserAddresseMapper;
import com.hanfu.user.center.dao.HfUserMapper;
import com.hanfu.user.center.model.HfAuth;
import com.hanfu.user.center.model.HfUser;
import com.hanfu.user.center.model.HfUserAddresse;
import com.hanfu.user.center.response.handler.ParamInvalidException;
import com.hanfu.user.center.service.UserCenterService;
import com.hanfu.user.center.utils.Constants;
import com.hanfu.user.center.utils.GetMessageCode;
@Component
@Service("UserCenterService")
@org.apache.dubbo.config.annotation.Service(registry = "dubboProductServer")
public class UserCenterServiceImpl implements UserCenterService,com.hanfu.inner.sdk.user.center.UserCenterService{
	@Autowired
	HfUserMapper hfUserMapper;
	@Autowired
	HfUserAddresseMapper hfUserAddresseMapper;
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	public boolean checkToken(String token){
		//解析出userId和uuid
		if(token==null || "".equals(token)){
			return false;
		}
		String[] arr1 = token.split("_");
		if(arr1.length != 2){
			return false;
		}
		//根据redis进行检查
		String key = arr1[0]+"_token";
		String r_token = (String)redisTemplate.opsForValue().get(key);
		if(r_token==null){
			return false;
		}
		if(!token.equals(r_token)){
			return false;
		}
		//返回检测结果,更新token时间
		redisTemplate.opsForValue().set(key, token, 
				Constants.STATE_MANAGER, TimeUnit.HOURS);
		return true;
	}
	@Override
	public Map<String, Integer> login(String authType, String authKey, String passwd, String token) throws Exception {
		HfAuth hfAuth = new HfAuth();
		Map<String , Integer> list = new HashMap<>();
		//生成token
		UUID uuid = UUID.randomUUID();
		token ="_"+uuid.toString().replaceAll("-", "");
		//将token存入redis
		String key ="_token";
		redisTemplate.opsForValue().set(key, token, 
				Constants.STATE_MANAGER, TimeUnit.HOURS);
		if (StringUtils.isEmpty(token)) { 
			checkToken(token);
		}
		if(hfAuth.getAuthType().equals(authType)) {
			if(hfAuth.getAuthKey().equals(authKey)) {
				if(passwd != GetMessageCode.getCode(authKey)) {
					throw new ParamInvalidException("authType is invalid");
				}
			}		
		}
		list.put(token, hfAuth.getUserId());
		return list;
	}
	@Override
	public List<com.hanfu.inner.model.product.center.HfUser> getUserById(Integer id) {	
		List<HfUser> hfUser = hfUserMapper.selectByExample(null);
		 return JSONArray.parseArray(JSONObject.toJSONString(hfUser), com.hanfu.inner.model.product.center.HfUser.class);
	}
	@Override
	public List<com.hanfu.inner.model.product.center.HfUserAddresse> getUserAddressById(Integer id) {
		List<HfUserAddresse> hfUserAddress = hfUserAddresseMapper.selectByExample(null);
		 return JSONArray.parseArray(JSONObject.toJSONString(hfUserAddress), com.hanfu.inner.model.product.center.HfUserAddresse.class);
	}
}
