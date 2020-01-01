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
    private String out_trade_no;//商户订单号
    @NotNull
    private Integer user_id;//用户id
    @NotNull
    private Integer id;//订单的id

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

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
