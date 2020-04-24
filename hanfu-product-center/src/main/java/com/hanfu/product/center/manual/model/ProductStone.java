package com.hanfu.product.center.manual.model;

import com.hanfu.product.center.manual.model.Evaluate.EvaluateTypeEnum;

public class ProductStone {
	
public static enum StonePictureTypeEnum {
        
		BACKGROUND("background"),
		AVATAR("avatar");
		private String stonePictureType;

		private StonePictureTypeEnum(String stonePictureType) {
			this.stonePictureType = stonePictureType;
		}
		

		public String getStonePictureType() {
			return stonePictureType;
		}
		public void setStonePictureType(String stonePictureType) {
			this.stonePictureType = stonePictureType;
		}

		public static StonePictureTypeEnum getStonePictureTypeEnum(String stonePictureType) {
			for(StonePictureTypeEnum d:StonePictureTypeEnum.values()) {
				if(d.getStonePictureType().equals(stonePictureType)) {
					return d;
				}
			}
			return AVATAR;
		}
    }
	
    private Integer productId;//物品id
    private Integer stoneId;//店铺id

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getStoneId() {
        return stoneId;
    }

    public void setStoneId(Integer stoneId) {
        this.stoneId = stoneId;
    }
}
