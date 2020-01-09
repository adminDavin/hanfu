package com.hanfu.seckill.center.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author:gyj
 * @date: 2020/1/9
 * @time: 14:58
 */
public class TodayDate implements Serializable {

    private Long time;


    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
