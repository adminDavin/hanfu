package com.hanfu.product.center.request;

import io.swagger.annotations.ApiParam;

public class HfGoodsRequest {
	@ApiParam(name = "商品实体Id", required = true, type = "Integer")
	private Integer productEntityId;
	@ApiParam(name = "商品库存", required = true, type = "Integer")
	private Integer productStock;
	@ApiParam(name = "备注", required = true, type = "String")
	private String remark;
	@ApiParam(name = "价格Id", required = true,type = "Integer")
	private Integer priceId;
	public Integer getProductEntityId() {
		return productEntityId;
	}
	public void setProductEntityId(Integer productEntityId) {
		this.productEntityId = productEntityId;
	}
	public Integer getProductStock() {
		return productStock;
	}
	public void setProductStock(Integer productStock) {
		this.productStock = productStock;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getPriceId() {
		return priceId;
	}
	public void setPriceId(Integer priceId) {
		this.priceId = priceId;
	}
	
}
