package com.hanfu.user.center.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.hanfu.user.center.dao.HfUserMapper;
import com.hanfu.user.center.model.HfUser;
import com.hanfu.user.center.service.UserCenterService;
import com.hanfu.user.center.utils.Constants;
@Component
public class UserCenterServiceImpl implements UserCenterService{
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	@Autowired
	private HfUserMapper hfUsersMapper;
	@Override
	public Map<String, Integer> login() {	
		Map<String , Integer> list = new HashMap<>();
		//生成token
		UUID uuid = UUID.randomUUID();
		String token ="_"+uuid.toString().replaceAll("-", "");
		//将token存入redis
		String key ="_token";
		redisTemplate.opsForValue().set(key, token, 
			Constants.STATE_MANAGER, TimeUnit.HOURS);
        HfUser hfUser = new HfUser();
		list.put(token, hfUser.getId());
		return list;
	}
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
}
