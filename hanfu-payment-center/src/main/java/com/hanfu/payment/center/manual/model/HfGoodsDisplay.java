package com.hanfu.payment.center.manual.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class HfGoodsDisplay implements Serializable {
    private Integer id;
    private String hfName;
    private Integer fileId;
    private Integer stoneId;
    private String stoneName;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getFileId() {
        return fileId;
    }
    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }
    public String getHfName() {
        return hfName;
    }
    public void setHfName(String hfName) {
        this.hfName = hfName;
    }
    public Integer getStoneId() {
        return stoneId;
    }
    public void setStoneId(Integer stoneId) {
        this.stoneId = stoneId;
    }
    public String getStoneName() {
        return stoneName;
    }
    public void setStoneName(String stoneName) {
        this.stoneName = stoneName;
    }
    
    
}