package com.hanfu.product.center.model;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

public class ProductSpec implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_spec.id
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    @KeySql(useGeneratedKeys = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_spec.hf_name
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    private String hfName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_spec.category_spec_id
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    private Integer categorySpecId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_spec.product_id
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    private Integer productId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_spec.spec_type
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    private String specType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_spec.spec_unit
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    private String specUnit;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_spec.spec_value
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    private String specValue;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_spec.create_time
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_spec.modify_time
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_spec.is_deleted
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    private Short isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table product_spec
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_spec.id
     *
     * @return the value of product_spec.id
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_spec.id
     *
     * @param id the value for product_spec.id
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_spec.hf_name
     *
     * @return the value of product_spec.hf_name
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    public String getHfName() {
        return hfName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_spec.hf_name
     *
     * @param hfName the value for product_spec.hf_name
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    public void setHfName(String hfName) {
        this.hfName = hfName == null ? null : hfName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_spec.category_spec_id
     *
     * @return the value of product_spec.category_spec_id
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    public Integer getCategorySpecId() {
        return categorySpecId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_spec.category_spec_id
     *
     * @param categorySpecId the value for product_spec.category_spec_id
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    public void setCategorySpecId(Integer categorySpecId) {
        this.categorySpecId = categorySpecId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_spec.product_id
     *
     * @return the value of product_spec.product_id
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_spec.product_id
     *
     * @param productId the value for product_spec.product_id
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_spec.spec_type
     *
     * @return the value of product_spec.spec_type
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    public String getSpecType() {
        return specType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_spec.spec_type
     *
     * @param specType the value for product_spec.spec_type
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    public void setSpecType(String specType) {
        this.specType = specType == null ? null : specType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_spec.spec_unit
     *
     * @return the value of product_spec.spec_unit
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    public String getSpecUnit() {
        return specUnit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_spec.spec_unit
     *
     * @param specUnit the value for product_spec.spec_unit
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    public void setSpecUnit(String specUnit) {
        this.specUnit = specUnit == null ? null : specUnit.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_spec.spec_value
     *
     * @return the value of product_spec.spec_value
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    public String getSpecValue() {
        return specValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_spec.spec_value
     *
     * @param specValue the value for product_spec.spec_value
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    public void setSpecValue(String specValue) {
        this.specValue = specValue == null ? null : specValue.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_spec.create_time
     *
     * @return the value of product_spec.create_time
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_spec.create_time
     *
     * @param createTime the value for product_spec.create_time
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_spec.modify_time
     *
     * @return the value of product_spec.modify_time
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_spec.modify_time
     *
     * @param modifyTime the value for product_spec.modify_time
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_spec.is_deleted
     *
     * @return the value of product_spec.is_deleted
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_spec.is_deleted
     *
     * @param isDeleted the value for product_spec.is_deleted
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_spec
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", hfName=").append(hfName);
        sb.append(", categorySpecId=").append(categorySpecId);
        sb.append(", productId=").append(productId);
        sb.append(", specType=").append(specType);
        sb.append(", specUnit=").append(specUnit);
        sb.append(", specValue=").append(specValue);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}