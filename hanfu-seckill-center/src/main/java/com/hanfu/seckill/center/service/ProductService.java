package com.hanfu.seckill.center.service;


import com.hanfu.seckill.center.model.Product;

import java.util.List;

/**
 * @author:gyj
 * @date: 2020/1/7
 * @time: 12:55
 */
public interface ProductService {
    List<Product> selectByPrimaryKey(Integer id);
}
