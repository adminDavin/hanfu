package com.hanfu.cancel.model;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

public class HfPrice {
    @KeySql(useGeneratedKeys = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private Integer googsId;
    private Integer priceModeId;
    private Integer sellPrice;
    private Short isUsePriceMode;
    private String hfDesc;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    private String lastModifier;
    private Short isDeleted;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoogsId() {
        return googsId;
    }

    public void setGoogsId(Integer googsId) {
        this.googsId = googsId;
    }

    public Integer getPriceModeId() {
        return priceModeId;
    }

    public void setPriceModeId(Integer priceModeId) {
        this.priceModeId = priceModeId;
    }

    public Integer getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Integer sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Short getIsUsePriceMode() {
        return isUsePriceMode;
    }

    public void setIsUsePriceMode(Short isUsePriceMode) {
        this.isUsePriceMode = isUsePriceMode;
    }

    public String getHfDesc() {
        return hfDesc;
    }

    public void setHfDesc(String hfDesc) {
        this.hfDesc = hfDesc;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getLastModifier() {
        return lastModifier;
    }

    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }
}
