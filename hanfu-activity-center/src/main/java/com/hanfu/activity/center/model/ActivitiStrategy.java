package com.hanfu.activity.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ActivitiStrategy implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activiti_strategy.id
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activiti_strategy.strategy_name
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    private String strategyName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activiti_strategy.strategy_desc
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    private String strategyDesc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activiti_strategy.strategy_type
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    private String strategyType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activiti_strategy.strategy_status
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    private String strategyStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activiti_strategy.create_time
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    private LocalDateTime createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activiti_strategy.modify_time
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    private LocalDateTime modifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activiti_strategy.is_deleted
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    private Short isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table activiti_strategy
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activiti_strategy.id
     *
     * @return the value of activiti_strategy.id
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activiti_strategy.id
     *
     * @param id the value for activiti_strategy.id
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activiti_strategy.strategy_name
     *
     * @return the value of activiti_strategy.strategy_name
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    public String getStrategyName() {
        return strategyName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activiti_strategy.strategy_name
     *
     * @param strategyName the value for activiti_strategy.strategy_name
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName == null ? null : strategyName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activiti_strategy.strategy_desc
     *
     * @return the value of activiti_strategy.strategy_desc
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    public String getStrategyDesc() {
        return strategyDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activiti_strategy.strategy_desc
     *
     * @param strategyDesc the value for activiti_strategy.strategy_desc
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    public void setStrategyDesc(String strategyDesc) {
        this.strategyDesc = strategyDesc == null ? null : strategyDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activiti_strategy.strategy_type
     *
     * @return the value of activiti_strategy.strategy_type
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    public String getStrategyType() {
        return strategyType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activiti_strategy.strategy_type
     *
     * @param strategyType the value for activiti_strategy.strategy_type
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    public void setStrategyType(String strategyType) {
        this.strategyType = strategyType == null ? null : strategyType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activiti_strategy.strategy_status
     *
     * @return the value of activiti_strategy.strategy_status
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    public String getStrategyStatus() {
        return strategyStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activiti_strategy.strategy_status
     *
     * @param strategyStatus the value for activiti_strategy.strategy_status
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    public void setStrategyStatus(String strategyStatus) {
        this.strategyStatus = strategyStatus == null ? null : strategyStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activiti_strategy.create_time
     *
     * @return the value of activiti_strategy.create_time
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activiti_strategy.create_time
     *
     * @param createTime the value for activiti_strategy.create_time
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activiti_strategy.modify_time
     *
     * @return the value of activiti_strategy.modify_time
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activiti_strategy.modify_time
     *
     * @param modifyTime the value for activiti_strategy.modify_time
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activiti_strategy.is_deleted
     *
     * @return the value of activiti_strategy.is_deleted
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activiti_strategy.is_deleted
     *
     * @param isDeleted the value for activiti_strategy.is_deleted
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_strategy
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", strategyName=").append(strategyName);
        sb.append(", strategyDesc=").append(strategyDesc);
        sb.append(", strategyType=").append(strategyType);
        sb.append(", strategyStatus=").append(strategyStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}