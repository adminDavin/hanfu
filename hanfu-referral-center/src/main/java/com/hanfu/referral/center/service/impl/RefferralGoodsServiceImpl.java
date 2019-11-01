package com.hanfu.referral.center.service.impl;

import java.util.List;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import com.hanfu.inner.model.product.center.HfGoodsDisplay;
import com.hanfu.inner.sdk.goods.center.GoodsService;
import com.hanfu.referral.center.service.ReferralGoodsService;
import com.hanfu.referral.center.service.ReferralProductService;


@Service("referralGoodsService")
public class RefferralGoodsServiceImpl implements ReferralGoodsService {

    @Reference(registry = "dubboProductServer", url = "dubbo://127.0.0.1:1900/com.hanfu.inner.sdk.goods.center.GoodsService")
    private GoodsService goodsService;

    @Override
    public List<HfGoodsDisplay> getAllGoods() {
        return goodsService.findAllGoods();
    }
    
    @Override
    public Integer[] findAllPicture() {
    	return goodsService.findAllPicture();
    }
    
//    @Override
//    public void getPicture() {
//    	// TODO Auto-generated method stub
//    }
}
