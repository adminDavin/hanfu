package com.hanfu.product.center.manual.model;

public class IsDelete {
    private Integer stoneId;
    private Short isDelete;
    private Integer bossId;
    private String productName;
    private String category;
    private Integer type; //2上新

    public Integer getStoneId() {
        return stoneId;
    }

    public void setStoneId(Integer stoneId) {
        this.stoneId = stoneId;
    }

    public Short getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Short isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getBossId() {
        return bossId;
    }

    public void setBossId(Integer bossId) {
        this.bossId = bossId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
