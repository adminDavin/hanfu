package com.hanfu.user.center.manual.model;

import io.swagger.annotations.ApiParam;

import java.io.Serializable;

@SuppressWarnings("serial")
public class HfAuthInfo implements Serializable {
    @ApiParam(required = false, value = "用户Id")
    private Integer userId;
    @ApiParam(required = false, value = "鉴定方式")
    private String authType;
    @ApiParam(required = false, value = "鉴权key")
    private String authKey;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }


}
