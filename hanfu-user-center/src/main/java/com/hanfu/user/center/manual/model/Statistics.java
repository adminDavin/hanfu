package com.hanfu.user.center.manual.model;

import io.swagger.models.auth.In;

public class Statistics {
    private Integer userNum;
    private Integer orderNum;

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}
