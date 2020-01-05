package com.hanfu.activity.center.manual.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanfu.activity.center.manual.model.FileDesc;
import com.hanfu.activity.center.manual.model.HfUser;
import com.hanfu.activity.center.manual.model.VoteRecordsEntity;
import com.hanfu.activity.center.model.ActivityVoteRecords;

@Repository
public class VoteRecordsDaoImpl implements VoteRecordsDao {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<Integer> distinctUserId(VoteRecordsEntity entity) {
        List<Integer> result = sqlSessionTemplate.selectList("distinctUserId", entity);
        return result;
    }

    @Override
    public List<Integer> distinctElectedId(Integer activityId) {
        List<Integer> result = sqlSessionTemplate.selectList("distinctElectedId", activityId);
        return result;
    }

    @Override
    public List<Integer> distinctUserIdvote(VoteRecordsEntity entity) {
        List<Integer> result = sqlSessionTemplate.selectList("distinctUserIdvote", entity);
        return result;
    }

}
