package com.hanfu.order.center.manual.model;


import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class OrdersInfo implements Serializable{
    @ApiModelProperty(required = false, value = "用户id")
    private Integer userId;
    @ApiModelProperty(required = false, value = "支付状态")
    private Integer payStatus;
    @ApiModelProperty(required = false, value = "订单类型")
    private String orderType;
    @ApiModelProperty(required = false, value = "支付金额")
    private Integer amount;
    @ApiModelProperty(required = false, value = "支付方式类型")
    private Integer payMethodType;
    @ApiModelProperty(required = false, value = "支付附言")
    private String hfMemo;
    @ApiModelProperty(required = false, value = "备注")
    private String hfRemark;
    @ApiModelProperty(required = false, value = "支付方式名称")
    private String payMethodName;
    @ApiModelProperty(required = false, value = "仓库id")
    private Integer respId;
    @ApiModelProperty(required = false, value = "订单详情状况")
    private String orderDetailStatus;
    @ApiModelProperty(required = false, value = "物品id")
    private Integer[] googsId;
    @ApiModelProperty(required = false, value = "税金")
    private Integer hfTax;
    @ApiModelProperty(required = false, value = "购买价格")
    private Integer[] purchasePrice;
    @ApiModelProperty(required = false, value = "购买数量")
    private Integer[] purchaseQuantity;
    @ApiModelProperty(required = false, value = "配送方式")
    private String distribution;
    @ApiModelProperty(required = false, value = "订单描述")
    private String hfDesc;
    @ApiModelProperty(required = false, value = "用户地址id")
    private Integer userAddressId;
    @ApiModelProperty(required = false, value = "物流名称")
    private String logisticsOrderName;
    @ApiModelProperty(required = false, value = "物流订单号")
    private String logisticsOrdersId;
    @ApiModelProperty(required = false, value = "物流公司")
    private String logisticsCompany;

	public Integer getUserAddressId() {
		return userAddressId;
	}
	public void setUserAddressId(Integer userAddressId) {
		this.userAddressId = userAddressId;
	}
	public String getLogisticsOrderName() {
		return logisticsOrderName;
	}
	public void setLogisticsOrderName(String logisticsOrderName) {
		this.logisticsOrderName = logisticsOrderName;
	}
	public String getLogisticsOrdersId() {
		return logisticsOrdersId;
	}
	public void setLogisticsOrdersId(String logisticsOrdersId) {
		this.logisticsOrdersId = logisticsOrdersId;
	}
	public String getLogisticsCompany() {
		return logisticsCompany;
	}
	public void setLogisticsCompany(String logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
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
		this.orderType = orderType;
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
		this.hfMemo = hfMemo;
	}
	public String getHfRemark() {
		return hfRemark;
	}
	public void setHfRemark(String hfRemark) {
		this.hfRemark = hfRemark;
	}
	public String getPayMethodName() {
		return payMethodName;
	}
	public void setPayMethodName(String payMethodName) {
		this.payMethodName = payMethodName;
	}
	public Integer getRespId() {
		return respId;
	}
	public void setRespId(Integer respId) {
		this.respId = respId;
	}
	public String getOrderDetailStatus() {
		return orderDetailStatus;
	}
	public void setOrderDetailStatus(String orderDetailStatus) {
		this.orderDetailStatus = orderDetailStatus;
	}
	public Integer[] getGoogsId() {
		return googsId;
	}
	public void setGoogsId(Integer[] googsId) {
		this.googsId = googsId;
	}
	public Integer getHfTax() {
		return hfTax;
	}
	public void setHfTax(Integer hfTax) {
		this.hfTax = hfTax;
	}
	public Integer[] getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(Integer[] purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public Integer[] getPurchaseQuantity() {
		return purchaseQuantity;
	}
	public void setPurchaseQuantity(Integer[] purchaseQuantity) {
		this.purchaseQuantity = purchaseQuantity;
	}
	public String getDistribution() {
		return distribution;
	}
	public void setDistribution(String distribution) {
		this.distribution = distribution;
	}
	public String getHfDesc() {
		return hfDesc;
	}
	public void setHfDesc(String hfDesc) {
		this.hfDesc = hfDesc;
	}

}
