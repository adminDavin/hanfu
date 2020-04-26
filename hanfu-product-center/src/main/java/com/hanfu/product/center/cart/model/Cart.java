package com.hanfu.product.center.cart.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class Cart implements Serializable {
    private String productsId;
    private String productId;
    private Integer productPrice;
    private Integer productNum;
    /*是否勾选*/
    private String check;
    private String productName;
    /**
     * 状态, 0正常1下架.
     */
    private Short productStatus;

    private Integer stoneId;
    private String stoneName;

    /* 商品小图*/
    private Integer productIcon;
    private Integer instanceId;

    private List<Map<String,String>> goodsSpec;


    public String getCheck() {
        return check;
    }

    public void setCheck(String checkAll) {
        this.check = checkAll;
    }


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Short getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Short productStatus) {
        this.productStatus = productStatus;
    }

    public Integer getProductIcon() {
        return productIcon;
    }

    public void setProductIcon(Integer productIcon) {
        this.productIcon = productIcon;
    }

    public String getProductsId() {
        return productsId;
    }

    public void setProductsId(String productsId) {
        this.productsId = productsId;
    }

    public List<Map<String, String>> getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(List<Map<String, String>> goodsSpec) {
        this.goodsSpec = goodsSpec;
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

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }
}
