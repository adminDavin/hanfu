package com.hanfu.group.center.service;

import com.hanfu.group.center.manual.model.Group;

import java.text.ParseException;
import java.util.List;

/**
 * @author:gyj
 * @date: 2019/12/30
 * @time: 16:37
 */
public interface HfOrdersService {
    List<Object> insert(Group group, Integer userId) throws ParseException;
    boolean seckillByPay(Integer id);
}
