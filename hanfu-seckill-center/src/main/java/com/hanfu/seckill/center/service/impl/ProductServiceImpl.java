package com.hanfu.seckill.center.service.impl;


import com.hanfu.seckill.center.dao.ProductMapper;
import com.hanfu.seckill.center.model.Product;
import com.hanfu.seckill.center.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Override
    public List<Product> selectByPrimaryKey(Integer id) {
        return productMapper.selectByPrimaryKey(id);
    }
}
