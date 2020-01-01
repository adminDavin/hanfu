package com.hanfu.group.center.manual.model;

import java.io.Serializable;

public class GroupOpenConnect implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer groupOpenId;

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    private Short isDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGroupOpenId() {
        return groupOpenId;
    }

    public void setGroupOpenId(Integer groupOpenId) {
        this.groupOpenId = groupOpenId;
    }
}