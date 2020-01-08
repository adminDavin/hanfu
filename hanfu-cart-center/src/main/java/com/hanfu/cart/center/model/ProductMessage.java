package com.hanfu.cart.center.model;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("serial")
public class ProductMessage implements Serializable{
	 private String productId;
	    private String productPrice;
	    private String productNum;
	    /*是否勾选*/
	    private String check;
	    private String productName;
	    private String productIcon;
	    private String userid;
	    /**
	     * 状态, 0正常1下架.
	     */
	    private String productStatus;

	    /* 商品小图*/
	    private String img;


		public String getProductId() {
			return productId;
		}



		public void setProductId(String productId) {
			this.productId = productId;
		}



		public String getProductPrice() {
			return productPrice;
		}



		public void setProductPrice(String productPrice) {
			this.productPrice = productPrice;
		}



		public String getProductNum() {
			return productNum;
		}



		public void setProductNum(String productNum) {
			this.productNum = productNum;
		}



		public String getCheck() {
			return check;
		}



		public void setCheck(String check) {
			this.check = check;
		}



		public String getProductName() {
			return productName;
		}



		public void setProductName(String productName) {
			this.productName = productName;
		}



		public String getProductIcon() {
			return productIcon;
		}



		public void setProductIcon(String productIcon) {
			this.productIcon = productIcon;
		}



		public String getUserid() {
			return userid;
		}



		public void setUserid(String userid) {
			this.userid = userid;
		}



		public String getProductStatus() {
			return productStatus;
		}



		public void setProductStatus(String productStatus) {
			this.productStatus = productStatus;
		}



		public String getImg() {
			return img;
		}



		public void setImg(String img) {
			this.img = img;
		}



		@Override
		public String toString() {
			return "ProductMessage [productId=" + productId + ", productPrice=" + productPrice + ", productNum="
					+ productNum + ", check=" + check + ", productName=" + productName + ", productIcon=" + productIcon
					+ ", userid=" + userid + ", productStatus=" + productStatus + ", img=" + img + "]";
		}


}
