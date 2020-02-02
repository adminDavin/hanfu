package com.hanfu.product.center.service;

import java.util.List;


import com.hanfu.product.center.model.Product;
import com.hanfu.product.center.model.SpecS;

public interface SpecsService {
	List<SpecS> selectSpecs();
	List<Product> selectByPrimaryKey(Integer id);
}
