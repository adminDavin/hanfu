package com.hanfu.seckill.center.model;

import java.io.Serializable;

/**
 * @author:gyj
 * @date: 2019/12/31
 * @time: 10:53
 */
public class SeckillConnect implements Serializable {
    private Integer id;
    private  Integer userId;
    private  Integer seckillId;
    private  Integer isDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(Integer seckillId) {
        this.seckillId = seckillId;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "SeckillConnect{" +
                "id=" + id +
                ", userId=" + userId +
                ", seckillId=" + seckillId +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
