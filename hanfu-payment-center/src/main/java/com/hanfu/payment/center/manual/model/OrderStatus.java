package com.hanfu.payment.center.manual.model;

public enum OrderStatus {
    ALL("all"),
    PAYMENT("payment"),
    PROCESS("process"),
    TRANSPORT("transport"),
    COMPLETE("complete"),
    EVALUATE("evaluate"),
    CONTROVERSIAL("controversial"),
    CANCEL("cancel");

    private String orderStatus;
    OrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    public String getOrderStatus() {
        return this.orderStatus;
    }
    
    public static OrderStatus getOrderStatusEnum(String name) {
        for(OrderStatus payment: OrderStatus.values()) {
            if (payment.getOrderStatus().equals(name)) {
                return payment;
            }
        }
        return PAYMENT;
     }


}
