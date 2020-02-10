package com.hanfu.payment.center.manual.model;

import java.time.LocalDateTime;

public class Conupons {
   private String hfStatus;
   private String hfName;
   private String hfDesc;
   private String couponsInfo;
   private LocalDateTime effectiveDate;
   private LocalDateTime expireDate;

    public String getHfStatus() {
        return hfStatus;
    }

    public void setHfStatus(String hfStatus) {
        this.hfStatus = hfStatus;
    }

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

    public String getCouponsInfo() {
        return couponsInfo;
    }

    public void setCouponsInfo(String couponsInfo) {
        this.couponsInfo = couponsInfo;
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
}
