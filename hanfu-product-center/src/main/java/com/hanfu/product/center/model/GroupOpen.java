package com.hanfu.product.center.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class GroupOpen  implements Serializable {
    private Integer id;

    private Integer groupId;

    private Integer connectId;

    private Short isDeleted;
    @DateTimeFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss",timezone = "GMT+8")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss",timezone = "GMT+8")
    private Date stopTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getConnectId() {
        return connectId;
    }

    public void setConnectId(Integer connectId) {
        this.connectId = connectId;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }


}
