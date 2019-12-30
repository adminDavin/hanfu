package com.hanfu.product.center.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.product.center.dao.FileDescMapper;
import com.hanfu.product.center.dao.GoodsSpecMapper;
import com.hanfu.product.center.dao.HfGoodsMapper;
import com.hanfu.product.center.dao.HfGoodsPictrueMapper;
import com.hanfu.product.center.dao.HfPriceMapper;
import com.hanfu.product.center.dao.HfRespMapper;
import com.hanfu.product.center.dao.HfStoneMapper;
import com.hanfu.product.center.dao.ProductInstanceMapper;
import com.hanfu.product.center.dao.ProductMapper;
import com.hanfu.product.center.dao.ProductSpecMapper;
import com.hanfu.product.center.dao.WarehouseMapper;
import com.hanfu.product.center.manual.dao.HfGoodsDao;
import com.hanfu.product.center.manual.dao.ProductInstanceDao;
//import com.hanfu.product.center.manual.model.AwardInfo;
import com.hanfu.product.center.manual.model.HfGoodsDisplay;
import com.hanfu.product.center.manual.model.ProductDispaly;
import com.hanfu.product.center.model.FileDesc;
import com.hanfu.product.center.model.GoodsSpec;
import com.hanfu.product.center.model.GoodsSpecExample;
import com.hanfu.product.center.model.HfGoods;
import com.hanfu.product.center.model.HfGoodsExample;
import com.hanfu.product.center.model.HfGoodsPictrue;
import com.hanfu.product.center.model.HfGoodsPictrueExample;
import com.hanfu.product.center.model.HfPrice;
import com.hanfu.product.center.model.HfPriceExample;
import com.hanfu.product.center.model.HfResp;
import com.hanfu.product.center.model.HfRespExample;
import com.hanfu.product.center.model.HfStone;
import com.hanfu.product.center.model.Product;
import com.hanfu.product.center.model.ProductInstance;
import com.hanfu.product.center.model.ProductInstanceExample;
import com.hanfu.product.center.model.ProductSpec;
import com.hanfu.product.center.model.ProductSpecExample;
import com.hanfu.product.center.model.Warehouse;
import com.hanfu.product.center.request.DeleteGoodsPictrueRequest;
import com.hanfu.product.center.request.GoodsPictrueRequest;
import com.hanfu.product.center.request.GoodsPriceInfo;
import com.hanfu.product.center.request.GoodsSpecRequest;
import com.hanfu.product.center.request.HfGoodsInfo;
import com.hanfu.product.center.request.HfGoodsRequest;
import com.hanfu.product.center.request.HfRespRequest;
import com.hanfu.product.center.request.ProductInstanceRequest;
import com.hanfu.product.center.request.RespInfo;
import com.hanfu.product.center.service.GoodsService;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import com.hanfu.utils.response.handler.ResponseUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/goods")
@Api
public class GoodsController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private HfGoodsMapper hfGoodsMapper;

	@Autowired
	private HfGoodsDao hfGoodsDao;

	@Autowired
	private GoodsSpecMapper goodsSpecMapper;

	@Autowired
	private HfGoodsPictrueMapper hfGoodsPictrueMapper;

	@Autowired
	private FileDescMapper fileDescMapper;

	@Autowired
	private HfPriceMapper hfPriceMapper;

	@Autowired
	private HfRespMapper hfRespMapper;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private ProductSpecMapper productSpecMapper;

	@Autowired
	private HfStoneMapper hfStoneMapper;

	@Autowired
	private ProductInstanceMapper productInstanceMapper;

	@Autowired
	private ProductInstanceDao productInstanceDao;

	@Autowired
	private WarehouseMapper warehouseMapper;
	
	@Autowired
	private GoodsService goodsService;

	@ApiOperation(value = "获取商品实体id获取物品列表", notes = "即某商品在店铺内的所有规格")
	@RequestMapping(value = "/byInstanceId", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "instanceId", value = "商品实体id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> listProduct(@RequestParam Integer instanceId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoodsExample example = new HfGoodsExample();
		example.createCriteria().andInstanceIdEqualTo(instanceId);
		return builder.body(ResponseUtils.getResponseBody(hfGoodsMapper.selectByExample(example)));
	}

	@ApiOperation(value = "根据物品id获取物品详情", notes = "即某物品的详细信息")
	@RequestMapping(value = "/byGoodsId", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "goodsId", value = "物品id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> listGoodsInfo(@RequestParam Integer goodsId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(goodsService.getGoodsInfo(goodsId)));
	}
	
	@ApiOperation(value = "获取商品列表", notes = "根据类目id查询商品列表")
	@RequestMapping(value = "/categoryId", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> selectProductBycategoryIdOrProductName(
			@RequestParam(name = "goodsDisplay",required = false)HfGoodsDisplay goodsDisplay,
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "size", required = false) Integer size) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		if(!StringUtils.isEmpty(page)) {
			if(!StringUtils.isEmpty(size)) {
				PageHelper.startPage(page, size);
			}
		}
		List<HfGoodsDisplay> list = hfGoodsDao.selectProductBycategoryIdOrProductName(goodsDisplay);
		if(list.isEmpty()) {
			return builder.body(ResponseUtils.getResponseBody(null));
		}
		for(int i=0;i<list.size();i++) {
			if (list.get(i).getPriceId() != null) {
				HfPrice hfPrice = hfPriceMapper.selectByPrimaryKey(list.get(i).getPriceId());
				list.get(i).setSellPrice(hfPrice.getSellPrice());
			}
			HfRespExample example = new HfRespExample();
			example.createCriteria().andGoogsIdEqualTo(list.get(i).getId());
			List<HfResp> hfResp = hfRespMapper.selectByExample(example);
			if (!hfResp.isEmpty()) {
				list.get(i).setQuantity(hfResp.get(0).getQuantity());
				Warehouse warehouse = warehouseMapper.selectByPrimaryKey(hfResp.get(0).getWarehouseId());
				if(warehouse !=null) {
					list.get(i).setWarehouseName(warehouse.getHfName());
				}
			}
			HfGoodsPictrueExample example1 = new HfGoodsPictrueExample();
			example1.createCriteria().andGoodsIdEqualTo(list.get(i).getId());
			List<HfGoodsPictrue> hfGoodsPictrue = hfGoodsPictrueMapper.selectByExample(example1);
			if(!hfGoodsPictrue.isEmpty()) {
				list.get(i).setHfGoodsPictureId(hfGoodsPictrue.get(0).getFileId());
			}
		}
		return builder.body(ResponseUtils.getResponseBody(list));
	}

	@ApiOperation(value = "添加物品", notes = "添加物品")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> createGood(HfGoodsInfo hfGoodsInfo) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Product product = productMapper.selectByPrimaryKey(hfGoodsInfo.getProductId());
		if (product == null) {
			throw new Exception("商品不存在");
		}
		HfStone hfStone = hfStoneMapper.selectByPrimaryKey(hfGoodsInfo.getHfStoreId());
		if (hfStone == null) {
			throw new Exception("商铺不存在");
		}

		ProductInstance item = new ProductInstance();
		ProductInstanceExample example = new ProductInstanceExample();
		example.createCriteria().andStoneIdEqualTo(hfStone.getId()).andProductIdEqualTo(product.getId());
		List<ProductInstance> productInst = productInstanceMapper.selectByExample(example);
		if (productInst.isEmpty()) {
			item.setProductId(product.getId());
			item.setCategoryId(product.getCategoryId());
			item.setStoneId(hfStone.getId());
			item.setBossId(hfGoodsInfo.getBossId());
			item.setBrandId(hfGoodsInfo.getBrandId());
			item.setCreateTime(LocalDateTime.now());
			item.setModifyTime(LocalDateTime.now());
			item.setIsDeleted((short) 0);
			item.setLastModifier(hfGoodsInfo.getUsername());
			productInstanceMapper.insert(item);
		} else {
			item.setId(productInst.get(0).getId());
		}
		HfGoods record = new HfGoods();
		record.setInstanceId(item.getId());
		record.setBossId(hfGoodsInfo.getBossId());
		record.setCategoryId(product.getCategoryId());
		record.setProductId(product.getId());
		record.setGoodsDesc(hfGoodsInfo.getGoodsDesc());
		record.setHfName(hfGoodsInfo.getGoodName());
		record.setStoneId(hfGoodsInfo.getHfStoreId());
		record.setBrandId(hfGoodsInfo.getBrandId());
		record.setCreateTime(LocalDateTime.now());
		record.setModifyTime(LocalDateTime.now());
		record.setIsDeleted((short) 0);
		return builder.body(ResponseUtils.getResponseBody(hfGoodsMapper.insert(record)));
	}

	@ApiOperation(value = "编辑物品", notes = "编辑物品")
	@RequestMapping(value = "/updategood", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> updateGood(HfGoodsDisplay hfGoodsDisplay) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoods hfGoods = hfGoodsMapper.selectByPrimaryKey(hfGoodsDisplay.getId());
		if (hfGoods == null) {
			throw new Exception("物品不存在");
		}
		if (!StringUtils.isEmpty(hfGoodsDisplay.getGoodName())) {
			hfGoods.setHfName(hfGoodsDisplay.getGoodName());
		}
		if (!StringUtils.isEmpty(hfGoodsDisplay.getGoodsDesc())) {
			hfGoods.setGoodsDesc(hfGoodsDisplay.getGoodsDesc());
			;
		}
		hfGoods.setModifyTime(LocalDateTime.now());
//        if(hfGoods.getPriceId()==null) {
//        	GoodsPriceInfo goodsPriceInfo = new GoodsPriceInfo();
//        	goodsPriceInfo.setHfGoodsId(hfGoods.getId());
//        	goodsPriceInfo.setHfPrice(hfGoodsDisplay.getSellPrice());
//        	goodsPriceInfo.setUsername(hfGoodsDisplay.getUsername());
//        	setGoodsPrice(goodsPriceInfo);
//        }else {
//        	 HfPrice hfPrice = hfPriceMapper.selectByPrimaryKey(hfGoods.getPriceId());
//             if(!StringUtils.isEmpty(hfGoodsDisplay.getSellPrice())) {
//             	hfPrice.setSellPrice(hfGoodsDisplay.getSellPrice());
//             }
//             hfPriceMapper.updateByPrimaryKey(hfPrice);
//        }

//        HfResp hfResp = new HfResp();
//        GoodsSpec goodsSpec = new GoodsSpec();
//        goodsSpec.setHfValue(hfGoodsDisplay.getSpecValue());
//        goodsSpec.setGoodsId(hfGoodsDisplay.getId());
//        goodsSpec.setHfSpecId(String.valueOf(hfGoodsDisplay.getProductSpecId()));

		return builder.body(ResponseUtils.getResponseBody(hfGoodsMapper.updateByPrimaryKey(hfGoods)));
	}

	@ApiOperation(value = "获取物品规格", notes = "获取物品规格")
	@RequestMapping(value = "/specifies", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "goodsId", value = "物品id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> getGoodsSpec(@RequestParam(name = "goodsId") Integer goodsId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoods goods = hfGoodsMapper.selectByPrimaryKey(goodsId);
		ProductSpecExample pExample = new ProductSpecExample();
		pExample.createCriteria().andProductIdEqualTo(goods.getProductId());
		List<ProductSpec> productSpecs = productSpecMapper.selectByExample(pExample);
		productSpecs.stream().forEach(spec -> {
			GoodsSpecExample example = new GoodsSpecExample();
			example.createCriteria().andGoodsIdEqualTo(goodsId).andHfSpecIdEqualTo(String.valueOf(spec.getId()));
			List<GoodsSpec> items = goodsSpecMapper.selectByExample(example);
			if (!items.isEmpty()) {
				spec.setSpecValue(items.get(0).getHfValue());
			}
		});
		return builder.body(ResponseUtils.getResponseBody(productSpecs));
	}
	
	@ApiOperation(value = "添加物品规格", notes = "添加物品规格")
	@RequestMapping(value = "/addSpecify", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addGoodsSpec(GoodsSpecRequest request) throws Exception {
		HfGoods hfGoods = hfGoodsMapper.selectByPrimaryKey(request.getGoodsId());
		if (hfGoods == null) {
			throw new Exception("物品不存在");
		}
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ProductSpec productSpec = productSpecMapper.selectByPrimaryKey(request.getProductSpecId());
		GoodsSpec item = new GoodsSpec();
		item.setGoodsId(request.getGoodsId());
		item.setLastModifier(request.getUsername());
		item.setHfSpecId(String.valueOf(productSpec.getId()));
		item.setHfValue(request.getSpecValue());
		item.setCategorySpecId(request.getCatrgorySpecId());
		item.setCreateTime(LocalDateTime.now());
		item.setModifyTime(LocalDateTime.now());
		item.setLastModifier(request.getUsername());
		item.setIsDeleted((short) 0);
		return builder.body(ResponseUtils.getResponseBody(goodsSpecMapper.insert(item)));
	}

	@ApiOperation(value = "更新物品规格", notes = "更新物品规格")
	@RequestMapping(value = "/spec/update", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> updateGoodsSpec(GoodsSpecRequest request) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		GoodsSpecExample example = new GoodsSpecExample();
		example.createCriteria().andGoodsIdEqualTo(request.getGoodsId())
				.andHfSpecIdEqualTo(String.valueOf(request.getProductSpecId()));
		List<GoodsSpec> items = goodsSpecMapper.selectByExample(example);
		if (items.isEmpty()) {
			addGoodsSpec(request);
		} else {
			GoodsSpec item = items.get(0);
			item.setHfValue(request.getSpecValue());
			item.setModifyTime(LocalDateTime.now());
			item.setLastModifier(request.getUsername());
			goodsSpecMapper.updateByPrimaryKey(item);
		}
		return builder.body(ResponseUtils.getResponseBody(goodsSpecMapper.selectByExample(example)));
	}

//	@ApiOperation(value = "获取物品图片", notes = "获取物品图片")
//	@RequestMapping(value = "/resp/list", method = RequestMethod.GET)
//	@ApiImplicitParams({
//			@ApiImplicitParam(paramType = "query", name = "goodsId", value = "物品id", required = true, type = "Integer") })
//	public ResponseEntity<JSONObject> listResp(@RequestParam(name = "goodsId") Integer goodsId) throws JSONException {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		HfRespExample example = new HfRespExample();
//		example.createCriteria().andGoogsIdEqualTo(goodsId);
//		return builder.body(ResponseUtils.getResponseBody(hfRespMapper.selectByExample(example)));
//	}

//    @ApiOperation(value = "更新物品存储", notes = "更新物品存储")
//    @RequestMapping(value = "/resp/update", method = RequestMethod.POST)
//    public ResponseEntity<JSONObject> updateResp(HfRespRequest request) throws JSONException {
//        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//        HfRespExample example = new HfRespExample();
//        example.createCriteria().andGoogsIdEqualTo(request.getGoodsId()).andWarehouseIdEqualTo(request.getWareHouseId());
//        List<HfResp> items = hfRespMapper.selectByExample(example);
//        if (items.isEmpty()) {
//            addResp(request);
//        } else {
//            HfResp item = items.get(0);
//            item.setQuantity(request.getQuatity());
//            hfRespMapper.updateByPrimaryKey(item);
//        }
//        return builder.body(ResponseUtils.getResponseBody(hfRespMapper.selectByExample(example)));
//    }
//    
//    
//    @ApiOperation(value = "添加物品存储", notes = "添加物品存储")
//    @RequestMapping(value = "/resp/add", method = RequestMethod.POST)
//    public ResponseEntity<JSONObject> addResp(HfRespRequest request) throws JSONException {
//        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//        HfRespExample example = new HfRespExample();
//        example.createCriteria().andGoogsIdEqualTo(request.getGoodsId()).andWarehouseIdEqualTo(request.getWareHouseId());
//        List<HfResp> items = hfRespMapper.selectByExample(example);
//        if (items.isEmpty()) {
//            HfResp record = new HfResp();
//            record.setCreateTime(LocalDateTime.now());
//            record.setModifyTime(LocalDateTime.now());
//            record.setLastModifier(request.getUsername());
//            record.setIsDeleted((short) 0);
//            record.setGoogsId(request.getGoodsId());
//            record.setQuantity(request.getQuatity());
//            record.setWarehouseId(record.getWarehouseId());
//            hfRespMapper.insert(record);
//        } else {
//            HfResp item = items.get(0);
//            item.setQuantity(request.getQuatity());
//            item.setModifyTime(LocalDateTime.now());
//            item.setLastModifier(request.getUsername());
//            hfRespMapper.updateByPrimaryKey(item);
//        }
//        return builder.body(ResponseUtils.getResponseBody(hfRespMapper.selectByExample(example)));
//    }

	@ApiOperation(value = "设置物品价格", notes = "设置物品价格")
	@RequestMapping(value = "/setPrice", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> setGoodsPrice(GoodsPriceInfo request) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoods goods = hfGoodsMapper.selectByPrimaryKey(request.getHfGoodsId());
		if (goods == null) {
			throw new Exception("物品不存在");
		}
		HfPrice price = new HfPrice();
		if (goods.getPriceId() == null) {
			price.setGoogsId(request.getHfGoodsId());
			price.setSellPrice(request.getSellPrice());
			price.setCreateTime(LocalDateTime.now());
			price.setModifyTime(LocalDateTime.now());
			price.setLastModifier(request.getUsername());
			price.setIsDeleted((short) 0);
			hfPriceMapper.insert(price);
			goods.setPriceId(price.getId());
			goods.setModifyTime(LocalDateTime.now());
			hfGoodsMapper.updateByPrimaryKey(goods);
		} else {
			HfPrice hfPrice = hfPriceMapper.selectByPrimaryKey(goods.getPriceId());
			if (!StringUtils.isEmpty(request.getSellPrice())) {
				hfPrice.setSellPrice(request.getSellPrice());
			}
			hfPrice.setModifyTime(LocalDateTime.now());
			if (!StringUtils.isEmpty(request.getUsername())) {
				hfPrice.setLastModifier(request.getUsername());
			}
//        	HfPriceExample example = new HfPriceExample();
//        	example.createCriteria().andIdEqualTo(goods.getPriceId());
			hfPriceMapper.updateByPrimaryKey(hfPrice);
		}
		return builder.body(ResponseUtils.getResponseBody(price.getId()));
	}

	@ApiOperation(value = "设置物品物品数量", notes = "设置物品数量")
	@RequestMapping(value = "/setGoodsQuantity", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> setGoodsQuantity(RespInfo request) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoods goods = hfGoodsMapper.selectByPrimaryKey(request.getHfGoodsId());
		if (goods == null) {
			throw new Exception("物品不存在");
		}
		HfRespExample example = new HfRespExample();
		example.createCriteria().andGoogsIdEqualTo(goods.getId());
		List<HfResp> item = hfRespMapper.selectByExample(example);
		HfResp resp = new HfResp();
		Integer respId = null;
		if (item.isEmpty()) {
			resp.setGoogsId(goods.getId());
			resp.setHfStatus(1);
			resp.setQuantity(String.valueOf(request.getQuantity()));
			resp.setHfDesc(request.getRespDesc());
			if(!StringUtils.isEmpty(request.getWareHouseId())) {
				Warehouse warehouse = warehouseMapper.selectByPrimaryKey(request.getWareHouseId());
				if (warehouse == null) {
					throw new Exception("仓库不存在");
				}
				resp.setWarehouseId(request.getWareHouseId());
			}
			resp.setCreateTime(LocalDateTime.now());
			resp.setModifyTime(LocalDateTime.now());
			resp.setLastModifier(request.getUsername());
			resp.setIsDeleted((short) 0);
			hfRespMapper.insert(resp);
			respId = resp.getId();
		} else {
			resp = item.get(0);
			if (!StringUtils.isEmpty(request.getQuantity())) {
				resp.setQuantity(String.valueOf(request.getQuantity()));
			}
			if (!StringUtils.isEmpty(request.getRespDesc())) {
				resp.setHfDesc(request.getRespDesc());
			}
			if (!StringUtils.isEmpty(request.getWareHouseId())) {
				Warehouse warehouse = warehouseMapper.selectByPrimaryKey(request.getWareHouseId());
				if (warehouse == null) {
					throw new Exception("仓库不存在");
				}
				resp.setWarehouseId(request.getWareHouseId());
			}
			resp.setModifyTime(LocalDateTime.now());
			if (!StringUtils.isEmpty(request.getUsername())) {
				resp.setLastModifier(request.getUsername());
			}
			hfRespMapper.updateByPrimaryKey(resp);
			respId = resp.getId();
		}
		return builder.body(ResponseUtils.getResponseBody(respId));
	}

	@ApiOperation(value = "获取物品图片", notes = "获取物品图片")
	@RequestMapping(value = "/pictures", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "goodsId", value = "物品id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> getGoodsPicture(@RequestParam(name = "goodsId") Integer goodsId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoodsPictrueExample example = new HfGoodsPictrueExample();
		example.createCriteria().andGoodsIdEqualTo(goodsId);
		return builder.body(ResponseUtils.getResponseBody(hfGoodsPictrueMapper.selectByExample(example)));
	}
	
	@ApiOperation(value = "获取所有物品图片", notes = "获取所有物品图片")
	@RequestMapping(value = "/picturesAll", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getGoodsPictureAll()
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoodsPictrueExample example = new HfGoodsPictrueExample();
		return builder.body(ResponseUtils.getResponseBody(hfGoodsPictrueMapper.selectByExample(example)));
	}
	
	@ApiOperation(value = "获取所有文件图片", notes = "获取所有文件图片")
	@RequestMapping(value = "/filePicturesAll", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getfilePicturesAll()
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(fileDescMapper.selectByExample(null)));
	}

	@ApiOperation(value = "添加物品图片", notes = "添加物品图片")
	@PostMapping(value = "/addPicture")
	public ResponseEntity<JSONObject> addGoodsPicture(GoodsPictrueRequest request) throws JSONException, IOException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoods goods = hfGoodsMapper.selectByPrimaryKey(request.getGoodsId());
		if (goods == null) {
		}
		List<HfGoodsPictrue> pictures = Lists.newArrayList();
			try {
				FileMangeService fileMangeService = new FileMangeService();
				String arr[];
				MultipartFile fileInfo = request.getFileInfo();
                arr = fileMangeService.uploadFile(fileInfo .getBytes(), String.valueOf(request.getUserId()));
				FileDesc fileDesc = new FileDesc();
				fileDesc.setFileName(fileInfo.getName());
				fileDesc.setGroupName(arr[0]);
				fileDesc.setRemoteFilename(arr[1]);
				fileDesc.setUserId(request.getUserId());
				fileDesc.setCreateTime(LocalDateTime.now());
				fileDesc.setModifyTime(LocalDateTime.now());
				fileDesc.setIsDeleted((short) 0);
				fileDescMapper.insert(fileDesc);
				HfGoodsPictrue picture = new HfGoodsPictrue();
				picture.setFileId(fileDesc.getId());
				picture.setGoodsId(goods.getId());
				picture.setHfName(fileInfo.getName());
				picture.setSpecDesc(request.getPrictureDesc());
				picture.setCreateTime(LocalDateTime.now());
				picture.setModifyTime(LocalDateTime.now());
				picture.setLastModifier(request.getUsername());
				picture.setIsDeleted((short) 0);
				hfGoodsPictrueMapper.insert(picture);
				pictures.add(picture);
			} catch (IOException e) {
				logger.error("add picture failed", e);
			}
		return builder.body(ResponseUtils.getResponseBody(pictures));
	}

	@ApiOperation(value = "获取图片", notes = "获取图片")
	@RequestMapping(value = "/getFile", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "fileId", value = "文件id", required = true, type = "Integer") })
	public void getFile(@RequestParam(name = "fileId") Integer fileId, HttpServletResponse response) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		goodsService.getFile(fileId, response);
	}
	
