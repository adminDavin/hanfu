package com.hanfu.order.center.request;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class GoodsPictrueRequest extends CommonRequest {
    @ApiModelProperty(required = true, value = "物品id, 即对应到商铺商品的某一种种类")
    private Integer goodsId;
    @ApiModelProperty(required = true, value = "图片名称")
    private String pictureName;
    @ApiModelProperty(required = true, value = "图片描述")
    private String prictureDesc;
    @ApiModelProperty(required = true, value = "图片文件")
    private MultipartFile fileInfo;
    @ApiModelProperty(required = true, value = "店家名称, 登录修改的用户名称")
    private String username;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
