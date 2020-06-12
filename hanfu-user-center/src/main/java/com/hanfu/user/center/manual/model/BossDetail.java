package com.hanfu.user.center.manual.model;

import java.time.LocalDateTime;
import java.util.List;

public class BossDetail {
    private Integer bossId;
    private AppletName appletName;
    private OpenAccount openAccount;
    private LocalDateTime expireTime;
    private Statistics statistics;

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

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }
}
