package com.hanfu.product.center.manual.model;

import java.io.Serializable;


@SuppressWarnings("serial")
public class UserInfo implements Serializable {

    private Integer userId;

    private String username;

    private String email;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
