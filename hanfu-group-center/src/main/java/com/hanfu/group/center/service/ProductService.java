package com.hanfu.group.center.service;

import com.hanfu.group.center.manual.model.Product;

import java.util.List;


public interface ProductService {
    List<Product> selectByPrimaryKey(Integer id);
}
