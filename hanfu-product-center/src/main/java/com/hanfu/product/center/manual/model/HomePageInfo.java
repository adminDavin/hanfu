package com.hanfu.product.center.manual.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

public class HomePageInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3834215356820052568L;

    @ApiModelProperty(required = true, value = "今日总金额")
    private double amountDay;
    @ApiModelProperty(required = true, value = "今日支付订单数")
    private Integer orderCountsDay;
    @ApiModelProperty(required = true, value = "今日支付人数")
    private Integer paymentConutsDay;
    @ApiModelProperty(required = true, value = "今日浏览人数")
    private Integer browseCountsDay;
    @ApiModelProperty(required = true, value = "昨日支付订单数")
    private Integer orderCountsYestday;
    @ApiModelProperty(required = true, value = "昨日支付人数")
    private Integer paymentConutsYestday;
    @ApiModelProperty(required = true, value = "昨日浏览人数")
    private Integer browseCountsYestday;
    @ApiModelProperty(required = true, value = "今月总金额")
    private double amountMouth;
    @ApiModelProperty(required = true, value = "本月支付订单数")
    private Integer orderConutsMouth;
    @ApiModelProperty(required = true, value = "本月支付人数")
    private Integer paymentConutsMouth;
    @ApiModelProperty(required = true, value = "本月浏览人数")
    private Integer browseCountsMouth;
    @ApiModelProperty(required = true, value = "上月支付订单数")
    private Integer orderConutsLastMouth;
    @ApiModelProperty(required = true, value = "上月支付人数")
    private Integer paymentConutsLastMouth;
    @ApiModelProperty(required = true, value = "上月浏览人数")
    private Integer browseCountsLastMouth;
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(required = false, value = "创建时间")
    private LocalDateTime createTime;

	public double getAmountDay() {
		return amountDay;
	}

	public void setAmountDay(double amountDay) {
		this.amountDay = amountDay;
	}

	public Integer getOrderCountsDay() {
		return orderCountsDay;
	}

	public void setOrderCountsDay(Integer orderCountsDay) {
		this.orderCountsDay = orderCountsDay;
	}

	public Integer getPaymentConutsDay() {
		return paymentConutsDay;
	}

	public void setPaymentConutsDay(Integer paymentConutsDay) {
		this.paymentConutsDay = paymentConutsDay;
	}

	public Integer getBrowseCountsDay() {
		return browseCountsDay;
	}

	public void setBrowseCountsDay(Integer browseCountsDay) {
		this.browseCountsDay = browseCountsDay;
	}

	public Integer getOrderCountsYestday() {
		return orderCountsYestday;
	}

	public void setOrderCountsYestday(Integer orderCountsYestday) {
		this.orderCountsYestday = orderCountsYestday;
	}

	public Integer getPaymentConutsYestday() {
		return paymentConutsYestday;
	}

	public void setPaymentConutsYestday(Integer paymentConutsYestday) {
		this.paymentConutsYestday = paymentConutsYestday;
	}

	public Integer getBrowseCountsYestday() {
		return browseCountsYestday;
	}

	public void setBrowseCountsYestday(Integer browseCountsYestday) {
		this.browseCountsYestday = browseCountsYestday;
	}

	public double getAmountMouth() {
		return amountMouth;
	}

	public void setAmountMouth(double amountMouth) {
		this.amountMouth = amountMouth;
	}

	public Integer getOrderConutsMouth() {
		return orderConutsMouth;
	}

	public void setOrderConutsMouth(Integer orderConutsMouth) {
		this.orderConutsMouth = orderConutsMouth;
	}

	public Integer getPaymentConutsMouth() {
		return paymentConutsMouth;
	}

	public void setPaymentConutsMouth(Integer paymentConutsMouth) {
		this.paymentConutsMouth = paymentConutsMouth;
	}

	public Integer getBrowseCountsMouth() {
		return browseCountsMouth;
	}

	public void setBrowseCountsMouth(Integer browseCountsMouth) {
		this.browseCountsMouth = browseCountsMouth;
	}

	public Integer getOrderConutsLastMouth() {
		return orderConutsLastMouth;
	}

	public void setOrderConutsLastMouth(Integer orderConutsLastMouth) {
		this.orderConutsLastMouth = orderConutsLastMouth;
	}

	public Integer getPaymentConutsLastMouth() {
		return paymentConutsLastMouth;
	}

	public void setPaymentConutsLastMouth(Integer paymentConutsLastMouth) {
		this.paymentConutsLastMouth = paymentConutsLastMouth;
	}

	public Integer getBrowseCountsLastMouth() {
		return browseCountsLastMouth;
	}

	public void setBrowseCountsLastMouth(Integer browseCountsLastMouth) {
		this.browseCountsLastMouth = browseCountsLastMouth;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}
