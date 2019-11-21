package com.hanfu.message.center.manual.dao;

import java.util.List;

import com.hanfu.message.center.manual.model.MessageInfo;

public interface MessageDao {

	List<MessageInfo> selectMeaasgeList();

}
