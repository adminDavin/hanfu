package com.hanfu.order.center.cancel.model;

public class test {
    private Integer goodsId;
    private Integer orderId;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "test{" +
                "goodsId=" + goodsId +
                ", orderId=" + orderId +
                '}';
    }
}
