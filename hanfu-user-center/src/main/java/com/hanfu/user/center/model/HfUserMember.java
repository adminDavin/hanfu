package com.hanfu.user.center.model;

import java.time.LocalDateTime;

/**
 * @ClassName HfUserMember
 * @Date 2019/12/24 14:44
 * @Author CONSAK
 **/
public class HfUserMember {
    private Integer id;
    private Integer userId;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private Byte userStatus;


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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Byte getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Byte userStatus) {
        this.userStatus = userStatus;
    }
}