package com.hanfu.activity.center.manual.dao;

import java.util.List;

import com.hanfu.activity.center.manual.model.FileDesc;
import com.hanfu.activity.center.manual.model.VoteRecordsEntity;
import com.hanfu.activity.center.model.ActivityVoteRecords;

public interface VoteRecordsDao {

    List<Integer> distinctUserId(VoteRecordsEntity entity);

    List<Integer> distinctElectedId(Integer activityId);

    List<Integer> distinctUserIdvote(VoteRecordsEntity entity);
}
