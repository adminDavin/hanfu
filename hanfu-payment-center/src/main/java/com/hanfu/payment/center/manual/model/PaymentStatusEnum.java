package com.hanfu.payment.center.manual.model;

public enum PaymentStatusEnum {
    UNPAID(0),
    PAID(1);
    private Integer status;
    PaymentStatusEnum(Integer status) {
        this.status = status;
    }
    public Integer getStatus() {
        return this.status;
    }
}
