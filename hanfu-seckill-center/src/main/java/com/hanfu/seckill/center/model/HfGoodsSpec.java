package com.hanfu.seckill.center.model;

import java.util.Date;

public class HfGoodsSpec {
    private Integer id;

    private Integer goodsId;

    private String hfSpecId;

    private Integer categorySpecId;

    private String hfValue;

    private Date createTime;

    private Date modifyTime;

    private String lastModifier;

    private Short isDeleted;

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

    public String getHfSpecId() {
        return hfSpecId;
    }

    public void setHfSpecId(String hfSpecId) {
        this.hfSpecId = hfSpecId == null ? null : hfSpecId.trim();
    }

    public Integer getCategorySpecId() {
        return categorySpecId;
    }

    public void setCategorySpecId(Integer categorySpecId) {
        this.categorySpecId = categorySpecId;
    }

    public String getHfValue() {
        return hfValue;
    }

    public void setHfValue(String hfValue) {
        this.hfValue = hfValue == null ? null : hfValue.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getLastModifier() {
        return lastModifier;
    }

    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier == null ? null : lastModifier.trim();
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }
}