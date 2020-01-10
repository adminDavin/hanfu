package com.hanfu.user.center.model;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
/**
 * 皓月千里
 *
 * @param
 * @return
 */
public class HfUserAddress {
    @KeySql(useGeneratedKeys = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private Integer userId;

    private Integer isFaultAddress;

    private String hfProvince;

    private String hfCity;

    private String hfConty;

    private String hfAddressDetail;

    private String contact;

    private String phoneNumber;

    private String hfDesc;

    private LocalDateTime createTime;

    private LocalDateTime modifyTime;

    private String lastModifier;

    private Short isDeleted;

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

    public Integer getIsFaultAddress() {
        return isFaultAddress;
    }

    public void setIsFaultAddress(Integer isFaultAddress) {
        this.isFaultAddress = isFaultAddress;
    }

    public String getHfProvince() {
        return hfProvince;
    }

    public void setHfProvince(String hfProvince) {
        this.hfProvince = hfProvince == null ? null : hfProvince.trim();
    }

    public String getHfCity() {
        return hfCity;
    }

    public void setHfCity(String hfCity) {
        this.hfCity = hfCity == null ? null : hfCity.trim();
    }

    public String getHfConty() {
        return hfConty;
    }

    public void setHfConty(String hfConty) {
        this.hfConty = hfConty == null ? null : hfConty.trim();
    }

    public String getHfAddressDetail() {
        return hfAddressDetail;
    }

    public void setHfAddressDetail(String hfAddressDetail) {
        this.hfAddressDetail = hfAddressDetail == null ? null : hfAddressDetail.trim();
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public String getHfDesc() {
        return hfDesc;
    }

    public void setHfDesc(String hfDesc) {
        this.hfDesc = hfDesc == null ? null : hfDesc.trim();
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
        this.lastModifier = lastModifier == null ? null : lastModifier.trim();
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }
}