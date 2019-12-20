package com.hanfu.seckill.center.dao;

import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.Date;

/**
 * @author:gyj
 * @date: 2019/12/11
 * @time: 10:01
 */
@Mapper
public interface SeckillDao {
    Integer getRepertory(@Param("goodsId") Integer goodsId, @Param("bossId") Integer bossId);
    void updateRepertory(@Param("goodsId") Integer goodsId, @Param("bossId") Integer bossId, @Param("repertory") Integer repertory);
    void insertSeckill(@Param("bossId") Integer bossId, @Param("goodsId") Integer goodsId, @Param("startTime") Date startTime, @Param("categoryId") Integer categoryId, @Param("price") Double price,
                       @Param("repertory") Integer repertory, @Param("isDeleted") Short isDeleted);
    void updateIsDeleted(@Param("goodsId") Integer goodsId, @Param("bossId") Integer bossId, @Param("isDeleted") Short isDeleted);
    void deleteByPrimaryKey(Integer id);
    void updateByPrimaryKey(@Param("id") Integer id,@Param("bossId") Integer bossId, @Param("goodsId") Integer goodsId, @Param("startTime") Date startTime, @Param("categoryId") Integer categoryId, @Param("price") Double price,
                            @Param("repertory") Integer repertory);
}
