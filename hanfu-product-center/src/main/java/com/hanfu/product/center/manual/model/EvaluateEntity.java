package com.hanfu.product.center.manual.model;

import java.io.Serializable;
import java.util.List;

public class EvaluateEntity implements Serializable{

	private Evaluate parentEvaluate;
	private List<Evaluate> childEvaluate;
	public Evaluate getParentEvaluate() {
		return parentEvaluate;
	}
	public void setParentEvaluate(Evaluate parentEvaluate) {
		this.parentEvaluate = parentEvaluate;
	}
	public List<Evaluate> getChildEvaluate() {
		return childEvaluate;
	}
	public void setChildEvaluate(List<Evaluate> childEvaluate) {
		this.childEvaluate = childEvaluate;
	}
	
}
