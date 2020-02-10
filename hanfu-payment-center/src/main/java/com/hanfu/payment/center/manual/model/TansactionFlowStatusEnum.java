package com.hanfu.payment.center.manual.model;


public enum TansactionFlowStatusEnum {
    PROCESS("process"),
    COMPLETE("complete");
    String status;
    
    TansactionFlowStatusEnum(String status) {
        this.status = status;
    }
    
    public static TansactionFlowStatusEnum getTansactionFlowStatusEnum(String takingType) {
        for(TansactionFlowStatusEnum item : TansactionFlowStatusEnum.values()) {
            if (item.status.equals(takingType)) {
                return item;
            }
        }
        return COMPLETE;
    }
    
    public String getStatus() {
        return this.status;
    }
}
