package com.hanfu.payment.center.manual.dao;

import com.hanfu.payment.center.manual.model.UserMemberInfo;

public interface UserMemberDao {
	
	public Integer isMemberByUserId(UserMemberInfo info);
	
	public void addMember(UserMemberInfo info);
}
