package com.hanfu.product.center.manual.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

@SuppressWarnings("serial")
public class HfGoodsDisplayInfo implements Serializable {
    private Integer id;
    private String goodsName;
    private Integer productId;
    private String goodsDesc;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime modifyTime;
    private Integer warehouseId;
    private Integer quantity;
    private Integer respStatus;
    private Integer priceId;
    private Integer priceModeId;
    private List<Integer> fileIds;
    private String sellPrice;
    private short isUsePriceMode;
    private Integer linePrice;
    private Integer stoneId;
    private Integer instanceId;
    private List<HfGoodsSpecDisplay> hfGoodsSpecs;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public List<Integer> getFileIds() {
        return fileIds;
    }
    public void setFileIds(List<Integer> fileIds) {
        this.fileIds = fileIds;
    }
    public String getGoodsName() {
        return goodsName;
    }
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
    public Integer getProductId() {
        return productId;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public String getGoodsDesc() {
        return goodsDesc;
    }
    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }
    public Integer getWarehouseId() {
        return warehouseId;
    }
    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Integer getRespStatus() {
        return respStatus;
    }
    public void setRespStatus(Integer respStatus) {
        this.respStatus = respStatus;
    }
    public Integer getPriceId() {
        return priceId;
    }
    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }
    public Integer getPriceModeId() {
        return priceModeId;
    }
    public void setPriceModeId(Integer priceModeId) {
        this.priceModeId = priceModeId;
    }
    public String getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(String sellPrice) {
		this.sellPrice = sellPrice;
	}
	public short getIsUsePriceMode() {
        return isUsePriceMode;
    }
    public void setIsUsePriceMode(short isUsePriceMode) {
        this.isUsePriceMode = isUsePriceMode;
    }
    public List<HfGoodsSpecDisplay> getHfGoodsSpecs() {
        return hfGoodsSpecs;
    }
    public void setHfGoodsSpecs(List<HfGoodsSpecDisplay> hfGoodsSpecs) {
        this.hfGoodsSpecs = hfGoodsSpecs;
    }

    public Integer getLinePrice() {
        return linePrice;
    }

    public void setLinePrice(Integer linePrice) {
        this.linePrice = linePrice;
    }
	public Integer getStoneId() {
		return stoneId;
	}
	public void setStoneId(Integer stoneId) {
		this.stoneId = stoneId;
	}
	public Integer getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(Integer instanceId) {
		this.instanceId = instanceId;
	}

    @Override
    public String toString() {
        return "HfGoodsDisplayInfo{" +
                "id=" + id +
                ", goodsName='" + goodsName + '\'' +
                ", productId=" + productId +
                ", goodsDesc='" + goodsDesc + '\'' +
                ", modifyTime=" + modifyTime +
                ", warehouseId=" + warehouseId +
                ", quantity=" + quantity +
                ", respStatus=" + respStatus +
                ", priceId=" + priceId +
                ", priceModeId=" + priceModeId +
                ", fileIds=" + fileIds +
                ", sellPrice='" + sellPrice + '\'' +
                ", isUsePriceMode=" + isUsePriceMode +
                ", linePrice=" + linePrice +
                ", stoneId=" + stoneId +
                ", instanceId=" + instanceId +
                ", hfGoodsSpecs=" + hfGoodsSpecs +
                '}';
    }
}
