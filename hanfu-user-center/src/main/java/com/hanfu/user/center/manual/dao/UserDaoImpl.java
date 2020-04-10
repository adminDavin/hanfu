package com.hanfu.user.center.manual.dao;


import com.hanfu.user.center.manual.model.ActivityUserInfo;
import com.hanfu.user.center.manual.model.Order;
import com.hanfu.user.center.manual.model.UserInfo;
import com.hanfu.user.center.manual.model.UserOrderInfo;
import com.hanfu.user.center.manual.model.UserQuery;
import com.hanfu.user.center.manual.model.test;
import com.hanfu.user.center.model.HfAuth;
import com.hanfu.user.center.model.HfUser;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public HfAuth selectAuthList(String authKey) {
        HfAuth result = sqlSessionTemplate.selectOne("selectAuthList", authKey);
        return result;
    }

    @Override
    public List<HfUser> selectUserList(UserInfo userInfo) {
        List<HfUser> result = sqlSessionTemplate.selectList("selectUserList",userInfo);
        return result;
    }

    @Override
    public List<ActivityUserInfo> findActivityUserInfo(test test) {
        List<ActivityUserInfo> result = sqlSessionTemplate.selectList("findActivityUserInfo", test);
        return result;
    }


    @Override
    public List<ActivityUserInfo> findActivityUserInfoTP(UserQuery userQuery) {
        System.out.println("userDao:"+userQuery);
        List<ActivityUserInfo> result = sqlSessionTemplate.selectList("findActivityUserInfoTP",userQuery);
        return result;
    }

    @Override
    public String findDepartmentName(Integer departmentId) {
        String result = sqlSessionTemplate.selectOne("findDepartmentName", departmentId);
        return result;
    }
    
    @Override
    public Integer selectBrowseCount(Integer userId) {
    	Integer result = sqlSessionTemplate.selectOne("selectBrowseCount", userId);
    	return result;
    }
    
    @Override
    public Integer selectCollectCount(Integer userId) {
    	Integer result = sqlSessionTemplate.selectOne("selectCollectCount", userId);
    	return result;
    }
    
    @Override
    public Integer selectConcernCount(Integer userId) {
    	Integer result = sqlSessionTemplate.selectOne("selectConcernCount", userId);
    	return result;
    }
    
    @Override
    public Integer selectUserOrderInfo(Order order) {
    	Integer result = sqlSessionTemplate.selectOne("selectUserOrderInfo", order);
    	return result;
    }
}
