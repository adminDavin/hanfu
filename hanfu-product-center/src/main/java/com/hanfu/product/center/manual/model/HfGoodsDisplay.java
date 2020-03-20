package com.hanfu.product.center.manual.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanfu.product.center.request.CommonRequest;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class HfGoodsDisplay extends CommonRequest {

    @ApiModelProperty(required = false, value = "物品id")
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
    @ApiModelProperty(required = false, value = "库存")
    private Integer quantity;
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
    @ApiModelProperty(required = false, value = "物品图片id")
    private Integer fileId;
    @ApiModelProperty(required = false, value = "店家名称, 登录修改的用户名称")
    private String username;
    @ApiModelProperty(required = false, value = "店铺id")
    private Integer stoneId;
    @ApiModelProperty(required = false, value = "核销id")
    private Integer cancelId;
    @ApiModelProperty(required = false, value = "是否自提")
    private Integer claim;
    @ApiModelProperty(required = false, value = "上下架")
    private Integer frames;
    @ApiModelProperty(required = false, value = "品牌名称")
    private String brandName;
    @ApiModelProperty(required = false, value = "图片描述")
    private String prictureDesc;
    @ApiModelProperty(required = false, value = "会员")
    private Integer memeber;
    @ApiModelProperty(required = false, value = "价格")
    private Integer sellPrice1;
    @ApiModelProperty(required = false, value = "价格")
    private Integer sellPrice2;
    @ApiModelProperty(required = false, value = "评价")
    private String evaluate;
    @ApiModelProperty(required = false, value = "规格")
    private String hfValue;
    @ApiModelProperty(required = false, value = "商品名称")
    private String productName;
    @ApiModelProperty(required = false, value = "商品规格名称")
    private String productSpecName;
    @ApiModelProperty(required = false, value = "划线价")
    private Integer linePrice;
    @ApiModelProperty(required = false, value = "榜单id")
    private Integer seniorityId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private LocalDateTime createTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime modifyTime;
    private short isDeleted;
    
    public Integer getSeniorityId() {
		return seniorityId;
	}

	public void setSeniorityId(Integer seniorityId) {
		this.seniorityId = seniorityId;
	}

	public Integer getLinePrice() {
		return linePrice;
	}

	public void setLinePrice(Integer linePrice) {
		this.linePrice = linePrice;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductSpecName() {
		return productSpecName;
	}

	public void setProductSpecName(String productSpecName) {
		this.productSpecName = productSpecName;
	}

	public String getHfValue() {
		return hfValue;
	}

	public void setHfValue(String hfValue) {
		this.hfValue = hfValue;
	}

	public String getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}

	public Integer getMemeber() {
		return memeber;
	}

	public void setMemeber(Integer memeber) {
		this.memeber = memeber;
	}

	public Integer getSellPrice1() {
		return sellPrice1;
	}

	public void setSellPrice1(Integer sellPrice1) {
		this.sellPrice1 = sellPrice1;
	}

	public Integer getSellPrice2() {
		return sellPrice2;
	}

	public void setSellPrice2(Integer sellPrice2) {
		this.sellPrice2 = sellPrice2;
	}

	public String getPrictureDesc() {
		return prictureDesc;
	}

	public void setPrictureDesc(String prictureDesc) {
		this.prictureDesc = prictureDesc;
	}
    public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
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

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Integer getStoneId() {
        return stoneId;
    }

    public void setStoneId(Integer stoneId) {
        this.stoneId = stoneId;
    }

    public Integer getCancelId() {
        return cancelId;
    }

    public void setCancelId(Integer cancelId) {
        this.cancelId = cancelId;
    }

    public Integer getClaim() {
        return claim;
    }

    public void setClaim(Integer claim) {
        this.claim = claim;
    }

    public Integer getFrames() {
        return frames;
    }

    public void setFrames(Integer frames) {
        this.frames = frames;
    }
}
