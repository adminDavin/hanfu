package com.hanfu.user.center.manual.model;

/**
 * @author shihao
 * @Title: VipOrder
 * @ProjectName Second-order-center
 * @Description: viporder
 * @date Created in
 * @Version: $
 */
public class VipOrder {
    //状态
    public static enum OrderStatus {
        ALL("all"),
        PAYMENT("payment"),//未支付
        COMPLETE("complete"),//完成
        CONTROVERSIAL("controversial"),
        CANCEL("cancel");//取消

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
    //支付方式
    public static enum PaymentType {
        WECHART(1, "wechart"),//小程序微信
        BALANCE(0, "balance"),//余额
        APPCHART(2, "appchart"),//app微信
        APPALIPAY(3,"appalipay");//app支付宝
        private Integer paymentType;
        private String paymentTypeName;

        PaymentType(Integer paymentType, String paymentTypeName) {
            this.paymentType = paymentType;
            this.paymentTypeName = paymentTypeName;
        }

        public Integer getPaymentType() {
            return this.paymentType;
        }
        public String getPaymentTypeName() {
            return this.paymentTypeName;
        }

        public static PaymentType getPaymentTypeEnum(String name) {
            for(PaymentType payment: PaymentType.values()) {
                if (payment.getPaymentTypeName().equals(name)) {
                    return payment;
                }
            }
            return BALANCE;
        }
    }
    //支付状态
    public static enum PaymentStatus {
        UNPAID(0),
        PAID(1);
        private Integer paymentStatus;
        PaymentStatus(Integer paymentStatus) {
            this.paymentStatus = paymentStatus;
        }
        public Integer getPaymentStatus() {
            return this.paymentStatus;
        }
    }
}
