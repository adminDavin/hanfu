package com.hanfu.seckill.center.service;

import com.hanfu.seckill.center.model.Seckill;

import java.text.ParseException;
import java.util.List;


public interface HfOrdersService {
    List<Object> insert (Seckill seckill, Integer userId,String desc,Integer addressId) throws ParseException;
    boolean seckillByPay(Integer id);
}
