package com.hanfu.user.center.manual.dao;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanfu.user.center.model.HfAuth;
@Repository
public class UserDaoImpl implements UserDao{
	@Autowired
    SqlSessionTemplate sqlSessionTemplate;
	@Override
	public HfAuth selectList(String authKey) {
		HfAuth result = sqlSessionTemplate.selectOne("selectAuthList",authKey);
		return result;
	}

}
