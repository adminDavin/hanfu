package com.hanfu.user.center.request;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class UserPictrueRequeest implements Serializable {
    @ApiModelProperty(required = true, value = "用户Id")
    private Integer userId;
    @ApiModelProperty(required = false, value = "图片描述")
    private String prictureDesc;
    @ApiModelProperty(required = true, value = "图片路径")
    private MultipartFile fileInfo;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPrictureDesc() {
        return prictureDesc;
    }

    public void setPrictureDesc(String prictureDesc) {
        this.prictureDesc = prictureDesc;
    }

    public MultipartFile getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(MultipartFile fileInfo) {
        this.fileInfo = fileInfo;
    }

}
