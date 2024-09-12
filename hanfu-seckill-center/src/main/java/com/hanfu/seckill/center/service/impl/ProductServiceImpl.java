package com.hanfu.seckill.center.service.impl;


import com.hanfu.seckill.center.dao.ProductMapper;
import com.hanfu.seckill.center.model.Product;
import com.hanfu.seckill.center.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:gyj
 * @date: 2020/1/7
 * @time: 13:01
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Override
    public List<Product> selectByPrimaryKey(Integer id) {
        return productMapper.selectByPrimaryKey(id);
    }
}
