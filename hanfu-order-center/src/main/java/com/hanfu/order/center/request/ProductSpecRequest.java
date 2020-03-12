package com.hanfu.order.center.request;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class ProductSpecRequest extends CommonRequest {
    @ApiModelProperty(required = true, value = "规格名称")
    private String hfName;
    @ApiModelProperty(required = true, value = "商品id")
    private Integer productId;
    @ApiModelProperty(required = true, value = "规格类型")
    private String specType;
    @ApiModelProperty(required = true, value = "规格单元")
    private String specUnit;
    @ApiModelProperty(required = true, value = "规格规格默认值")
    private String specValue;

    public String getHfName() {
        return hfName;
    }

    public void setHfName(String hfName) {
        this.hfName = hfName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

    public String getSpecValue() {
        return specValue;
    }

    public void setSpecValue(String specValue) {
        this.specValue = specValue;
    }


}
