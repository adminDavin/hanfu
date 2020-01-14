package com.hanfu.payment.center.manual.model;

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


}
