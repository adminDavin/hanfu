package com.hanfu.user.center.request;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiParam;

@SuppressWarnings("serial")
public class UserAddressRequest implements Serializable {

    @ApiParam(required = true, value = "id", example = "0")
    private Integer id;
    @ApiParam(required = true, value = "登录的用户id", example = "0")
    private Integer userId;
    @ApiParam(required = true, value = "国家")
    private String hfConty;
    @ApiParam(required = true, value = "省")
    private String hfProvince;
    @ApiParam(required = true, value = "城市")
    private String hfCity;
    @ApiParam(required = true, value = "备注")
    private String hfDesc;
    //	@ApiParam(required = true, value = "创建时间")
//	private LocalDateTime createTime;
    @ApiParam(required = true, value = "详细地址信息")
    private String hfAddressDetail;
    @ApiParam(required = true, value = "是否为默认地址")
    private Integer isFaultAddress;
    @ApiParam(required = true, value = "联系人")
    private String contact;
    @ApiParam(required = true, value = "手机号")
    private String phoneNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getHfDesc() {
        return hfDesc;
    }

    public void setHfDesc(String hfDesc) {
        this.hfDesc = hfDesc;
    }

    //	public LocalDateTime getCreateTime() {
//		return createTime;
//	}
//	public void setCreateTime(LocalDateTime createTime) {
//		this.createTime = createTime;
//	}
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
