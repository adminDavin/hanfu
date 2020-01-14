package com.hanfu.product.center.request;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class HfGoodsInfo extends CommonRequest {
//    @ApiModelProperty(required = false, value = "图片路径")
//    private MultipartFile[] fileInfo;
    @ApiModelProperty(required = true, value = "商品id")
    private Integer productId;
    @ApiModelProperty(required = true, value = "物品名称")
    private String goodName;
    @ApiModelProperty(required = true, value = "物品描述")
    private String goodsDesc;
    @ApiModelProperty(required = true, value = "商铺id")
    private Integer hfStoreId;
    @ApiModelProperty(required = true, value = "商家id")
    private Integer bossId;
    @ApiModelProperty(required = false, value = "品牌id")
    private Integer brandId;
    @ApiModelProperty(name = "username", required = true, value = "商家名称")
    private String username;
    @ApiModelProperty(required = false, value = "类目规格id")
    private Integer catrgorySpecId[];
    @ApiModelProperty(required = false, value = "物品规格id")
    private Integer goodsSpecId;
    @ApiModelProperty(required = true, value = "规格值")
    private String[] specValue;
    @ApiModelProperty(required = true, value = "是否为会员 0 非会员 1会员")
    private Integer member;
    @ApiModelProperty(required = false, value = "图片描述")
    private String prictureDesc;
    @ApiModelProperty(required = true, value = "核销员Id")
    private Integer cancelId;
    @ApiModelProperty(required = true, value = "上下架")
    private Integer frames;
    @ApiModelProperty(required = false, value = "类目Id")
    private Integer catrgoryId;
    @ApiModelProperty(required = false, value = "是否自提")
    private Integer claim;
    @ApiModelProperty(required = false, value = "规格单位")
    private String  specUnit;
    @ApiModelProperty(required = false, value = "类目名称")
    private String categoryName;
    @ApiModelProperty(required = false, value = "库存量")
    private Integer quantity;
    @ApiModelProperty(required = false, value = "价格")
    private Integer sellPrice;
    
	public Integer getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Integer sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getSpecUnit() {
		return specUnit;
	}

	public void setSpecUnit(String specUnit) {
		this.specUnit = specUnit;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getClaim() {
		return claim;
	}

	public void setClaim(Integer claim) {
		this.claim = claim;
	}

	public Integer getCatrgoryId() {
		return catrgoryId;
	}

	public void setCatrgoryId(Integer catrgoryId) {
		this.catrgoryId = catrgoryId;
	}

	public Integer getFrames() {
		return frames;
	}

	public void setFrames(Integer frames) {
		this.frames = frames;
	}

	public String getPrictureDesc() {
		return prictureDesc;
	}

	public void setPrictureDesc(String prictureDesc) {
		this.prictureDesc = prictureDesc;
	}

//	public MultipartFile[] getFileInfo() {
//		return fileInfo;
//	}
//
//	public void setFileInfo(MultipartFile[] fileInfo) {
//		this.fileInfo = fileInfo;
//	}

	public Integer getCancelId() {
		return cancelId;
	}

	public void setCancelId(Integer cancelId) {
		this.cancelId = cancelId;
	}

	public Integer getMember() {
		return member;
	}

	public void setMember(Integer member) {
		this.member = member;
	}

	public Integer[] getCatrgorySpecId() {
		return catrgorySpecId;
	}

	public void setCatrgorySpecId(Integer[] catrgorySpecId) {
		this.catrgorySpecId = catrgorySpecId;
	}

	public Integer getGoodsSpecId() {
		return goodsSpecId;
	}

	public void setGoodsSpecId(Integer goodsSpecId) {
		this.goodsSpecId = goodsSpecId;
	}

	public String[] getSpecValue() {
		return specValue;
	}

	public void setSpecValue(String[] specValue) {
		this.specValue = specValue;
	}

	public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodDesc) {
        this.goodsDesc = goodDesc;
    }

    public Integer getBossId() {
        return bossId;
    }

    public void setBossId(Integer bossId) {
        this.bossId = bossId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getHfStoreId() {
        return hfStoreId;
    }

    public void setHfStoreId(Integer hfStoreId) {
        this.hfStoreId = hfStoreId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
