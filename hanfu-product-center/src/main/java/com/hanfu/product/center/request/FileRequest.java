package com.hanfu.product.center.request;

import io.swagger.annotations.ApiParam;

public class FileRequest extends CommonRequest{
	@ApiParam(name = "文件名", required = true, type = "String")
	private String fileName;
	@ApiParam(name = "用户Id", required = true, type = "Integer")
	private Integer userId;
	@ApiParam(name = "文件组", required = true, type = "String")
	private String groupName;
	@ApiParam(name = "文件路径", required = true, type = "String")
	private String remoteFileName;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getRemoteFileName() {
		return remoteFileName;
	}
	public void setRemoteFileName(String remoteFileName) {
		this.remoteFileName = remoteFileName;
	}
	
}
