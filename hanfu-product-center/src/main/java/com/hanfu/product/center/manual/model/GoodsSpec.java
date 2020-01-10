package com.hanfu.product.center.manual.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class GoodsSpec implements Serializable {

    @ApiModelProperty(required = false, value = "商品规格名称")
    private String productSpecName;
    private List<HfGoodsDisplay> HfGoodsDisplayList;

	public List<HfGoodsDisplay> getHfGoodsDisplayList() {
		return HfGoodsDisplayList;
	}

	public void setHfGoodsDisplayList(List<HfGoodsDisplay> hfGoodsDisplayList) {
		HfGoodsDisplayList = hfGoodsDisplayList;
	}

	public String getProductSpecName() {
		return productSpecName;
	}

	public void setProductSpecName(String productSpecName) {
		this.productSpecName = productSpecName;
	}

    
}
