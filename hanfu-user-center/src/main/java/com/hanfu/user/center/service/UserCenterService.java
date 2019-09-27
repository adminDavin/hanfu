package com.hanfu.user.center.service;

import java.util.Map;


public interface UserCenterService {

	boolean checkToken(String token);

	Map<String, Integer> login();

	Map<String, Integer> register();



}
