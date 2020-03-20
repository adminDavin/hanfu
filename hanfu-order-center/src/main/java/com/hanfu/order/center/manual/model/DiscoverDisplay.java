package com.hanfu.order.center.manual.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

public class DiscoverDisplay {
    @ApiModelProperty(required = false, value = "用户id")
    private Integer userId;
    @ApiModelProperty(required = false, value = "发现标题")
    private String discoverHeadline;
    @ApiModelProperty(required = false, value = "发现内容")
    private String discoverContent;
    @ApiModelProperty(required = false, value = "发现描述")
    private String discoverDesc;
    private Integer[] productId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer[] getProductId() {
        return productId;
    }

    public void setProductId(Integer[] productId) {
        this.productId = productId;
    }
}
