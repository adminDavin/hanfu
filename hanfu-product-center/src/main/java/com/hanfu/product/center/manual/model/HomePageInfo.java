package com.hanfu.product.center.manual.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanfu.product.center.model.HfOrderDetail;

import io.swagger.annotations.ApiModelProperty;

public class HomePageInfo implements Serializable {
	
	
	public static enum MouthEnum {
		January(1,"Jan"),
		February(2,"Feb"),
		March(3,"Mar"),
		April(4,"Apr"),
		May(5,"May"),
		June(6,"June"),
		July(7,"July"),
		August(8,"Aug"),
		September(9,"Sept"),
		October(10,"Oct"),
		November(11,"Nov"),
		December(12,"Dec");
		private Integer month;
		private String monthStr;
		private MouthEnum(Integer month,String monthStr) {
			this.month = month;
			this.monthStr = monthStr;
		}
		public Integer getMonth() {
			return month;
		}
		public void setMonth(Integer month) {
			this.month = month;
		}
		public String getMonthStr() {
			return monthStr;
		}
		public void setMonthStr(String monthStr) {
			this.monthStr = monthStr;
		}
		public static String getPaymentTypeEnum(Integer month) {
	           for(MouthEnum mouthEnum: MouthEnum.values()) {
	               if (mouthEnum.getMonth().equals(month)) {
	                   return mouthEnum.getMonthStr();
	               }
	           }
	           return "Jan";
	        }
    }
	
    /**
     *
     */
    private static final long serialVersionUID = 3834215356820052568L;

