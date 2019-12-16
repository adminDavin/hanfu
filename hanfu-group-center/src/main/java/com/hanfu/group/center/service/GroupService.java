package com.hanfu.group.center.service;


import com.hanfu.group.center.manual.model.Group;

import java.util.Date;

/**
 * @author:gyj
 * @date: 2019/12/13
 * @time: 10:57
 */
public interface GroupService {
    int deleteByPrimaryKey(Integer id);

    void insert(Integer bossId, Integer goodsId,Double price,Integer number,Date startTime, Date stopTime,Integer repertory);

    int insertSelective(Group record);

    Group selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Group record);

    int updateByPrimaryKey(Group record);
    void updateRrepertory(Integer id,Integer repertory);
}