//	@ApiOperation(value = "获取物品图片", notes = "获取物品图片")
//	@RequestMapping(value = "/getFileByGoods", method = RequestMethod.GET)
//	@ApiImplicitParams({
//			@ApiImplicitParam(paramType = "query", name = "goodsId", value = "物品id", required = true, type = "Integer") })
//	public void getFileByGoods(@RequestParam(name = "goodsId") Integer goodsId, HttpServletResponse response) throws Exception {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		HfGoodsPictrueExample example = new HfGoodsPictrueExample();
//		example.createCriteria().andGoodsIdEqualTo(goodsId);
//		List<HfGoodsPictrue> list = hfGoodsPictrueMapper.selectByExample(example);
//		for(int i=0;i<list.size();i++) {
//		if(!list.isEmpty()) {
//			FileDesc fileDesc = fileDescMapper.selectByPrimaryKey(list.get(0).getFileId());
//			if (fileDesc == null) {
//				throw new Exception("file not exists");
//			}
//			FileMangeService fileManageService = new FileMangeService();
//			byte[] file = fileManageService.downloadFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
//			if(file!=null) {
//				BufferedImage readImg = ImageIO.read(new ByteArrayInputStream(file));
//				ImageIO.write(readImg, "png", response.getOutputStream());
//			}
//		}
//			
//		}
//	}
	
	@ApiOperation(value = "删除图片", notes = "删除图片根据物品")
	@RequestMapping(value = "/deleteFile", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "goodsId", value = "物品id", required = true, type = "Integer") })
	public void deleteFile(@RequestParam(name = "goodsId") Integer goodsId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoodsPictrueExample example = new HfGoodsPictrueExample();
		example.createCriteria().andGoodsIdEqualTo(goodsId);
		List<HfGoodsPictrue> list = hfGoodsPictrueMapper.selectByExample(example);
		for(int i=0;i<list.size();i++) {
			FileDesc fileDesc = fileDescMapper.selectByPrimaryKey(list.get(i).getFileId());
			FileMangeService fileManageService = new FileMangeService();
			if(fileDesc!=null) {
				fileManageService.deleteFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
			}
			hfGoodsPictrueMapper.deleteByPrimaryKey(list.get(i).getId());
			fileDescMapper.deleteByPrimaryKey(fileDesc.getId());
		}
	}
	
	@ApiOperation(value = "删除单张图片", notes = "删除单张图片")
	@RequestMapping(value = "/deletePicture", method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "id", value = "物品图片id", required = true, type = "Integer") })
	public void deletePicture(@RequestParam(name = "id") Integer id) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoodsPictrue hfGoodsPictrue = hfGoodsPictrueMapper.selectByPrimaryKey(id);
		if(hfGoodsPictrue!=null) {
			FileDesc fileDesc = fileDescMapper.selectByPrimaryKey(hfGoodsPictrue.getFileId());
			FileMangeService fileMangeService = new FileMangeService();
			fileMangeService.deleteFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
			fileDescMapper.deleteByPrimaryKey(fileDesc.getId());
			hfGoodsPictrueMapper.deleteByPrimaryKey(id);
		}
	}
	
//	@ApiOperation(value = "添加活动奖项", notes = "添加活动奖项")
//	@RequestMapping(value = "/addAwardInfo", method = RequestMethod.POST)
//	public ResponseEntity<JSONObject> addAwardInfo(AwardInfo awardInfo) throws JSONException{
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		Integer row = goodsService.insertAwardInfo(awardInfo);
//		return builder.body(ResponseUtils.getResponseBody(null));
//	}

}
