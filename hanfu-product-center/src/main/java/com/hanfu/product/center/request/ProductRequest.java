package com.hanfu.product.center.request;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class ProductRequest extends CommonRequest {
    @ApiModelProperty(required = false, value = "商品id")
    private Integer id;
    @ApiModelProperty(required = true, value = "商品名称")
    private String hfName;
    @ApiModelProperty(required = true, value = "商品所属的类目id")
    private Integer categoryId;
    @ApiModelProperty(required = true, value = "商品描述")
    private String productDesc;
    @ApiModelProperty(required = true, name = "bossId", value = "商家id")
    private Integer bossId;
    @ApiModelProperty(name = "lastModifier", required = true, value = "商家名称")
    private String lastModifier;

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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }


    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public Integer getBossId() {
        return bossId;
    }

    public void setBossId(Integer bossId) {
        this.bossId = bossId;
    }

    public String getLastModifier() {
        return lastModifier;
    }

    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier;
    }

}
