package com.hanfu.user.center.response.handler;

@SuppressWarnings("serial")
public class AuthKeyIsExistException extends Exception {
    public AuthKeyIsExistException(String message) {
        super(message);
    }
}
