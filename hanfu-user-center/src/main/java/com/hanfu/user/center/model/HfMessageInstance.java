package com.hanfu.user.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HfMessageInstance implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_message_instance.id
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_message_instance.status
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    private Integer status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_message_instance.subject
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    private String subject;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_message_instance.template_type_id
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    private Integer templateTypeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_message_instance.template_id
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    private String templateId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_message_instance.template_param
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    private String templateParam;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_message_instance.content
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    private String content;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_message_instance.refuse_reason
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    private String refuseReason;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_message_instance.create_time
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_message_instance.modify_time
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_message_instance.is_deleted
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    private Byte isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hf_message_instance
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_message_instance.id
     *
     * @return the value of hf_message_instance.id
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_message_instance.id
     *
     * @param id the value for hf_message_instance.id
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_message_instance.status
     *
     * @return the value of hf_message_instance.status
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_message_instance.status
     *
     * @param status the value for hf_message_instance.status
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_message_instance.subject
     *
     * @return the value of hf_message_instance.subject
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    public String getSubject() {
        return subject;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_message_instance.subject
     *
     * @param subject the value for hf_message_instance.subject
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_message_instance.template_type_id
     *
     * @return the value of hf_message_instance.template_type_id
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    public Integer getTemplateTypeId() {
        return templateTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_message_instance.template_type_id
     *
     * @param templateTypeId the value for hf_message_instance.template_type_id
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    public void setTemplateTypeId(Integer templateTypeId) {
        this.templateTypeId = templateTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_message_instance.template_id
     *
     * @return the value of hf_message_instance.template_id
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_message_instance.template_id
     *
     * @param templateId the value for hf_message_instance.template_id
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_message_instance.template_param
     *
     * @return the value of hf_message_instance.template_param
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    public String getTemplateParam() {
        return templateParam;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_message_instance.template_param
     *
     * @param templateParam the value for hf_message_instance.template_param
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    public void setTemplateParam(String templateParam) {
        this.templateParam = templateParam == null ? null : templateParam.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_message_instance.content
     *
     * @return the value of hf_message_instance.content
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_message_instance.content
     *
     * @param content the value for hf_message_instance.content
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_message_instance.refuse_reason
     *
     * @return the value of hf_message_instance.refuse_reason
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    public String getRefuseReason() {
        return refuseReason;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_message_instance.refuse_reason
     *
     * @param refuseReason the value for hf_message_instance.refuse_reason
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason == null ? null : refuseReason.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_message_instance.create_time
     *
     * @return the value of hf_message_instance.create_time
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_message_instance.create_time
     *
     * @param createTime the value for hf_message_instance.create_time
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_message_instance.modify_time
     *
     * @return the value of hf_message_instance.modify_time
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_message_instance.modify_time
     *
     * @param modifyTime the value for hf_message_instance.modify_time
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_message_instance.is_deleted
     *
     * @return the value of hf_message_instance.is_deleted
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    public Byte getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_message_instance.is_deleted
     *
     * @param isDeleted the value for hf_message_instance.is_deleted
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_message_instance
     *
     * @mbg.generated Sat Aug 29 10:20:32 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", status=").append(status);
        sb.append(", subject=").append(subject);
        sb.append(", templateTypeId=").append(templateTypeId);
        sb.append(", templateId=").append(templateId);
        sb.append(", templateParam=").append(templateParam);
        sb.append(", content=").append(content);
        sb.append(", refuseReason=").append(refuseReason);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}