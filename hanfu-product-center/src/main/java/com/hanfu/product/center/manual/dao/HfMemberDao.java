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

public interface HfMemberDao {

	public Integer selectIsMember(Integer userId);
}
