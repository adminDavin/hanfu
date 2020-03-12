package com.hanfu.activity.center.manual.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanfu.activity.center.model.ActivityUserEvaluate;

import io.swagger.annotations.ApiModelProperty;

public class UserFormInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3487964551024846418L;

    private Integer fileId;
    private String username;
    private String departmentName;
    @ApiModelProperty(value = "创建时间", required = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date hiredate;
    private String date;
    private String jobContent;
    private String phone;
    private String evaluation;
    private Integer departmentId;
    private List<ActivityUserEvaluate> list;

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getJobContent() {
        return jobContent;
    }

    public void setJobContent(String jobContent) {
        this.jobContent = jobContent;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public List<ActivityUserEvaluate> getList() {
        return list;
    }

    public void setList(List<ActivityUserEvaluate> list) {
        this.list = list;
    }

    public Date getHiredate() {
        return hiredate;
    }

    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
