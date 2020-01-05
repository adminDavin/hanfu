package com.hanfu.activity.center.request;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
@ApiModel
public class ActivityCompanyRequest extends CommonRequest {
    @ApiModelProperty(required = false, value = "部门id")
    private Integer id;
    @ApiModelProperty(required = false, value = "公司名字")
    private String companyName;
    @ApiModelProperty(required = false, value = "公司信息")
    private String companyInfo;
    @ApiModelProperty(required = false, value = "评论")
    private String remarks;
    @ApiModelProperty(required = false, value = "文件id")
    private Integer fileId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(String companyInfo) {
        this.companyInfo = companyInfo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

}
