package com.hanfu.group.center.manual.dao;




import com.hanfu.group.center.manual.model.HfGoods;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper
public interface HfGoodsMapper {

    List<HfGoods> selectByPrimaryKey(Integer id);

    List<HfGoods> selectByName(String name);

    List<HfGoods> selectAll();

    int updateByPrimaryKeySelective(HfGoods record);

    int updateByPrimaryKey(HfGoods record);
}