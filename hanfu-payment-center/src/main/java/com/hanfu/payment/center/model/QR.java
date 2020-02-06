package com.hanfu.payment.center.model;

import io.swagger.models.auth.In;

import java.time.LocalDateTime;

public class QR {
    private String qrCodeType;
    private String userId;
//    private String money;
    private String qrCode;


    public String getQrCodeType() {
        return qrCodeType;
    }

    public void setQrCodeType(String qrCodeType) {
        this.qrCodeType = qrCodeType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

//    public String getMoney() {
//        return money;
//    }
//
//    public void setMoney(String money) {
//        this.money = money;
//    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

}
