package com.hanfu.user.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HfUser implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user.id
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user.username
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private String username;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user.phone
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private String phone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user.email
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private String email;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user.source_type
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private String sourceType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user.nick_name
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private String nickName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user.real_name
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private String realName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user.sex
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private Byte sex;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user.birth_day
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private LocalDateTime birthDay;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user.user_status
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private Byte userStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user.file_id
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private Integer fileId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user.address
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private String address;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user.user_level
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private Byte userLevel;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user.last_auth_time
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private LocalDateTime lastAuthTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user.region
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private String region;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user.create_date
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private LocalDateTime createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user.modify_date
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private LocalDateTime modifyDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_user.id_deleted
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private Byte idDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hf_user
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user.id
     *
     * @return the value of hf_user.id
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user.id
     *
     * @param id the value for hf_user.id
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user.username
     *
     * @return the value of hf_user.username
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user.username
     *
     * @param username the value for hf_user.username
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user.phone
     *
     * @return the value of hf_user.phone
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user.phone
     *
     * @param phone the value for hf_user.phone
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user.email
     *
     * @return the value of hf_user.email
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user.email
     *
     * @param email the value for hf_user.email
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user.source_type
     *
     * @return the value of hf_user.source_type
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public String getSourceType() {
        return sourceType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user.source_type
     *
     * @param sourceType the value for hf_user.source_type
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType == null ? null : sourceType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user.nick_name
     *
     * @return the value of hf_user.nick_name
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user.nick_name
     *
     * @param nickName the value for hf_user.nick_name
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user.real_name
     *
     * @return the value of hf_user.real_name
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public String getRealName() {
        return realName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user.real_name
     *
     * @param realName the value for hf_user.real_name
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user.sex
     *
     * @return the value of hf_user.sex
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public Byte getSex() {
        return sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user.sex
     *
     * @param sex the value for hf_user.sex
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setSex(Byte sex) {
        this.sex = sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user.birth_day
     *
     * @return the value of hf_user.birth_day
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public LocalDateTime getBirthDay() {
        return birthDay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user.birth_day
     *
     * @param birthDay the value for hf_user.birth_day
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setBirthDay(LocalDateTime birthDay) {
        this.birthDay = birthDay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user.user_status
     *
     * @return the value of hf_user.user_status
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public Byte getUserStatus() {
        return userStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user.user_status
     *
     * @param userStatus the value for hf_user.user_status
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setUserStatus(Byte userStatus) {
        this.userStatus = userStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user.file_id
     *
     * @return the value of hf_user.file_id
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public Integer getFileId() {
        return fileId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user.file_id
     *
     * @param fileId the value for hf_user.file_id
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user.address
     *
     * @return the value of hf_user.address
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user.address
     *
     * @param address the value for hf_user.address
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user.user_level
     *
     * @return the value of hf_user.user_level
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public Byte getUserLevel() {
        return userLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user.user_level
     *
     * @param userLevel the value for hf_user.user_level
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setUserLevel(Byte userLevel) {
        this.userLevel = userLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user.last_auth_time
     *
     * @return the value of hf_user.last_auth_time
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public LocalDateTime getLastAuthTime() {
        return lastAuthTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user.last_auth_time
     *
     * @param lastAuthTime the value for hf_user.last_auth_time
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setLastAuthTime(LocalDateTime lastAuthTime) {
        this.lastAuthTime = lastAuthTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user.region
     *
     * @return the value of hf_user.region
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public String getRegion() {
        return region;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user.region
     *
     * @param region the value for hf_user.region
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user.create_date
     *
     * @return the value of hf_user.create_date
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user.create_date
     *
     * @param createDate the value for hf_user.create_date
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user.modify_date
     *
     * @return the value of hf_user.modify_date
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user.modify_date
     *
     * @param modifyDate the value for hf_user.modify_date
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_user.id_deleted
     *
     * @return the value of hf_user.id_deleted
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public Byte getIdDeleted() {
        return idDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_user.id_deleted
     *
     * @param idDeleted the value for hf_user.id_deleted
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    public void setIdDeleted(Byte idDeleted) {
        this.idDeleted = idDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_user
     *
     * @mbg.generated Mon Oct 14 06:52:54 CST 2019
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", phone=").append(phone);
        sb.append(", email=").append(email);
        sb.append(", sourceType=").append(sourceType);
        sb.append(", nickName=").append(nickName);
        sb.append(", realName=").append(realName);
        sb.append(", sex=").append(sex);
        sb.append(", birthDay=").append(birthDay);
        sb.append(", userStatus=").append(userStatus);
        sb.append(", fileId=").append(fileId);
        sb.append(", address=").append(address);
        sb.append(", userLevel=").append(userLevel);
        sb.append(", lastAuthTime=").append(lastAuthTime);
        sb.append(", region=").append(region);
        sb.append(", createDate=").append(createDate);
        sb.append(", modifyDate=").append(modifyDate);
        sb.append(", idDeleted=").append(idDeleted);
        sb.append("]");
        return sb.toString();
    }
}