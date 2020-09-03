package com.hanfu.payment.center.manual.model;

import java.time.LocalDateTime;

public class Withdrawals {
    /**
     * 取款账单id
     */
    private Integer id;
    /**
     * 取款订单号
     */
    private String withdrawalMark;
    /**
     * 实际到账金额
     */
    private Integer realityMoney;
    /**
     * 取款账号
     */
    private String withdrawalAccount;
    /**
     * 取款人姓名
     */
    private String withdrawalName;
    /**
     * 取款状态
     */
    private String withdrawalState;
    /**
     * 取款总金额
     */
    private Integer withdrawalMoney;
    /**
     * 取款创建时间
     */
    private LocalDateTime createDate;
    /**
     * 取款方式名称
     */
    private String withdrawalWayName;
    /**
     * 取款手续费率
     */
    private Integer withdrawalCommission;
    /**
     * 取款手续费类型
     */
    private String commissionType;
    /**
     * 取款手机号
     */
    private String phone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWithdrawalMark() {
        return withdrawalMark;
    }

    public void setWithdrawalMark(String withdrawalMark) {
        this.withdrawalMark = withdrawalMark;
    }

    public Integer getRealityMoney() {
        return realityMoney;
    }

    public void setRealityMoney(Integer realityMoney) {
        this.realityMoney = realityMoney;
    }

    public String getWithdrawalAccount() {
        return withdrawalAccount;
    }

    public void setWithdrawalAccount(String withdrawalAccount) {
        this.withdrawalAccount = withdrawalAccount;
    }

    public String getWithdrawalName() {
        return withdrawalName;
    }

    public void setWithdrawalName(String withdrawalName) {
        this.withdrawalName = withdrawalName;
    }

    public String getWithdrawalState() {
        return withdrawalState;
    }

    public void setWithdrawalState(String withdrawalState) {
        this.withdrawalState = withdrawalState;
    }

    public Integer getWithdrawalMoney() {
        return withdrawalMoney;
    }

    public void setWithdrawalMoney(Integer withdrawalMoney) {
        this.withdrawalMoney = withdrawalMoney;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getWithdrawalWayName() {
        return withdrawalWayName;
    }

    public void setWithdrawalWayName(String withdrawalWayName) {
        this.withdrawalWayName = withdrawalWayName;
    }

    public Integer getWithdrawalCommission() {
        return withdrawalCommission;
    }

    public void setWithdrawalCommission(Integer withdrawalCommission) {
        this.withdrawalCommission = withdrawalCommission;
    }

    public String getCommissionType() {
        return commissionType;
    }

    public void setCommissionType(String commissionType) {
        this.commissionType = commissionType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
