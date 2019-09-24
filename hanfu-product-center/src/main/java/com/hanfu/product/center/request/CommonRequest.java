package com.hanfu.product.center.request;

import io.swagger.annotations.ApiParam;

public class CommonRequest {
	@ApiParam(name = "用户Id", required = true, type = "Integer")
	private Integer userId;
	@ApiParam(name = "请求Id", required = true, type = "String")
	private String requestId;
	@ApiParam(name = "用户token", required = true, type = "String")
	private String token;
	@ApiParam(name = "请求时间", required = true, type = "String")
	private String timestamp;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	
}

