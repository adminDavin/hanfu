package com.hanfu.order.center.manual.model;

import java.util.List;

public class OrderCHECK {
    private Integer GoodsNum;
    private Integer activityId;
    private Integer goodsId;

    public Integer getGoodsNum() {
        return GoodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        GoodsNum = goodsNum;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

}
