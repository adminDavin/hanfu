package com.hanfu.group.center.service;

import com.hanfu.group.center.manual.model.Group;

import java.text.ParseException;
import java.util.List;


public interface HfOrdersService {
    List<Object> insert(Group group, Integer userId,Integer groupOpenId) throws ParseException;
    boolean seckillByPay(Integer id);
}
