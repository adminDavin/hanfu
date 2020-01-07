package com.hanfu.group.center.service;

import com.hanfu.group.center.manual.model.Product;

import java.util.List;

/**
 * @author:gyj
 * @date: 2020/1/7
 * @time: 12:55
 */
public interface ProductService {
    List<Product> selectByPrimaryKey(Integer id);
}
