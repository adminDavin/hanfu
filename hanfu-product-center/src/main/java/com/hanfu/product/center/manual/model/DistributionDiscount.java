package com.hanfu.product.center.manual.model;

import java.io.Serializable;

public class DistributionDiscount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2028356644399790405L;

	private String name;
	private String index;
	private String ratio;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getRatio() {
		return ratio;
	}
	public void setRatio(String ratio) {
		this.ratio = ratio;
	}
	public DistributionDiscount(String name, String index, String ratio) {
		super();
		this.name = name;
		this.index = index;
		this.ratio = ratio;
	}
	@Override
	public String toString() {
		return "DistributionDiscount [name=" + name + ", index=" + index + ", ratio=" + ratio + "]";
	}
}
