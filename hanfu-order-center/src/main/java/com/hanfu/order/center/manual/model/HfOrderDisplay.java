package com.hanfu.order.center.manual.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanfu.order.center.model.HfOrderDetail;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.List;


public class HfOrderDisplay {
    private Integer id;
    private String orderCode;
    private Integer amount;
    private String hfRemark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    private String orderStatus;
    private String orderType;
    private Integer payStatus;
    private String paymentName;
    private Integer paymentType;
    private Integer userId;
    @ApiModelProperty(required = true, value = "用户昵称")
    private String nickName;

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
    private List<DetailRequest> detailRequestList;

    private String activity;
    private Long activityTime;
    private Integer nowSum;
    private Integer groupSum;
    private Integer activityState;
    private Integer payOrderId;



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

    public Integer getFileId() {
        return fileId;
    }
    public void setFileId(Integer fileId) {
        this.fileId = fileId;
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

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Long getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(Long activityTime) {
        this.activityTime = activityTime;
    }

    public Integer getNowSum() {
        return nowSum;
    }

    public void setNowSum(Integer nowSum) {
        this.nowSum = nowSum;
    }

    public Integer getGroupSum() {
        return groupSum;
    }

    public void setGroupSum(Integer groupSum) {
        this.groupSum = groupSum;
    }

    public Integer getActivityState() {
        return activityState;
    }

    public void setActivityState(Integer activityState) {
        this.activityState = activityState;
    }

    public List<DetailRequest> getDetailRequestList() {
        return detailRequestList;
    }

    public void setDetailRequestList(List<DetailRequest> detailRequestList) {
        this.detailRequestList = detailRequestList;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(Integer payOrderId) {
        this.payOrderId = payOrderId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}

