package com.hanfu.product.center.manual.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CategoryInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8286142101647864914L;

	private String TwoLevelName;
	private Integer TwoLevelId;
	private Integer id;
	private String hfName;
	private Integer level;
	private Integer fileId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime date;
	private List<Categories> categories;
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
	public List<Categories> getCategories() {
		return categories;
	}
	public void setCategories(List<Categories> categories) {
		this.categories = categories;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getHfName() {
		return hfName;
	}
	public void setHfName(String hfName) {
		this.hfName = hfName;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public Integer getFileId() {
		return fileId;
	}
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
}
