package com.hanfu.seckill.center.dao;


import com.hanfu.seckill.center.model.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ProductMapper {
    List<Product> selectByPrimaryKey(Integer id);
}
