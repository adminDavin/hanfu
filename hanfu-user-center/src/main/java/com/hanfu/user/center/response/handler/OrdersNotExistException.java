package com.hanfu.user.center.response.handler;

@SuppressWarnings("serial")
public class OrdersNotExistException extends Exception {
    public OrdersNotExistException(String message) {
        super(message);
    }
}
