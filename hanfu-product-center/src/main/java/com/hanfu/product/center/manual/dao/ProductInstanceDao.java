package com.hanfu.product.center.manual.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanfu.product.center.model.ProductInstance;
import com.hanfu.product.center.request.ProductInfoRequest;
import com.hanfu.product.center.request.ProductInstanceRequest;

@Repository
public interface ProductInstanceDao {
	
	public Integer deleteProductInstance(ProductInstance example);
	
	public ProductInstance selectProductInstance(ProductInstanceRequest productInstanceRequest);
	
}
