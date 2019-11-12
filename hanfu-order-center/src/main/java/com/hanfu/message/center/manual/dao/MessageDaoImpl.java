package com.hanfu.message.center.manual.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.hanfu.message.center.manual.model.MessageInfo;

public class MessageDaoImpl implements MessageDao{

	@Autowired
    SqlSessionTemplate sqlSessionTemplate;
	@Override
	public List<MessageInfo> selectMeaasgeList() {
		return null;
	}

}
