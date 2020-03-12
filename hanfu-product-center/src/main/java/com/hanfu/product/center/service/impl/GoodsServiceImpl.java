package com.hanfu.product.center.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.dubbo.common.json.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.inner.model.product.center.HfGoodsDisplay;
import com.hanfu.product.center.dao.FileDescMapper;
import com.hanfu.product.center.dao.HfGoodsMapper;
import com.hanfu.product.center.dao.HfGoodsPictrueMapper;
import com.hanfu.product.center.dao.HfPriceMapper;
import com.hanfu.product.center.dao.HfRespMapper;
import com.hanfu.product.center.dao.WarehouseMapper;
import com.hanfu.product.center.manual.dao.HfGoodsDao;
//import com.hanfu.product.center.manual.model.AwardInfo;
import com.hanfu.product.center.model.FileDesc;
import com.hanfu.product.center.model.HfGoods;
import com.hanfu.product.center.model.HfGoodsPictrue;
import com.hanfu.product.center.model.HfGoodsPictrueExample;
import com.hanfu.product.center.model.HfPrice;
import com.hanfu.product.center.model.HfResp;
import com.hanfu.product.center.model.HfRespExample;
import com.hanfu.product.center.model.Warehouse;
import com.hanfu.product.center.service.GoodsService;


@Service("GoodsService")
@org.apache.dubbo.config.annotation.Service(registry = "dubboProductServer")
public class GoodsServiceImpl implements com.hanfu.inner.sdk.goods.center.GoodsService, GoodsService {

    private static final String LOCK = "LOCK";

