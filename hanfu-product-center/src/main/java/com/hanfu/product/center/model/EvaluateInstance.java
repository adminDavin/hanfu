package com.hanfu.product.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class EvaluateInstance implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column evaluate_instance.id
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column evaluate_instance.parent_evaluate_id
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    private Integer parentEvaluateId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column evaluate_instance.out_evaluate_id
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    private Integer outEvaluateId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column evaluate_instance.in_evaluate_id
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    private Integer inEvaluateId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column evaluate_instance.evaluate_content
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    private String evaluateContent;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column evaluate_instance.comment_count
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    private Integer commentCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column evaluate_instance.praise
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    private Integer praise;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column evaluate_instance.create_time
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column evaluate_instance.modify_time
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column evaluate_instance.is_deleted
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    private Byte isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table evaluate_instance
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column evaluate_instance.id
     *
     * @return the value of evaluate_instance.id
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column evaluate_instance.id
     *
     * @param id the value for evaluate_instance.id
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column evaluate_instance.parent_evaluate_id
     *
     * @return the value of evaluate_instance.parent_evaluate_id
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    public Integer getParentEvaluateId() {
        return parentEvaluateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column evaluate_instance.parent_evaluate_id
     *
     * @param parentEvaluateId the value for evaluate_instance.parent_evaluate_id
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    public void setParentEvaluateId(Integer parentEvaluateId) {
        this.parentEvaluateId = parentEvaluateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column evaluate_instance.out_evaluate_id
     *
     * @return the value of evaluate_instance.out_evaluate_id
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    public Integer getOutEvaluateId() {
        return outEvaluateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column evaluate_instance.out_evaluate_id
     *
     * @param outEvaluateId the value for evaluate_instance.out_evaluate_id
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    public void setOutEvaluateId(Integer outEvaluateId) {
        this.outEvaluateId = outEvaluateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column evaluate_instance.in_evaluate_id
     *
     * @return the value of evaluate_instance.in_evaluate_id
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    public Integer getInEvaluateId() {
        return inEvaluateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column evaluate_instance.in_evaluate_id
     *
     * @param inEvaluateId the value for evaluate_instance.in_evaluate_id
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    public void setInEvaluateId(Integer inEvaluateId) {
        this.inEvaluateId = inEvaluateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column evaluate_instance.evaluate_content
     *
     * @return the value of evaluate_instance.evaluate_content
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    public String getEvaluateContent() {
        return evaluateContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column evaluate_instance.evaluate_content
     *
     * @param evaluateContent the value for evaluate_instance.evaluate_content
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    public void setEvaluateContent(String evaluateContent) {
        this.evaluateContent = evaluateContent == null ? null : evaluateContent.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column evaluate_instance.comment_count
     *
     * @return the value of evaluate_instance.comment_count
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    public Integer getCommentCount() {
        return commentCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column evaluate_instance.comment_count
     *
     * @param commentCount the value for evaluate_instance.comment_count
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column evaluate_instance.praise
     *
     * @return the value of evaluate_instance.praise
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    public Integer getPraise() {
        return praise;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column evaluate_instance.praise
     *
     * @param praise the value for evaluate_instance.praise
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    public void setPraise(Integer praise) {
        this.praise = praise;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column evaluate_instance.create_time
     *
     * @return the value of evaluate_instance.create_time
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column evaluate_instance.create_time
     *
     * @param createTime the value for evaluate_instance.create_time
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column evaluate_instance.modify_time
     *
     * @return the value of evaluate_instance.modify_time
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column evaluate_instance.modify_time
     *
     * @param modifyTime the value for evaluate_instance.modify_time
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column evaluate_instance.is_deleted
     *
     * @return the value of evaluate_instance.is_deleted
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    public Byte getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column evaluate_instance.is_deleted
     *
     * @param isDeleted the value for evaluate_instance.is_deleted
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table evaluate_instance
     *
     * @mbg.generated Wed Apr 29 10:20:12 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", parentEvaluateId=").append(parentEvaluateId);
        sb.append(", outEvaluateId=").append(outEvaluateId);
        sb.append(", inEvaluateId=").append(inEvaluateId);
        sb.append(", evaluateContent=").append(evaluateContent);
        sb.append(", commentCount=").append(commentCount);
        sb.append(", praise=").append(praise);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}