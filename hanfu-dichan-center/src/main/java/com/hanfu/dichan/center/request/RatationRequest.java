package com.hanfu.dichan.center.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@SuppressWarnings("serial")
@ApiModel
public class RatationRequest extends CommonRequest {
    @ApiModelProperty(required = false, value = "类型（首页/关于我们）")
    private String type;
    @ApiModelProperty(required = false, value = "项目id")
    private Integer projectId;
    @ApiModelProperty(required = false, value = "图片id")
    private Integer fileId;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public Integer getFileId() {
		return fileId;
	}
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	
}
