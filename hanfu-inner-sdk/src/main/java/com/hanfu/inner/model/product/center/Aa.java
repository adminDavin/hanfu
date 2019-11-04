package com.hanfu.inner.model.product.center;

import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

public class Aa implements Serializable{
	Integer FileDescId;
	HttpServletResponse response;
	public Integer getFileDescId() {
		return FileDescId;
	}
	public void setFileDescId(Integer fileDescId) {
		FileDescId = fileDescId;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	
}
