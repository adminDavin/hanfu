package com.hanfu.product.center.model;

import java.io.Serializable;

public class GroupOpenConnect implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer groupOpenId;

    private  Integer ordersId;

    private String Hfdesc;

    private Integer addressId;
    private Short isDeleted;
    public String getHfdesc() {
        return Hfdesc;
    }

    public void setHfdesc(String hfdesc) {
        Hfdesc = hfdesc;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Integer ordersId) {
        this.ordersId = ordersId;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGroupOpenId() {
        return groupOpenId;
    }

    public void setGroupOpenId(Integer groupOpenId) {
        this.groupOpenId = groupOpenId;
    }
}
