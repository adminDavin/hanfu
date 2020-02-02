package com.hanfu.product.center.service.impl;

import java.util.List;


import com.hanfu.product.center.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hanfu.product.center.dao.SpecsMapper;
import com.hanfu.product.center.model.SpecS;
import com.hanfu.product.center.service.SpecsService;
@Service
public class SpecsServiceImpl implements SpecsService{
	@Autowired
	private SpecsMapper specsMapper;
	@Override
    public List<SpecS> selectSpecs() {
        return specsMapper.selectSpecs();
    }

    @Override
    public List<Product> selectByPrimaryKey(Integer id) {
        return null;
    }
}
