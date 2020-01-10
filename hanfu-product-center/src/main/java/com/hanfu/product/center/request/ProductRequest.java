package com.hanfu.product.center.request;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class ProductRequest extends CommonRequest {
    @ApiModelProperty(required = false, value = "商品id")
    private Integer id;
    @ApiModelProperty(required = true, value = "商品名称")
    private String hfName;
    @ApiModelProperty(required = true, value = "商品所属的类目id")
    private Integer categoryId;
    @ApiModelProperty(required = true, value = "品牌id")
    private Integer brandId;
    @ApiModelProperty(required = true, value = "商品描述")
    private String productDesc;
    @ApiModelProperty(required = true, name = "bossId", value = "商家id")
    private Integer bossId;
    @ApiModelProperty(name = "lastModifier", required = true, value = "商家名称")
    private String lastModifier;
    @ApiModelProperty( required = true, value = "是否自提")
    private Integer claim;
    @ApiModelProperty(required = true, value = "核销Id")
    private Integer cancelId;
    @ApiModelProperty(required = true, value = "是否为会员商品")
    private Integer member;
    
    public Integer getClaim() {
		return claim;
	}

	public void setClaim(Integer claim) {
		this.claim = claim;
	}


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

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHfName() {
        return hfName;
    }

    public void setHfName(String hfName) {
        this.hfName = hfName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public Integer getBossId() {
        return bossId;
    }

    public void setBossId(Integer bossId) {
        this.bossId = bossId;
    }

    public String getLastModifier() {
        return lastModifier;
    }

    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier;
    }

}
