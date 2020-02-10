package com.hanfu.product.center.service;



import com.hanfu.product.center.model.Product;

import java.util.List;


public interface ProductGroupService {
    List<Product> selectByPrimaryKey(Integer id);
}
