package com.hanfu.cart.center.utils;

public interface KeyPrefix {
    /**
     * 有效期
     *
     * @return
     */
    public int expireSeconds();

    /**
     * key前缀，防止其他的人使用redis时覆盖
     *
     * @return
     */
    public String getPrefix();
}
