package com.hanfu.product.center.manual.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class HfGoodsDisplay implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5934450949730812779L;
	
	@ApiModelProperty(required = true, value = "物品id")
	private Integer id;
	@ApiModelProperty(required = false, value = "物品名称")
	private String goodName;
	@ApiModelProperty(required = false, value = "商品类目名称")
	private String productCategoryName;
	@ApiModelProperty(required = false, value = "物品描述")
	private String goodsDesc;
	@ApiModelProperty(required = false, value = "类目id")
	private Integer categoryId;
	@ApiModelProperty(required = false, value = "数量")
	private Integer quantity;
	@ApiModelProperty(required = false, value = "销售价格")
	private Integer sellPrice;
	@ApiModelProperty(required = false, value = "规格值")
	private String specValue;
	@ApiModelProperty(required = false, value = "商品规格id")
	private Integer productSpecId;
	@ApiModelProperty(required = false, value = "价格id")
	private Integer priceId;
	@ApiModelProperty(required = false, value = "库存id")
	private Integer respId;
	@ApiModelProperty(required = false, value = "商品id")
	private Integer productId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public String getProductCategoryName() {
		return productCategoryName;
	}
	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}
	public String getGoodsDesc() {
		return goodsDesc;
	}
	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(Integer sellPrice) {
		this.sellPrice = sellPrice;
	}
	public String getSpecValue() {
		return specValue;
	}
	public void setSpecValue(String specValue) {
		this.specValue = specValue;
	}
	public Integer getProductSpecId() {
		return productSpecId;
	}
	public void setProductSpecId(Integer productSpecId) {
		this.productSpecId = productSpecId;
	}
	public Integer getPriceId() {
		return priceId;
	}
	public void setPriceId(Integer priceId) {
		this.priceId = priceId;
	}
	public Integer getRespId() {
		return respId;
	}
	public void setRespId(Integer respId) {
		this.respId = respId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
	
}
