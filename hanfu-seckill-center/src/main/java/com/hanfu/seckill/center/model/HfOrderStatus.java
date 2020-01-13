package com.hanfu.seckill.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HfOrderStatus implements Serializable {

    private Integer id;


    private String hfName;


    private Integer hfStatus;

    private String hfDesc;


    private LocalDateTime createTime;


    private LocalDateTime modifyTime;

    private Short isDeleted;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public String getHfName() {
        return hfName;
    }

    public void setHfName(String hfName) {
        this.hfName = hfName == null ? null : hfName.trim();
    }


    public Integer getHfStatus() {
        return hfStatus;
    }

    public void setHfStatus(Integer hfStatus) {
        this.hfStatus = hfStatus;
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

    public Short getIsDeleted() {
        return isDeleted;
    }


    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }



}