package com.hanfu.order.center.manual.model;

import com.alipay.api.domain.OrderDetail;
import com.hanfu.order.center.model.HfOrderDetail;

import java.util.List;

public class DetailRequest {
    private Integer stoneId;
    private String stoneName;
    private List<HfOrderDetail> hfOrderDetailList;

    public Integer getStoneId() {
        return stoneId;
    }

    public void setStoneId(Integer stoneId) {
        this.stoneId = stoneId;
    }

    public String getStoneName() {
        return stoneName;
    }

    public void setStoneName(String stoneName) {
        this.stoneName = stoneName;
    }

    public List<HfOrderDetail> getHfOrderDetailList() {
        return hfOrderDetailList;
    }

    public void setHfOrderDetailList(List<HfOrderDetail> hfOrderDetailList) {
        this.hfOrderDetailList = hfOrderDetailList;
    }
}