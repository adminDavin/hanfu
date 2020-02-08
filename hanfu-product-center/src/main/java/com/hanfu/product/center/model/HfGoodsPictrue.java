package com.hanfu.product.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HfGoodsPictrue implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_goods_pictrue.id
     *
     * @mbg.generated Sat Feb 08 10:29:37 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_goods_pictrue.goods_id
     *
     * @mbg.generated Sat Feb 08 10:29:37 CST 2020
     */
    private Integer goodsId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_goods_pictrue.hf_name
     *
     * @mbg.generated Sat Feb 08 10:29:37 CST 2020
     */
    private String hfName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_goods_pictrue.spec_desc
     *
     * @mbg.generated Sat Feb 08 10:29:37 CST 2020
     */
    private String specDesc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_goods_pictrue.file_id
     *
     * @mbg.generated Sat Feb 08 10:29:37 CST 2020
     */
    private Integer fileId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_goods_pictrue.create_time
     *
     * @mbg.generated Sat Feb 08 10:29:37 CST 2020
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_goods_pictrue.modify_time
     *
     * @mbg.generated Sat Feb 08 10:29:37 CST 2020
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_goods_pictrue.last_modifier
     *
     * @mbg.generated Sat Feb 08 10:29:37 CST 2020
     */
    private String lastModifier;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_goods_pictrue.is_deleted
     *
     * @mbg.generated Sat Feb 08 10:29:37 CST 2020
     */
    private Short isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hf_goods_pictrue
     *
     * @mbg.generated Sat Feb 08 10:29:37 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_goods_pictrue.id
     *
     * @return the value of hf_goods_pictrue.id
     *
     * @mbg.generated Sat Feb 08 10:29:37 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_goods_pictrue.id
     *
     * @param id the value for hf_goods_pictrue.id
     *
     * @mbg.generated Sat Feb 08 10:29:37 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_goods_pictrue.goods_id
     *
     * @return the value of hf_goods_pictrue.goods_id
     *
     * @mbg.generated Sat Feb 08 10:29:37 CST 2020
     */
    public Integer getGoodsId() {
        return goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_goods_pictrue.goods_id
     *
     * @param goodsId the value for hf_goods_pictrue.goods_id
     *
     * @mbg.generated Sat Feb 08 10:29:37 CST 2020
     */
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_goods_pictrue.hf_name
     *
     * @return the value of hf_goods_pictrue.hf_name
     *
     * @mbg.generated Sat Feb 08 10:29:37 CST 2020
     */
    public String getHfName() {
        return hfName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_goods_pictrue.hf_name
     *
     * @param hfName the value for hf_goods_pictrue.hf_name
     *
     * @mbg.generated Sat Feb 08 10:29:37 CST 2020
     */
    public void setHfName(String hfName) {
        this.hfName = hfName == null ? null : hfName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_goods_pictrue.spec_desc
     *
     * @return the value of hf_goods_pictrue.spec_desc
     *
     * @mbg.generated Sat Feb 08 10:29:37 CST 2020
     */
    public String getSpecDesc() {
        return specDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_goods_pictrue.spec_desc
     *
     * @param specDesc the value for hf_goods_pictrue.spec_desc
     *
     * @mbg.generated Sat Feb 08 10:29:37 CST 2020
     */
    public void setSpecDesc(String specDesc) {
        this.specDesc = specDesc == null ? null : specDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_goods_pictrue.file_id
     *
     * @return the value of hf_goods_pictrue.file_id
     *
     * @mbg.generated Sat Feb 08 10:29:37 CST 2020
     */
    public Integer getFileId() {
        return fileId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_goods_pictrue.file_id
     *
     * @param fileId the value for hf_goods_pictrue.file_id
     *
     * @mbg.generated Sat Feb 08 10:29:37 CST 2020
     */
    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_goods_pictrue.create_time
     *
     * @return the value of hf_goods_pictrue.create_time
     *
     * @mbg.generated Sat Feb 08 10:29:37 CST 2020
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_goods_pictrue.create_time
     *
     * @param createTime the value for hf_goods_pictrue.create_time
     *
     * @mbg.generated Sat Feb 08 10:29:37 CST 2020
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_goods_pictrue.modify_time
     *
     * @return the value of hf_goods_pictrue.modify_time
     *
     * @mbg.generated Sat Feb 08 10:29:37 CST 2020
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_goods_pictrue.modify_time
     *
     * @param modifyTime the value for hf_goods_pictrue.modify_time
     *
     * @mbg.generated Sat Feb 08 10:29:37 CST 2020
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_goods_pictrue.last_modifier
     *
     * @return the value of hf_goods_pictrue.last_modifier
     *
     * @mbg.generated Sat Feb 08 10:29:37 CST 2020
     */
    public String getLastModifier() {
        return lastModifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_goods_pictrue.last_modifier
     *
     * @param lastModifier the value for hf_goods_pictrue.last_modifier
     *
     * @mbg.generated Sat Feb 08 10:29:37 CST 2020
     */
    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier == null ? null : lastModifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_goods_pictrue.is_deleted
     *
     * @return the value of hf_goods_pictrue.is_deleted
     *
     * @mbg.generated Sat Feb 08 10:29:37 CST 2020
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_goods_pictrue.is_deleted
     *
     * @param isDeleted the value for hf_goods_pictrue.is_deleted
     *
     * @mbg.generated Sat Feb 08 10:29:37 CST 2020
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_pictrue
     *
     * @mbg.generated Sat Feb 08 10:29:37 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", goodsId=").append(goodsId);
        sb.append(", hfName=").append(hfName);
        sb.append(", specDesc=").append(specDesc);
        sb.append(", fileId=").append(fileId);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", lastModifier=").append(lastModifier);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}