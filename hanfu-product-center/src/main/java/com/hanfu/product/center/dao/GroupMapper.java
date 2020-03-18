package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.Group;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


public interface GroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(@Param("bossId") Integer bossId, @Param("goodsId") Integer goodsId, @Param("price") Double price, @Param("number")
            Integer number, @Param("startTime") Date startTime, @Param("stopTime") Date stopTime, @Param("repertory") Integer repertory);

    int insertSelective(Group record);

    Group selectByPrimaryKey(Integer id);
    List<Group> seleteId(Integer groupId);
    List<Group> seleteGroup(Integer bossId);

    int updateByPrimaryKeySelective(Group record);

    void updateByPrimaryKey(@Param("groupId") Integer groupId, @Param("bossId") Integer bossId, @Param("goodsId") Integer goodsId, @Param("price")
            Double price, @Param("number") Integer number, @Param("startTime") Date startTime, @Param("stopTime") Date stopTime, @Param("repertory") Integer repertory);

    void updateRrepertory(@Param("id") Integer id, @Param("repertory") Integer repertory);
    void updateState(Integer id);
    List<Group> seleteAll(Integer bossId);

    List<Group>selectCategory(Integer id);
    List<String>selectCategoryName();
    Group selectDate(Integer id);
    void  updateIsDeleted(@Param("isDeleted") Integer isDeleted, @Param("id") Integer id);
}
