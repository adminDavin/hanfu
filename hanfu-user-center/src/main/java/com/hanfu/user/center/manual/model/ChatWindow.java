package com.hanfu.user.center.manual.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * @author shihao
 * @Title: ChatWindow
 * @ProjectName Second-order-center
 * @Description: 聊天窗口
 * @date Created in
 * @Version: $
 */
public class ChatWindow {
    private Integer userId;//建立人id
    private String userName;//建立人姓名
    private Integer userFile;//头像
    private Integer byUserFile;//被建立人头像
    private String byUserName;//被建立人姓名
    private Integer byUserId;//被建立人id
    private Integer unread;//未读条数
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyTime;//修改时间

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getByUserName() {
        return byUserName;
    }

    public void setByUserName(String byUserName) {
        this.byUserName = byUserName;
    }

    public Integer getByUserId() {
        return byUserId;
    }

    public void setByUserId(Integer byUserId) {
        this.byUserId = byUserId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getUserFile() {
        return userFile;
    }

    public void setUserFile(Integer userFile) {
        this.userFile = userFile;
    }

    public Integer getByUserFile() {
        return byUserFile;
    }

    public void setByUserFile(Integer byUserFile) {
        this.byUserFile = byUserFile;
    }

    public Integer getUnread() {
        return unread;
    }

    public void setUnread(Integer unread) {
        this.unread = unread;
    }

    @Override
    public String toString() {
        return "ChatWindow{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userFile='" + userFile + '\'' +
                ", byUserFile='" + byUserFile + '\'' +
                ", byUserName='" + byUserName + '\'' +
                ", byUserId=" + byUserId +
                ", unread=" + unread +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
