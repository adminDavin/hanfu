package com.hanfu.dichan.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DcEvaluate implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_evaluate.id
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_evaluate.boss_id
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    private Integer bossId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_evaluate.type
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    private String type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_evaluate.type_content
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    private String typeContent;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_evaluate.level_id
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    private Integer levelId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_evaluate.parent_evaluate_id
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    private Integer parentEvaluateId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_evaluate.instance_id
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    private Integer instanceId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_evaluate.user_id
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_evaluate.star
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    private Integer star;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_evaluate.evaluate
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    private String evaluate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_evaluate.transmit
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    private Integer transmit;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_evaluate.praise
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    private Integer praise;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_evaluate.comment_count
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    private Integer commentCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_evaluate.create_time
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_evaluate.modify_time
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_evaluate.is_deleted
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    private Byte isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table dc_evaluate
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_evaluate.id
     *
     * @return the value of dc_evaluate.id
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_evaluate.id
     *
     * @param id the value for dc_evaluate.id
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_evaluate.boss_id
     *
     * @return the value of dc_evaluate.boss_id
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public Integer getBossId() {
        return bossId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_evaluate.boss_id
     *
     * @param bossId the value for dc_evaluate.boss_id
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public void setBossId(Integer bossId) {
        this.bossId = bossId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_evaluate.type
     *
     * @return the value of dc_evaluate.type
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_evaluate.type
     *
     * @param type the value for dc_evaluate.type
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_evaluate.type_content
     *
     * @return the value of dc_evaluate.type_content
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public String getTypeContent() {
        return typeContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_evaluate.type_content
     *
     * @param typeContent the value for dc_evaluate.type_content
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public void setTypeContent(String typeContent) {
        this.typeContent = typeContent == null ? null : typeContent.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_evaluate.level_id
     *
     * @return the value of dc_evaluate.level_id
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public Integer getLevelId() {
        return levelId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_evaluate.level_id
     *
     * @param levelId the value for dc_evaluate.level_id
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_evaluate.parent_evaluate_id
     *
     * @return the value of dc_evaluate.parent_evaluate_id
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public Integer getParentEvaluateId() {
        return parentEvaluateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_evaluate.parent_evaluate_id
     *
     * @param parentEvaluateId the value for dc_evaluate.parent_evaluate_id
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public void setParentEvaluateId(Integer parentEvaluateId) {
        this.parentEvaluateId = parentEvaluateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_evaluate.instance_id
     *
     * @return the value of dc_evaluate.instance_id
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public Integer getInstanceId() {
        return instanceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_evaluate.instance_id
     *
     * @param instanceId the value for dc_evaluate.instance_id
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_evaluate.user_id
     *
     * @return the value of dc_evaluate.user_id
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_evaluate.user_id
     *
     * @param userId the value for dc_evaluate.user_id
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_evaluate.star
     *
     * @return the value of dc_evaluate.star
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public Integer getStar() {
        return star;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_evaluate.star
     *
     * @param star the value for dc_evaluate.star
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public void setStar(Integer star) {
        this.star = star;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_evaluate.evaluate
     *
     * @return the value of dc_evaluate.evaluate
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public String getEvaluate() {
        return evaluate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_evaluate.evaluate
     *
     * @param evaluate the value for dc_evaluate.evaluate
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate == null ? null : evaluate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_evaluate.transmit
     *
     * @return the value of dc_evaluate.transmit
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public Integer getTransmit() {
        return transmit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_evaluate.transmit
     *
     * @param transmit the value for dc_evaluate.transmit
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public void setTransmit(Integer transmit) {
        this.transmit = transmit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_evaluate.praise
     *
     * @return the value of dc_evaluate.praise
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public Integer getPraise() {
        return praise;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_evaluate.praise
     *
     * @param praise the value for dc_evaluate.praise
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public void setPraise(Integer praise) {
        this.praise = praise;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_evaluate.comment_count
     *
     * @return the value of dc_evaluate.comment_count
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public Integer getCommentCount() {
        return commentCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_evaluate.comment_count
     *
     * @param commentCount the value for dc_evaluate.comment_count
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_evaluate.create_time
     *
     * @return the value of dc_evaluate.create_time
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_evaluate.create_time
     *
     * @param createTime the value for dc_evaluate.create_time
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_evaluate.modify_time
     *
     * @return the value of dc_evaluate.modify_time
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_evaluate.modify_time
     *
     * @param modifyTime the value for dc_evaluate.modify_time
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_evaluate.is_deleted
     *
     * @return the value of dc_evaluate.is_deleted
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public Byte getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_evaluate.is_deleted
     *
     * @param isDeleted the value for dc_evaluate.is_deleted
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_evaluate
     *
     * @mbg.generated Sat Jul 11 09:20:39 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", bossId=").append(bossId);
        sb.append(", type=").append(type);
        sb.append(", typeContent=").append(typeContent);
        sb.append(", levelId=").append(levelId);
        sb.append(", parentEvaluateId=").append(parentEvaluateId);
        sb.append(", instanceId=").append(instanceId);
        sb.append(", userId=").append(userId);
        sb.append(", star=").append(star);
        sb.append(", evaluate=").append(evaluate);
        sb.append(", transmit=").append(transmit);
        sb.append(", praise=").append(praise);
        sb.append(", commentCount=").append(commentCount);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}