package com.hanfu.message.center.vo;

import java.io.Serializable;

/**
 * 获取微信accessToken接口信息，参数封装
 */
@SuppressWarnings("serial")
public class WxAccessTokenVO implements Serializable {

    /**
     * 获取到的凭证.
     */
    private String accessToken;

    /**
     * 凭证有效时间，单位：秒.
     */
    private Integer expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }
}
