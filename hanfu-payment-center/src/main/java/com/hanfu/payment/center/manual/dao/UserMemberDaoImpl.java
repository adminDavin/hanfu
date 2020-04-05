package com.hanfu.payment.center.manual.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.hanfu.payment.center.manual.model.UserMemberInfo;

public class UserMemberDaoImpl implements UserMemberDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public Integer isMemberByUserId(UserMemberInfo info) {
		Integer result = sqlSessionTemplate.selectOne("isMemberByUserId", info);
		return result;
	}
	
	@Override
	public void addMember(UserMemberInfo info) {
		sqlSessionTemplate.insert("addMember", info);
		
	}
}
