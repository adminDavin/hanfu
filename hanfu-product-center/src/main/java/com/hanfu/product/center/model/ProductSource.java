package com.hanfu.product.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ProductSource implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_source.id
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_source.name
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    private Integer name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_source.factory_id
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    private Integer factoryId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_source.create_time
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_source.is_deleted
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    private Short isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table product_source
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_source.id
     *
     * @return the value of product_source.id
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_source.id
     *
     * @param id the value for product_source.id
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_source.name
     *
     * @return the value of product_source.name
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    public Integer getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_source.name
     *
     * @param name the value for product_source.name
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    public void setName(Integer name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_source.factory_id
     *
     * @return the value of product_source.factory_id
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    public Integer getFactoryId() {
        return factoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_source.factory_id
     *
     * @param factoryId the value for product_source.factory_id
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    public void setFactoryId(Integer factoryId) {
        this.factoryId = factoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_source.create_time
     *
     * @return the value of product_source.create_time
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_source.create_time
     *
     * @param createTime the value for product_source.create_time
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_source.is_deleted
     *
     * @return the value of product_source.is_deleted
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_source.is_deleted
     *
     * @param isDeleted the value for product_source.is_deleted
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_source
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", factoryId=").append(factoryId);
        sb.append(", createTime=").append(createTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}