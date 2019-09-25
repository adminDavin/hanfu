package com.hanfu.user.center.request;

import java.io.Serializable;

public class RegisterReuqest implements Serializable {
	private String username;
	private String password;
	private String sourceType;
	private String authKey;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getAuthKey() {
		return authKey;
	}
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
	@Override
	public String toString() {
		return "RegisterReuqest [username=" + username + ", password=" + password + ", sourceType=" + sourceType
				+ ", authKey=" + authKey + "]";
	}
	
}
