package com.hanfu.user.center.manual.model;

import java.io.Serializable;

public class MessageType implements Serializable{
	
	public static enum MessageTypeEnum{
		SHORT_BREATH("shortBreath"),
		EMAIL("email");
		private String messageType;
		
		public String getMessageType() {
			return messageType;
		}
		
		private MessageTypeEnum(String messageType) {
			this.messageType = messageType;
		}
		
		public static MessageTypeEnum getMessageTypeEnum(String messageType) {
			for (MessageTypeEnum item : MessageTypeEnum.values()) {
				if(item.getMessageType().equals(messageType)) {
					return item;
				}
			}
			return null;
		}
		
	}
	
	public static enum MessageContentTypeEnum{
		LOGIN("login"),
		ORDER_CREATE("orderCreate");
		private String messageContentType;
		private MessageContentTypeEnum(String messageContentType) {
			this.messageContentType = messageContentType;
		}
		public String getMessageContentType() {
			return messageContentType;
		}
		public void setMessageContentType(String messageContentType) {
			this.messageContentType = messageContentType;
		}
		public static MessageContentTypeEnum getMessageContentTypeEnum(String messageContentType) {
			for (MessageContentTypeEnum item : MessageContentTypeEnum.values()) {
				if(item.getMessageContentType().equals(messageContentType)) {
					return item;
				}
			}
			return null;
		}
	}
}
