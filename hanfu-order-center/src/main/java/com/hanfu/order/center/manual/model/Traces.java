package com.hanfu.order.center.manual.model;

import java.time.LocalDateTime;

public class Traces {
    private String AcceptStation;
    private LocalDateTime AcceptTime;

    public String getAcceptStation() {
        return AcceptStation;
    }

    public void setAcceptStation(String acceptStation) {
        AcceptStation = acceptStation;
    }

    public LocalDateTime getAcceptTime() {
        return AcceptTime;
    }

    public void setAcceptTime(LocalDateTime acceptTime) {
        AcceptTime = acceptTime;
    }
}
