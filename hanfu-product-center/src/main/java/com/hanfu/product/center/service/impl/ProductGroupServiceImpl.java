package com.hanfu.product.center.service.impl;

import com.hanfu.product.center.dao.ProductGroupMapper;
import com.hanfu.product.center.model.Product;
import com.hanfu.product.center.service.ProductGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ProductGroupServiceImpl implements ProductGroupService {
    @Autowired
    ProductGroupMapper productMapper;
    @Override
    @Transactional
    public List<Product> selectByPrimaryKey(Integer id) {
        return productMapper.selectByPrimaryKey(id);
    }
}
