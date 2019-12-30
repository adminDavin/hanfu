package com.hanfu.payment.center.service;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.utils.response.handler.ResponseEntity;

public interface PayReturnService {

    ResponseEntity<JSONObject> refund(String out_trade_no, String transaction_id, int total_fee) throws Exception;

}