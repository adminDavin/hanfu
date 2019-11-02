package com.hanfu.product.center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hanfu.inner.model.product.center.HfGoodsDisplay;
import com.hanfu.product.center.dao.FileDescMapper;
import com.hanfu.product.center.dao.HfPriceMapper;
import com.hanfu.product.center.dao.HfRespMapper;
import com.hanfu.product.center.manual.dao.HfGoodsDao;
import com.hanfu.product.center.model.FileDesc;
import com.hanfu.product.center.model.HfPrice;
import com.hanfu.product.center.model.HfResp;
import com.hanfu.product.center.model.Product;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;


@Service("GoodsService")
@org.apache.dubbo.config.annotation.Service(registry = "dubboProductServer")
public class GoodsServiceImpl implements com.hanfu.inner.sdk.goods.center.GoodsService {
	
	@Autowired
	private HfGoodsDao hfGoodsDao;
	
	@Autowired
	private HfPriceMapper hfPriceMapper;
	
	@Autowired
	private HfRespMapper hfRespMapper;
	
	@Autowired
	private FileDescMapper fileDescMapper;

	@Override
    public List<com.hanfu.inner.model.product.center.HfGoodsDisplay> findAllGoods() {
		List<HfGoodsDisplay> list = hfGoodsDao.selectAllGoodsInfo();
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getPriceId() != null) {
					HfPrice hfPrice = hfPriceMapper.selectByPrimaryKey(list.get(i).getPriceId());
					if(hfPrice != null) {
						list.get(i).setSellPrice(hfPrice.getSellPrice());
					}
				}
				if (list.get(i).getRespId() != null) {
					HfResp hfResp = hfRespMapper.selectByPrimaryKey(list.get(i).getRespId());
					if(hfResp != null) {
						list.get(i).setQuantity(hfResp.getQuantity());
					}
				}
			}
		}
        return JSONArray.parseArray(JSONObject.toJSONString(list), com.hanfu.inner.model.product.center.HfGoodsDisplay.class);
    }
	
	@Override
	public Integer[] findAllPicture() {
		Integer[] count = new Integer[10];
		List<FileDesc> fileDesc = fileDescMapper.selectByExample(null);
		for(int i=0;i<fileDesc.size();i++) {
			count[i] = fileDesc.get(i).getId();
		}
		return count;
	}
	
//	@Override
//	public void getPicture() {
//		// TODO Auto-generated method stub
//		
//	}
	
}
