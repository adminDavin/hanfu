package com.hanfu.order.center.request;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class CreateHfOrderRequest extends CommonRequest {
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
    
    public static enum OrderTypeEnum {
        NOMAL_ORDER("nomalOrder"),
        RECHAEGE_ORDER("rechargeOrder"),
        SHOPPING_ORDER("shoppingOrder");
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
    
    public static enum TakingTypeEnum {
        DELIVERY("delivery"),
        SELF_PICKUP("selfPickUp");
        private String takingType;
        
        TakingTypeEnum(String takingType) {
            this.takingType = takingType;
        }
        public String getTakingType() {
            return this.takingType;
        }
        
        public static TakingTypeEnum getTakingTypeEnum(String takingType) {
            for(TakingTypeEnum item : TakingTypeEnum.values()) {
                if (item.takingType.equals(takingType)) {
                    return item;
                }
            }
            return SELF_PICKUP;
        }
    }
    
    public static enum OrderStatus {
        ALL("all"),
        PAYMENT("payment"),
        PROCESS("process"),
        TRANSPORT("transport"),
        EVALUATE("evaluate"),
        COMPLETE("complete"),
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
    
    public static enum PaymentType {
        WECHART(1, "wechart"),
        BALANCE(0, "balance");
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
    
    @ApiModelProperty(required = true, value = "订单类型")
    private String orderType;
    @ApiModelProperty(required = true, value = "支付金额")
    private Integer amount;
    @ApiModelProperty(required = true, value = "支付类型")
    private String paymentName;
    @ApiModelProperty(required = true, value = "订单备注")
    private String hfRemark;
    @ApiModelProperty(required = false, value = "店铺id")
    private Integer stoneId;

    @ApiModelProperty(required = false, value = "核销人员id")
    private Integer distributorId;
    // 订单物品属性
    @ApiModelProperty(required = true, value = "物品id")
    private Integer goodsId;
    @ApiModelProperty(required = true, value = "售卖价格")
    private Integer sellPrice;
    @ApiModelProperty(required = true, value = "实际价格")
    private Integer actualPrice;
    @ApiModelProperty(required = true, value = "购买数量")
    private Integer quantity;
    @ApiModelProperty(required = true, value = "取货方式")
    private String takingType;
    @ApiModelProperty(required = true, value = "运费")
    private Integer freight;
    @ApiModelProperty(required = true, value = "描述")
    private String hfDesc;
    @ApiModelProperty(required = true, value = "邮寄地址")
    private Integer userAddressId;
    
    
    public Integer getStoneId() {
        return stoneId;
    }
    public void setStoneId(Integer stoneId) {
        this.stoneId = stoneId;
    }
    public Integer getUserAddressId() {
        return userAddressId;
    }
    public void setUserAddressId(Integer userAddressId) {
        this.userAddressId = userAddressId;
    }
    
    public Integer getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }
    public Integer getSellPrice() {
        return sellPrice;
    }
    public void setSellPrice(Integer sellPrice) {
        this.sellPrice = sellPrice;
    }
    public Integer getActualPrice() {
        return actualPrice;
    }
    public void setActualPrice(Integer actualPrice) {
        this.actualPrice = actualPrice;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public String getTakingType() {
        return takingType;
    }
    public void setTakingType(String takingType) {
        this.takingType = takingType;
    }
    public Integer getFreight() {
        return freight;
    }
    public void setFreight(Integer freight) {
        this.freight = freight;
    }
    public String getHfDesc() {
        return hfDesc;
    }
    public void setHfDesc(String hfDesc) {
        this.hfDesc = hfDesc;
    }
 

    public String getOrderType() {
        return orderType;
    }
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Integer getDistributorId() {
        return distributorId;
    }
    public void setDistributorId(Integer distributorId) {
        this.distributorId = distributorId;
    }
    public String getPaymentName() {
        return paymentName;
    }
    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }


    public String getHfRemark() {
        return hfRemark;
    }




    public void setHfRemark(String hfRemark) {
        this.hfRemark = hfRemark;
    }




     


}
