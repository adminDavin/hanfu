package com.hanfu.product.center.manual.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

public class ActivityProductInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3834215356820052568L;

    @ApiModelProperty(required = true, value = "序列号")
    private Integer id;
    @ApiModelProperty(required = true, value = "活动id")
    private Integer acivityId;
    @ApiModelProperty(required = true, value = "商品id")
    private Integer productId;
    @ApiModelProperty(required = true, value = "商品名称")
    private String productName;
    @ApiModelProperty(required = true, value = "折扣比例")
    private Double discountRatio;
    @ApiModelProperty(required = true, value = "优惠价格")
    private Double favoravlePrice;
    @ApiModelProperty(required = false, value = "团购人数")
    private Integer groupNum;
    @ApiModelProperty(required = false, value = "商品上限")
    private Integer inventoryCelling;
    @ApiModelProperty(required = false, value = "分销比例")
    private String distributionRatio;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(required = false, value = "创建时间")
    private LocalDateTime createTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(required = false, value = "修改时间")
    private LocalDateTime modifyTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAcivityId() {
		return acivityId;
	}
	public void setAcivityId(Integer acivityId) {
		this.acivityId = acivityId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Double getDiscountRatio() {
		return discountRatio;
	}
	public void setDiscountRatio(Double discountRatio) {
		this.discountRatio = discountRatio;
	}
	public Double getFavoravlePrice() {
		return favoravlePrice;
	}
	public void setFavoravlePrice(Double favoravlePrice) {
		this.favoravlePrice = favoravlePrice;
	}
	public Integer getGroupNum() {
		return groupNum;
	}
	public void setGroupNum(Integer groupNum) {
		this.groupNum = groupNum;
	}
	public Integer getInventoryCelling() {
		return inventoryCelling;
	}
	public void setInventoryCelling(Integer inventoryCelling) {
		this.inventoryCelling = inventoryCelling;
	}
	public String getDistributionRatio() {
		return distributionRatio;
	}
	public void setDistributionRatio(String distributionRatio) {
		this.distributionRatio = distributionRatio;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
