package com.hanfu.product.center.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Group implements Serializable {
    private Integer id;

    private Integer bossId;

    private Integer goodsId;

    private Double price;
    private Integer practicalPrice;
    private Integer number;
    @DateTimeFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss",timezone = "GMT+8")
    private Date stopTime;
    private Short isDeleted;
    private Integer Repertory;
    private HfGoods hfGoods;
    private List<FileDesc> fileDesc;
    private List<Product> product;

    public Integer getPracticalPrice() {
        return practicalPrice;
    }

    public void setPracticalPrice(Integer practicalPrice) {
        this.practicalPrice = practicalPrice;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    private List<HfGoodsSpec> hfGoodsSpec;

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

    public Integer getRepertory() {
        return Repertory;
    }

    public void setRepertory(Integer repertory) {
        Repertory = repertory;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", bossId=" + bossId +
                ", goodsId=" + goodsId +
                ", price=" + price +
                ", number=" + number +
                ", startTime=" + startTime +
                ", stopTime=" + stopTime +
                ", isDeleted=" + isDeleted +
                ", Repertory=" + Repertory +
                ", hfGoods=" + hfGoods +
                ", fileDesc=" + fileDesc +
                ", product=" + product +
                ", hfGoodsSpec=" + hfGoodsSpec +
                '}';
    }
}