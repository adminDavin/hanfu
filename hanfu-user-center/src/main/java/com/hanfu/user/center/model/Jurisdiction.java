package com.hanfu.user.center.model;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
/**
 * 皓月千里
 *
 * @param
 * @return
 */
public class Jurisdiction {
    @KeySql(useGeneratedKeys = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String jurisdictionName;

    private String jurisdictionModule;

    private String parentInventoryId;

    private Short inventoryStatus;

    private Integer inventoryRankId;

    private String accessCode;

    private Date createTime;

    private Date modifyTime;

    private Short isDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJurisdictionName() {
        return jurisdictionName;
    }

    public void setJurisdictionName(String jurisdictionName) {
        this.jurisdictionName = jurisdictionName == null ? null : jurisdictionName.trim();
    }

    public String getJurisdictionModule() {
        return jurisdictionModule;
    }

    public void setJurisdictionModule(String jurisdictionModule) {
        this.jurisdictionModule = jurisdictionModule == null ? null : jurisdictionModule.trim();
    }

    public String getParentInventoryId() {
        return parentInventoryId;
    }

    public void setParentInventoryId(String parentInventoryId) {
        this.parentInventoryId = parentInventoryId == null ? null : parentInventoryId.trim();
    }

    public Short getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(Short inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public Integer getInventoryRankId() {
        return inventoryRankId;
    }

    public void setInventoryRankId(Integer inventoryRankId) {
        this.inventoryRankId = inventoryRankId;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode == null ? null : accessCode.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }
}