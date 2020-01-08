package com.hanfu.product.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HfResp implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_resp.id
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_resp.googs_id
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    private Integer googsId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_resp.warehouse_id
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    private Integer warehouseId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_resp.quantity
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    private String quantity;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_resp.hf_status
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    private Integer hfStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_resp.hf_desc
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    private String hfDesc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_resp.create_time
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_resp.modify_time
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_resp.last_modifier
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    private String lastModifier;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_resp.is_deleted
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    private Short isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hf_resp
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_resp.id
     *
     * @return the value of hf_resp.id
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_resp.id
     *
     * @param id the value for hf_resp.id
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_resp.googs_id
     *
     * @return the value of hf_resp.googs_id
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    public Integer getGoogsId() {
        return googsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_resp.googs_id
     *
     * @param googsId the value for hf_resp.googs_id
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    public void setGoogsId(Integer googsId) {
        this.googsId = googsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_resp.warehouse_id
     *
     * @return the value of hf_resp.warehouse_id
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    public Integer getWarehouseId() {
        return warehouseId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_resp.warehouse_id
     *
     * @param warehouseId the value for hf_resp.warehouse_id
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_resp.quantity
     *
     * @return the value of hf_resp.quantity
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_resp.quantity
     *
     * @param quantity the value for hf_resp.quantity
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity == null ? null : quantity.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_resp.hf_status
     *
     * @return the value of hf_resp.hf_status
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    public Integer getHfStatus() {
        return hfStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_resp.hf_status
     *
     * @param hfStatus the value for hf_resp.hf_status
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    public void setHfStatus(Integer hfStatus) {
        this.hfStatus = hfStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_resp.hf_desc
     *
     * @return the value of hf_resp.hf_desc
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    public String getHfDesc() {
        return hfDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_resp.hf_desc
     *
     * @param hfDesc the value for hf_resp.hf_desc
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    public void setHfDesc(String hfDesc) {
        this.hfDesc = hfDesc == null ? null : hfDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_resp.create_time
     *
     * @return the value of hf_resp.create_time
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_resp.create_time
     *
     * @param createTime the value for hf_resp.create_time
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_resp.modify_time
     *
     * @return the value of hf_resp.modify_time
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_resp.modify_time
     *
     * @param modifyTime the value for hf_resp.modify_time
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_resp.last_modifier
     *
     * @return the value of hf_resp.last_modifier
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    public String getLastModifier() {
        return lastModifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_resp.last_modifier
     *
     * @param lastModifier the value for hf_resp.last_modifier
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier == null ? null : lastModifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_resp.is_deleted
     *
     * @return the value of hf_resp.is_deleted
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_resp.is_deleted
     *
     * @param isDeleted the value for hf_resp.is_deleted
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_resp
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", googsId=").append(googsId);
        sb.append(", warehouseId=").append(warehouseId);
        sb.append(", quantity=").append(quantity);
        sb.append(", hfStatus=").append(hfStatus);
        sb.append(", hfDesc=").append(hfDesc);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", lastModifier=").append(lastModifier);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}