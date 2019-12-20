package com.hanfu.seckill.center.dao;

import com.hanfu.seckill.center.model.Seckill;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.Date;
import java.util.List;

/**
 * @author:gyj
 * @date: 2019/12/11
 * @time: 10:01
 */
@Mapper
public interface SeckillDao {
    Integer getRepertory(@Param("goodsId") Integer goodsId, @Param("bossId") Integer bossId);
    void updateRepertory(@Param("goodsId") Integer goodsId, @Param("bossId") Integer bossId, @Param("repertory") Integer repertory);
    void insertSeckill(@Param("bossId") Integer bossId, @Param("goodsId") Integer goodsId, @Param("startTime") Date startTime,@Param("stopTime") Date stopTime,   @Param("categoryId") Integer categoryId, @Param("price") Double price,
                       @Param("repertory") Integer repertory, @Param("isDeleted") Short isDeleted);
    void updateIsDeleted(@Param("goodsId") Integer goodsId, @Param("bossId") Integer bossId, @Param("isDeleted") Short isDeleted);
    void deleteByPrimaryKey(Integer id);
    void updateByPrimaryKey(@Param("id") Integer id,@Param("bossId") Integer bossId, @Param("goodsId") Integer goodsId, @Param("startTime") Date startTime,@Param("stopTime") Date stopTime, @Param("categoryId") Integer categoryId, @Param("price") Double price,
                            @Param("repertory") Integer repertory);
    List<Seckill> selectSeckill(Integer bossId);
    List<Seckill> selectGoodsId(Integer goodsId);
     void updateState(Integer id);
    Seckill selectId(Integer id);
    List<Seckill> selectAll(Integer bossId);
}
