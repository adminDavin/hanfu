package com.hanfu.user.center.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanfu.user.center.dao.UsersMapper;
import com.hanfu.user.center.manual.dao.ManualDao;
import com.hanfu.user.center.model.Users;
import com.hanfu.user.center.model.UsersExample;
import com.hanfu.user.center.request.LoginReuqest;


@Service("commonService")
public class CommonServiceImpl implements CommonService {
 
	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private ManualDao manualDao;
	@Override
	public List<Users> getUserList() {
		UsersExample example = new UsersExample();
		return usersMapper.selectByExample(example);
	}


	@Override
	public Map<String, String> loginUser(LoginReuqest request) {
		// TODO Auto-generated method stub
		return null;
	}


}
