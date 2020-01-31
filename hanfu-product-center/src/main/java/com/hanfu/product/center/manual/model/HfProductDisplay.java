package com.hanfu.product.center.manual.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class HfProductDisplay implements Serializable {

    @ApiModelProperty(required = false, value = "商品d")
    private Integer id;
    @ApiModelProperty(required = false, value = "商品名称")
    private String productName;
    @ApiModelProperty(required = false, value = "商品描述")
    private String productDesc;
    @ApiModelProperty(required = true, value = "商铺id")
    private Integer stoneId;
    @ApiModelProperty(required = false, value = "商铺id")
    private List<Integer> fileIds;
    @ApiModelProperty(required = true, value = "商铺名称")
    private String stoneName;
    @ApiModelProperty(required = false, value = "类目id")
    private Integer categoryId;
    @ApiModelProperty(required = false, value = "类目名称")
    private String categoryName;
    @ApiModelProperty(required = false, value = "品牌id")
    private Integer brandId;
    @ApiModelProperty(required = false, value = "品牌名称")
    private String branName;
    @ApiModelProperty(required = false, value = "商品图片Id")
    private Integer fileId;
    @ApiModelProperty(required = false, value = "商品图片Id")
    private String priceArea;
    @ApiModelProperty(required = false, value = "默认物品Id")
    private Integer defaultGoodsId;
    @ApiModelProperty(required = false, value = "更新商品的时间")
    private LocalDateTime modifyTime;

    public List<Integer> getFileIds() {
        return fileIds;
    }

    public void setFileIds(List<Integer> fileIds) {
        this.fileIds = fileIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getPriceArea() {
        return priceArea;
    }

    public void setPriceArea(String priceArea) {
        this.priceArea = priceArea;
    }


    public String getBranName() {
        return branName;
    }

    public void setBranName(String branName) {
        this.branName = branName;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public Integer getDefaultGoodsId() {
        return defaultGoodsId;
    }

    public void setDefaultGoodsId(Integer defaultGoodsId) {
        this.defaultGoodsId = defaultGoodsId;
    }    

}
