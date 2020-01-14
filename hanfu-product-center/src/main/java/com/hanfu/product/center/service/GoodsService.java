package com.hanfu.product.center.service;

import javax.servlet.http.HttpServletResponse;

//import com.hanfu.product.center.manual.model.AwardInfo;
import com.hanfu.product.center.manual.model.HfGoodsDisplay;

public interface GoodsService {
    public void getFile(Integer FileDescId, HttpServletResponse response) throws Exception;

    public HfGoodsDisplay getGoodsInfo(Integer goodsId);

//	public Integer insertAwardInfo(AwardInfo awardInfo);

}
