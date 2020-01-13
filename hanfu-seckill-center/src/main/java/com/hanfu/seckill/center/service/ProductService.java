package com.hanfu.seckill.center.service;


import com.hanfu.seckill.center.model.Product;

import java.util.List;


public interface ProductService {
    List<Product> selectByPrimaryKey(Integer id);
}
