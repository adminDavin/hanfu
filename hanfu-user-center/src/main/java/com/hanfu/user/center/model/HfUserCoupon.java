package com.hanfu.user.center.model;

import java.time.LocalDateTime;

/**
 * @ClassName HfUserCoupon
 * @Date 2020/1/14 19:05
 * @Author CONSAK
 **/
public class HfUserCoupon {
    private int id;
    private int user_id;
    private int coupon_id;
    private LocalDateTime create_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public LocalDateTime getCreate_time() {
        return create_time;
    }

    public void setCreate_time(LocalDateTime create_time) {
        this.create_time = create_time;
    }
}