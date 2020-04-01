package com.hanfu.order.center.manual.model;

import java.util.List;

public class Logistics {
    private Object logisticCode;
    private Object shipperCode;
    private Object traces;
    private Object acceptTime;
    private Object state;
    private Object eBusinessID;
    private Object company;

    public Object getLogisticCode() {
        return logisticCode;
    }

    public void setLogisticCode(Object logisticCode) {
        this.logisticCode = logisticCode;
    }

    public Object getShipperCode() {
        return shipperCode;
    }

    public void setShipperCode(Object shipperCode) {
        this.shipperCode = shipperCode;
    }

    public Object getTraces() {
        return traces;
    }

    public void setTraces(Object traces) {
        this.traces = traces;
    }

    public Object getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Object acceptTime) {
        this.acceptTime = acceptTime;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Object geteBusinessID() {
        return eBusinessID;
    }

    public void seteBusinessID(Object eBusinessID) {
        this.eBusinessID = eBusinessID;
    }

    public Object getCompany() {
        return company;
    }

    public void setCompany(Object company) {
        this.company = company;
    }
}