package com.hanfu.product.center.model;

import java.io.Serializable;

public class GoodsResp implements Serializable {
    private Integer id;
    private String hfName;
    private String goodsDesc;
    private Integer quantity;
    private Integer sellPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHfName() {
        return hfName;
    }

    public void setHfName(String hfName) {
        this.hfName = hfName;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Integer sellPrice) {
        this.sellPrice = sellPrice;
    }
}
