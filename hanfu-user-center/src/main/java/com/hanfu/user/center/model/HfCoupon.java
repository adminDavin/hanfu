package com.hanfu.user.center.model;

/**
 * @ClassName HfCoupon
 * @Date 2020/1/14 17:41
 * @Author CONSAK
 **/
public class HfCoupon {
    private int id;
    private int money;
    private int total;
    private String body;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}