package com.hanfu.group.center.service.impl;

import com.hanfu.group.center.manual.dao.ProductMapper;
import com.hanfu.group.center.manual.model.Product;
import com.hanfu.group.center.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Override
    @Transactional
    public List<Product> selectByPrimaryKey(Integer id) {
        return productMapper.selectByPrimaryKey(id);
    }
}
