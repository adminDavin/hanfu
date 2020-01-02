package com.hanfu.message.center.service;

import org.springframework.boot.configurationprocessor.json.JSONException;

public interface WxTemplateService {

    void sendTemplateToUsers(String message01, String message02) throws JSONException;

}
