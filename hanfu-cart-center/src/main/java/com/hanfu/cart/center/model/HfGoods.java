package com.hanfu.cart.center.model;

import io.swagger.annotations.ApiModelProperty;



import java.io.Serializable;

import java.time.LocalDateTime;

@SuppressWarnings("serial")

public class HfGoods implements Serializable {

    @ApiModelProperty(required = true, value = "物品id")

    private Integer id;

    @ApiModelProperty(required = false, value = "物品名称")

    private String goodName;

    @ApiModelProperty(required = false, value = "仓库名称")

    private String warehouseName;

    @ApiModelProperty(required = false, value = "商品类目名称")

    private String productCategoryName;

    @ApiModelProperty(required = false, value = "物品描述")

    private String goodsDesc;

    @ApiModelProperty(required = false, value = "类目id")

    private Integer categoryId;

    @ApiModelProperty(required = false, value = "数量")

    private String quantity;

    @ApiModelProperty(required = false, value = "销售价格")

    private Integer sellPrice;

    @ApiModelProperty(required = false, value = "规格值")

    private String specValue;

    @ApiModelProperty(required = false, value = "商品规格id")

    private Integer productSpecId;

    @ApiModelProperty(required = false, value = "价格id")

    private Integer priceId;

    @ApiModelProperty(required = false, value = "库存id")

    private Integer respId;

    @ApiModelProperty(required = false, value = "商品id")

    private Integer productId;

    @ApiModelProperty(required = true, value = "物品图片id")

    private Integer hfGoodsPictureId;

    @ApiModelProperty(required = true, value = "店家名称, 登录修改的用户名称")

    private String username;

    @ApiModelProperty(required = true, value = "店铺id")

    private Integer stoneId;
    @ApiModelProperty(required = true, value = "上下架")

    private Integer frames;
    @ApiModelProperty(required = true, value = "图片Id")

    private Integer fileId;

    private LocalDateTime createTime;

    private LocalDateTime modifyTime;

    private short isDeleted;

    public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public Integer getFrames() {
		return frames;
	}

	public void setFrames(Integer frames) {
		this.frames = frames;
	}

	public Integer getId() {

        return id;

    }

    public void setId(Integer id) {

        this.id = id;

    }

    public String getGoodName() {

        return goodName;

    }

    public void setGoodName(String goodName) {

        this.goodName = goodName;

    }

    public String getProductCategoryName() {

        return productCategoryName;

    }

    public void setProductCategoryName(String productCategoryName) {

        this.productCategoryName = productCategoryName;

    }

    public String getGoodsDesc() {

        return goodsDesc;

    }

    public void setGoodsDesc(String goodsDesc) {

        this.goodsDesc = goodsDesc;

    }

    public Integer getCategoryId() {

        return categoryId;

    }

    public void setCategoryId(Integer categoryId) {

        this.categoryId = categoryId;

    }

    public String getQuantity() {

        return quantity;

    }

    public void setQuantity(String quantity) {

        this.quantity = quantity;

    }

    public Integer getSellPrice() {

        return sellPrice;

    }

    public void setSellPrice(Integer sellPrice) {

        this.sellPrice = sellPrice;

    }

    public String getSpecValue() {

        return specValue;

    }

    public void setSpecValue(String specValue) {

        this.specValue = specValue;

    }

    public Integer getProductSpecId() {

        return productSpecId;

    }

    public void setProductSpecId(Integer productSpecId) {

        this.productSpecId = productSpecId;

    }

    public Integer getPriceId() {

        return priceId;

    }

    public void setPriceId(Integer priceId) {

        this.priceId = priceId;

    }

    public Integer getRespId() {

        return respId;

    }

    public void setRespId(Integer respId) {

        this.respId = respId;

    }

    public Integer getProductId() {

        return productId;

    }

    public void setProductId(Integer productId) {

        this.productId = productId;

    }

    public String getUsername() {

        return username;

    }

    public void setUsername(String username) {

        this.username = username;

    }

    public LocalDateTime getCreateTime() {

        return createTime;

    }

    public void setCreateTime(LocalDateTime createTime) {

        this.createTime = createTime;

    }

    public LocalDateTime getModifyTime() {

        return modifyTime;

    }

    public void setModifyTime(LocalDateTime modifyTime) {

        this.modifyTime = modifyTime;

    }

    public short getIsDeleted() {

        return isDeleted;

    }

    public void setIsDeleted(short isDeleted) {

        this.isDeleted = isDeleted;

    }

    public String getWarehouseName() {

        return warehouseName;

    }

    public void setWarehouseName(String warehouseName) {

        this.warehouseName = warehouseName;

    }

    public Integer getHfGoodsPictureId() {

        return hfGoodsPictureId;

    }

    public void setHfGoodsPictureId(Integer hfGoodsPictureId) {

        this.hfGoodsPictureId = hfGoodsPictureId;

    }

    public Integer getStoneId() {

        return stoneId;

    }

    public void setStoneId(Integer stoneId) {

        this.stoneId = stoneId;

    }

}