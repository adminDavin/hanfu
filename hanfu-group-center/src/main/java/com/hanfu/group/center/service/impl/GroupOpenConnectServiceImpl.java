package com.hanfu.group.center.service.impl;

import com.hanfu.group.center.manual.dao.GroupOpenConnectMapper;
import com.hanfu.group.center.manual.model.GroupOpenConnect;
import com.hanfu.group.center.service.GroupOpenConnectService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author:gyj
 * @date: 2019/12/13
 * @time: 11:04
 */
@Service
@Transactional
public class GroupOpenConnectServiceImpl implements GroupOpenConnectService {
    @Autowired
    GroupOpenConnectMapper groupOpenConnectMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return groupOpenConnectMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByGroupOpenId(Integer id) {
        groupOpenConnectMapper.deleteByGroupOpenId(id);
    }

    @Override
    public void insert(Integer userId,Integer groupOpenId,Integer ordersId) {
        groupOpenConnectMapper.insert(userId,groupOpenId, ordersId);
    }

    @Override
    public int insertSelective(GroupOpenConnect groupOpenConnect) {
        return 0;
    }

    @Override
    public GroupOpenConnect selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(GroupOpenConnect groupOpenConnect) {
        return 0;
    }

    @Override
    public void updateIsDeleted(Integer userId, Integer groupOpenId) {
        groupOpenConnectMapper.updateisDeleted(userId,groupOpenId);
    }

    @Override
    public void updateState(Integer userId, Integer groupOpenId) {
        groupOpenConnectMapper.updateState(userId,groupOpenId);
    }


}
