package com.hanfu.user.center.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hanfu.user.center.dao.UsersMapper;
import com.hanfu.user.center.model.Users;
import com.hanfu.user.center.model.UsersExample;
import com.hanfu.user.center.request.LoginReuqest;
import com.hanfu.user.center.request.RegisterReuqest;


@Service("commonService")
public class CommonServiceImpl implements CommonService {
 
	@Autowired
	private UsersMapper usersMapper;
	
	
	@Override
	public List<Users> getUserList() {
		UsersExample example = new UsersExample();
		return usersMapper.selectByExample(example);
	}

 
	@Override
	public Map<String, String> loginUser(LoginReuqest request) {
		Map<String,String> list = new HashMap<>();
		UsersExample example = new UsersExample();
		usersMapper.selectByExample(example);
		example.createCriteria().andIdGreaterThanOrEqualTo(1);
        long count = usersMapper.countByExample(example);
	        if (count > 0) {
	            return null; 
	        }
			list.put(request.getSourceType(), request.getAuthKey());
	        return list;
	}


	@Override
	public Map<String, String> registerUser(RegisterReuqest request) {			
		RegisterReuqest re= JSON.parseObject(JSON.toJSONString(request),RegisterReuqest.class);
		Map<String,String> list = new HashMap<>();
		list.put(re.getAuthKey(), re.getSourceType());
		return list;
	}


}
