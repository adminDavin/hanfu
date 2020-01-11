package com.hanfu.product.center.response.handler;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends com.hanfu.utils.response.handler.GlobalExceptionHandler {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "unknow exception")
    public void exceptionHandler(HttpServletRequest req, Exception e) {
        logger.error("unknow exception:", e);
    }

    @ExceptionHandler(value = LoginException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "login failed")
    public void loginExceptionHandler(HttpServletRequest req, Exception e) {
        logger.error("login failed:", e);
    }

    @ExceptionHandler(value = GoodsNotExistException.class)
    @ResponseStatus(value = HttpStatus.PRECONDITION_FAILED, reason = "goods is invalid")
    public void goodsNotExistExceptionHandler(HttpServletRequest req, Exception e) {
        logger.error("goods is invalid:", e);
    }

    @ExceptionHandler(value = ProductNotExistException.class)
    @ResponseStatus(value = HttpStatus.PRECONDITION_FAILED, reason = "product is invalid")
    public void productNotExistExceptionHandler(HttpServletRequest req, Exception e) {
        logger.error("product is invalid:", e);
    }
}
