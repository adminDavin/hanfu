package com.hanfu.product.center.model;

public class Amount {
    private Integer id;
    private Integer money;
    private Integer GoodsNum;
    private Integer discountMoney;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getGoodsNum() {
        return GoodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        GoodsNum = goodsNum;
    }

	public Integer getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(Integer discountMoney) {
		this.discountMoney = discountMoney;
	}
    
}
