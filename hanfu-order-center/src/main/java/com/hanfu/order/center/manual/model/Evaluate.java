package com.hanfu.order.center.manual.model;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("serial")
public class Evaluate implements Serializable{
private String evaluate;
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
