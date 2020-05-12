package com.hanfu.product.center.manual.model;

import java.io.Serializable;
import java.util.Date;

public class WarehouseFindConditional implements Serializable{

	private static final long serialVersionUID = 4784901329959354977L;
	
	private String goodName;
	private Integer warehousrId;
	private Integer type;
	private String data_type;
	private Date start;
	private Date end;
	private String categoryName;
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public Integer getWarehousrId() {
		return warehousrId;
	}
	public void setWarehousrId(Integer warehousrId) {
		this.warehousrId = warehousrId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
