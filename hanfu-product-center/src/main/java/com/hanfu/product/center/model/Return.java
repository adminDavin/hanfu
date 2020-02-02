package com.hanfu.product.center.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Return implements Serializable {
    private Integer id;
    private String name;
    private String picture;
    private Double price;
    private String goodsName;
    private Integer number;
    private Integer numberFew;
    private  Short isDeleted;
    @DateTimeFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss",timezone = "GMT+8")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss",timezone = "GMT+8")
    private Date stopTime;
    private FileDesc fileDesc;

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public FileDesc getFileDesc() {
        return fileDesc;
    }

    public void setFileDesc(FileDesc fileDesc) {
        this.fileDesc = fileDesc;
    }

    private HfUser hfUser;
    private List<HfUser> user;

    public List<HfUser> getUser() {
        return user;
    }

    public void setUser(List<HfUser> user) {
        this.user = user;
    }

    public HfUser getHfUser() {
        return hfUser;
    }

    public void setHfUser(HfUser hfUser) {
        this.hfUser = hfUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getNumberFew() {
        return numberFew;
    }

    public void setNumberFew(Integer numberFew) {
        this.numberFew = numberFew;
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
}
