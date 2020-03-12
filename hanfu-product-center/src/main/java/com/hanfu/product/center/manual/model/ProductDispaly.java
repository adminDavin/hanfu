package com.hanfu.product.center.manual.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;

public class ProductDispaly implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3834215356820052568L;

    @ApiModelProperty(required = true, value = "商家id")
    private Integer bossId;
    @ApiModelProperty(required = false, value = "物品名字")
    private String productName;
    @ApiModelProperty(required = false, value = "物品描述")
    private String productDesc;
    @ApiModelProperty(required = false, value = "类目名称")
    private String productCategoryName;
    @ApiModelProperty(required = false, value = "物品id")
    private Integer id;
    @ApiModelProperty(required = false, value = "类目id")
    private Integer categoryId;
    @ApiModelProperty(required = false, value = "品牌id")
    private Integer brandId;
    @ApiModelProperty(required = false, value = "店家名称，登录修改的用户名称")
    private String lastModifier;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    private short isDeleted;

    public Integer getBossId() {
        return bossId;
    }

    public void setBossId(Integer bossId) {
        this.bossId = bossId;
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

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getLastModifier() {
        return lastModifier;
    }

    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier;
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

    public short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(short isDeleted) {
        this.isDeleted = isDeleted;
    }
}
