package com.hanfu.payment.center.model;

import io.swagger.models.auth.In;

import java.time.LocalDateTime;

public class QR {
    private String qrCodeType;
    private String userId;
    private String money;
    private Integer year;
    private Integer month;
    private Integer day;
    private Integer hour;
    private Integer minute;
    private Integer second;
    private String qrCode;
    private Integer qrCodeId;

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

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }


    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Integer getQrCodeId() {
        return qrCodeId;
    }

    public void setQrCodeId(Integer qrCodeId) {
        this.qrCodeId = qrCodeId;
    }
}
