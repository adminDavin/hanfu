package com.hanfu.order.center.manual.model;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class Evaluate implements Serializable{
	@ApiModelProperty(required = false, value = "id")
	private String evaluate;
	@ApiModelProperty(required = false, value = "id")
	private MultipartFile multipartFile;
	public String getEvaluate() {
		return evaluate;
	}
	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}
	public MultipartFile getMultipartFile() {
		return multipartFile;
	}
	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}


}
