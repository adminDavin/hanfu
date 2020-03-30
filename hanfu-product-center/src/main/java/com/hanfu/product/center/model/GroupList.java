package com.hanfu.product.center.model;

import java.util.List;
import java.util.Map;

public class GroupList {
    private Integer groupId;
    private Integer productId;
    private String productName;
    private Integer sellPrice;
    private Integer linePrice;
    private List<Map<String,String>> user;
    private Long time;
    private Integer nowSum;
    private Integer groupSum;
    private String groupUserName;
    private Integer groupFileId;
    private Integer productFileId;

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
        this.productName = productName;
    }

    public Integer getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Integer sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Integer getLinePrice() {
        return linePrice;
    }

    public void setLinePrice(Integer linePrice) {
        this.linePrice = linePrice;
    }

    public List<Map<String, String>> getUser() {
        return user;
    }

    public void setUser(List<Map<String, String>> user) {
        this.user = user;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getNowSum() {
        return nowSum;
    }

    public void setNowSum(Integer nowSum) {
        this.nowSum = nowSum;
    }

    public Integer getGroupSum() {
        return groupSum;
    }

    public void setGroupSum(Integer groupSum) {
        this.groupSum = groupSum;
    }

    public String getGroupUserName() {
        return groupUserName;
    }

    public void setGroupUserName(String groupUserName) {
        this.groupUserName = groupUserName;
    }

    public Integer getGroupFileId() {
        return groupFileId;
    }

    public void setGroupFileId(Integer groupFileId) {
        this.groupFileId = groupFileId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getProductFileId() {
        return productFileId;
    }

    public void setProductFileId(Integer productFileId) {
        this.productFileId = productFileId;
    }
}
