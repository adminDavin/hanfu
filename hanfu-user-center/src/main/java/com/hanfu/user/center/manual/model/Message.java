package com.hanfu.user.center.manual.model;

/**
 * @author shihao
 * @Title: Message
 * @ProjectName Second-order-center
 * @Description: 发送的消息
 * @date Created in
 * @Version: $
 */
public class Message {
    private Integer userId;
    private Integer byUserId;
    private String message;
    private String type;
    private Integer bossId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getByUserId() {
        return byUserId;
    }

    public void setByUserId(Integer byUserId) {
        this.byUserId = byUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getBossId() {
        return bossId;
    }

    public void setBossId(Integer bossId) {
        this.bossId = bossId;
    }
}
