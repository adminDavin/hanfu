package com.hanfu.product.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HfPrice implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_price.id
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_price.googs_id
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    private Integer googsId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_price.price_mode_id
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    private Integer priceModeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_price.sell_price
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    private Integer sellPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_price.is_use_price_mode
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    private Short isUsePriceMode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_price.hf_desc
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    private String hfDesc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_price.create_time
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    private LocalDateTime createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_price.modify_time
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    private LocalDateTime modifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_price.last_modifier
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    private String lastModifier;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_price.is_deleted
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    private Short isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hf_price
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_price.id
     *
     * @return the value of hf_price.id
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_price.id
     *
     * @param id the value for hf_price.id
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_price.googs_id
     *
     * @return the value of hf_price.googs_id
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public Integer getGoogsId() {
        return googsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_price.googs_id
     *
     * @param googsId the value for hf_price.googs_id
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public void setGoogsId(Integer googsId) {
        this.googsId = googsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_price.price_mode_id
     *
     * @return the value of hf_price.price_mode_id
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public Integer getPriceModeId() {
        return priceModeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_price.price_mode_id
     *
     * @param priceModeId the value for hf_price.price_mode_id
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public void setPriceModeId(Integer priceModeId) {
        this.priceModeId = priceModeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_price.sell_price
     *
     * @return the value of hf_price.sell_price
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public Integer getSellPrice() {
        return sellPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_price.sell_price
     *
     * @param sellPrice the value for hf_price.sell_price
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public void setSellPrice(Integer sellPrice) {
        this.sellPrice = sellPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_price.is_use_price_mode
     *
     * @return the value of hf_price.is_use_price_mode
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public Short getIsUsePriceMode() {
        return isUsePriceMode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_price.is_use_price_mode
     *
     * @param isUsePriceMode the value for hf_price.is_use_price_mode
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public void setIsUsePriceMode(Short isUsePriceMode) {
        this.isUsePriceMode = isUsePriceMode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_price.hf_desc
     *
     * @return the value of hf_price.hf_desc
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public String getHfDesc() {
        return hfDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_price.hf_desc
     *
     * @param hfDesc the value for hf_price.hf_desc
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public void setHfDesc(String hfDesc) {
        this.hfDesc = hfDesc == null ? null : hfDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_price.create_time
     *
     * @return the value of hf_price.create_time
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_price.create_time
     *
     * @param createTime the value for hf_price.create_time
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_price.modify_time
     *
     * @return the value of hf_price.modify_time
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_price.modify_time
     *
     * @param modifyTime the value for hf_price.modify_time
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_price.last_modifier
     *
     * @return the value of hf_price.last_modifier
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public String getLastModifier() {
        return lastModifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_price.last_modifier
     *
     * @param lastModifier the value for hf_price.last_modifier
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier == null ? null : lastModifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_price.is_deleted
     *
     * @return the value of hf_price.is_deleted
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_price.is_deleted
     *
     * @param isDeleted the value for hf_price.is_deleted
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_price
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
        sb.append(", googsId=").append(googsId);
        sb.append(", priceModeId=").append(priceModeId);
        sb.append(", sellPrice=").append(sellPrice);
        sb.append(", isUsePriceMode=").append(isUsePriceMode);
        sb.append(", hfDesc=").append(hfDesc);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", lastModifier=").append(lastModifier);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}