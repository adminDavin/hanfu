package com.hanfu.order.center.manual.model;

import java.io.Serializable;


@SuppressWarnings("serial")
public class Evaluate implements Serializable{
private String evaluate;
private Integer star;
public String getEvaluate() {
	return evaluate;
}
public void setEvaluate(String evaluate) {
	this.evaluate = evaluate;
}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}
}
