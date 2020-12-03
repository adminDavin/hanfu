package com.hanfu.user.center.manual.model;

public class MessageEnum {
    /**
     * 消息类型
     */
    public static enum MessageStatus {
        SOLO("solo"),//单聊
        Group("group");//群发
        private String messageStatus;
        MessageStatus(String messageStatus) {
            this.messageStatus = messageStatus;
        }
        public String getMessageStatus() {
            return this.messageStatus;
        }
    }
}
