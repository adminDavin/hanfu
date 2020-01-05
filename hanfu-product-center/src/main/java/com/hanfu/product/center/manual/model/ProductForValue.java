package com.hanfu.product.center.manual.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class ProductForValue implements Serializable{
	@ApiModelProperty(required = true, value = "类目id")
	private String categroyName;
	@ApiModelProperty(required = true, value = "品牌名称")
	private String brandName;
}
