package com.hanfu.payment.center.manual.model;

import java.time.LocalDateTime;

public class Privilege {
   private String hfName;
   private String hfDesc;
   private String hfStatus;
   private LocalDateTime effectiveDate;
   private LocalDateTime expireDate;
   private LocalDateTime privilegeInfo;

    public String getHfName() {
        return hfName;
    }

    public void setHfName(String hfName) {
        this.hfName = hfName;
    }

    public String getHfDesc() {
        return hfDesc;
    }

    public void setHfDesc(String hfDesc) {
        this.hfDesc = hfDesc;
    }

    public String getHfStatus() {
        return hfStatus;
    }

    public void setHfStatus(String hfStatus) {
        this.hfStatus = hfStatus;
    }

    public LocalDateTime getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    public LocalDateTime getPrivilegeInfo() {
        return privilegeInfo;
    }

    public void setPrivilegeInfo(LocalDateTime privilegeInfo) {
        this.privilegeInfo = privilegeInfo;
    }
}
