package com.hanfu.activity.center.manual.model;

import org.springframework.web.multipart.MultipartFile;

import com.hanfu.activity.center.request.CommonRequest;

public class Pictures {

    private MultipartFile fileInfo;
    private Integer activityId;
    private Integer userId;

    public MultipartFile getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(MultipartFile fileInfo) {
        this.fileInfo = fileInfo;
    }

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
}
