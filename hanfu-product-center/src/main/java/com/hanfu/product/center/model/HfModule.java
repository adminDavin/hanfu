package com.hanfu.product.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HfModule implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_module.id
     *
     * @mbg.generated Fri May 15 08:52:16 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_module.hf_model
     *
     * @mbg.generated Fri May 15 08:52:16 CST 2020
     */
    private String hfModel;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_module.model_code
     *
     * @mbg.generated Fri May 15 08:52:16 CST 2020
     */
    private String modelCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_module.model_type
     *
     * @mbg.generated Fri May 15 08:52:16 CST 2020
     */
    private String modelType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_module.create_time
     *
     * @mbg.generated Fri May 15 08:52:16 CST 2020
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_module.modify_time
     *
     * @mbg.generated Fri May 15 08:52:16 CST 2020
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_module.last_modifier
     *
     * @mbg.generated Fri May 15 08:52:16 CST 2020
     */
    private String lastModifier;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_module.is_deleted
     *
     * @mbg.generated Fri May 15 08:52:16 CST 2020
     */
    private Byte isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hf_module
     *
     * @mbg.generated Fri May 15 08:52:16 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_module.id
     *
     * @return the value of hf_module.id
     *
     * @mbg.generated Fri May 15 08:52:16 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_module.id
     *
     * @param id the value for hf_module.id
     *
     * @mbg.generated Fri May 15 08:52:16 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_module.hf_model
     *
     * @return the value of hf_module.hf_model
     *
     * @mbg.generated Fri May 15 08:52:16 CST 2020
     */
    public String getHfModel() {
        return hfModel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_module.hf_model
     *
     * @param hfModel the value for hf_module.hf_model
     *
     * @mbg.generated Fri May 15 08:52:16 CST 2020
     */
    public void setHfModel(String hfModel) {
        this.hfModel = hfModel == null ? null : hfModel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_module.model_code
     *
     * @return the value of hf_module.model_code
     *
     * @mbg.generated Fri May 15 08:52:16 CST 2020
     */
    public String getModelCode() {
        return modelCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_module.model_code
     *
     * @param modelCode the value for hf_module.model_code
     *
     * @mbg.generated Fri May 15 08:52:16 CST 2020
     */
    public void setModelCode(String modelCode) {
        this.modelCode = modelCode == null ? null : modelCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_module.model_type
     *
     * @return the value of hf_module.model_type
     *
     * @mbg.generated Fri May 15 08:52:16 CST 2020
     */
    public String getModelType() {
        return modelType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_module.model_type
     *
     * @param modelType the value for hf_module.model_type
     *
     * @mbg.generated Fri May 15 08:52:16 CST 2020
     */
    public void setModelType(String modelType) {
        this.modelType = modelType == null ? null : modelType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_module.create_time
     *
     * @return the value of hf_module.create_time
     *
     * @mbg.generated Fri May 15 08:52:16 CST 2020
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_module.create_time
     *
     * @param createTime the value for hf_module.create_time
     *
     * @mbg.generated Fri May 15 08:52:16 CST 2020
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_module.modify_time
     *
     * @return the value of hf_module.modify_time
     *
     * @mbg.generated Fri May 15 08:52:16 CST 2020
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_module.modify_time
     *
     * @param modifyTime the value for hf_module.modify_time
     *
     * @mbg.generated Fri May 15 08:52:16 CST 2020
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_module.last_modifier
     *
     * @return the value of hf_module.last_modifier
     *
     * @mbg.generated Fri May 15 08:52:16 CST 2020
     */
    public String getLastModifier() {
        return lastModifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_module.last_modifier
     *
     * @param lastModifier the value for hf_module.last_modifier
     *
     * @mbg.generated Fri May 15 08:52:16 CST 2020
     */
    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier == null ? null : lastModifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_module.is_deleted
     *
     * @return the value of hf_module.is_deleted
     *
     * @mbg.generated Fri May 15 08:52:16 CST 2020
     */
    public Byte getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_module.is_deleted
     *
     * @param isDeleted the value for hf_module.is_deleted
     *
     * @mbg.generated Fri May 15 08:52:16 CST 2020
     */
    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_module
     *
     * @mbg.generated Fri May 15 08:52:16 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", hfModel=").append(hfModel);
        sb.append(", modelCode=").append(modelCode);
        sb.append(", modelType=").append(modelType);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", lastModifier=").append(lastModifier);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}