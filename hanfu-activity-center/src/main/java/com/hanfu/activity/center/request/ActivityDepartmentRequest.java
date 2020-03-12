package com.hanfu.activity.center.request;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
@ApiModel
public class ActivityDepartmentRequest extends CommonRequest {
    @ApiModelProperty(required = true, value = "部门id")
    private Integer id;
    @ApiModelProperty(required = false, value = "部门名称")
    private String departmentName;
    @ApiModelProperty(required = false, value = "上级部门id")
    private Integer superiorId;
    @ApiModelProperty(required = false, value = "公司id")
    private Integer companyId;
    @ApiModelProperty(required = false, value = "公司名字")
    private String companyName;
    @ApiModelProperty(required = false, value = "评论")
    private String remarks;
    @ApiModelProperty(required = false, value = "文件")
    private MultipartFile fileInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getSuperiorId() {
        return superiorId;
    }

    public void setSuperiorId(Integer superiorId) {
        this.superiorId = superiorId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public MultipartFile getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(MultipartFile fileInfo) {
        this.fileInfo = fileInfo;
    }
}
