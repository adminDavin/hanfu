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
