package com.hanfu.order.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class hfOrderAddress implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_address.id
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_address.order_id
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    private Integer orderId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_address.origin_address_id
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    private Integer originAddressId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_address.address
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    private String address;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_address.detail
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    private String detail;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_address.contact
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    private String contact;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_address.phone
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    private String phone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_address.hf_desc
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    private String hfDesc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_address.create_time
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_address.modify_time
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_address.last_modifier
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    private String lastModifier;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_address.id_deleted
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    private Byte idDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hf_order_address
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_address.id
     *
     * @return the value of hf_order_address.id
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_address.id
     *
     * @param id the value for hf_order_address.id
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_address.order_id
     *
     * @return the value of hf_order_address.order_id
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_address.order_id
     *
     * @param orderId the value for hf_order_address.order_id
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_address.origin_address_id
     *
     * @return the value of hf_order_address.origin_address_id
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    public Integer getOriginAddressId() {
        return originAddressId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_address.origin_address_id
     *
     * @param originAddressId the value for hf_order_address.origin_address_id
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    public void setOriginAddressId(Integer originAddressId) {
        this.originAddressId = originAddressId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_address.address
     *
     * @return the value of hf_order_address.address
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_address.address
     *
     * @param address the value for hf_order_address.address
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_address.detail
     *
     * @return the value of hf_order_address.detail
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    public String getDetail() {
        return detail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_address.detail
     *
     * @param detail the value for hf_order_address.detail
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_address.contact
     *
     * @return the value of hf_order_address.contact
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    public String getContact() {
        return contact;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_address.contact
     *
     * @param contact the value for hf_order_address.contact
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_address.phone
     *
     * @return the value of hf_order_address.phone
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_address.phone
     *
     * @param phone the value for hf_order_address.phone
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_address.hf_desc
     *
     * @return the value of hf_order_address.hf_desc
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    public String getHfDesc() {
        return hfDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_address.hf_desc
     *
     * @param hfDesc the value for hf_order_address.hf_desc
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    public void setHfDesc(String hfDesc) {
        this.hfDesc = hfDesc == null ? null : hfDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_address.create_time
     *
     * @return the value of hf_order_address.create_time
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_address.create_time
     *
     * @param createTime the value for hf_order_address.create_time
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_address.modify_time
     *
     * @return the value of hf_order_address.modify_time
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_address.modify_time
     *
     * @param modifyTime the value for hf_order_address.modify_time
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_address.last_modifier
     *
     * @return the value of hf_order_address.last_modifier
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    public String getLastModifier() {
        return lastModifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_address.last_modifier
     *
     * @param lastModifier the value for hf_order_address.last_modifier
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier == null ? null : lastModifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_address.id_deleted
     *
     * @return the value of hf_order_address.id_deleted
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    public Byte getIdDeleted() {
        return idDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_address.id_deleted
     *
     * @param idDeleted the value for hf_order_address.id_deleted
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    public void setIdDeleted(Byte idDeleted) {
        this.idDeleted = idDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_address
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderId=").append(orderId);
        sb.append(", originAddressId=").append(originAddressId);
        sb.append(", address=").append(address);
        sb.append(", detail=").append(detail);
        sb.append(", contact=").append(contact);
        sb.append(", phone=").append(phone);
        sb.append(", hfDesc=").append(hfDesc);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", lastModifier=").append(lastModifier);
        sb.append(", idDeleted=").append(idDeleted);
        sb.append("]");
        return sb.toString();
    }
}