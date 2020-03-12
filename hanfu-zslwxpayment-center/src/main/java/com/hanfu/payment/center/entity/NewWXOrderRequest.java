package com.hanfu.payment.center.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: ningcs
 * @create: 2019-06-25 15:06
 **/
@Data
public class NewWXOrderRequest {

    @NotNull
    private String openId; // 小程序的用户openId
    @NotNull
    private Integer total_fee;//金额
    @NotNull
    private String body;//描述
    @NotNull
    private String id;//订单的id

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(Integer total_fee) {
        this.total_fee = total_fee;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
