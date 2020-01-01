package com.hanfu.user.center.manual.dao;


import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanfu.user.center.manual.model.ActivityUserInfo;
import com.hanfu.user.center.manual.model.UserQuery;
import com.hanfu.user.center.model.HfAuth;
import com.hanfu.user.center.model.HfUser;
@Repository
public class UserDaoImpl implements UserDao{
	@Autowired
    SqlSessionTemplate sqlSessionTemplate;
	@Override
	public HfAuth selectAuthList(String authKey) {
		HfAuth result = sqlSessionTemplate.selectOne("selectAuthList",authKey);
		return result;
	}
	@Override
	public List<HfUser> selectUserList() {
		List<HfUser> result = sqlSessionTemplate.selectList("selectUserList");
		return result;
	}
	
	  @Override
      public List<ActivityUserInfo> findActivityUserInfo(Integer time) {
              List<ActivityUserInfo> result = sqlSessionTemplate.selectList("findActivityUserInfo",time);
              return result;
      }
	
	@Override
	public String findDepartmentName(Integer departmentId) {
		String result = sqlSessionTemplate.selectOne("findDepartmentName",departmentId);
		return result;
	}
	
	@Override
    public List<ActivityUserInfo> findActivityUserInfoTP(UserQuery userQuery) {
            System.out.println("userDao:"+userQuery);
            List<ActivityUserInfo> result = sqlSessionTemplate.selectList("findActivityUserInfoTP",userQuery);
            return result;
    }
}
