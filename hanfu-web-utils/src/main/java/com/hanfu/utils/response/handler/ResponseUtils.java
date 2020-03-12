package com.hanfu.utils.response.handler;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

public class ResponseUtils {
    public static final String DATA = "data";
    public static final String STATUS = "status";

    public static BodyBuilder getBodyBuilder() {
        BodyBuilder builder = ResponseEntity.status(HttpStatus.OK);
        builder.contentType(MediaType.APPLICATION_JSON);
        return builder;
    }

    public static BodyBuilder getBodyBuilder(HttpStatus status) {
        BodyBuilder builder = ResponseEntity.status(status);
        builder.contentType(MediaType.APPLICATION_JSON);
        return builder;
    }

    public static JSONObject getResponseBody(Object message) throws JSONException {
        BodyBuilder builder = ResponseEntity.status(HttpStatus.OK);
        builder.contentType(MediaType.APPLICATION_JSON);
        JSONObject body = new JSONObject();
        body.put(STATUS, HttpStatus.OK.value());
        body.put(DATA, message);
        return body;
    }
}
