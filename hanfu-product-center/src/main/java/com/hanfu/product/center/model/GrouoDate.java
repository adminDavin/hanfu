package com.hanfu.product.center.model;



import java.io.Serializable;
import java.util.List;


public class GrouoDate implements Serializable {
    private Integer id;

    private Integer bossId;

    private Integer goodsId;

    private Double price;

    private Integer number;

    private Integer Repertory;

    private HfGoods hfGoods;

    private List<FileDesc> fileDesc;

    private  Long startTime;

    private Long stopTime;

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


    public Integer getRepertory() {
        return Repertory;
    }

    public void setRepertory(Integer repertory) {
        Repertory = repertory;
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


    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getStopTime() {
        return stopTime;
    }

    public void setStopTime(Long stopTime) {
        this.stopTime = stopTime;
    }
}
