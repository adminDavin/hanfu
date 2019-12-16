package com.hanfu.group.center.service.impl;


import com.hanfu.group.center.manual.dao.GroupMapper;
import com.hanfu.group.center.manual.model.Group;
import com.hanfu.group.center.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * @author:gyj
 * @date: 2019/12/13
 * @time: 10:58
 */
@Service
class GroupServiceImpl implements GroupService {
    @Autowired
    GroupMapper groupMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public void insert(Integer bossId, Integer goodsId,Double price,Integer number,Date startTime, Date stopTime,Integer repertory) {
        groupMapper.insert(  bossId,  goodsId, price, number, startTime,  stopTime,repertory);
    }

    @Override
    public int insertSelective(Group record) {
        return 0;
    }

    @Override
    public Group selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(Group record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Group record) {
        return 0;
    }

    @Override
    public void updateRrepertory(Integer id, Integer repertory) {
        groupMapper.updateRrepertory(id,repertory);
    }
}
