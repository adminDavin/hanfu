package com.hanfu.product.center.request;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class GoodsSpecRequest extends CommonRequest {
    @ApiModelProperty(required = true, value = "物品id")
    private Integer goodsId;
    @ApiModelProperty(required = false, value = "类目规格id")
    private Integer catrgorySpecId;
    @ApiModelProperty(required = false, value = "商品规格id")
    private Integer productSpecId;
    @ApiModelProperty(required = true, value = "规格值")
    private String specValue;
    @ApiModelProperty(required = false, value = "店家名称, 登录修改的用户名称")
    private String username;
    @ApiModelProperty(required = false, value = "商品描述")
    private String prictureDesc;
    
    
    public String getPrictureDesc() {
		return prictureDesc;
	}

	public void setPrictureDesc(String prictureDesc) {
		this.prictureDesc = prictureDesc;
	}

	public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getCatrgorySpecId() {
        return catrgorySpecId;
    }

    public void setCatrgorySpecId(Integer catrgorySpecId) {
        this.catrgorySpecId = catrgorySpecId;
    }

    public String getSpecValue() {
        return specValue;
    }

    public void setSpecValue(String specValue) {
        this.specValue = specValue;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getProductSpecId() {
        return productSpecId;
    }

    public void setProductSpecId(Integer productSpecId) {
        this.productSpecId = productSpecId;
    }

}
