package com.hanfu.product.center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hanfu.inner.model.product.center.HfGoodsDisplay;
import com.hanfu.product.center.dao.HfPriceMapper;
import com.hanfu.product.center.manual.dao.HfGoodsDao;
import com.hanfu.product.center.model.HfPrice;
import com.hanfu.product.center.model.Product;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;


@Service("GoodsService")
@org.apache.dubbo.config.annotation.Service(registry = "dubboProductServer")
public class GoodsServiceImpl implements com.hanfu.inner.sdk.product.center.GoodsService {
	
	@Autowired
	private HfGoodsDao hfGoodsDao;
	
	@Autowired
	private HfPriceMapper hfPriceMapper;

	@Override
    public List<com.hanfu.inner.model.product.center.HfGoodsDisplay> findAllGoods() {
		List<HfGoodsDisplay> list = hfGoodsDao.selectAllGoodsInfo();
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getPriceId() != null) {
					HfPrice hfPrice = hfPriceMapper.selectByPrimaryKey(list.get(i).getPriceId());
					list.get(i).setSellPrice(hfPrice.getSellPrice());
				}
			}
		}
        return JSONArray.parseArray(JSONObject.toJSONString(list), com.hanfu.inner.model.product.center.HfGoodsDisplay.class);
    }
	
}
