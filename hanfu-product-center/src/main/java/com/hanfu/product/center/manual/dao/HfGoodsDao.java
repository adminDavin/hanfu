package com.hanfu.product.center.manual.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

//import com.hanfu.product.center.manual.model.AwardInfo;
import com.hanfu.product.center.manual.model.Categories;
import com.hanfu.product.center.manual.model.CheckResp;
import com.hanfu.product.center.manual.model.HfGoodsDisplay;
import com.hanfu.product.center.manual.model.PriceRanking;
import com.hanfu.product.center.manual.model.ProductForValue;
import com.hanfu.product.center.manual.model.UserInfo;
import com.hanfu.product.center.model.HfGoods;

public interface HfGoodsDao {

    public List<com.hanfu.inner.model.product.center.HfGoodsDisplay> selectAllGoodsInfo();
    
    public HfGoods selectFromHfGoods(Integer goodsId);

    public HfGoodsDisplay selectAllGoods(HfGoodsDisplay hfGoodsDisplay);

    public List<HfGoodsDisplay> selectAllGoodsPartInfo(Integer stoneId);

    public List<HfGoods> selectByStoneId(Integer stoneId);

    public List<HfGoodsDisplay> selectGoodsInfo(Integer goodsId);

    public HfGoodsDisplay selectGoodsPartInfo(Integer goodsId);

    public Integer updateGoods(HfGoods hfGoods);

    public List<HfGoodsDisplay> selectProductBycategoryIdOrProductName(HfGoodsDisplay hfGoodsDisplay);

	public List<HfGoodsDisplay> selectPrice(PriceRanking priceRanking);

	public List<HfGoodsDisplay> selectPriceDec(PriceRanking priceRanking);

	public List<HfGoodsDisplay> selectList(ProductForValue productForValue);

	public Long queryGoods();

	public List<HfGoodsDisplay> updateFrames(Integer frames, Integer goodsId);

	public List<HfGoodsDisplay> selectQueryList(ProductForValue productForValue);

	public List<HfGoodsDisplay> selectGoodsSpec(Integer productId);

	public HfGoodsDisplay checkResp(CheckResp checkResp);

	public List<HfGoodsDisplay> selectSlideshow();

//	public Integer insertAwardInfo(AwardInfo awardInfo);
}
