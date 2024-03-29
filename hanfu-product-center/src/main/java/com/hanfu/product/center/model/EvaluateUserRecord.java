package com.hanfu.product.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class EvaluateUserRecord implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column evaluate_user_record.id
     *
     * @mbg.generated Wed Apr 29 10:45:55 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column evaluate_user_record.type
     *
     * @mbg.generated Wed Apr 29 10:45:55 CST 2020
     */
    private String type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column evaluate_user_record.level_id
     *
     * @mbg.generated Wed Apr 29 10:45:55 CST 2020
     */
    private Integer levelId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column evaluate_user_record.evaluate
     *
     * @mbg.generated Wed Apr 29 10:45:55 CST 2020
     */
    private Integer evaluate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column evaluate_user_record.user_id
     *
     * @mbg.generated Wed Apr 29 10:45:55 CST 2020
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column evaluate_user_record.create_time
     *
     * @mbg.generated Wed Apr 29 10:45:55 CST 2020
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column evaluate_user_record.modify_time
     *
     * @mbg.generated Wed Apr 29 10:45:55 CST 2020
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column evaluate_user_record.is_deleted
     *
     * @mbg.generated Wed Apr 29 10:45:55 CST 2020
     */
    private Byte isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table evaluate_user_record
     *
     * @mbg.generated Wed Apr 29 10:45:55 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column evaluate_user_record.id
     *
     * @return the value of evaluate_user_record.id
     *
     * @mbg.generated Wed Apr 29 10:45:55 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column evaluate_user_record.id
     *
     * @param id the value for evaluate_user_record.id
     *
     * @mbg.generated Wed Apr 29 10:45:55 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column evaluate_user_record.type
     *
     * @return the value of evaluate_user_record.type
     *
     * @mbg.generated Wed Apr 29 10:45:55 CST 2020
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column evaluate_user_record.type
     *
     * @param type the value for evaluate_user_record.type
     *
     * @mbg.generated Wed Apr 29 10:45:55 CST 2020
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column evaluate_user_record.level_id
     *
     * @return the value of evaluate_user_record.level_id
     *
     * @mbg.generated Wed Apr 29 10:45:55 CST 2020
     */
    public Integer getLevelId() {
        return levelId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column evaluate_user_record.level_id
     *
     * @param levelId the value for evaluate_user_record.level_id
     *
     * @mbg.generated Wed Apr 29 10:45:55 CST 2020
     */
    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column evaluate_user_record.evaluate
     *
     * @return the value of evaluate_user_record.evaluate
     *
     * @mbg.generated Wed Apr 29 10:45:55 CST 2020
     */
    public Integer getEvaluate() {
        return evaluate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column evaluate_user_record.evaluate
     *
     * @param evaluate the value for evaluate_user_record.evaluate
     *
     * @mbg.generated Wed Apr 29 10:45:55 CST 2020
     */
    public void setEvaluate(Integer evaluate) {
        this.evaluate = evaluate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column evaluate_user_record.user_id
     *
     * @return the value of evaluate_user_record.user_id
     *
     * @mbg.generated Wed Apr 29 10:45:55 CST 2020
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column evaluate_user_record.user_id
     *
     * @param userId the value for evaluate_user_record.user_id
     *
     * @mbg.generated Wed Apr 29 10:45:55 CST 2020
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column evaluate_user_record.create_time
     *
     * @return the value of evaluate_user_record.create_time
     *
     * @mbg.generated Wed Apr 29 10:45:55 CST 2020
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column evaluate_user_record.create_time
     *
     * @param createTime the value for evaluate_user_record.create_time
     *
     * @mbg.generated Wed Apr 29 10:45:55 CST 2020
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column evaluate_user_record.modify_time
     *
     * @return the value of evaluate_user_record.modify_time
     *
     * @mbg.generated Wed Apr 29 10:45:55 CST 2020
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column evaluate_user_record.modify_time
     *
     * @param modifyTime the value for evaluate_user_record.modify_time
     *
     * @mbg.generated Wed Apr 29 10:45:55 CST 2020
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column evaluate_user_record.is_deleted
     *
     * @return the value of evaluate_user_record.is_deleted
     *
     * @mbg.generated Wed Apr 29 10:45:55 CST 2020
     */
    public Byte getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column evaluate_user_record.is_deleted
     *
     * @param isDeleted the value for evaluate_user_record.is_deleted
     *
     * @mbg.generated Wed Apr 29 10:45:55 CST 2020
     */
    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table evaluate_user_record
     *
     * @mbg.generated Wed Apr 29 10:45:55 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", levelId=").append(levelId);
        sb.append(", evaluate=").append(evaluate);
        sb.append(", userId=").append(userId);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}