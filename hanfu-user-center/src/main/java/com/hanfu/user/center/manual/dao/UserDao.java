package com.hanfu.user.center.manual.dao;


import com.hanfu.user.center.manual.model.ActivityUserInfo;
import com.hanfu.user.center.manual.model.UserInfo;
import com.hanfu.user.center.manual.model.UserQuery;
import com.hanfu.user.center.manual.model.test;
import com.hanfu.user.center.model.HfAuth;
import com.hanfu.user.center.model.HfUser;

import java.util.List;

public interface UserDao {

    HfAuth selectAuthList(String authKey);

    List<HfUser> selectUserList(UserInfo userInfo);

//	List<ActivityUserInfo> findActivityUserInfo();

    String findDepartmentName(Integer departmentId);

    List<ActivityUserInfo> findActivityUserInfo(test test);

    List<ActivityUserInfo> findActivityUserInfoTP(UserQuery userQuery);

}
