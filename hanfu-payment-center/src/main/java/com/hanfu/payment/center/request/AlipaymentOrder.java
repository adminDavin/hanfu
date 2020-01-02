package com.hanfu.payment.center.request;

import io.swagger.annotations.ApiModelProperty;

public class AlipaymentOrder {
    @ApiModelProperty(required = true, value = "序列号id")
    private String ClubOrderId;
    @ApiModelProperty(required = true, value = "订单号")
    private String OrderId;
    @ApiModelProperty(required = true, value = "交易状态")
    private Byte Status;
    @ApiModelProperty(required = true, value = "订单金额")
    private Integer Amount;
    @ApiModelProperty(required = true, value = "退款金额")
    private Integer RefundFee;

    public String getClubOrderId() {
        return ClubOrderId;
    }

    public void setClubOrderId(String clubOrderId) {
        ClubOrderId = clubOrderId;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public Byte getStatus() {
        return Status;
    }

    public void setStatus(Byte status) {
        Status = status;
    }

    public Integer getAmount() {
        return Amount;
    }

    public void setAmount(Integer amount) {
        Amount = amount;
    }

    public Integer getRefundFee() {
        return RefundFee;
    }

    public void setRefundFee(Integer refundFee) {
        RefundFee = refundFee;
    }
}
