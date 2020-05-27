package com.hanfu.user.center.manual.model;

import java.io.Serializable;

public class MessageType implements Serializable{
	
	public static enum MessageTypeEnum{
		LOGIN("login"),
		ORDER_CREATE("orderCreate");
		private String messageType;
		private MessageTypeEnum(String messageType) {
			this.messageType = messageType;
		}
		public String getMessageType() {
			return messageType;
		}
		public void setMessageType(String messageType) {
			this.messageType = messageType;
		}
		public static MessageTypeEnum getMessageTypeEnum(String messageType) {
			for (MessageTypeEnum item : MessageTypeEnum.values()) {
				if(messageType.equals(item.getMessageType())) {
					return item;
				}
			}
			return null;
		}
	}
}
