package com.hanfu.user.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HfUserAddresse implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user_address.id
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user_address.user_id
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user_address.is_fault_address
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private Integer isFaultAddress;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user_address.hf_province
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private String hfProvince;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user_address.hf_city
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private String hfCity;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user_address.hf_conty
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private String hfConty;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user_address.hf_address_detail
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private String hfAddressDetail;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user_address.contact
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private String contact;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user_address.phone_number
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private String phoneNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user_address.hf_desc
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private String hfDesc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user_address.create_time
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private LocalDateTime createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user_address.modify_time
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private LocalDateTime modifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user_address.last_modifier
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private String lastModifier;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user_address.is_deleted
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private Short isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hf_user_address
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user_address.id
     *
     * @return the value of hf_user_address.id
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user_address.id
     *
     * @param id the value for hf_user_address.id
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user_address.user_id
     *
     * @return the value of hf_user_address.user_id
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user_address.user_id
     *
     * @param userId the value for hf_user_address.user_id
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user_address.is_fault_address
     *
     * @return the value of hf_user_address.is_fault_address
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public Integer getIsFaultAddress() {
        return isFaultAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user_address.is_fault_address
     *
     * @param isFaultAddress the value for hf_user_address.is_fault_address
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setIsFaultAddress(Integer isFaultAddress) {
        this.isFaultAddress = isFaultAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user_address.hf_province
     *
     * @return the value of hf_user_address.hf_province
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public String getHfProvince() {
        return hfProvince;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user_address.hf_province
     *
     * @param hfProvince the value for hf_user_address.hf_province
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setHfProvince(String hfProvince) {
        this.hfProvince = hfProvince;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user_address.hf_city
     *
     * @return the value of hf_user_address.hf_city
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public String getHfCity() {
        return hfCity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user_address.hf_city
     *
     * @param hfCity the value for hf_user_address.hf_city
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setHfCity(String hfCity) {
        this.hfCity = hfCity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user_address.hf_conty
     *
     * @return the value of hf_user_address.hf_conty
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public String getHfConty() {
        return hfConty;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user_address.hf_conty
     *
     * @param hfConty the value for hf_user_address.hf_conty
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setHfConty(String hfConty) {
        this.hfConty = hfConty == null ? null : hfConty.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user_address.hf_address_detail
     *
     * @return the value of hf_user_address.hf_address_detail
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public String getHfAddressDetail() {
        return hfAddressDetail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user_address.hf_address_detail
     *
     * @param hfAddressDetail the value for hf_user_address.hf_address_detail
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setHfAddressDetail(String hfAddressDetail) {
        this.hfAddressDetail = hfAddressDetail == null ? null : hfAddressDetail.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user_address.contact
     *
     * @return the value of hf_user_address.contact
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public String getContact() {
        return contact;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user_address.contact
     *
     * @param contact the value for hf_user_address.contact
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user_address.phone_number
     *
     * @return the value of hf_user_address.phone_number
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user_address.phone_number
     *
     * @param phoneNumber the value for hf_user_address.phone_number
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user_address.hf_desc
     *
     * @return the value of hf_user_address.hf_desc
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public String getHfDesc() {
        return hfDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user_address.hf_desc
     *
     * @param hfDesc the value for hf_user_address.hf_desc
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setHfDesc(String hfDesc) {
        this.hfDesc = hfDesc == null ? null : hfDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user_address.create_time
     *
     * @return the value of hf_user_address.create_time
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user_address.create_time
     *
     * @param createTime the value for hf_user_address.create_time
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user_address.modify_time
     *
     * @return the value of hf_user_address.modify_time
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user_address.modify_time
     *
     * @param modifyTime the value for hf_user_address.modify_time
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user_address.last_modifier
     *
     * @return the value of hf_user_address.last_modifier
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public String getLastModifier() {
        return lastModifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user_address.last_modifier
     *
     * @param lastModifier the value for hf_user_address.last_modifier
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier == null ? null : lastModifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user_address.is_deleted
     *
     * @return the value of hf_user_address.is_deleted
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user_address.is_deleted
     *
     * @param isDeleted the value for hf_user_address.is_deleted
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_user_address
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", isFaultAddress=").append(isFaultAddress);
        sb.append(", hfProvince=").append(hfProvince);
        sb.append(", hfCity=").append(hfCity);
        sb.append(", hfConty=").append(hfConty);
        sb.append(", hfAddressDetail=").append(hfAddressDetail);
        sb.append(", contact=").append(contact);
        sb.append(", phoneNumber=").append(phoneNumber);
        sb.append(", hfDesc=").append(hfDesc);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", lastModifier=").append(lastModifier);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}