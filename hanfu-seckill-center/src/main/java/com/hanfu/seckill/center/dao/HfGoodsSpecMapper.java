package com.hanfu.seckill.center.dao;


import com.hanfu.seckill.center.model.HfGoodsSpec;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HfGoodsSpecMapper {
    List<HfGoodsSpec> selectByPrimaryKey(Integer goodsId);

}