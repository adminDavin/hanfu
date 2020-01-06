package com.hanfu.seckill.center.service;

import com.hanfu.seckill.center.model.Seckill;

import java.text.ParseException;
import java.util.List;

/**
 * @author:gyj
 * @date: 2019/12/30
 * @time: 16:37
 */
public interface HfOrdersService {
    List<Object> insert (Seckill seckill, Integer userId) throws ParseException;
    boolean seckillByPay(Integer id);
}
