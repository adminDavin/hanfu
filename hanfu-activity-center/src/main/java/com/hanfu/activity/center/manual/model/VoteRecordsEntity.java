package com.hanfu.activity.center.manual.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class VoteRecordsEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4746650606978736939L;

    private Integer activityId;
    private Integer userId;
    private Integer electedId;
    private String voteName;
    private String voteRealName;
    private String voteNickName;
    private String eceltedName;
    private String voteTimes;
    private List<VoteEntity> voteEntity;
    private String totalScore;
    private String onlineScore;
    private String offlineScore;
    private Integer voteCount;
    private List<Double> socre;
    private Integer type;

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getVoteName() {
        return voteName;
    }

    public void setVoteName(String voteName) {
        this.voteName = voteName;
    }

    public String getEceltedName() {
        return eceltedName;
    }

    public void setEceltedName(String eceltedName) {
        this.eceltedName = eceltedName;
    }

    public String getVoteTimes() {
        return voteTimes;
    }

    public void setVoteTimes(String voteTimes) {
        this.voteTimes = voteTimes;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public List<VoteEntity> getVoteEntity() {
        return voteEntity;
    }

    public void setVoteEntity(List<VoteEntity> voteEntity) {
        this.voteEntity = voteEntity;
    }

    public String getOnlineScore() {
        return onlineScore;
    }

    public void setOnlineScore(String onlineScore) {
        this.onlineScore = onlineScore;
    }

    public String getOfflineScore() {
        return offlineScore;
    }

    public void setOfflineScore(String offlineScore) {
        this.offlineScore = offlineScore;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public String getVoteRealName() {
        return voteRealName;
    }

    public void setVoteRealName(String voteRealName) {
        this.voteRealName = voteRealName;
    }

    public String getVoteNickName() {
        return voteNickName;
    }

    public void setVoteNickName(String voteNickName) {
        this.voteNickName = voteNickName;
    }

    public List<Double> getSocre() {
        return socre;
    }

    public void setSocre(List<Double> socre) {
        this.socre = socre;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getElectedId() {
        return electedId;
    }

    public void setElectedId(Integer electedId) {
        this.electedId = electedId;
    }

    @Override
    public String toString() {
        return "VoteRecordsEntity [activityId=" + activityId + ", userId=" + userId + ", electedId=" + electedId
                + ", voteName=" + voteName + ", voteRealName=" + voteRealName + ", voteNickName=" + voteNickName
                + ", eceltedName=" + eceltedName + ", voteTimes=" + voteTimes + ", voteEntity=" + voteEntity
                + ", totalScore=" + totalScore + ", onlineScore=" + onlineScore + ", offlineScore=" + offlineScore
                + ", voteCount=" + voteCount + ", socre=" + socre + ", type=" + type + "]";
    }
}
