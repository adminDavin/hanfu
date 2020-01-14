package com.hanfu.activity.center.manual.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.hanfu.activity.center.manual.model.HfUser;
import com.hanfu.activity.center.manual.model.UserFormInfo;

public interface ActivityDao {
    Integer updateActivityStart(Integer activityId);

    Integer updateActivityEnd(Integer activityId);

}
