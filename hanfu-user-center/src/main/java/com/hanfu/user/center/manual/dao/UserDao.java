package com.hanfu.user.center.manual.dao;


import java.util.List;

import com.hanfu.user.center.manual.model.ActivityUserInfo;
import com.hanfu.user.center.manual.model.UserQuery;
import com.hanfu.user.center.model.HfAuth;
import com.hanfu.user.center.model.HfUser;

public interface UserDao {

	HfAuth selectAuthList(String authKey);

	List<HfUser> selectUserList();
	
//	List<ActivityUserInfo> findActivityUserInfo();
	
	String findDepartmentName(Integer departmentId);
	
	List<ActivityUserInfo> findActivityUserInfo(Integer time);
	
	List<ActivityUserInfo> findActivityUserInfoTP(UserQuery userQuery);

}
