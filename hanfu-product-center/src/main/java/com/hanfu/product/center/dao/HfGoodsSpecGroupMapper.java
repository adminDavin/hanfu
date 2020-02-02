package com.hanfu.product.center.dao;




import com.hanfu.product.center.model.HfGoodsSpec;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


public interface HfGoodsSpecGroupMapper {
    List<HfGoodsSpec> selectByPrimaryKey(Integer goodsId);

}