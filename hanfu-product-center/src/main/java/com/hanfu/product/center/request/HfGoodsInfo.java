package com.hanfu.product.center.request;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class HfGoodsInfo extends CommonRequest {
    @ApiModelProperty(required = true, value = "商品id")
    private Integer productId;

    @ApiModelProperty(required = true, value = "物品名称")
    private String hfName;
    @ApiModelProperty(required = true, value = "物品描述")
    private String hfDesc;
    
    @ApiModelProperty(required = true, value = "商家id")
    private Integer bossId;
    @ApiModelProperty(required = false, value = "品牌id")
    private Integer brandId;
    public Integer getProductId() {
        return productId;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public String getHfName() {
        return hfName;
    }
    public void setHfName(String hfName) {
        this.hfName = hfName;
    }
    public String getHfDesc() {
        return hfDesc;
    }
    public void setHfDesc(String hfDesc) {
        this.hfDesc = hfDesc;
    }
    public Integer getBossId() {
        return bossId;
    }
    public void setBossId(Integer bossId) {
        this.bossId = bossId;
    }
    public Integer getBrandId() {
        return brandId;
    }
    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    

}
