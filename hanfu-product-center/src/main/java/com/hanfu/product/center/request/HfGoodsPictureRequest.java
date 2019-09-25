package com.hanfu.product.center.request;

import io.swagger.annotations.ApiParam;

public class HfGoodsPictureRequest extends CommonRequest{
	@ApiParam(name = "商品实体定价单元Id", required = true, type = "Integer")
	private Integer productUnitId;
	@ApiParam(name = "图片名称", required = true, type = "String")
	private String pictureName;
	@ApiParam(name = "图片说明", required = true, type = "String")
	private String pictureshow;
	@ApiParam(name = "文件Id", required = true, type = "Integer")
	private Integer fileId;
	public Integer getProductUnitId() {
		return productUnitId;
	}
	public void setProductUnitId(Integer productUnitId) {
		this.productUnitId = productUnitId;
	}
	public String getPictureName() {
		return pictureName;
	}
	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}
	public String getPictureshow() {
		return pictureshow;
	}
	public void setPictureshow(String pictureshow) {
		this.pictureshow = pictureshow;
	}
	public Integer getFileId() {
		return fileId;
	}
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	
}
