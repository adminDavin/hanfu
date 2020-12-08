package com.hanfu.user.center.manual.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * @author shihao
 * @Title: VipOrders
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
public class VipOrders {
    private Integer vipOrderId;
    private Integer userId;
    private Integer bossId;
    private Integer vipId;
    private String orderCode;
    private Integer amount;
    private String payName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    private String vipName;
    private String label;
    private Integer file;

    public Integer getVipOrderId() {
        return vipOrderId;
    }

    public void setVipOrderId(Integer vipOrderId) {
        this.vipOrderId = vipOrderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBossId() {
        return bossId;
    }

    public void setBossId(Integer bossId) {
        this.bossId = bossId;
    }

    public Integer getVipId() {
        return vipId;
    }

    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setFile(Integer file) {
        this.file = file;
    }

}
