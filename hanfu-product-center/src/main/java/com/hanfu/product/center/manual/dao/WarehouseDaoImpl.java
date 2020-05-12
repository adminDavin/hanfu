package com.hanfu.product.center.manual.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanfu.product.center.manual.model.WarehouseFindConditional;
import com.hanfu.product.center.model.WarehouseRespRecord;

@Repository
public class WarehouseDaoImpl implements WarehouseDao{
	
	@Autowired
    SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public List<WarehouseRespRecord> findWarehouseRespRecord(WarehouseFindConditional wfc) {
		List<WarehouseRespRecord> result = sqlSessionTemplate.selectList("findWarehouseRespRecord", wfc);
		return result;
	}
}
