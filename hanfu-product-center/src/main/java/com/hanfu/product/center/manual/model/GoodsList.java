package com.hanfu.product.center.manual.model;

public class GoodsList {
    private Integer goodsId;
    private Integer quantity;
    private Integer stoneId;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getStoneId() {
        return stoneId;
    }

    public void setStoneId(Integer stoneId) {
        this.stoneId = stoneId;
    }

	@Override
	public String toString() {
		return "GoodsList [goodsId=" + goodsId + ", quantity=" + quantity + ", stoneId=" + stoneId + "]";
	}
    
}
