package com.hanfu.order.center.manual.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GoodsSpec implements Serializable{
private String specValue;

public String getSpecValue() {
	return specValue;
}

public void setSpecValue(String specValue) {
	this.specValue = specValue;
}

}
