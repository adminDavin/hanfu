package com.hanfu.product.center.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class HfProductCoupons implements Serializable {
    private Integer id;

    private String hfName;

    private String hfDesc;

    private String couponsType;

    private Integer productId;

    private String productName;

    private Integer goodsId;

    private String goodsName;

    private Integer groupNumber;

    private Integer purchaseUpperLimit;

    private Integer repertoryLimit;

    private Integer couponsStrategyId;

    private Date effectiveDate;

    private Date expireDate;

    private Date createDate;

    private Date modifyDate;

    private Byte idDeleted;
    private HfGoods hfGoods;
    private List<FileDesc> fileDesc;
    private List<HfGoodsSpec> hfGoodsSpec;
    private List<Product> product;
    private Integer Price;

    public Integer getPrice() {
        return Price;
    }

    public void setPrice(Integer price) {
        Price = price;
    }

    public HfGoods getHfGoods() {
        return hfGoods;
    }

    public void setHfGoods(HfGoods hfGoods) {
        this.hfGoods = hfGoods;
    }

    public List<FileDesc> getFileDesc() {
        return fileDesc;
    }

    public void setFileDesc(List<FileDesc> fileDesc) {
        this.fileDesc = fileDesc;
    }

    public List<HfGoodsSpec> getHfGoodsSpec() {
        return hfGoodsSpec;
    }

    public void setHfGoodsSpec(List<HfGoodsSpec> hfGoodsSpec) {
        this.hfGoodsSpec = hfGoodsSpec;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

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
        this.hfName = hfName == null ? null : hfName.trim();
    }

    public String getHfDesc() {
        return hfDesc;
    }

    public void setHfDesc(String hfDesc) {
        this.hfDesc = hfDesc == null ? null : hfDesc.trim();
    }

    public String getCouponsType() {
        return couponsType;
    }

    public void setCouponsType(String couponsType) {
        this.couponsType = couponsType == null ? null : couponsType.trim();
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public Integer getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(Integer groupNumber) {
        this.groupNumber = groupNumber;
    }

    public Integer getPurchaseUpperLimit() {
        return purchaseUpperLimit;
    }

    public void setPurchaseUpperLimit(Integer purchaseUpperLimit) {
        this.purchaseUpperLimit = purchaseUpperLimit;
    }

    public Integer getRepertoryLimit() {
        return repertoryLimit;
    }

    public void setRepertoryLimit(Integer repertoryLimit) {
        this.repertoryLimit = repertoryLimit;
    }

    public Integer getCouponsStrategyId() {
        return couponsStrategyId;
    }

    public void setCouponsStrategyId(Integer couponsStrategyId) {
        this.couponsStrategyId = couponsStrategyId;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Byte getIdDeleted() {
        return idDeleted;
    }

    public void setIdDeleted(Byte idDeleted) {
        this.idDeleted = idDeleted;
    }

    @Override
    public String toString() {
        return "HfProductCoupons{" +
                "id=" + id +
                ", hfName='" + hfName + '\'' +
                ", hfDesc='" + hfDesc + '\'' +
                ", couponsType='" + couponsType + '\'' +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", groupNumber=" + groupNumber +
                ", purchaseUpperLimit=" + purchaseUpperLimit +
                ", repertoryLimit=" + repertoryLimit +
                ", couponsStrategyId=" + couponsStrategyId +
                ", effectiveDate=" + effectiveDate +
                ", expireDate=" + expireDate +
                ", createDate=" + createDate +
                ", modifyDate=" + modifyDate +
                ", idDeleted=" + idDeleted +
                ", hfGoods=" + hfGoods +
                ", fileDesc=" + fileDesc +
                ", hfGoodsSpec=" + hfGoodsSpec +
                ", product=" + product +
                ", Price=" + Price +
                '}';
    }
}