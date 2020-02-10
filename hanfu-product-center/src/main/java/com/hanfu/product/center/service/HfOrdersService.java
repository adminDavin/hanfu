package com.hanfu.product.center.service;



import com.hanfu.product.center.model.Group;
import com.hanfu.product.center.model.Seckill;

import java.text.ParseException;
import java.util.List;


public interface HfOrdersService {
    List<Object> insert(Group group, Integer userId, Integer groupOpenId) throws ParseException;
    List<Object> insertSeckill(Seckill seckill, Integer userId, String desc, Integer addressId) throws ParseException;
    boolean seckillByPay(Integer id);
}
