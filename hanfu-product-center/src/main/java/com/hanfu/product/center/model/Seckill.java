package com.hanfu.product.center.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class Seckill implements Serializable{
    private Integer id;
    private Integer bossId;
    private Integer goodsId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date startTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date stopTime;
    private Integer categoryId;
    private Double   price;
    private Integer repertory;
    private Short isDeleted;
    private HfGoods hfGoods;
    private List<FileDesc> fileDesc;
    private List<HfGoodsSpec> hfGoodsSpec;
    private List<Product> product;

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public List<HfGoodsSpec> getHfGoodsSpec() {
        return hfGoodsSpec;
    }

    public void setHfGoodsSpec(List<HfGoodsSpec> hfGoodsSpec) {
        this.hfGoodsSpec = hfGoodsSpec;
    }

    public List<FileDesc> getFileDesc() {
        return fileDesc;
    }

    public void setFileDesc(List<FileDesc> fileDesc) {
        this.fileDesc = fileDesc;
    }

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public HfGoods getHfGoods() {
        return hfGoods;
    }

    public void setHfGoods(HfGoods hfGoods) {
        this.hfGoods = hfGoods;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBossId() {
        return bossId;
    }

    public void setBossId(Integer bossId) {
        this.bossId = bossId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getRepertory() {
        return repertory;
    }

    public void setRepertory(Integer repertory) {
        this.repertory = repertory;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }
     
}
