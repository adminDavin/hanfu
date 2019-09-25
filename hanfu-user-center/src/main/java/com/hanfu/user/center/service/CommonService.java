package com.hanfu.user.center.service;

import java.util.List;
import java.util.Map;

import com.hanfu.user.center.model.Users;
import com.hanfu.user.center.request.LoginReuqest;
import com.hanfu.user.center.request.RegisterReuqest;

public interface CommonService {
	public List<Users> getUserList();

	public Map<String, String> loginUser(LoginReuqest request);

	public Map<String, String> registerUser(RegisterReuqest request);
}
