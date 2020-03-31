package com.hanfu.product.center.manual.model;

import java.io.Serializable;

import com.alibaba.fastjson.JSONArray;

public class HomePageOrderType implements Serializable{
	
	private Integer value;
	private String name;
	private String[] data;
	private JSONArray js;
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getData() {
		return data;
	}
	public void setData(String[] data) {
		this.data = data;
	}
	public JSONArray getJs() {
		return js;
	}
	public void setJs(JSONArray js) {
		this.js = js;
	}
	
}
