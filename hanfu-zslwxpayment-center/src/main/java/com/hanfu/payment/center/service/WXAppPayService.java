package com.hanfu.payment.center.service;

import java.util.Map;

public interface WXAppPayService {
    public Map<String, String> dounifiedOrder(String type, String out_trade_no, String money) throws Exception;
    public String payBack(String notifyData);
}
