package com.hanfu.order.center.manual.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

@SuppressWarnings("serial")
public class OrderInfo implements Serializable {
    @ApiModelProperty(required = true, value = "id")
    private Integer id;
    @ApiModelProperty(required = true, value = "订单id")
    private Integer ordersId;
    @ApiModelProperty(required = true, value = "订单详情id")
    private Integer orderDetailId;
    @ApiModelProperty(required = true, value = "用户地址id")
    private Integer userAddressId;
    @ApiModelProperty(required = true, value = "物品id")
    private Integer googsId;
    @ApiModelProperty(required = true, value = "物流名称")
    private String logisticsOrderName;
    @ApiModelProperty(required = true, value = "仓库id")
    private Integer respId;
    @ApiModelProperty(required = true, value = "物流订单号")
    private String logisticsOrdersId;
    @ApiModelProperty(required = true, value = "物流公司")
    private String logisticsCompany;
    @ApiModelProperty(required = true, value = "商品名称")
    private String hfName;
    @ApiModelProperty(required = true, value = "支付金额")
    private Integer amount;
    @ApiModelProperty(required = true, value = "商品价格")
    private String purchasePrice;
    @ApiModelProperty(required = true, value = "支付方式")
    private String payMethodName;
    @ApiModelProperty(required = true, value = "支付类型")
    private Integer payMethodType;
    @ApiModelProperty(required = true, value = "订单状态")
    private String orderDetailStatus;
    @ApiModelProperty(required = true, value = "地址")
    private String address;
    @ApiModelProperty(required = true, value = "购买数量")
    private Integer purchaseQuantity;
    @ApiModelProperty(required = true, value = "物流描述")
    private String hfDesc;
    @ApiModelProperty(required = true, value = "添加时间")
    private LocalDateTime createTime;
    @ApiModelProperty(required = true, value = "修改时间")
    private LocalDateTime modifyTime;
    @ApiModelProperty(required = true, value = "最后一次修改人")
    private String lastModifier;
    @ApiModelProperty(required = true, value = "用户id")
    private Integer userId;
    @ApiModelProperty(required = true, value = "订单类型")
    private String orderType;
    @ApiModelProperty(required = true, value = "支付状态")
    private Integer payStatus;
    @ApiModelProperty(required = true, value = "真实姓名")
    private String realName;
    @ApiModelProperty(required = true, value = "商品规格")
    private String goodsDesc;
    @ApiModelProperty(required = true, value = "手机号")
    private String phone;
    @ApiModelProperty(required = true, value = "用户名")
    private String userName;
    @ApiModelProperty(required = true, value = "图片Id")
    private Integer fileId;
    @ApiModelProperty(required = true, value = "运费")
    private Integer fare;
    @ApiModelProperty(required = true, value = "评价")
    private String evaluate;
    @ApiModelProperty(required = true, value = "物品名称")
    private String goodName;
    @ApiModelProperty(required = true, value = "用户昵称")
    private String nickName;
    @ApiParam(required = true, value = "国家")
    private String hfConty;
    @ApiParam(required = true, value = "省")
    private String hfProvince;
    @ApiParam(required = true, value = "城市")
    private String hfCity;
    @ApiParam(required = true, value = "详细地址信息")
    private String hfAddressDetail;
    @ApiParam(required = true, value = "是否为默认地址")
    private Integer isFaultAddress;
//    @ApiModelProperty(required = true, value = "物品规格")
//    private String specValue;
    private List<GoodsSpec> goodsSpecList;
    
//    public String getSpecValue() {
//		return specValue;
//	}
//
//	public void setSpecValue(String specValue) {
//		this.specValue = specValue;
//	}

	public String getGoodName() {
		return goodName;
	}

	public String getHfConty() {
		return hfConty;
	}

	public void setHfConty(String hfConty) {
		this.hfConty = hfConty;
	}

	public String getHfProvince() {
		return hfProvince;
	}

	public void setHfProvince(String hfProvince) {
		this.hfProvince = hfProvince;
	}

	public String getHfCity() {
		return hfCity;
	}

	public void setHfCity(String hfCity) {
		this.hfCity = hfCity;
	}

	public String getHfAddressDetail() {
		return hfAddressDetail;
	}

	public void setHfAddressDetail(String hfAddressDetail) {
		this.hfAddressDetail = hfAddressDetail;
	}

	public Integer getIsFaultAddress() {
		return isFaultAddress;
	}

	public void setIsFaultAddress(Integer isFaultAddress) {
		this.isFaultAddress = isFaultAddress;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public List<GoodsSpec> getGoodsSpecList() {
		return goodsSpecList;
	}

	public void setGoodsSpecList(List<GoodsSpec> goodsSpecList) {
		this.goodsSpecList = goodsSpecList;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public String getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}

	public Integer getFare() {
		return fare;
	}

	public void setFare(Integer fare) {
		this.fare = fare;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Integer ordersId) {
        this.ordersId = ordersId;
    }

    public Integer getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Integer getUserAddressId() {
        return userAddressId;
    }

    public void setUserAddressId(Integer userAddressId) {
        this.userAddressId = userAddressId;
    }

    public Integer getGoogsId() {
        return googsId;
    }

    public void setGoogsId(Integer googsId) {
        this.googsId = googsId;
    }

    public String getLogisticsOrderName() {
        return logisticsOrderName;
    }

    public void setLogisticsOrderName(String logisticsOrderName) {
        this.logisticsOrderName = logisticsOrderName;
    }

    public Integer getRespId() {
        return respId;
    }

    public void setRespId(Integer respId) {
        this.respId = respId;
    }

    public String getLogisticsOrdersId() {
        return logisticsOrdersId;
    }

    public void setLogisticsOrdersId(String logisticsOrdersId) {
        this.logisticsOrdersId = logisticsOrdersId;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public String getHfName() {
        return hfName;
    }

    public void setHfName(String hfName) {
        this.hfName = hfName;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getPayMethodName() {
        return payMethodName;
    }

    public void setPayMethodName(String payMethodName) {
        this.payMethodName = payMethodName;
    }

    public Integer getPayMethodType() {
        return payMethodType;
    }

    public void setPayMethodType(Integer payMethodType) {
        this.payMethodType = payMethodType;
    }

    public String getOrderDetailStatus() {
        return orderDetailStatus;
    }

    public void setOrderDetailStatus(String orderDetailStatus) {
        this.orderDetailStatus = orderDetailStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(Integer purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public String getHfDesc() {
        return hfDesc;
    }

    public void setHfDesc(String hfDesc) {
        this.hfDesc = hfDesc;
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

    public String getLastModifier() {
        return lastModifier;
    }

    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier;
    }

}
