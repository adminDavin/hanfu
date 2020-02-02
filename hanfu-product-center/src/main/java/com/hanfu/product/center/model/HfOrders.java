package com.hanfu.product.center.model;

import java.io.Serializable;
import java.util.Date;

public class HfOrders implements Serializable {

    private Integer id;

    private Integer userId;


    private Integer payStatus;

    private String orderType;

    private Integer amount;


    private Integer payMethodType;


    private String hfMemo;

    private String hfRemark;


    private String payMethodName;

    private Date createTime;

    private Date modifyTime;


    private String lastModifier;


    private Short isDeleted;


    private static final long serialVersionUID = 1L;


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

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }


    public String getOrderType() {
        return orderType;
    }


    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }


    public Integer getAmount() {
        return amount;
    }


    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPayMethodType() {
        return payMethodType;
    }

    public void setPayMethodType(Integer payMethodType) {
        this.payMethodType = payMethodType;
    }


    public String getHfMemo() {
        return hfMemo;
    }


    public void setHfMemo(String hfMemo) {
        this.hfMemo = hfMemo == null ? null : hfMemo.trim();
    }

    public String getHfRemark() {
        return hfRemark;
    }

    public void setHfRemark(String hfRemark) {
        this.hfRemark = hfRemark == null ? null : hfRemark.trim();
    }


    public String getPayMethodName() {
        return payMethodName;
    }

    public void setPayMethodName(String payMethodName) {
        this.payMethodName = payMethodName == null ? null : payMethodName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getLastModifier() {
        return lastModifier;
    }


    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier == null ? null : lastModifier.trim();
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }


}