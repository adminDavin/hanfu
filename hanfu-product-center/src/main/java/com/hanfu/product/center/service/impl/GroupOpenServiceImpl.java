package com.hanfu.product.center.service.impl;



import com.hanfu.product.center.dao.GroupOpenMapper;
import com.hanfu.product.center.model.GroupOpen;
import com.hanfu.product.center.service.GroupOpenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class GroupOpenServiceImpl implements GroupOpenService {
    @Autowired
    GroupOpenMapper groupOpen;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return groupOpen.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteByGroupId(Integer groupId) {
        return 0;
    }

    @Override
    public List<Integer> selectId(Integer groupId) {
        return groupOpen.selectId( groupId);
    }

    @Override
    public void insert(Integer groupId, Date startTime, Date stopTime) {
        groupOpen.insert( groupId,startTime, stopTime);

    }

    @Override
    public List<Date> getStopTime() {
       return groupOpen.getStopTime();
    }

    @Override
    public GroupOpen selectStopTime(Date stopTime) {
        return groupOpen.selectStopTime(stopTime);
    }

    @Override
    public int insertSelective(GroupOpenMapper groupOpen) {
        groupOpen.insertSelective(groupOpen);
        return 0;
    }

    @Override
    public GroupOpen selectByPrimaryKey(Integer id) {
        return groupOpen.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(GroupOpenMapper groupOpen) {
        return groupOpen.updateByPrimaryKeySelective(groupOpen);
    }

    @Override
    public int updateByPrimaryKey(GroupOpenMapper groupOpen) {
        return groupOpen.updateByPrimaryKey(groupOpen);
    }

    @Override
    public int selectNumber(Integer groupId) {
        return groupOpen.selectNumber(groupId);
    }

    @Override
    public List<Integer> selectUserId(Integer groupId) {
        return groupOpen.selectUserId(groupId);
    }

    @Override
    public GroupOpen selectById(Integer id) {
        return groupOpen.selectById(id);
    }

    @Override
    public  List<GroupOpen> selectByGroupOpen(Integer groupId) {
        return groupOpen.selectByGroupOpen(groupId);
    }

    @Override
    public GroupOpen selectByStopTime(Integer groupId,Date stopTime) {
        return  groupOpen.selectByStopTime( groupId,stopTime);
    }

    @Override
    public void updateByIsDeleted(Integer id) {
        groupOpen.updateByIsDeleted(id);
    }

    @Override
    public List<Integer> selectByGroupOpenId(Integer groupId) {
        return groupOpen.selectByGroupOpenId(groupId);
    }

    @Override
    public GroupOpen selectByGroup(Integer groupId, Integer userId) {
        return groupOpen.selectByGroup(groupId,userId);
    }

    @Override
    public List<Integer> selectByUserId(Integer groupId) {
        return groupOpen.selectByUserId(groupId);
    }

    @Override
    public List<Integer> selectAllUserId(Integer groupId) {
        return groupOpen.selectAllUserId(groupId);
    }
}
