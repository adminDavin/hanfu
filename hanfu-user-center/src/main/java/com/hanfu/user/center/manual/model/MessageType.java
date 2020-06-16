package com.hanfu.user.center.manual.model;

import java.io.Serializable;
import java.util.List;

import com.hanfu.user.center.model.HfMessageTemplate;

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
	
	private String contentType;
	private HfMessageTemplate template;
	private String messageType;
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public HfMessageTemplate getTemplate() {
		return template;
	}
	public void setTemplate(HfMessageTemplate template) {
		this.template = template;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
}
