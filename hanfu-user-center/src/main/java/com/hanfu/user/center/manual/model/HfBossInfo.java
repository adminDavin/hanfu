package com.hanfu.user.center.manual.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanfu.user.center.model.HfStone;

import io.swagger.annotations.ApiParam;

@SuppressWarnings("serial")
public class HfBossInfo implements Serializable {
	
	@ApiParam(required = false, value = "总金额")
	private double amount;
	@ApiParam(required = false, value = "总金额")
	private String bossName;
	@ApiParam(required = false, value = "注册资本")
	private Integer registeredCapital;
	@ApiParam(required = false, value = "经营范围")
	private String businessScope;
	@ApiParam(required = false, value = "联系电话")
	private String phone;
	@ApiParam(required = false, value = "店铺")
	private List<HfStone> stones;
	@ApiParam(required = false, value = "总浏览数")
	private double browseCounts;
	@ApiParam(required = false, value = "有效时间")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT")
	private LocalDateTime expireDate;
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getBossName() {
		return bossName;
	}
	public void setBossName(String bossName) {
		this.bossName = bossName;
	}
	public Integer getRegisteredCapital() {
		return registeredCapital;
	}
	public void setRegisteredCapital(Integer registeredCapital) {
		this.registeredCapital = registeredCapital;
	}
	public String getBusinessScope() {
		return businessScope;
	}
	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public List<HfStone> getStones() {
		return stones;
	}
	public void setStones(List<HfStone> stones) {
		this.stones = stones;
	}
	public double getBrowseCounts() {
		return browseCounts;
	}
	public void setBrowseCounts(double browseCounts) {
		this.browseCounts = browseCounts;
	}
	public LocalDateTime getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(LocalDateTime expireDate) {
		this.expireDate = expireDate;
	}
}
