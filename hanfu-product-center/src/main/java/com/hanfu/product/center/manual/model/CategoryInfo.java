package com.hanfu.product.center.manual.model;

import java.io.Serializable;
import java.util.List;

public class CategoryInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8286142101647864914L;

	private String TwoLevelName;
	private Integer TwoLevelId;
	private List<Categories> categories;
	public String getTwoLevelName() {
		return TwoLevelName;
	}
	public void setTwoLevelName(String twoLevelName) {
		TwoLevelName = twoLevelName;
	}
	public Integer getTwoLevelId() {
		return TwoLevelId;
	}
	public void setTwoLevelId(Integer twoLevelId) {
		TwoLevelId = twoLevelId;
	}
	public List<Categories> getCategories() {
		return categories;
	}
	public void setCategories(List<Categories> categories) {
		this.categories = categories;
	}
}
