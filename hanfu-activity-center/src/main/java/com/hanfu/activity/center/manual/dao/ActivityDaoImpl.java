package com.hanfu.activity.center.manual.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanfu.activity.center.manual.model.HfUser;
import com.hanfu.activity.center.manual.model.UserFormInfo;

@Repository
public class ActivityDaoImpl implements ActivityDao {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public Integer updateActivityEnd(Integer activityId) {
        Integer row = sqlSessionTemplate.update("updateActivityEnd", activityId);
        return row;
    }

    @Override
    public Integer updateActivityStart(Integer activityId) {
        Integer row = sqlSessionTemplate.update("updateActivityStart", activityId);
        return row;
    }
}
