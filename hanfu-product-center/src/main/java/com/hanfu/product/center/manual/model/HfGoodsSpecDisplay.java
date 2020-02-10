package com.hanfu.product.center.manual.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class HfGoodsSpecDisplay implements Serializable {
    private Integer id;
    private Integer goodsId;
    private Integer hfSpecId;
    private String hfName;
    private String hfValue;
    private String specType;
    private String specUnit;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }
    public Integer getHfSpecId() {
        return hfSpecId;
    }
    public void setHfSpecId(Integer hfSpecId) {
        this.hfSpecId = hfSpecId;
    }
    public String getHfName() {
        return hfName;
    }
    public void setHfName(String hfName) {
        this.hfName = hfName;
    }
    public String getHfValue() {
        return hfValue;
    }
    public void setHfValue(String hfValue) {
        this.hfValue = hfValue;
    }
    public String getSpecType() {
        return specType;
    }
    public void setSpecType(String specType) {
        this.specType = specType;
    }
    public String getSpecUnit() {
        return specUnit;
    }
    public void setSpecUnit(String specUnit) {
        this.specUnit = specUnit;
    }
    
}