    @ApiModelProperty(required = true, value = "今日总金额")
    private double amountDay;
    @ApiModelProperty(required = true, value = "今日支付订单数")
    private Integer orderCountsDay;
    @ApiModelProperty(required = true, value = "今日支付人数")
    private Integer paymentConutsDay;
    @ApiModelProperty(required = true, value = "今日浏览人数")
    private Integer browseCountsDay;
    @ApiModelProperty(required = true, value = "今日会员数")
    private Integer membersDay;
    @ApiModelProperty(required = true, value = "昨日支付订单数")
    private Integer orderCountsYestday;
    @ApiModelProperty(required = true, value = "昨日支付人数")
    private Integer paymentConutsYestday;
    @ApiModelProperty(required = true, value = "昨日浏览人数")
    private Integer browseCountsYestday;
    @ApiModelProperty(required = true, value = "本月总金额")
    private double amountMouth;
    @ApiModelProperty(required = true, value = "本月支付订单数")
    private Integer orderConutsMouth;
    @ApiModelProperty(required = true, value = "本月支付人数")
    private Integer paymentConutsMouth;
    @ApiModelProperty(required = true, value = "本月浏览人数")
    private Integer browseCountsMouth;
    @ApiModelProperty(required = true, value = "本月会员数")
    private Integer membersMouth;
    @ApiModelProperty(required = true, value = "上月支付订单数")
    private Integer orderConutsLastMouth;
    @ApiModelProperty(required = true, value = "上月支付人数")
    private Integer paymentConutsLastMouth;
    @ApiModelProperty(required = true, value = "上月浏览人数")
    private Integer browseCountsLastMouth;
    @ApiModelProperty(required = true, value = "总订单数")
    private Integer orderCountsAll;
    @ApiModelProperty(required = true, value = "总收入")
    private Integer amountAll;
    @ApiModelProperty(required = true, value = "总访问量")
    private Integer browseCountsAll;
    @ApiModelProperty(required = true, value = "总会员数")
    private Integer membersAll;
    @ApiModelProperty(required = true, value = "物品id")
    private Integer goodId;
    @ApiModelProperty(required = true, value = "商品id")
    private Integer productId;
    @ApiModelProperty(required = true, value = "销售量")
    private Integer salesCount;
    @ApiModelProperty(required = true, value = "商品销售量")
    private Integer salesCountAll;
    @ApiModelProperty(required = true, value = "商品下物品销售量")
    private List<HomePageInfo> goodsInfo;
    @ApiModelProperty(required = true, value = "商品名字")
    private String productName;
    @ApiModelProperty(required = true, value = "物品名字")
    private String goodName;
    @ApiModelProperty(required = true, value = "订单类型")
    private String orderType;
    @ApiModelProperty(required = true, value = "订单类型数量")
    private Integer orderTypeCounts;
    @ApiModelProperty(required = true, value = "月份")
    private String[] mouth;
    @ApiModelProperty(required = true, value = "月销售量")
    private Integer[] salesCountMonth;
    @ApiModelProperty(required = true, value = "订单类型数组")
    private String[] orderTypeStr;
    @ApiModelProperty(required = true, value = "订单类型数目数组")
    private Integer[] orderTypeCountsStr;
    @ApiModelProperty(required = true, value = "年份")
    private Integer[] year;
    @ApiModelProperty(required = true, value = "浏览数量")
    private Integer[] browseCountForYeay;
    @ApiModelProperty(required = true, value = "类型")
    private JSONArray typeJson;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(required = false, value = "创建时间")
    private LocalDateTime createTime;
	public double getAmountDay() {
		return amountDay;
	}
	public void setAmountDay(double amountDay) {
		this.amountDay = amountDay;
	}
	public Integer getOrderCountsDay() {
		return orderCountsDay;
	}
	public void setOrderCountsDay(Integer orderCountsDay) {
		this.orderCountsDay = orderCountsDay;
	}
	public Integer getPaymentConutsDay() {
		return paymentConutsDay;
	}
	public void setPaymentConutsDay(Integer paymentConutsDay) {
		this.paymentConutsDay = paymentConutsDay;
	}
	public Integer getBrowseCountsDay() {
		return browseCountsDay;
	}
	public void setBrowseCountsDay(Integer browseCountsDay) {
		this.browseCountsDay = browseCountsDay;
	}
	public Integer getMembersDay() {
		return membersDay;
	}
	public void setMembersDay(Integer membersDay) {
		this.membersDay = membersDay;
	}
	public Integer getOrderCountsYestday() {
		return orderCountsYestday;
	}
	public void setOrderCountsYestday(Integer orderCountsYestday) {
		this.orderCountsYestday = orderCountsYestday;
	}
	public Integer getPaymentConutsYestday() {
		return paymentConutsYestday;
	}
	public void setPaymentConutsYestday(Integer paymentConutsYestday) {
		this.paymentConutsYestday = paymentConutsYestday;
	}
	public Integer getBrowseCountsYestday() {
		return browseCountsYestday;
	}
	public void setBrowseCountsYestday(Integer browseCountsYestday) {
		this.browseCountsYestday = browseCountsYestday;
	}
	public double getAmountMouth() {
		return amountMouth;
	}
	public void setAmountMouth(double amountMouth) {
		this.amountMouth = amountMouth;
	}
	public Integer getOrderConutsMouth() {
		return orderConutsMouth;
	}
	public void setOrderConutsMouth(Integer orderConutsMouth) {
		this.orderConutsMouth = orderConutsMouth;
	}
	public Integer getPaymentConutsMouth() {
		return paymentConutsMouth;
	}
	public void setPaymentConutsMouth(Integer paymentConutsMouth) {
		this.paymentConutsMouth = paymentConutsMouth;
	}
	public Integer getBrowseCountsMouth() {
		return browseCountsMouth;
	}
	public void setBrowseCountsMouth(Integer browseCountsMouth) {
		this.browseCountsMouth = browseCountsMouth;
	}
	public Integer getMembersMouth() {
		return membersMouth;
	}
	public void setMembersMouth(Integer membersMouth) {
		this.membersMouth = membersMouth;
	}
	public Integer getOrderConutsLastMouth() {
		return orderConutsLastMouth;
	}
	public void setOrderConutsLastMouth(Integer orderConutsLastMouth) {
		this.orderConutsLastMouth = orderConutsLastMouth;
	}
	public Integer getPaymentConutsLastMouth() {
		return paymentConutsLastMouth;
	}
	public void setPaymentConutsLastMouth(Integer paymentConutsLastMouth) {
		this.paymentConutsLastMouth = paymentConutsLastMouth;
	}
	public Integer getBrowseCountsLastMouth() {
		return browseCountsLastMouth;
	}
	public void setBrowseCountsLastMouth(Integer browseCountsLastMouth) {
		this.browseCountsLastMouth = browseCountsLastMouth;
	}
	public Integer getOrderCountsAll() {
		return orderCountsAll;
	}
	public void setOrderCountsAll(Integer orderCountsAll) {
		this.orderCountsAll = orderCountsAll;
	}
	public Integer getAmountAll() {
		return amountAll;
	}
	public void setAmountAll(Integer amountAll) {
		this.amountAll = amountAll;
	}
	public Integer getBrowseCountsAll() {
		return browseCountsAll;
	}
	public void setBrowseCountsAll(Integer browseCountsAll) {
		this.browseCountsAll = browseCountsAll;
	}
	public Integer getMembersAll() {
		return membersAll;
	}
	public void setMembersAll(Integer membersAll) {
		this.membersAll = membersAll;
	}
	public Integer getGoodId() {
		return goodId;
	}
	public void setGoodId(Integer goodId) {
		this.goodId = goodId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getSalesCount() {
		return salesCount;
	}
	public void setSalesCount(Integer salesCount) {
		this.salesCount = salesCount;
	}
	public Integer getSalesCountAll() {
		return salesCountAll;
	}
	public void setSalesCountAll(Integer salesCountAll) {
		this.salesCountAll = salesCountAll;
	}
	public List<HomePageInfo> getGoodsInfo() {
		return goodsInfo;
	}
	public void setGoodsInfo(List<HomePageInfo> goodsInfo) {
		this.goodsInfo = goodsInfo;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public Integer getOrderTypeCounts() {
		return orderTypeCounts;
	}
	public void setOrderTypeCounts(Integer orderTypeCounts) {
		this.orderTypeCounts = orderTypeCounts;
	}
	public String[] getMouth() {
		return mouth;
	}
	public void setMouth(String[] mouth) {
		this.mouth = mouth;
	}
	public Integer[] getSalesCountMonth() {
		return salesCountMonth;
	}
	public void setSalesCountMonth(Integer[] salesCountMonth) {
		this.salesCountMonth = salesCountMonth;
	}
	public String[] getOrderTypeStr() {
		return orderTypeStr;
	}
	public void setOrderTypeStr(String[] orderTypeStr) {
		this.orderTypeStr = orderTypeStr;
	}
	public Integer[] getOrderTypeCountsStr() {
		return orderTypeCountsStr;
	}
	public void setOrderTypeCountsStr(Integer[] orderTypeCountsStr) {
		this.orderTypeCountsStr = orderTypeCountsStr;
	}
	public Integer[] getYear() {
		return year;
	}
	public void setYear(Integer[] year) {
		this.year = year;
	}
	public Integer[] getBrowseCountForYeay() {
		return browseCountForYeay;
	}
	public void setBrowseCountForYeay(Integer[] browseCountForYeay) {
		this.browseCountForYeay = browseCountForYeay;
	}
	public JSONArray getTypeJson() {
		return typeJson;
	}
	public void setTypeJson(JSONArray typeJson) {
		this.typeJson = typeJson;
	}
	public LocalDateTime getCreateTime() {
		return createTime;
	}
	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
