package com.hanfu.user.center.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.hanfu.user.center.dao.HfAuthMapper;
import com.hanfu.user.center.dao.HfUserMapper;
import com.hanfu.user.center.model.HfAuth;
import com.hanfu.user.center.model.HfAuthExample;
import com.hanfu.user.center.model.HfUser;
import com.hanfu.user.center.response.handler.ParamInvalidException;
import com.hanfu.user.center.service.UserCenterService;
import com.hanfu.user.center.utils.Constants;
import com.hanfu.user.center.utils.GetMessageCode;
@Component
public class UserCenterServiceImpl implements UserCenterService{
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	@Autowired
	private HfAuthMapper hfAuthMapper;
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
}
