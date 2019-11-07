package com.hanfu.order.center.response.handler;

@SuppressWarnings("serial")
public class GlobalException extends RuntimeException{
	private String msg;

    public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public GlobalException(String message) {
        this.msg = message;
    }
}
