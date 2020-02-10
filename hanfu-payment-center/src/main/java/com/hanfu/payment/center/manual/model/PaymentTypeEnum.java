package com.hanfu.payment.center.manual.model;


public enum PaymentTypeEnum {
    WECHART(1, "wechart"),
    BALANCE(0, "balance");
    private Integer paymentType;
    private String paymentTypeName;
    
    PaymentTypeEnum(Integer paymentType, String paymentTypeName) {
        this.paymentType = paymentType;
        this.paymentTypeName = paymentTypeName;
    }
    
    public Integer getPaymentType() {
        return this.paymentType;
    }
    public String getPaymentTypeName() {
        return this.paymentTypeName;
    }
    
    public static PaymentTypeEnum getPaymentTypeEnum(String name) {
       for(PaymentTypeEnum payment: PaymentTypeEnum.values()) {
           if (payment.getPaymentTypeName().equals(name)) {
               return payment;
           }
       }
       return BALANCE;
    }
}
