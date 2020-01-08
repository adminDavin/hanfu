package com.hanfu.product.center.manual.model;

import java.io.Serializable;
import java.util.List;

public class CategoryInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8286142101647864914L;

	private String TwoLevelName;
	private Integer TwoLevelId;
	private List<String> ThreeLevelName;
	private List<Integer> ThreeLevelId;
	private List<Integer> ThreeLevelFileId;
	public String getTwoLevelName() {
		return TwoLevelName;
	}
	public void setTwoLevelName(String twoLevelName) {
		TwoLevelName = twoLevelName;
	}
	public Integer getTwoLevelId() {
		return TwoLevelId;
	}
	public void setTwoLevelId(Integer twoLevelId) {
		TwoLevelId = twoLevelId;
	}
	public List<String> getThreeLevelName() {
		return ThreeLevelName;
	}
	public void setThreeLevelName(List<String> threeLevelName) {
		ThreeLevelName = threeLevelName;
	}
	public List<Integer> getThreeLevelId() {
		return ThreeLevelId;
	}
	public void setThreeLevelId(List<Integer> threeLevelId) {
		ThreeLevelId = threeLevelId;
	}
	public List<Integer> getThreeLevelFileId() {
		return ThreeLevelFileId;
	}
	public void setThreeLevelFileId(List<Integer> threeLevelFileId) {
		ThreeLevelFileId = threeLevelFileId;
	}
}
