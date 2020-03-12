package com.hanfu.order.center.response.handler;

@SuppressWarnings("serial")
public class OrderIsExistException extends Exception {
    public OrderIsExistException(String message) {
        super(message);
    }
}