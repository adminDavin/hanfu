package com.hanfu.payment.center.manual.model;


public enum OrderTypeEnum {
    NOMAL_ORDER("nomalOrder"),
    PAYMENT_ORDER("paymentOrder");
    private String orderType;
    OrderTypeEnum(String orderType) {
        this.orderType = orderType;
    }
    public String getOrderType() {
        return this.orderType;
    }
    
    public static OrderTypeEnum getOrderTypeEnum(String orderType) {
        for(OrderTypeEnum item : OrderTypeEnum.values()) {
            if (item.orderType.equals(orderType)) {
                return item;
            }
        }
        return NOMAL_ORDER;
    }
}
