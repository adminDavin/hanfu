package com.hanfu.product.center.service;

import com.hanfu.product.center.model.Group;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface GroupService {
    void deleteByPrimaryKey(Integer id);

    void insert(Integer bossId, Integer goodsId, Double price, Integer number, Date startTime, Date stopTime, Integer repertory);

    int insertSelective(Group record);

    List<Group> seleteId(Integer groupId);

    List<Group> seleteGroup(Integer bossId);
    Group selectByPrimaryKey(Integer id);


    void updateByPrimaryKey(Integer groupId, Integer bossId, Integer goodsId, Double price, Integer number, Date startTime, Date stopTime, Integer repertory);

    void updateRrepertory(Integer id, Integer repertory);
//    更改状态
    void updateState(Integer id);
    List<Group> seleteAll(Integer bossId);
    List<Group>selectCategory(Integer id);

     Group selectDate(Integer id);

    List<String>selectCategoryName();
    void  updateIsDeleted(Integer isDeleted, Integer id);
}
