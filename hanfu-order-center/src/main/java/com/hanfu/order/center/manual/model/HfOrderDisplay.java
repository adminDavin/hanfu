package com.hanfu.order.center.manual.model;

import java.time.LocalDateTime;


public class HfOrderDisplay {
    private Integer id;
    private String orderCode;
    private Integer amount;
    private String hfRemark;
    private LocalDateTime modifyTime;
    private String orderStatus;
    private String orderType;
    private Integer payStatus;
    private String paymentName;
    private Integer paymentType;
    private Integer userId;
    
    // 订单详情
    private Integer stoneId;
    private String stoneName;
    private String goodsName;
    private Integer goodsId;
    private Integer fileId;
    private Integer actualPrice;
    private Integer quantity;
    private String takingType;
    private Integer freight;
    private String hfDesc;   
    // 地址管理
    private Integer addressId;
    private String address;
    private String addressDetail;
    private String contact;
    private String phone;
    private String addressDesc;
    private Integer distributorId;

    
    
    public Integer getDistributorId() {
        return distributorId;
    }
    public void setDistributorId(Integer distributorId) {
        this.distributorId = distributorId;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getOrderCode() {
        return orderCode;
    }
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public String getHfRemark() {
        return hfRemark;
    }
    public void setHfRemark(String hfRemark) {
        this.hfRemark = hfRemark;
    }
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }
    public Integer getFileId() {
        return fileId;
    }
    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }
    public String getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    public String getOrderType() {
        return orderType;
    }
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
    public Integer getPayStatus() {
        return payStatus;
    }
    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }
    public String getPaymentName() {
        return paymentName;
    }
    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }
    public Integer getPaymentType() {
        return paymentType;
    }
    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
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
    public String getGoodsName() {
        return goodsName;
    }
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
    public Integer getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }
    public Integer getActualPrice() {
        return actualPrice;
    }
    public void setActualPrice(Integer actualPrice) {
        this.actualPrice = actualPrice;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public String getTakingType() {
        return takingType;
    }
    public void setTakingType(String takingType) {
        this.takingType = takingType;
    }
    public Integer getFreight() {
        return freight;
    }
    public void setFreight(Integer freight) {
        this.freight = freight;
    }
    public String getHfDesc() {
        return hfDesc;
    }
    public void setHfDesc(String hfDesc) {
        this.hfDesc = hfDesc;
    }
    public Integer getAddressId() {
        return addressId;
    }
    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddressDetail() {
        return addressDetail;
    }
    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAddressDesc() {
        return addressDesc;
    }
    public void setAddressDesc(String addressDesc) {
        this.addressDesc = addressDesc;
    }
        
}

