package com.hanfu.activity.center.manual.model;

import java.io.Serializable;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.hanfu.activity.center.model.ActivityVoteRecords;

public class VoteEntity implements Serializable {

    private String voteName;
    private Integer count;
    private List<VoteRecordsEntity> voteRecordsEntity;
    private List<Evaluate> evaluate;
    private PageInfo<ActivityVoteRecords> pageInfo;

    public String getVoteName() {
        return voteName;
    }

    public void setVoteName(String voteName) {
        this.voteName = voteName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<VoteRecordsEntity> getVoteRecordsEntity() {
        return voteRecordsEntity;
    }

    public void setVoteRecordsEntity(List<VoteRecordsEntity> voteRecordsEntity) {
        this.voteRecordsEntity = voteRecordsEntity;
    }

    public List<Evaluate> getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(List<Evaluate> evaluate) {
        this.evaluate = evaluate;
    }

    public PageInfo<ActivityVoteRecords> getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo<ActivityVoteRecords> pageInfo) {
        this.pageInfo = pageInfo;
    }
}
