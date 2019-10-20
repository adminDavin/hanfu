package com.hanfu.order.center.request;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class HfOrderLogisticsRequest implements Serializable{
	@ApiModelProperty(required = true, value = "订单id")
	private Integer ordersId;
	@ApiModelProperty(required = true, value = "订单详情id")
	private Integer orderDetailId ;
	@ApiModelProperty(required = true, value = "用户地址id")
	private Integer userAddressId;
	@ApiModelProperty(required = true, value = "物品id")
	private Integer  googsId;
	@ApiModelProperty(required = true, value = "物流名称")
	private String logisticsOrderName;
	@ApiModelProperty(required = true, value = "仓库id")
	private Integer  respId ;
	@ApiModelProperty(required = true, value = "物流订单号")
	private String logisticsOrdersId;
	@ApiModelProperty(required = true, value = "物流公司")
	private String logisticsCompany;
	@ApiModelProperty(required = true, value = "物流描述")
	private String hfDesc;
//	@ApiModelProperty(required = true, value = "添加时间")
//	private LocalDateTime createTime;
	public Integer getOrdersId() {
		return ordersId;
	}
	public void setOrdersId(Integer ordersId) {
		this.ordersId = ordersId;
	}
	public Integer getOrderDetailId() {
		return orderDetailId;
	}
	public void setOrderDetailId(Integer orderDetailId) {
		this.orderDetailId = orderDetailId;
	}
	public Integer getUserAddressId() {
		return userAddressId;
	}
	public void setUserAddressId(Integer userAddressId) {
		this.userAddressId = userAddressId;
	}
	public Integer getGoogsId() {
		return googsId;
	}
	public void setGoogsId(Integer googsId) {
		this.googsId = googsId;
	}
	public String getLogisticsOrderName() {
		return logisticsOrderName;
	}
	public void setLogisticsOrderName(String logisticsOrderName) {
		this.logisticsOrderName = logisticsOrderName;
	}
	public Integer getRespId() {
		return respId;
	}
	public void setRespId(Integer respId) {
		this.respId = respId;
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
	public String getHfDesc() {
		return hfDesc;
	}
	public void setHfDesc(String hfDesc) {
		this.hfDesc = hfDesc;
	}
//	public LocalDateTime getCreateTime() {
//		return createTime;
//	}
//	public void setCreateTime(LocalDateTime createTime) {
//		this.createTime = createTime;
//	}

}