package com.hanfu.user.center.model;

/**
 * @ClassName HfSetBuyMember
 * @Date 2020/1/6 17:41
 * @Author CONSAK
 **/
public class HfSetBuyMember {
    private int id;
    private int money;

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


    @Override
    public String toString() {
        return "HfSetBuyMember{" +
                "id=" + id +
                ", money=" + money +
                '}';
    }
}