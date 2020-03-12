package com.hanfu.payment.center.request;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class ProductInfoRequest extends CommonRequest {
    @ApiModelProperty(required = true, value = "属性名称")
    private String hfName;
    @ApiModelProperty(required = true, value = "商品id")
    private Integer productId;
    @ApiModelProperty(required = true, value = "属性值")
    private String infoValue;
    @ApiModelProperty(name = "username", required = true, value = "商家名称")
    private String lastModifier;
    @ApiModelProperty(name = "hfRemark", required = false, value = "备注")
    private String hfRemark;

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

    public String getInfoValue() {
        return infoValue;
    }

    public void setInfoValue(String infoValue) {
        this.infoValue = infoValue;
    }

    public String getLastModifier() {
        return lastModifier;
    }

    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier;
    }

    public String getHfRemark() {
        return hfRemark;
    }

    public void setHfRemark(String hfRemark) {
        this.hfRemark = hfRemark;
    }


}
