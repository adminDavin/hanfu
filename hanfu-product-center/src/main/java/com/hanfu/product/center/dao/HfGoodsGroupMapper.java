package com.hanfu.product.center.dao;





import com.hanfu.product.center.model.HfGoods;
import org.mapstruct.Mapper;

import java.util.List;


public interface HfGoodsGroupMapper {

    List<HfGoods> selectByPrimaryKey(Integer id);
    HfGoods selectById(Integer id);
    List<HfGoods> selectByName(String name);

    List<HfGoods> selectAll();

    int updateByPrimaryKeySelective(HfGoods record);

    int updateByPrimaryKey(HfGoods record);
    Integer selectByPrice(Integer id);
}