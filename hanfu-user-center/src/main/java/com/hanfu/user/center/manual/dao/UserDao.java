package com.hanfu.user.center.manual.dao;


import java.util.List;

import com.hanfu.user.center.model.HfAuth;
import com.hanfu.user.center.model.HfUser;

public interface UserDao {

	HfAuth selectList(String authKey);

	List<HfUser> selectUserList();

}
