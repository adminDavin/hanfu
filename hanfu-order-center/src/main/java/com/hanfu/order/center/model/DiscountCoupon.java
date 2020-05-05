package com.hanfu.order.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DiscountCoupon implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column discount_coupon.id
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column discount_coupon.discount_coupon_name
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    private String discountCouponName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column discount_coupon.discount_coupon_type
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    private String discountCouponType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column discount_coupon.discount_coupon_desc
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    private String discountCouponDesc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column discount_coupon.start_time
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    private LocalDateTime startTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column discount_coupon.stop_time
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    private LocalDateTime stopTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column discount_coupon.scope
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    private String scope;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column discount_coupon.superposition
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    private Integer superposition;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column discount_coupon.use_limit
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    private String useLimit;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column discount_coupon.use_state
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    private Integer useState;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column discount_coupon.file_id
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    private Integer fileId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column discount_coupon.boss_id
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    private Integer bossId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column discount_coupon.stone_id
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    private Integer stoneId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column discount_coupon.create_date
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    private LocalDateTime createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column discount_coupon.modify_date
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    private LocalDateTime modifyDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column discount_coupon.id_deleted
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    private Byte idDeleted;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column discount_coupon.creator
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    private Integer creator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table discount_coupon
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_coupon.id
     *
     * @return the value of discount_coupon.id
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_coupon.id
     *
     * @param id the value for discount_coupon.id
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_coupon.discount_coupon_name
     *
     * @return the value of discount_coupon.discount_coupon_name
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public String getDiscountCouponName() {
        return discountCouponName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_coupon.discount_coupon_name
     *
     * @param discountCouponName the value for discount_coupon.discount_coupon_name
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public void setDiscountCouponName(String discountCouponName) {
        this.discountCouponName = discountCouponName == null ? null : discountCouponName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_coupon.discount_coupon_type
     *
     * @return the value of discount_coupon.discount_coupon_type
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public String getDiscountCouponType() {
        return discountCouponType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_coupon.discount_coupon_type
     *
     * @param discountCouponType the value for discount_coupon.discount_coupon_type
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public void setDiscountCouponType(String discountCouponType) {
        this.discountCouponType = discountCouponType == null ? null : discountCouponType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_coupon.discount_coupon_desc
     *
     * @return the value of discount_coupon.discount_coupon_desc
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public String getDiscountCouponDesc() {
        return discountCouponDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_coupon.discount_coupon_desc
     *
     * @param discountCouponDesc the value for discount_coupon.discount_coupon_desc
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public void setDiscountCouponDesc(String discountCouponDesc) {
        this.discountCouponDesc = discountCouponDesc == null ? null : discountCouponDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_coupon.start_time
     *
     * @return the value of discount_coupon.start_time
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_coupon.start_time
     *
     * @param startTime the value for discount_coupon.start_time
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_coupon.stop_time
     *
     * @return the value of discount_coupon.stop_time
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public LocalDateTime getStopTime() {
        return stopTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_coupon.stop_time
     *
     * @param stopTime the value for discount_coupon.stop_time
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public void setStopTime(LocalDateTime stopTime) {
        this.stopTime = stopTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_coupon.scope
     *
     * @return the value of discount_coupon.scope
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public String getScope() {
        return scope;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_coupon.scope
     *
     * @param scope the value for discount_coupon.scope
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public void setScope(String scope) {
        this.scope = scope == null ? null : scope.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_coupon.superposition
     *
     * @return the value of discount_coupon.superposition
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public Integer getSuperposition() {
        return superposition;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_coupon.superposition
     *
     * @param superposition the value for discount_coupon.superposition
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public void setSuperposition(Integer superposition) {
        this.superposition = superposition;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_coupon.use_limit
     *
     * @return the value of discount_coupon.use_limit
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public String getUseLimit() {
        return useLimit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_coupon.use_limit
     *
     * @param useLimit the value for discount_coupon.use_limit
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public void setUseLimit(String useLimit) {
        this.useLimit = useLimit == null ? null : useLimit.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_coupon.use_state
     *
     * @return the value of discount_coupon.use_state
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public Integer getUseState() {
        return useState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_coupon.use_state
     *
     * @param useState the value for discount_coupon.use_state
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public void setUseState(Integer useState) {
        this.useState = useState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_coupon.file_id
     *
     * @return the value of discount_coupon.file_id
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public Integer getFileId() {
        return fileId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_coupon.file_id
     *
     * @param fileId the value for discount_coupon.file_id
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_coupon.boss_id
     *
     * @return the value of discount_coupon.boss_id
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public Integer getBossId() {
        return bossId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_coupon.boss_id
     *
     * @param bossId the value for discount_coupon.boss_id
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public void setBossId(Integer bossId) {
        this.bossId = bossId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_coupon.stone_id
     *
     * @return the value of discount_coupon.stone_id
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public Integer getStoneId() {
        return stoneId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_coupon.stone_id
     *
     * @param stoneId the value for discount_coupon.stone_id
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public void setStoneId(Integer stoneId) {
        this.stoneId = stoneId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_coupon.create_date
     *
     * @return the value of discount_coupon.create_date
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_coupon.create_date
     *
     * @param createDate the value for discount_coupon.create_date
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_coupon.modify_date
     *
     * @return the value of discount_coupon.modify_date
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_coupon.modify_date
     *
     * @param modifyDate the value for discount_coupon.modify_date
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_coupon.id_deleted
     *
     * @return the value of discount_coupon.id_deleted
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public Byte getIdDeleted() {
        return idDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_coupon.id_deleted
     *
     * @param idDeleted the value for discount_coupon.id_deleted
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public void setIdDeleted(Byte idDeleted) {
        this.idDeleted = idDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_coupon.creator
     *
     * @return the value of discount_coupon.creator
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public Integer getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_coupon.creator
     *
     * @param creator the value for discount_coupon.creator
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discount_coupon
     *
     * @mbg.generated Tue May 05 21:05:14 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", discountCouponName=").append(discountCouponName);
        sb.append(", discountCouponType=").append(discountCouponType);
        sb.append(", discountCouponDesc=").append(discountCouponDesc);
        sb.append(", startTime=").append(startTime);
        sb.append(", stopTime=").append(stopTime);
        sb.append(", scope=").append(scope);
        sb.append(", superposition=").append(superposition);
        sb.append(", useLimit=").append(useLimit);
        sb.append(", useState=").append(useState);
        sb.append(", fileId=").append(fileId);
        sb.append(", bossId=").append(bossId);
        sb.append(", stoneId=").append(stoneId);
        sb.append(", createDate=").append(createDate);
        sb.append(", modifyDate=").append(modifyDate);
        sb.append(", idDeleted=").append(idDeleted);
        sb.append(", creator=").append(creator);
        sb.append("]");
        return sb.toString();
    }
}