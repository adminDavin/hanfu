package com.hanfu.user.center.service;

import java.util.List;
import java.util.Map;

import com.hanfu.user.center.model.Users;
import com.hanfu.user.center.request.UserInfoRequest;

public interface UserCenterService {

	boolean checkToken(String token);

	Map<String, Integer> login();

	Map<String, Integer> register();

	List<Users> update(UserInfoRequest request);



}
