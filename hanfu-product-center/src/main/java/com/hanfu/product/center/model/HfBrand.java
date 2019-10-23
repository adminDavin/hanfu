package com.hanfu.product.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HfBrand implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_brand.id
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_brand.hf_name
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    private String hfName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_brand.brand_type
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    private String brandType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_brand.hf_desc
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    private String hfDesc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_brand.create_time
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_brand.modify_time
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_brand.is_deleted
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    private Short isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hf_brand
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_brand.id
     *
     * @return the value of hf_brand.id
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_brand.id
     *
     * @param id the value for hf_brand.id
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_brand.hf_name
     *
     * @return the value of hf_brand.hf_name
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public String getHfName() {
        return hfName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_brand.hf_name
     *
     * @param hfName the value for hf_brand.hf_name
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public void setHfName(String hfName) {
        this.hfName = hfName == null ? null : hfName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_brand.brand_type
     *
     * @return the value of hf_brand.brand_type
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public String getBrandType() {
        return brandType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_brand.brand_type
     *
     * @param brandType the value for hf_brand.brand_type
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public void setBrandType(String brandType) {
        this.brandType = brandType == null ? null : brandType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_brand.hf_desc
     *
     * @return the value of hf_brand.hf_desc
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public String getHfDesc() {
        return hfDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_brand.hf_desc
     *
     * @param hfDesc the value for hf_brand.hf_desc
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public void setHfDesc(String hfDesc) {
        this.hfDesc = hfDesc == null ? null : hfDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_brand.create_time
     *
     * @return the value of hf_brand.create_time
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_brand.create_time
     *
     * @param createTime the value for hf_brand.create_time
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_brand.modify_time
     *
     * @return the value of hf_brand.modify_time
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_brand.modify_time
     *
     * @param modifyTime the value for hf_brand.modify_time
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_brand.is_deleted
     *
     * @return the value of hf_brand.is_deleted
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_brand.is_deleted
     *
     * @param isDeleted the value for hf_brand.is_deleted
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_brand
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", hfName=").append(hfName);
        sb.append(", brandType=").append(brandType);
        sb.append(", hfDesc=").append(hfDesc);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}