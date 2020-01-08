package com.hanfu.user.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Authorization implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authorization.id
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authorization.file_id
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
    private Integer fileId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authorization.real_name
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
    private String realName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authorization.employee_code
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
    private String employeeCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authorization.phone
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
    private String phone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authorization.id_card
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
    private String idCard;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authorization.employee_email
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
    private String employeeEmail;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authorization.position
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
    private String position;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authorization.employee_sex
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
    private Integer employeeSex;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authorization.employee_name
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
    private String employeeName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authorization.employee_site
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
    private String employeeSite;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authorization.employee_department
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
    private String employeeDepartment;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authorization.state
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
    private Integer state;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authorization.remark
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
    private String remark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authorization.id_deleted
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
    private Byte idDeleted;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authorization.create_date
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
    private LocalDateTime createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column authorization.modify_date
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
    private LocalDateTime modifyDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table authorization
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authorization.id
     *
     * @return the value of authorization.id
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
    public Integer getId() {
        return id;
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authorization.id
     *
     * @param id the value for authorization.id
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public void setId(Integer id) {
        this.id = id;
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authorization.file_id
     *
     * @return the value of authorization.file_id
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public Integer getFileId() {
        return fileId;
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authorization.file_id
     *
     * @param fileId the value for authorization.file_id
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authorization.real_name
     *
     * @return the value of authorization.real_name
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public String getRealName() {
        return realName;
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authorization.real_name
     *
     * @param realName the value for authorization.real_name
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authorization.employee_code
     *
     * @return the value of authorization.employee_code
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public String getEmployeeCode() {
        return employeeCode;
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authorization.employee_code
     *
     * @param employeeCode the value for authorization.employee_code
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode == null ? null : employeeCode.trim();
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authorization.phone
     *
     * @return the value of authorization.phone
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public String getPhone() {
        return phone;
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authorization.phone
     *
     * @param phone the value for authorization.phone
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authorization.id_card
     *
     * @return the value of authorization.id_card
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public String getIdCard() {
        return idCard;
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authorization.id_card
     *
     * @param idCard the value for authorization.id_card
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authorization.employee_email
     *
     * @return the value of authorization.employee_email
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public String getEmployeeEmail() {
        return employeeEmail;
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authorization.employee_email
     *
     * @param employeeEmail the value for authorization.employee_email
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail == null ? null : employeeEmail.trim();
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authorization.position
     *
     * @return the value of authorization.position
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public String getPosition() {
        return position;
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authorization.position
     *
     * @param position the value for authorization.position
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authorization.employee_sex
     *
     * @return the value of authorization.employee_sex
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public Integer getEmployeeSex() {
        return employeeSex;
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authorization.employee_sex
     *
     * @param employeeSex the value for authorization.employee_sex
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public void setEmployeeSex(Integer employeeSex) {
        this.employeeSex = employeeSex;
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authorization.employee_name
     *
     * @return the value of authorization.employee_name
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public String getEmployeeName() {
        return employeeName;
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authorization.employee_name
     *
     * @param employeeName the value for authorization.employee_name
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName == null ? null : employeeName.trim();
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authorization.employee_site
     *
     * @return the value of authorization.employee_site
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public String getEmployeeSite() {
        return employeeSite;
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authorization.employee_site
     *
     * @param employeeSite the value for authorization.employee_site
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public void setEmployeeSite(String employeeSite) {
        this.employeeSite = employeeSite == null ? null : employeeSite.trim();
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authorization.employee_department
     *
     * @return the value of authorization.employee_department
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public String getEmployeeDepartment() {
        return employeeDepartment;
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authorization.employee_department
     *
     * @param employeeDepartment the value for authorization.employee_department
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public void setEmployeeDepartment(String employeeDepartment) {
        this.employeeDepartment = employeeDepartment == null ? null : employeeDepartment.trim();
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authorization.state
     *
     * @return the value of authorization.state
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public Integer getState() {
        return state;
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authorization.state
     *
     * @param state the value for authorization.state
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public void setState(Integer state) {
        this.state = state;
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authorization.remark
     *
     * @return the value of authorization.remark
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public String getRemark() {
        return remark;
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authorization.remark
     *
     * @param remark the value for authorization.remark
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authorization.id_deleted
     *
     * @return the value of authorization.id_deleted
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public Byte getIdDeleted() {
        return idDeleted;
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authorization.id_deleted
     *
     * @param idDeleted the value for authorization.id_deleted
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public void setIdDeleted(Byte idDeleted) {
        this.idDeleted = idDeleted;
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authorization.create_date
     *
     * @return the value of authorization.create_date
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public LocalDateTime getCreateDate() {
        return createDate;
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authorization.create_date
     *
     * @param createDate the value for authorization.create_date
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column authorization.modify_date
     *
     * @return the value of authorization.modify_date
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
=======
>>>>>>> master
    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

<<<<<<< HEAD
    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column authorization.modify_date
     *
     * @param modifyDate the value for authorization.modify_date
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table authorization
     *
     * @mbg.generated Tue Jan 07 14:58:38 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", fileId=").append(fileId);
        sb.append(", realName=").append(realName);
        sb.append(", employeeCode=").append(employeeCode);
        sb.append(", phone=").append(phone);
        sb.append(", idCard=").append(idCard);
        sb.append(", employeeEmail=").append(employeeEmail);
        sb.append(", position=").append(position);
        sb.append(", employeeSex=").append(employeeSex);
        sb.append(", employeeName=").append(employeeName);
        sb.append(", employeeSite=").append(employeeSite);
        sb.append(", employeeDepartment=").append(employeeDepartment);
        sb.append(", state=").append(state);
        sb.append(", remark=").append(remark);
        sb.append(", idDeleted=").append(idDeleted);
        sb.append(", createDate=").append(createDate);
        sb.append(", modifyDate=").append(modifyDate);
        sb.append("]");
        return sb.toString();
    }
=======
    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }
>>>>>>> master
}