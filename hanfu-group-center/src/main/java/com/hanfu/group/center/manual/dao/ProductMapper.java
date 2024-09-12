package com.hanfu.group.center.manual.dao;

import com.hanfu.group.center.manual.model.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> selectByPrimaryKey(Integer id);
}
