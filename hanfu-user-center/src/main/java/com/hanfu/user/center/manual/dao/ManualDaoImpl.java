package com.hanfu.user.center.manual.dao;

import java.util.List;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanfu.user.center.manual.model.UserInfo;


@Repository("dbDao")
public class ManualDaoImpl implements ManualDao {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<UserInfo> getSimpleUserInfo() {
        List<UserInfo> result = sqlSessionTemplate.selectList("getSimpleUserInfo");
        return result;
    }

}
