package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.SeckillConnect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


public interface SeckillConnectDao {
    SeckillConnect selectByUserId(Integer id);
    void insert(@Param("id") Integer id, @Param("seckillId") Integer seckillId);
    void updateIsDeleted(@Param("userId") Integer userId);
    SeckillConnect selectBySeckillId(@Param("id") Integer id, @Param("seckillId") Integer seckillId);
}
