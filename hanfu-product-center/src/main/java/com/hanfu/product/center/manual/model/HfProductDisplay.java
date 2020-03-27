package com.hanfu.product.center.manual.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class HfProductDisplay implements Serializable {

    @ApiModelProperty(required = false, value = "商品d")
    private Integer id;
    @ApiModelProperty(required = false, value = "商品名称")
    private String productName;
    @ApiModelProperty(required = false, value = "商品描述")
    private String productDesc;
    @ApiModelProperty(required = true, value = "商铺id")
    private Integer stoneId;
    @ApiModelProperty(required = false, value = "商铺id")
    private List<Integer> fileIds;
    @ApiModelProperty(required = true, value = "商铺名称")
    private String stoneName;
    @ApiModelProperty(required = false, value = "类目id")
    private Integer categoryId;
    @ApiModelProperty(required = false, value = "类目名称")
    private String categoryName;
    @ApiModelProperty(required = false, value = "品牌id")
    private Integer brandId;
    @ApiModelProperty(required = false, value = "品牌名称")
    private String branName;
    @ApiModelProperty(required = false, value = "商品图片Id")
    private Integer fileId;
    @ApiModelProperty(required = false, value = "商品图片Id")
    private String priceArea;
    @ApiModelProperty(required = false, value = "默认物品Id")
    private Integer defaultGoodsId;
    @ApiModelProperty(required = false, value = "更新商品的时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime modifyTime;
    @ApiModelProperty(required = false, value = "划线价")
    private Integer linePrice;
    @ApiModelProperty(required = false, value = "活动id")
    private Integer activityId;
    @ApiModelProperty(required = false, value = "折扣比例")
    private Double discountRatio;
    @ApiModelProperty(required = false, value = "团购人数")
    private Integer groupNum;
    @ApiModelProperty(required = false, value = "优惠价格")
    private Double favoravlePrice;
    @ApiModelProperty(required = false, value = "库存上线")
    private Integer inventoryCelling;
    @ApiModelProperty(required = false, value = "分销比例")
    private String distributionRatio;
    private Short isDeleted;
    private String lastModifier;
    private LocalDateTime createTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public Integer getStoneId() {
		return stoneId;
	}
	public void setStoneId(Integer stoneId) {
		this.stoneId = stoneId;
	}
	public List<Integer> getFileIds() {
		return fileIds;
	}
	public void setFileIds(List<Integer> fileIds) {
		this.fileIds = fileIds;
	}
	public String getStoneName() {
		return stoneName;
	}
	public void setStoneName(String stoneName) {
		this.stoneName = stoneName;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public String getBranName() {
		return branName;
	}
	public void setBranName(String branName) {
		this.branName = branName;
	}
	public Integer getFileId() {
		return fileId;
	}
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	public String getPriceArea() {
		return priceArea;
	}
	public void setPriceArea(String priceArea) {
		this.priceArea = priceArea;
	}
	public Integer getDefaultGoodsId() {
		return defaultGoodsId;
	}
	public void setDefaultGoodsId(Integer defaultGoodsId) {
		this.defaultGoodsId = defaultGoodsId;
	}
	public LocalDateTime getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(LocalDateTime modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Integer getLinePrice() {
		return linePrice;
	}
	public void setLinePrice(Integer linePrice) {
		this.linePrice = linePrice;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public Double getDiscountRatio() {
		return discountRatio;
	}
	public void setDiscountRatio(Double discountRatio) {
		this.discountRatio = discountRatio;
	}
	public Integer getGroupNum() {
		return groupNum;
	}
	public void setGroupNum(Integer groupNum) {
		this.groupNum = groupNum;
	}
	public Double getFavoravlePrice() {
		return favoravlePrice;
	}
	public void setFavoravlePrice(Double favoravlePrice) {
		this.favoravlePrice = favoravlePrice;
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
	public Short getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Short isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getLastModifier() {
		return lastModifier;
	}
	public void setLastModifier(String lastModifier) {
		this.lastModifier = lastModifier;
	}
	public LocalDateTime getCreateTime() {
		return createTime;
	}
	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "HfProductDisplay [id=" + id + ", productName=" + productName + ", productDesc=" + productDesc
				+ ", stoneId=" + stoneId + ", fileIds=" + fileIds + ", stoneName=" + stoneName + ", categoryId="
				+ categoryId + ", categoryName=" + categoryName + ", brandId=" + brandId + ", branName=" + branName
				+ ", fileId=" + fileId + ", priceArea=" + priceArea + ", defaultGoodsId=" + defaultGoodsId
				+ ", modifyTime=" + modifyTime + ", linePrice=" + linePrice + ", activityId=" + activityId
				+ ", discountRatio=" + discountRatio + ", groupNum=" + groupNum + ", favoravlePrice=" + favoravlePrice
				+ ", inventoryCelling=" + inventoryCelling + ", distributionRatio=" + distributionRatio + ", isDeleted="
				+ isDeleted + ", lastModifier=" + lastModifier + ", createTime=" + createTime + "]";
	}

}
