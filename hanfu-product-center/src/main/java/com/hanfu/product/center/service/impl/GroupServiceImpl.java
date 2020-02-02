package com.hanfu.product.center.service.impl;


import com.hanfu.product.center.dao.GroupMapper;
import com.hanfu.product.center.model.Group;
import com.hanfu.product.center.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
class GroupServiceImpl implements GroupService {
    @Autowired
    GroupMapper groupMapper;
    @Override
    public void deleteByPrimaryKey(Integer id) {
        groupMapper.deleteByPrimaryKey(id);
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
    public List<Group> seleteId(Integer groupId) {
        return groupMapper.seleteId(groupId);
    }

    @Override
    public List<Group> seleteGroup(Integer bossId) {
        return groupMapper.seleteGroup(bossId);
    }

    //    根据id查一个
    @Override
    public Group selectByPrimaryKey(Integer id) {
        return groupMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKey(Integer groupId, Integer bossId, Integer goodsId, Double price, Integer number, Date startTime, Date stopTime, Integer repertory) {
     groupMapper.updateByPrimaryKey( groupId,  bossId,  goodsId,  price,  number,  startTime,  stopTime,  repertory);
    }


    @Override
    public void updateRrepertory(Integer id, Integer repertory) {
        groupMapper.updateRrepertory(id,repertory);
    }

    @Override
    public void updateState(Integer id) {
        groupMapper.updateState(id);
    }

    @Override
    public List<Group> seleteAll(Integer bossId) {
        return groupMapper.seleteAll(bossId);
    }

    @Override
    public List<Group> selectCategory(Integer id) {
        return groupMapper.selectCategory( id);
    }

    @Override
    public  Group selectDate(Integer id) {
        return groupMapper.selectDate(id);
    }

    @Override
    public List<String> selectCategoryName() {
        return  groupMapper.selectCategoryName();
    }

    @Override
    public void updateIsDeleted(Integer isDeleted, Integer id) {
        groupMapper.updateIsDeleted(isDeleted,id);
    }
}