    @Autowired
    private HfGoodsMapper hfGoodsMapper;

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

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Override
    public List<com.hanfu.inner.model.product.center.HfGoodsDisplay> findAllGoods(Integer page, Integer size) {
        if (!StringUtils.isEmpty(page)) {
            if (!StringUtils.isEmpty(size)) {
                PageHelper.startPage(page, size);
            }
        }
        List<HfGoodsDisplay> list = hfGoodsDao.selectAllGoodsInfo();
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getPriceId() != null) {
                    HfPrice hfPrice = hfPriceMapper.selectByPrimaryKey(list.get(i).getPriceId());
                    if (hfPrice != null) {
                        list.get(i).setSellPrice(hfPrice.getSellPrice());
                    }
                }
                if (list.get(i).getRespId() != null) {
                    HfResp hfResp = hfRespMapper.selectByPrimaryKey(list.get(i).getRespId());
                    if (hfResp != null) {
                        list.get(i).setQuantity(hfResp.getQuantity());
                    }
                }
                HfGoodsPictrueExample example = new HfGoodsPictrueExample();
                example.createCriteria().andGoodsIdEqualTo(list.get(i).getId());
                List<HfGoodsPictrue> hfGoodsPictrue = hfGoodsPictrueMapper.selectByExample(example);
                if (!hfGoodsPictrue.isEmpty()) {
                    list.get(i).setHfGoodsPictureId(hfGoodsPictrue.get(0).getFileId());
                }
            }
        }
        return JSONArray.parseArray(JSONObject.toJSONString(list), com.hanfu.inner.model.product.center.HfGoodsDisplay.class);
    }

    @Override
    public List<HfGoodsDisplay> getGoodsInfoApp(Integer goodsId) {
//		List<HfGoodsDisplay> list = new ArrayList<HfGoodsDisplay>();
//		com.hanfu.product.center.manual.model.HfGoodsDisplay display = getGoodsInfoUtil(goodsId);
//		HfGoodsDisplay record = new HfGoodsDisplay();
//		record.setId(display.getId());
//		record.setGoodName(display.getGoodName());
//		record.setWarehouseName(display.getWarehouseName());
//		record.setProductCategoryName(display.getProductCategoryName());
//		record.setGoodsDesc(display.getGoodsDesc());
//		record.setCategoryId(display.getCategoryId());
//		record.setSpecValue(display.getSpecValue());
//		record.setProductSpecId(display.getProductSpecId());
//		record.setPriceId(display.getPriceId());
//		record.setRespId(display.getRespId());
//		record.setProductId(display.getProductId());
//		record.setUsername(display.getUsername());
//		record.setCreateTime(display.getCreateTime());
//		record.setModifyTime(display.getModifyTime());
//		record.setIsDeleted(display.getIsDeleted());
//		record.setIsDeleted(display.getIsDeleted());
//		record.setIsDeleted(display.getIsDeleted());
//		if(!StringUtils.isEmpty(display.getSellPrice())) {
//			record.setSellPrice(display.getSellPrice());
//		}
//		if(!StringUtils.isEmpty(display.getQuantity())) {
//			record.setQuantity(display.getQuantity());
//		}
//		return JSON.parse(JSONObject.toJSONString(getGoodsInfoUtil(goodsId)), com.hanfu.inner.model.product.center.HfGoodsDisplay.class);
        return JSONArray.parseArray(JSONObject.toJSONString(getGoodsInfoUtil(goodsId)), com.hanfu.inner.model.product.center.HfGoodsDisplay.class);
    }

    @Override
    public com.hanfu.product.center.manual.model.HfGoodsDisplay getGoodsInfo(Integer goodsId) {
        return getGoodsInfoUtil(goodsId);
    }

    public com.hanfu.product.center.manual.model.HfGoodsDisplay getGoodsInfoUtil(Integer goodsId) {
        com.hanfu.product.center.manual.model.HfGoodsDisplay hfGoodsDisplay = hfGoodsDao.selectGoodsPartInfo(goodsId);
        HfGoods hfGoods = hfGoodsDao.selectFromHfGoods(goodsId);
        if (hfGoods != null) {
            hfGoodsDisplay.setStoneId(hfGoods.getStoneId());
        }
        HfGoodsPictrueExample example2 = new HfGoodsPictrueExample();
        example2.createCriteria().andGoodsIdEqualTo(goodsId);
        List<HfGoodsPictrue> list = hfGoodsPictrueMapper.selectByExample(example2);
        if(hfGoodsDisplay != null) {
        	if(list != null) {
        		hfGoodsDisplay.setFileId(list.get(0).getFileId());
        	}
        	 if (hfGoodsDisplay.getPriceId() != null) {
                 HfPrice hfPrice = hfPriceMapper.selectByPrimaryKey(hfGoodsDisplay.getPriceId());
                 hfGoodsDisplay.setSellPrice(hfPrice.getSellPrice());
             }
             HfRespExample example = new HfRespExample();
             example.createCriteria().andGoogsIdEqualTo(goodsId);
             List<HfResp> hfResp = hfRespMapper.selectByExample(example);
             if (!hfResp.isEmpty()) {
                 hfGoodsDisplay.setQuantity(hfResp.get(0).getQuantity());
                 Warehouse warehouse = warehouseMapper.selectByPrimaryKey(hfResp.get(0).getWarehouseId());
                 if (warehouse != null) {
                     hfGoodsDisplay.setWarehouseName(warehouse.getHfName());
                 }
             }
        }
        return hfGoodsDisplay;
    }

    @Override
    public List<com.hanfu.inner.model.product.center.HfGoodsPictrue> findAllPicture() {
        List<HfGoodsPictrue> list = hfGoodsPictrueMapper.selectByExample(null);
        return JSONArray.parseArray(JSONObject.toJSONString(list), com.hanfu.inner.model.product.center.HfGoodsPictrue.class);
    }

//	@Override
//	public void getPicture(Integer FileDescId,HttpServletResponse response) throws Exception {
//		picture(FileDescId, response);
//	}

    @Override
    public void getFile(Integer FileDescId, HttpServletResponse response) throws Exception {
        picture(FileDescId, response);
    }

    public void picture(Integer FileDescId, HttpServletResponse response) throws Exception {
        FileDesc fileDesc = fileDescMapper.selectByPrimaryKey(FileDescId);
        if (fileDesc == null) {
            throw new Exception("file not exists");
        }
        FileMangeService fileManageService = new FileMangeService();
        synchronized (LOCK) {
            byte[] file = fileManageService.downloadFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
            ByteArrayInputStream stream = new ByteArrayInputStream(file);
            BufferedImage readImg = ImageIO.read(stream);
            stream.reset();
            OutputStream outputStream = response.getOutputStream();
            ImageIO.write(readImg, "png", outputStream);
            outputStream.close();
        }
    }

//	@Override
//	public Integer insertAwardInfo(AwardInfo awardInfo) {
//		Integer row = hfGoodsDao.insertAwardInfo(awardInfo);
//		return row;
//	}
}
