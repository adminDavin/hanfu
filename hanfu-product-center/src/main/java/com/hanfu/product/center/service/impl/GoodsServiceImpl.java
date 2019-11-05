package com.hanfu.product.center.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.inner.model.product.center.HfGoodsDisplay;
import com.hanfu.product.center.dao.FileDescMapper;
import com.hanfu.product.center.dao.HfGoodsPictrueMapper;
import com.hanfu.product.center.dao.HfPriceMapper;
import com.hanfu.product.center.dao.HfRespMapper;
import com.hanfu.product.center.manual.dao.HfGoodsDao;
import com.hanfu.product.center.model.FileDesc;
import com.hanfu.product.center.model.HfGoodsPictrue;
import com.hanfu.product.center.model.HfPrice;
import com.hanfu.product.center.model.HfResp; 
import com.hanfu.product.center.service.GoodsService; 


@Service("GoodsService")
@org.apache.dubbo.config.annotation.Service(registry = "dubboProductServer")
public class GoodsServiceImpl implements com.hanfu.inner.sdk.goods.center.GoodsService,GoodsService{
	
	private static final String LOCK = "LOCK";
	
	@Autowired
	private HfGoodsDao hfGoodsDao;
	
	@Autowired
	private HfPriceMapper hfPriceMapper;
	
	@Autowired
	private HfRespMapper hfRespMapper;
	
	@Autowired
	private FileDescMapper fileDescMapper;
	
	@Autowired
	private HfGoodsPictrueMapper hfGoodsPictrueMapper;

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
	public List<com.hanfu.inner.model.product.center.HfGoodsPictrue> findAllPicture() {
		List<HfGoodsPictrue> list = hfGoodsPictrueMapper.selectByExample(null);
		System.out.println(list.get(0).toString()+"========================================");
		return JSONArray.parseArray(JSONObject.toJSONString(list), com.hanfu.inner.model.product.center.HfGoodsPictrue.class);
	}
	
	
	@Override
	public void getFile(Integer FileDescId, HttpServletResponse response) throws Exception {
		picture(FileDescId,response);
	}
	
	public void picture(Integer FileDescId,HttpServletResponse response) throws Exception {
		FileDesc fileDesc = fileDescMapper.selectByPrimaryKey(FileDescId);
		if (fileDesc == null) {
			throw new Exception("file not exists");
		}
		FileMangeService fileManageService = new FileMangeService();
		synchronized(LOCK) {
			byte[] file = fileManageService.downloadFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
			ByteArrayInputStream stream = new ByteArrayInputStream(file);
			BufferedImage readImg = ImageIO.read(stream);
			stream.reset();
			stream.close();
			ImageIO.write(readImg, "png", response.getOutputStream());
		}
	}
}
