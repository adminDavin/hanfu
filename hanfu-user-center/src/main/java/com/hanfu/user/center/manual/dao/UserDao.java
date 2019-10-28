package com.hanfu.user.center.manual.dao;


import com.hanfu.user.center.model.HfAuth;

public interface UserDao {

	HfAuth selectList(String authKey);

}
