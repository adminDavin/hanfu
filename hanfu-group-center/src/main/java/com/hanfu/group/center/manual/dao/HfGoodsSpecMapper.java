package com.hanfu.group.center.manual.dao;




import com.hanfu.group.center.manual.model.HfGoodsSpec;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HfGoodsSpecMapper {
    List<HfGoodsSpec> selectByPrimaryKey(Integer goodsId);

}