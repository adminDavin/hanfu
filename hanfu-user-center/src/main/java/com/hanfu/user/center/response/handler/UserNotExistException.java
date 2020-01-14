package com.hanfu.user.center.response.handler;

@SuppressWarnings("serial")
public class UserNotExistException extends Exception {
    public UserNotExistException(String message) {
        super(message);
    }

}
