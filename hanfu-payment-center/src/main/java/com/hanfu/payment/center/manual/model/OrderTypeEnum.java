package com.hanfu.payment.center.manual.model;


public enum OrderTypeEnum {
    RECHAEGE_ORDER("rechargeOrder"), // 充值订单
    NOMAL_ORDER("nomalOrder"), // 购物订单
    SHOPPING_ORDER("shoppingOrder"); // 支付订单
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
