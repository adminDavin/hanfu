package com.hanfu.order.center.manual.model;

import com.alipay.api.domain.OrderDetail;
import com.hanfu.order.center.model.HfOrderDetail;

import java.util.List;

public class DetailRequest {
    private Integer stoneId;
    private String stoneName;
    private List<HfOrderDetail> hfOrderDetailList;
    private String detailStatus;
    private String takingType;
    private String wuliu;
    private Integer money;
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

    public String getDetailStatus() {
        return detailStatus;
    }

    public void setDetailStatus(String detailStatus) {
        this.detailStatus = detailStatus;
    }

    public String getTakingType() {
        return takingType;
    }

    public void setTakingType(String takingType) {
        this.takingType = takingType;
    }

    public String getWuliu() {
        return wuliu;
    }

    public void setWuliu(String wuliu) {
        this.wuliu = wuliu;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }
}
