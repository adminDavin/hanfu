package com.hanfu.activity.center.manual.model;

import java.io.Serializable;

public class VoteEntity implements Serializable{

	private String voteName;
	private Integer count;
	public String getVoteName() {
		return voteName;
	}
	public void setVoteName(String voteName) {
		this.voteName = voteName;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
}
