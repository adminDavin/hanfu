package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.GroupOpenConnect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

public interface GroupOpenConnectMapper {
    int deleteByPrimaryKey(Integer id);
    void deleteByGroupOpenId(Integer id);

    int insert(@Param("userId") Integer userId, @Param("groupOpenId") Integer groupOpenId, @Param("ordersId") Integer ordersId,
               @Param("hfDesc") String hfDesc, @Param("addressId") Integer addressId);

    int insertSelective(GroupOpenConnect record);

    GroupOpenConnect selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupOpenConnect record);

    void updateisDeleted(@Param("userId") Integer userId, @Param("groupOpenId") Integer groupOpenId);
    void updateState(@Param("userId") Integer userId, @Param("groupOpenId") Integer groupOpenId);
    GroupOpenConnect selectByGroup(@Param("id") Integer id, @Param("groupOpenId") Integer groupOpenId);

}
