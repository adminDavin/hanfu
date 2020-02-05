package com.hanfu.order.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HfOrderLogistics implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_logistics.id
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_logistics.orders_id
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    private Integer ordersId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_logistics.order_detail_id
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    private Integer orderDetailId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_logistics.user_id
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_logistics.user_address_id
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    private Integer userAddressId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_logistics.googs_id
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    private Integer googsId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_logistics.logistics_order_name
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    private String logisticsOrderName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_logistics.resp_id
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    private Integer respId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_logistics.logistics_orders_id
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    private String logisticsOrdersId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_logistics.logistics_company
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    private String logisticsCompany;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_logistics.hf_desc
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    private String hfDesc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_logistics.create_time
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_logistics.modify_time
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_logistics.last_modifier
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    private String lastModifier;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_logistics.is_deleted
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    private Short isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hf_order_logistics
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_logistics.id
     *
     * @return the value of hf_order_logistics.id
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_logistics.id
     *
     * @param id the value for hf_order_logistics.id
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_logistics.orders_id
     *
     * @return the value of hf_order_logistics.orders_id
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    public Integer getOrdersId() {
        return ordersId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_logistics.orders_id
     *
     * @param ordersId the value for hf_order_logistics.orders_id
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    public void setOrdersId(Integer ordersId) {
        this.ordersId = ordersId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_logistics.order_detail_id
     *
     * @return the value of hf_order_logistics.order_detail_id
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    public Integer getOrderDetailId() {
        return orderDetailId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_logistics.order_detail_id
     *
     * @param orderDetailId the value for hf_order_logistics.order_detail_id
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_logistics.user_id
     *
     * @return the value of hf_order_logistics.user_id
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_logistics.user_id
     *
     * @param userId the value for hf_order_logistics.user_id
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_logistics.user_address_id
     *
     * @return the value of hf_order_logistics.user_address_id
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    public Integer getUserAddressId() {
        return userAddressId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_logistics.user_address_id
     *
     * @param userAddressId the value for hf_order_logistics.user_address_id
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    public void setUserAddressId(Integer userAddressId) {
        this.userAddressId = userAddressId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_logistics.googs_id
     *
     * @return the value of hf_order_logistics.googs_id
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    public Integer getGoogsId() {
        return googsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_logistics.googs_id
     *
     * @param googsId the value for hf_order_logistics.googs_id
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    public void setGoogsId(Integer googsId) {
        this.googsId = googsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_logistics.logistics_order_name
     *
     * @return the value of hf_order_logistics.logistics_order_name
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    public String getLogisticsOrderName() {
        return logisticsOrderName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_logistics.logistics_order_name
     *
     * @param logisticsOrderName the value for hf_order_logistics.logistics_order_name
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    public void setLogisticsOrderName(String logisticsOrderName) {
        this.logisticsOrderName = logisticsOrderName == null ? null : logisticsOrderName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_logistics.resp_id
     *
     * @return the value of hf_order_logistics.resp_id
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    public Integer getRespId() {
        return respId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_logistics.resp_id
     *
     * @param respId the value for hf_order_logistics.resp_id
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    public void setRespId(Integer respId) {
        this.respId = respId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_logistics.logistics_orders_id
     *
     * @return the value of hf_order_logistics.logistics_orders_id
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    public String getLogisticsOrdersId() {
        return logisticsOrdersId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_logistics.logistics_orders_id
     *
     * @param logisticsOrdersId the value for hf_order_logistics.logistics_orders_id
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    public void setLogisticsOrdersId(String logisticsOrdersId) {
        this.logisticsOrdersId = logisticsOrdersId == null ? null : logisticsOrdersId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_logistics.logistics_company
     *
     * @return the value of hf_order_logistics.logistics_company
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_logistics.logistics_company
     *
     * @param logisticsCompany the value for hf_order_logistics.logistics_company
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany == null ? null : logisticsCompany.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_logistics.hf_desc
     *
     * @return the value of hf_order_logistics.hf_desc
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    public String getHfDesc() {
        return hfDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_logistics.hf_desc
     *
     * @param hfDesc the value for hf_order_logistics.hf_desc
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    public void setHfDesc(String hfDesc) {
        this.hfDesc = hfDesc == null ? null : hfDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_logistics.create_time
     *
     * @return the value of hf_order_logistics.create_time
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_logistics.create_time
     *
     * @param createTime the value for hf_order_logistics.create_time
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_logistics.modify_time
     *
     * @return the value of hf_order_logistics.modify_time
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_logistics.modify_time
     *
     * @param modifyTime the value for hf_order_logistics.modify_time
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_logistics.last_modifier
     *
     * @return the value of hf_order_logistics.last_modifier
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    public String getLastModifier() {
        return lastModifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_logistics.last_modifier
     *
     * @param lastModifier the value for hf_order_logistics.last_modifier
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier == null ? null : lastModifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_logistics.is_deleted
     *
     * @return the value of hf_order_logistics.is_deleted
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_logistics.is_deleted
     *
     * @param isDeleted the value for hf_order_logistics.is_deleted
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_logistics
     *
     * @mbg.generated Wed Feb 05 08:11:39 CST 2020
     */
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