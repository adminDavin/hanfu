package com.hanfu.product.center.request;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class HfGoodsInfo extends CommonRequest {
    @ApiModelProperty(required = true, value = "商品id")
    private Integer productId;
    @ApiModelProperty(required = true, value = "物品名称")
    private String goodName;
    @ApiModelProperty(required = true, value = "物品描述")
    private String goodsDesc;
    @ApiModelProperty(required = true, value = "商铺id")
    private Integer hfStoreId;
    @ApiModelProperty(required = true, value = "商家id")
    private Integer bossId;
    @ApiModelProperty(required = false, value = "品牌id")
    private Integer brandId;
    @ApiModelProperty(name = "username", required = true, value = "商家名称")
    private String username;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodDesc) {
        this.goodsDesc = goodDesc;
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

    public Integer getHfStoreId() {
        return hfStoreId;
    }

    public void setHfStoreId(Integer hfStoreId) {
        this.hfStoreId = hfStoreId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
