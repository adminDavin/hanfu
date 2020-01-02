package com.hanfu.payment.center.request;

import java.io.Serializable;

import io.swagger.annotations.ApiParam;


@SuppressWarnings("serial")
public class CommonRequest implements Serializable {

    @ApiParam(required = true, value = "登录的用户id", example = "0")
    private Integer userId;
    @ApiParam(required = true, value = "请求id, 发起请求的随机数, 用来判断请求是否重复, 一般使用UUID")
    private String requestId;
    @ApiParam(required = true, value = "登录成功后返回的token")
    private String token;
    @ApiParam(required = true, value = "发起请求的当前时间, 时间格式:20181023T081324Z")
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

