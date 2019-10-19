package com.hanfu.product.center.manual.dao;

import java.util.List;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanfu.product.center.manual.model.Categories;
import com.hanfu.product.center.manual.model.HfGoodsDisplay;
import com.hanfu.product.center.manual.model.UserInfo;
import com.hanfu.product.center.model.HfGoods;


@Repository
public class HfGoodsDaoImpl implements HfGoodsDao {

	@Autowired
    SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public List<HfGoodsDisplay> selectAllGoods(Integer stoneId) {
		List<HfGoodsDisplay> result = sqlSessionTemplate.selectList("selectAllGoods", stoneId);
		return result;
	}
	
	@Override
	public List<HfGoods> selectByStoneId(Integer stoneId) {
		List<HfGoods> result = sqlSessionTemplate.selectList("selectByStoneId", stoneId);
		return result;
	}
	
	@Override
	public Integer updateGoods(HfGoods hfGoods) {
		Integer row = sqlSessionTemplate.update("updateGoods", hfGoods);
		return row;
	}
}
