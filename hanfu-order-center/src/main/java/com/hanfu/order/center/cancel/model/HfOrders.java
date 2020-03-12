package com.hanfu.order.center.cancel.model;

import io.swagger.annotations.ApiModelProperty;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;


public class HfOrders {
    @KeySql(useGeneratedKeys = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    @ApiModelProperty(value = "支付状态")
    private Integer payStatus;
    @ApiModelProperty(value = "订单类型")
    private String orderType;
    @ApiModelProperty(value = "支付金额")
    private Integer amount;
    @ApiModelProperty(value = "支付方式类型")
    private Integer payMethodType;
    @ApiModelProperty(value = "支付附言")
    private String hfMemo;
    @ApiModelProperty(value = "备注")
    private String hfRemark;
    @ApiModelProperty(value = "支付方式名称")
    private String payMethodName;
    @ApiModelProperty(value = "添加时间")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifyTime;
    @ApiModelProperty(value = "最后一次修改人")
    private String lastModifier;
    @ApiModelProperty(value = "是否失效")
    private Short isDeleted;
    private Integer claim;

    public Integer getClaim() {
        return claim;
    }

    public void setClaim(Integer claim) {
        this.claim = claim;
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
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