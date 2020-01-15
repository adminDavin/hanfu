package com.hanfu.seckill.center.dao;


import com.hanfu.seckill.center.model.HfGoods;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper
public interface HfGoodsMapper {

    List<HfGoods> selectByPrimaryKey(Integer id);
    HfGoods selectById(Integer id);
    List<HfGoods> selectAll();
    List<HfGoods>selectByName(String name);

    int updateByPrimaryKeySelective(HfGoods record);

    int updateByPrimaryKey(HfGoods record);
}