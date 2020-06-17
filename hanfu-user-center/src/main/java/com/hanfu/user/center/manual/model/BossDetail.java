package com.hanfu.user.center.manual.model;

import java.time.LocalDateTime;
import java.util.List;

public class BossDetail {
    private Integer bossId;
    private AppletName appletName;
    private OpenAccount openAccount;
    private String expireTime;
    private Statistics statistics;
    private Short isPerpetual;

    public Integer getBossId() {
        return bossId;
    }

    public void setBossId(Integer bossId) {
        this.bossId = bossId;
    }

    public AppletName getAppletName() {
        return appletName;
    }

    public void setAppletName(AppletName appletName) {
        this.appletName = appletName;
    }

    public OpenAccount getOpenAccount() {
        return openAccount;
    }

    public void setOpenAccount(OpenAccount openAccount) {
        this.openAccount = openAccount;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public Short getIsPerpetual() {
        return isPerpetual;
    }

    public void setIsPerpetual(Short isPerpetual) {
        this.isPerpetual = isPerpetual;
    }
}
