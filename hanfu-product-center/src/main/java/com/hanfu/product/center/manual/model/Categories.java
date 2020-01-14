package com.hanfu.product.center.manual.model;

import java.io.Serializable;

public class Categories implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1966678079375432118L;

    private Integer id;
    private Integer levelId;
    private String hfName;
    private String ChildCategories;
    private Integer fileId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getHfName() {
        return hfName;
    }

    public void setHfName(String hfName) {
        this.hfName = hfName;
    }

    public String getChildCategories() {
        return ChildCategories;
    }

    public void setChildCategories(String childCategories) {
        ChildCategories = childCategories;
    }

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

}
