package com.hanfu.activity.center.manual.model;

import java.io.Serializable;

public class Evaluate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8591891341984149827L;
	
	private String evaluateContent;
	private String evaluateType;
	public String getEvaluateContent() {
		return evaluateContent;
	}
	public void setEvaluateContent(String evaluateContent) {
		this.evaluateContent = evaluateContent;
	}
	public String getEvaluateType() {
		return evaluateType;
	}
	public void setEvaluateType(String evaluateType) {
		this.evaluateType = evaluateType;
	}
	
}
