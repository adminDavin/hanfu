package com.hanfu.product.center.request;

import io.swagger.annotations.ApiParam;

public class CategoryRequest extends CommonRequest {
	
	@ApiParam(name = "类目名称", required = true, type = "String")
	private String category;
	@ApiParam(name = "商品类目上级ID", required = true, type="Integer")
	private Integer parentCategoryId;
	@ApiParam(name = "类目级别", required = true, type="Integer", allowableValues = "0,1,2,3")
	private Integer levelId;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Integer getParentCategoryId() {
		return parentCategoryId;
	}
	public void setParentCategoryId(Integer parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}
	public Integer getLevelId() {
		return levelId;
	}
	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}
	
}
