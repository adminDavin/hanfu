package com.hanfu.product.center.manual.model;

import java.io.Serializable;
import java.util.Date;

public class WarehouseFindConditional implements Serializable{

	private static final long serialVersionUID = 4784901329959354977L;
	
	private String goodName;
	private Integer warehousrId;
	private Integer type;
	private Date start;
	private Date end;
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
	
}
