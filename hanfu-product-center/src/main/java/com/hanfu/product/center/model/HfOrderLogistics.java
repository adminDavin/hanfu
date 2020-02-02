package com.hanfu.product.center.model;

import java.io.Serializable;
import java.util.Date;

public class HfOrderLogistics implements Serializable {

    private Integer id;


    private Integer ordersId;


    private Integer orderDetailId;


    private Integer userId;

    private Integer userAddressId;


    private Integer googsId;


    private String logisticsOrderName;

    private Integer respId;


    private String logisticsOrdersId;


    private String logisticsCompany;


    private String hfDesc;

    private Date createTime;


    private Date modifyTime;


    private String lastModifier;


    private Short isDeleted;


    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getOrdersId() {
        return ordersId;
    }


    public void setOrdersId(Integer ordersId) {
        this.ordersId = ordersId;
    }


    public Integer getOrderDetailId() {
        return orderDetailId;
    }


    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Integer getUserId() {
        return userId;
    }


    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public Integer getUserAddressId() {
        return userAddressId;
    }


    public void setUserAddressId(Integer userAddressId) {
        this.userAddressId = userAddressId;
    }


    public Integer getGoogsId() {
        return googsId;
    }


    public void setGoogsId(Integer googsId) {
        this.googsId = googsId;
    }


    public String getLogisticsOrderName() {
        return logisticsOrderName;
    }


    public void setLogisticsOrderName(String logisticsOrderName) {
        this.logisticsOrderName = logisticsOrderName == null ? null : logisticsOrderName.trim();
    }

    public Integer getRespId() {
        return respId;
    }


    public void setRespId(Integer respId) {
        this.respId = respId;
    }

    public String getLogisticsOrdersId() {
        return logisticsOrdersId;
    }

    public void setLogisticsOrdersId(String logisticsOrdersId) {
        this.logisticsOrdersId = logisticsOrdersId == null ? null : logisticsOrdersId.trim();
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }


    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany == null ? null : logisticsCompany.trim();
    }

    public String getHfDesc() {
        return hfDesc;
    }


    public void setHfDesc(String hfDesc) {
        this.hfDesc = hfDesc == null ? null : hfDesc.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getLastModifier() {
        return lastModifier;
    }


    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier == null ? null : lastModifier.trim();
    }

    public Short getIsDeleted() {
        return isDeleted;
    }


    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", ordersId=").append(ordersId);
        sb.append(", orderDetailId=").append(orderDetailId);
        sb.append(", userId=").append(userId);
        sb.append(", userAddressId=").append(userAddressId);
        sb.append(", googsId=").append(googsId);
        sb.append(", logisticsOrderName=").append(logisticsOrderName);
        sb.append(", respId=").append(respId);
        sb.append(", logisticsOrdersId=").append(logisticsOrdersId);
        sb.append(", logisticsCompany=").append(logisticsCompany);
        sb.append(", hfDesc=").append(hfDesc);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", lastModifier=").append(lastModifier);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}