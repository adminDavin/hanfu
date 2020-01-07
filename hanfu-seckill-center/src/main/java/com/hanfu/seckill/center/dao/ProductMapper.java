package com.hanfu.seckill.center.dao;


import com.hanfu.seckill.center.model.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author:gyj
 * @date: 2020/1/7
 * @time: 12:51
 */
@Mapper
public interface ProductMapper {
    List<Product> selectByPrimaryKey(Integer id);
}
