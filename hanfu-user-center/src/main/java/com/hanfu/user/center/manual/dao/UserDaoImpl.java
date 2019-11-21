package com.hanfu.user.center.manual.dao;


import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanfu.user.center.model.HfAuth;
import com.hanfu.user.center.model.HfUser;
@Repository
public class UserDaoImpl implements UserDao{
	@Autowired
    SqlSessionTemplate sqlSessionTemplate;
	@Override
	public HfAuth selectList(String authKey) {
		HfAuth result = sqlSessionTemplate.selectOne("selectAuthList",authKey);
		return result;
	}
	@Override
	public List<HfUser> selectUserList() {
		List<HfUser> result = sqlSessionTemplate.selectList("selectUserList");
		return result;
	}

}
