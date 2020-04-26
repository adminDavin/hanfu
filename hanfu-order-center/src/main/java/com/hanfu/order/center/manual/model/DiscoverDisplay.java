package com.hanfu.order.center.manual.model;

import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DiscoverDisplay {
	
	public static enum DiscoverTypeEnum {
        
		HEART("heart"),
		VIDEO("video"),
		ARTICLE("article");
		private String discoverType;

		private DiscoverTypeEnum(String discoverType) {
			this.discoverType = discoverType;
		}
		
		public String getDiscoverType() {
			return discoverType;
		}


		public void setDiscoverType(String discoverType) {
			this.discoverType = discoverType;
		}


		public static DiscoverTypeEnum getDiscoverTypeEnum(String discoverType) {
			for(DiscoverTypeEnum d:DiscoverTypeEnum.values()) {
				if(d.getDiscoverType().equals(discoverType)) {
					return d;
				}
			}
			return HEART;
		}
    }
	
    @ApiModelProperty(required = false, value = "用户id")
    private Integer userId;
    @ApiModelProperty(required = false, value = "用户名")
    private String username;
    @ApiModelProperty(required = false, value = "发现标题")
    private String discoverHeadline;
    @ApiModelProperty(required = false, value = "发现内容")
    private String discoverContent;
    @ApiModelProperty(required = false, value = "发现描述")
    private String discoverDesc;
    @ApiModelProperty(required = false, value = "商品id")
    private List<Integer> productId;
    @ApiModelProperty(required = false, value = "发现类型")
    private String discoverType;
    @ApiModelProperty(required = false, value = "文件id")
    private List<Integer> fileId;
    @ApiModelProperty(required = false, value = "评论时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime time;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDiscoverHeadline() {
		return discoverHeadline;
	}
	public void setDiscoverHeadline(String discoverHeadline) {
		this.discoverHeadline = discoverHeadline;
	}
	public String getDiscoverContent() {
		return discoverContent;
	}
	public void setDiscoverContent(String discoverContent) {
		this.discoverContent = discoverContent;
	}
	public String getDiscoverDesc() {
		return discoverDesc;
	}
	public void setDiscoverDesc(String discoverDesc) {
		this.discoverDesc = discoverDesc;
	}
	public List<Integer> getProductId() {
		return productId;
	}
	public void setProductId(List<Integer> productId) {
		this.productId = productId;
	}
	public String getDiscoverType() {
		return discoverType;
	}
	public void setDiscoverType(String discoverType) {
		this.discoverType = discoverType;
	}
	public List<Integer> getFileId() {
		return fileId;
	}
	public void setFileId(List<Integer> fileId) {
		this.fileId = fileId;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}

}
