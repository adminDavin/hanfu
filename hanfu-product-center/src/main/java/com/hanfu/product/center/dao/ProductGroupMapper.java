package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


public interface ProductGroupMapper {
    List<Product> selectByPrimaryKey(Integer id);
}
