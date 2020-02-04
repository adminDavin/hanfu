package com.hanfu.order.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class hfEvaluate implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_evaluate.id
     *
     * @mbg.generated Tue Feb 04 17:39:05 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_evaluate.user_id
     *
     * @mbg.generated Tue Feb 04 17:39:05 CST 2020
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_evaluate.order_id
     *
     * @mbg.generated Tue Feb 04 17:39:05 CST 2020
     */
    private Integer orderId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_evaluate.file_id
     *
     * @mbg.generated Tue Feb 04 17:39:05 CST 2020
     */
    private Integer fileId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_evaluate.create_time
     *
     * @mbg.generated Tue Feb 04 17:39:05 CST 2020
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_evaluate.modify_time
     *
     * @mbg.generated Tue Feb 04 17:39:05 CST 2020
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_evaluate.is_deleted
     *
     * @mbg.generated Tue Feb 04 17:39:05 CST 2020
     */
    
    private String evaluate;
    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column hf_evaluate.evaluate
    *
    * @mbg.generated Tue Feb 04 17:39:05 CST 2020
    */

    private Short isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hf_evaluate
     *
     * @mbg.generated Tue Feb 04 17:39:05 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_evaluate.id
     *
     * @return the value of hf_evaluate.id
     *
     * @mbg.generated Tue Feb 04 17:39:05 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_evaluate.id
     *
     * @param id the value for hf_evaluate.id
     *
     * @mbg.generated Tue Feb 04 17:39:05 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_evaluate.user_id
     *
     * @return the value of hf_evaluate.user_id
     *
     * @mbg.generated Tue Feb 04 17:39:05 CST 2020
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_evaluate.user_id
     *
     * @param userId the value for hf_evaluate.user_id
     *
     * @mbg.generated Tue Feb 04 17:39:05 CST 2020
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_evaluate.order_id
     *
     * @return the value of hf_evaluate.order_id
     *
     * @mbg.generated Tue Feb 04 17:39:05 CST 2020
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_evaluate.order_id
     *
     * @param orderId the value for hf_evaluate.order_id
     *
     * @mbg.generated Tue Feb 04 17:39:05 CST 2020
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_evaluate.file_id
     *
     * @return the value of hf_evaluate.file_id
     *
     * @mbg.generated Tue Feb 04 17:39:05 CST 2020
     */
    public Integer getFileId() {
        return fileId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_evaluate.file_id
     *
     * @param fileId the value for hf_evaluate.file_id
     *
     * @mbg.generated Tue Feb 04 17:39:05 CST 2020
     */
    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_evaluate.create_time
     *
     * @return the value of hf_evaluate.create_time
     *
     * @mbg.generated Tue Feb 04 17:39:05 CST 2020
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_evaluate.create_time
     *
     * @param createTime the value for hf_evaluate.create_time
     *
     * @mbg.generated Tue Feb 04 17:39:05 CST 2020
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_evaluate.modify_time
     *
     * @return the value of hf_evaluate.modify_time
     *
     * @mbg.generated Tue Feb 04 17:39:05 CST 2020
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_evaluate.modify_time
     *
     * @param modifyTime the value for hf_evaluate.modify_time
     *
     * @mbg.generated Tue Feb 04 17:39:05 CST 2020
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_evaluate.is_deleted
     *
     * @return the value of hf_evaluate.is_deleted
     *
     * @mbg.generated Tue Feb 04 17:39:05 CST 2020
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_evaluate.is_deleted
     *
     * @param isDeleted the value for hf_evaluate.is_deleted
     *
     * @mbg.generated Tue Feb 04 17:39:05 CST 2020
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_evaluate
     *
     * @mbg.generated Tue Feb 04 17:39:05 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", orderId=").append(orderId);
        sb.append(", fileId=").append(fileId);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}