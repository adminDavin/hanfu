package com.hanfu.product.center.model;

public class Amount {
    private Integer goodsId;
    private Integer goodsNum;
    private Integer money;
    private Integer discountMoney;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(Integer discountMoney) {
        this.discountMoney = discountMoney;
    }
}
