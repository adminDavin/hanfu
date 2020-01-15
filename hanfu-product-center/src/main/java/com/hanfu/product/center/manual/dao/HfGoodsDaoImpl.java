package com.hanfu.product.center.manual.dao;

import java.util.List;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//import com.hanfu.product.center.manual.model.AwardInfo;
import com.hanfu.product.center.manual.model.Categories;
import com.hanfu.product.center.manual.model.CheckResp;
import com.hanfu.product.center.manual.model.HfGoodsDisplay;
import com.hanfu.product.center.manual.model.PriceRanking;
import com.hanfu.product.center.manual.model.ProductForValue;
import com.hanfu.product.center.manual.model.UserInfo;
import com.hanfu.product.center.model.HfGoods;


@Repository
public class HfGoodsDaoImpl implements HfGoodsDao {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<com.hanfu.inner.model.product.center.HfGoodsDisplay> selectAllGoodsInfo() {
        List<com.hanfu.inner.model.product.center.HfGoodsDisplay> result = sqlSessionTemplate.selectList("selectAllGoodsInfo");
        return result;
    }
    
    @Override
    public HfGoods selectFromHfGoods(Integer goodsId) {
    	HfGoods hfGoods = sqlSessionTemplate.selectOne("selectFromHfGoods", goodsId);
    	return hfGoods;
    }
    
    @Override
    public HfGoodsDisplay selectAllGoods(HfGoodsDisplay hfGoodsDisplay) {
    	HfGoodsDisplay result = sqlSessionTemplate.selectOne("selectAllGoods", hfGoodsDisplay);
        return result;
    }

    @Override
    public List<HfGoodsDisplay> selectAllGoodsPartInfo(Integer stoneId) {
        List<HfGoodsDisplay> result = sqlSessionTemplate.selectList("selectAllGoodsPartInfo", stoneId);
        return result;
    }

    @Override
    public List<HfGoods> selectByStoneId(Integer stoneId) {
        List<HfGoods> result = sqlSessionTemplate.selectList("selectByStoneId", stoneId);
        return result;
    }

    @Override
    public List<HfGoodsDisplay> selectGoodsInfo(Integer goodsId) {
    	List<HfGoodsDisplay> hfGoodsDisplay = sqlSessionTemplate.selectList("selectGoodsInfo", goodsId);
        return hfGoodsDisplay;
    }

    @Override
    public HfGoodsDisplay selectGoodsPartInfo(Integer goodsId) {
        HfGoodsDisplay hfGoodsDisplay = sqlSessionTemplate.selectOne("selectGoodsPartInfo", goodsId);
        return hfGoodsDisplay;
    }

    @Override
    public Integer updateGoods(HfGoods hfGoods) {
        Integer row = sqlSessionTemplate.update("updateGoods", hfGoods);
        return row;
    }

    @Override
    public List<HfGoodsDisplay> selectProductBycategoryIdOrProductName(HfGoodsDisplay hfGoodsDisplay) {
        List<HfGoodsDisplay> list = sqlSessionTemplate.selectList("selectProductBycategoryIdOrProductName", hfGoodsDisplay);
        return list;
    }

	@Override
	public List<HfGoodsDisplay> selectPrice(PriceRanking priceRanking) {
		 List<HfGoodsDisplay> list = sqlSessionTemplate.selectList("selectPrice",priceRanking);
		return list;
	}

	@Override
	public List<HfGoodsDisplay> selectPriceDec(PriceRanking priceRanking) {
		 List<HfGoodsDisplay> list = sqlSessionTemplate.selectList("selectPriceDec",priceRanking);
		return list;
	}

	@Override
	public List<HfGoodsDisplay> selectList(ProductForValue productForValue) {
		 List<HfGoodsDisplay> list = sqlSessionTemplate.selectOne("selectList",productForValue);
		return list;
	}

	@Override
	public Long queryGoods() {
		Long count = sqlSessionTemplate.selectOne("queryGoods");
		return count;
	}

	@Override
	public List<HfGoodsDisplay> updateFrames(Integer frames, Integer goodsId) {
		sqlSessionTemplate.update("updateFrames", frames);
		return null;
	}

	@Override
	public List<HfGoodsDisplay> selectQueryList(ProductForValue productForValue) {
		List<HfGoodsDisplay> list = sqlSessionTemplate.selectList("selectQueryList",productForValue);
		return list;
	}

	@Override
	public List<HfGoodsDisplay> selectGoodsSpec(Integer productId) {
		List<HfGoodsDisplay> list = sqlSessionTemplate.selectList("selectGoodsSpec",productId);
		return list;
	}

	@Override
	public HfGoodsDisplay checkResp(CheckResp checkResp) {
		  HfGoodsDisplay hfGoodsDisplay = sqlSessionTemplate.selectOne("checkResp", checkResp);
	        return hfGoodsDisplay;
	}

	@Override
	public List<HfGoodsDisplay> selectSlideshow() {
		List<HfGoodsDisplay> list = sqlSessionTemplate.selectList("selectSlideshow");
		return list;
	}


//	@Override
//	public Integer insertAwardInfo(AwardInfo awardInfo) {
//		Integer row = sqlSessionTemplate.insert("insertAwardInfo", awardInfo);
//		return null;
//	}

}
