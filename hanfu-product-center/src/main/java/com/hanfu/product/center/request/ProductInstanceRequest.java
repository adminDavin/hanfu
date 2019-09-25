package com.hanfu.product.center.request;

import io.swagger.annotations.ApiParam;

public class ProductInstanceRequest extends CommonRequest{
	@ApiParam(name = "商品Id", required = true, type = "Integer")
	private Integer productId;
	@ApiParam(name = "商铺Id", required = true, type = "Integer")
	private Integer storeId;
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	
}
