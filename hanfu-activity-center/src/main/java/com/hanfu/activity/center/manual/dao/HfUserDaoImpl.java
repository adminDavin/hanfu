package com.hanfu.activity.center.manual.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanfu.activity.center.manual.model.HfUser;

@Repository
public class HfUserDaoImpl implements HfUserDao{

	@Autowired
    SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public List<HfUser> findAllUser() {
		List<HfUser> result = sqlSessionTemplate.selectList("findAllUser");
		return result;
	}
	
}
