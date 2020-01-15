package com.hanfu.product.center.manual.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PriceRanking implements Serializable{
 private Integer seniorityId;
 private Integer categoryId;
public Integer getSeniorityId() {
	return seniorityId;
}
public void setSeniorityId(Integer seniorityId) {
	this.seniorityId = seniorityId;
}
public Integer getCategoryId() {
	return categoryId;
}
public void setCategoryId(Integer categoryId) {
	this.categoryId = categoryId;
}
 
}
