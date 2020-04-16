package com.hanfu.product.center.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hanfu.product.center.manual.dao.*;
import com.hanfu.product.center.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.product.center.dao.EvaluateInstanceMapper;
import com.hanfu.product.center.dao.EvaluatePictureMapper;
import com.hanfu.product.center.dao.EvluateInstancePictureMapper;
import com.hanfu.product.center.dao.FileDescMapper;
import com.hanfu.product.center.dao.HfActivityProductMapper;
import com.hanfu.product.center.dao.HfEvaluateMapper;
import com.hanfu.product.center.dao.HfGoodsMapper;
import com.hanfu.product.center.dao.HfGoodsPictrueMapper;
import com.hanfu.product.center.dao.HfGoodsSpecMapper;
import com.hanfu.product.center.dao.HfOrderDetailMapper;
import com.hanfu.product.center.dao.HfOrderMapper;
import com.hanfu.product.center.dao.HfPriceMapper;
import com.hanfu.product.center.dao.HfRespMapper;
import com.hanfu.product.center.dao.HfStoneMapper;
import com.hanfu.product.center.dao.ProductInstanceMapper;
import com.hanfu.product.center.dao.ProductMapper;
import com.hanfu.product.center.dao.ProductSpecMapper;
import com.hanfu.product.center.dao.WarehouseMapper;
import com.hanfu.product.center.manual.model.CheckResp;
import com.hanfu.product.center.manual.model.Evaluate;
import com.hanfu.product.center.manual.model.EvaluateEntity;
import com.hanfu.product.center.manual.model.HfGoodsDisplay;
import com.hanfu.product.center.manual.model.HfGoodsDisplayInfo;
import com.hanfu.product.center.manual.model.HfGoodsSpecDisplay;
import com.hanfu.product.center.manual.model.HfProductDisplay;
import com.hanfu.product.center.manual.model.PriceRanking;
import com.hanfu.product.center.manual.model.ProductForValue;
import com.hanfu.product.center.request.GoodsPictrueRequest;
import com.hanfu.product.center.request.GoodsPriceInfo;
import com.hanfu.product.center.request.GoodsSpecRequest;
import com.hanfu.product.center.request.HfGoodsInfo;
import com.hanfu.product.center.request.RespInfo;
import com.hanfu.product.center.service.GoodsRespService;
import com.hanfu.product.center.service.GoodsService;
import com.hanfu.product.center.service.SpecsService;
import com.hanfu.user.center.dao.HfUserMapper;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import com.hanfu.utils.response.handler.ResponseUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import tk.mybatis.mapper.entity.Example;

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
	private HfGoodsSpecMapper hfGoodsSpecMapper;

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
	private HfGoodsDisplayDao hfGoodsDisplayDao;

	@Autowired
	private HfStoneMapper hfStoneMapper;

	@Autowired
	private WarehouseMapper warehouseMapper;

	@Autowired
	private GoodsService goodsService;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private GoodsRespService goodsRespService;

	@Autowired
	private SpecsService specsService;

	@Autowired
	private HfOrderMapper hfOrderMapper;

	@Autowired
	private HfOrderDetailMapper hfOrderDetailMapper;

	@Autowired
	private ProductInstanceMapper productInstanceMapper;

	@Autowired
	private HfEvaluateMapper hfEvaluateMapper;

	@Autowired
	private EvaluatePictureMapper evaluatePictureMapper;

	@Autowired
	private ManualDao manualDao;

	@Autowired
	private EvaluateInstanceMapper evaluateInstanceMapper;

	@Autowired
	private EvluateInstancePictureMapper evluateInstancePictureMapper;

	@Autowired
	private HfActivityProductMapper hfActivityProductMapper;

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
		return builder.body(ResponseUtils.getResponseBody(hfGoodsDao.selectGoodsInfo(goodsId)));
	}

	@ApiOperation(value = "获取物品列表", notes = "获取物品列表")
	@RequestMapping(value = "/listGoods", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> listGoods(HfGoodsDisplay hfGoodsDisplay) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoodsExample example = new HfGoodsExample();
		example.createCriteria().andStoneIdEqualTo(hfGoodsDisplay.getStoneId());
		List<HfGoods> hfGoods = hfGoodsMapper.selectByExample(example);
		List<HfGoodsDisplay> list = new ArrayList<>();
		for (HfGoods hfGood : hfGoods) {
			hfGoodsDisplay.setPriceId(hfGood.getPriceId());
			hfGoodsDisplay.setRespId(hfGood.getRespId());
			hfGoodsDisplay.setId(hfGood.getId());
			HfGoodsDisplay hfGoodsDis = hfGoodsDao.selectAllGoods(hfGoodsDisplay);
			list.add(hfGoodsDis);
		}
		return builder.body(ResponseUtils.getResponseBody(list));
	}
//	@ApiOperation(value = "获取商品列表", notes = "根据类目id查询商品列表")
//	@RequestMapping(value = "/categoryId", method = RequestMethod.GET)
//	public ResponseEntity<JSONObject> selectProductBycategoryIdOrProductName(
//			@RequestParam(name = "goodsDisplay", required = false) HfGoodsDisplay goodsDisplay,
//			@RequestParam(name = "page", required = false) Integer page,
//			@RequestParam(name = "size", required = false) Integer size) throws JSONException {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		if (!StringUtils.isEmpty(page)) {
//			if (!StringUtils.isEmpty(size)) {
//				PageHelper.startPage(page, size);
//			}
//		}
//		List<HfGoodsDisplay> list = hfGoodsDao.selectProductBycategoryIdOrProductName(goodsDisplay);
//		if (list.isEmpty()) {
//			return builder.body(ResponseUtils.getResponseBody(null));
//		}
//		for (int i = 0; i < list.size(); i++) {
//			if (list.get(i).getPriceId() != null) {
//				HfPrice hfPrice = hfPriceMapper.selectByPrimaryKey(list.get(i).getPriceId());
//				list.get(i).setSellPrice(hfPrice.getSellPrice());
//			}
//			HfRespExample example = new HfRespExample();
//			example.createCriteria().andGoogsIdEqualTo(list.get(i).getId());
//			List<HfResp> hfResp = hfRespMapper.selectByExample(example);
//			if (!hfResp.isEmpty()) {
//				list.get(i).setQuantity(hfResp.get(0).getQuantity());
//				Warehouse warehouse = warehouseMapper.selectByPrimaryKey(hfResp.get(0).getWarehouseId());
//				if (warehouse != null) {
//					list.get(i).setWarehouseName(warehouse.getHfName());
//				}
//			}
//			HfGoodsPictrueExample example1 = new HfGoodsPictrueExample();
//			example1.createCriteria().andGoodsIdEqualTo(list.get(i).getId());
//			List<HfGoodsPictrue> hfGoodsPictrue = hfGoodsPictrueMapper.selectByExample(example1);
//			if (!hfGoodsPictrue.isEmpty()) {
//				list.get(i).setFileId(hfGoodsPictrue.get(0).getFileId());
//			}
//		}
//		return builder.body(ResponseUtils.getResponseBody(list));
//	}

	@ApiOperation(value = "添加物品", notes = "添加物品")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> createGood(HfGoodsInfo hfGoodsInfo) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoods record = new HfGoods();
		record.setProductId(hfGoodsInfo.getProductId());
		Product product = productMapper.selectByPrimaryKey(hfGoodsInfo.getProductId());
		record.setBossId(1);
		record.setGoodsDesc(hfGoodsInfo.getGoodsDesc());
		record.setProductId(hfGoodsInfo.getProductId());
		record.setHfName(hfGoodsInfo.getGoodName());
		if (!StringUtils.isEmpty(hfGoodsInfo.getStoneId())) {
			record.setStoneId(hfGoodsInfo.getStoneId());
			ProductInstanceExample example = new ProductInstanceExample();
			example.createCriteria().andProductIdEqualTo(hfGoodsInfo.getProductId())
					.andStoneIdEqualTo(hfGoodsInfo.getStoneId());
			record.setInstanceId(productInstanceMapper.selectByExample(example).get(0).getId());
		}
		record.setBrandId(1);
//			record.setPriceId(1);
//			record.setRespId(1);
		record.setCreateTime(LocalDateTime.now());
		record.setModifyTime(LocalDateTime.now());
		record.setGmember(hfGoodsInfo.getMember());
//			record.setClaim(hfGoodsInfo.getClaim());
		record.setCategoryId(product.getCategoryId());
		record.setIsDeleted((short) 0);
		hfGoodsMapper.insert(record);
		if (hfGoodsInfo.getSpecValue() != null) {
			ProductSpec productSpec = productSpecMapper.selectByPrimaryKey(hfGoodsInfo.getProductSpecId());
			JSONObject specs = JSONObject.parseObject(hfGoodsInfo.getSpecValue());
			Iterator<String> iterator = specs.keySet().iterator();
			while (iterator.hasNext()) {
// 获得key
				String key = iterator.next();
				String value = specs.getString(key);
				System.out.println("key: " + key + ",value:" + value);
				HfGoodsSpec item = new HfGoodsSpec();
				item.setGoodsId(record.getId());
				item.setLastModifier(hfGoodsInfo.getUsername());
				item.setHfSpecId(String.valueOf(key));
				item.setHfValue(value);
				item.setCategorySpecId(hfGoodsInfo.getCatrgorySpecId());
				item.setCreateTime(LocalDateTime.now());
				item.setModifyTime(LocalDateTime.now());
				item.setIsDeleted((short) 0);
				hfGoodsSpecMapper.insert(item);
			}
		}
		return builder.body(ResponseUtils.getResponseBody(record.getId()));
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
			HfGoodsSpecExample example = new HfGoodsSpecExample();
			example.createCriteria().andGoodsIdEqualTo(goodsId).andHfSpecIdEqualTo(String.valueOf(spec.getId()));
			List<HfGoodsSpec> items = hfGoodsSpecMapper.selectByExample(example);
			if (!items.isEmpty()) {
				spec.setSpecValue(items.get(0).getHfValue());
			}
		});
		return builder.body(ResponseUtils.getResponseBody(productSpecs));
	}

	@ApiOperation(value = "获取物品规格", notes = "获取物品规格")
	@RequestMapping(value = "/specifiess", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "productId", value = "商品id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> getGoodsSpecs(@RequestParam(name = "productId") Integer productId)
			throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Product product = productMapper.selectByPrimaryKey(productId);
		if (product == null) {
			throw new Exception("该商品不存在");
		}
		return builder.body(ResponseUtils.getResponseBody(hfGoodsDao.selectGoodsSpec(product.getId())));
	}

	@ApiOperation(value = "编辑物品", notes = "编辑物品")
	@RequestMapping(value = "/updategood", method = RequestMethod.POST)

	public ResponseEntity<JSONObject> updateGood(@RequestParam("fileInfo1") MultipartFile[] fileInfo1,
			HfGoodsDisplay hfGoodsDisplay) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);

		List<HfGoodsPictrue> pictures = Lists.newArrayList();
		FileMangeService fileMangeService = new FileMangeService();
		String arr[];
		for (MultipartFile fileInfo : fileInfo1) {
			arr = fileMangeService.uploadFile(fileInfo.getBytes(), String.valueOf(hfGoodsDisplay.getUserId()));
			FileDesc fileDesc = new FileDesc();
			fileDesc.setFileName(fileInfo.getName());
			fileDesc.setGroupName(arr[0]);
			fileDesc.setRemoteFilename(arr[1]);
			fileDesc.setUserId(hfGoodsDisplay.getUserId());
			fileDesc.setCreateTime(LocalDateTime.now());
			fileDesc.setModifyTime(LocalDateTime.now());
			fileDesc.setIsDeleted((short) 0);
			fileDescMapper.insert(fileDesc);
			HfGoodsPictrue picture = new HfGoodsPictrue();
			picture.setFileId(fileDesc.getId());
			picture.setGoodsId(hfGoodsDisplay.getId());
			picture.setHfName(fileInfo.getName());
			picture.setSpecDesc(hfGoodsDisplay.getPrictureDesc());
			picture.setCreateTime(LocalDateTime.now());
			picture.setModifyTime(LocalDateTime.now());
			picture.setLastModifier(hfGoodsDisplay.getUsername());
			picture.setIsDeleted((short) 0);
			hfGoodsPictrueMapper.insert(picture);
			pictures.add(picture);
		}
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
//		if(hfGoods.getPriceId()==null || hfGoods.getRespId() == null) {
		GoodsPriceInfo goodsPriceInfo = new GoodsPriceInfo();
		goodsPriceInfo.setHfGoodsId(hfGoods.getId());
		goodsPriceInfo.setSellPrice(hfGoodsDisplay.getSellPrice());
		goodsPriceInfo.setQuantity(hfGoodsDisplay.getQuantity());
		goodsPriceInfo.setWareHouseId(hfGoodsDisplay.getWarehouseId());
		// goodsPriceInfo.setUsername(hfGoodsDisplay.getUsername());
		if (hfGoodsDisplay.getSellPrice() != null || hfGoodsDisplay.getQuantity() != null
				|| hfGoodsDisplay.getWarehouseId() != null) {
			setGoodsPrice(goodsPriceInfo);
		}
//		}else {
//			HfPrice hfPrice = hfPriceMapper.selectByPrimaryKey(hfGoods.getPriceId());
//			if(!StringUtils.isEmpty(hfGoodsDisplay.getSellPrice())) {
//				hfPrice.setSellPrice(hfGoodsDisplay.getSellPrice());
//				hfPriceMapper.updateByPrimaryKey(hfPrice);
//			}
//			HfResp hfResp = hfRespMapper.selectByPrimaryKey(hfGoods.getRespId());
//			if(!StringUtils.isEmpty(hfGoodsDisplay.getQuantity())) {
//				hfResp.setQuantity(hfGoodsDisplay.getQuantity());
//				hfRespMapper.updateByPrimaryKey(hfResp);
//			}
//		}
//		HfResp hfResp = new HfResp();
//		HfGoodsSpec goodsSpec = new HfGoodsSpec();
//		goodsSpec.setHfValue(hfGoodsDisplay.getSpecValue());
//		goodsSpec.setGoodsId(hfGoodsDisplay.getId());
		// goodsSpec.setHfSpecId(String.valueOf(hfGoodsDisplay.getProductSpecId()));
//		hfRespMapper.insert(hfResp);
		return builder.body(ResponseUtils.getResponseBody(hfGoodsMapper.updateByPrimaryKey(hfGoods)));
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
		HfGoodsSpec item = new HfGoodsSpec();
		item.setGoodsId(request.getGoodsId());
		item.setLastModifier(request.getUsername());
		item.setHfSpecId(String.valueOf(productSpec.getId()));
		item.setHfValue(request.getSpecValue());
		item.setCategorySpecId(request.getCatrgorySpecId());
		item.setCreateTime(LocalDateTime.now());
		item.setModifyTime(LocalDateTime.now());
		item.setIsDeleted((short) 0);
		return builder.body(ResponseUtils.getResponseBody(hfGoodsSpecMapper.insert(item)));
	}

	@ApiOperation(value = "更新物品图片", notes = "更新物品图片")
	@RequestMapping(value = "/updatePictrue", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> updateGoodsPictrue(MultipartFile fileInfo1, GoodsSpecRequest request, int fileID)
			throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);

		List<HfGoodsPictrue> pictures = Lists.newArrayList();
		FileMangeService fileMangeService = new FileMangeService();
		String arr[];
//		for (int i=0;i<=fileInfo1.length;i++) {
//			MultipartFile fileInfo=fileInfo1[i];
		arr = fileMangeService.uploadFile(fileInfo1.getBytes(), String.valueOf(request.getUserId()));
		FileDesc fileDesc = new FileDesc();
		fileDesc.setFileName(fileInfo1.getName());
		fileDesc.setGroupName(arr[0]);
		fileDesc.setRemoteFilename(arr[1]);
		fileDesc.setUserId(request.getUserId());
		fileDesc.setCreateTime(LocalDateTime.now());
		fileDesc.setModifyTime(LocalDateTime.now());
		fileDesc.setIsDeleted((short) 0);
		fileDescMapper.insert(fileDesc);
		HfGoodsPictrue picture = new HfGoodsPictrue();
		picture.setFileId(fileDesc.getId());
		picture.setGoodsId(request.getGoodsId());
		picture.setHfName(fileInfo1.getName());
		picture.setSpecDesc(request.getPrictureDesc());
		picture.setCreateTime(LocalDateTime.now());
		picture.setModifyTime(LocalDateTime.now());
		picture.setLastModifier(request.getUsername());
		picture.setIsDeleted((short) 0);
		HfGoodsPictrueExample example1 = new HfGoodsPictrueExample();
		example1.createCriteria().andGoodsIdEqualTo(request.getGoodsId()).andFileIdEqualTo(fileID);

		hfGoodsPictrueMapper.updateByExampleSelective(picture, example1);
//		pictures.add(picture);
		return builder.body(ResponseUtils.getResponseBody(fileDesc.getId()));
//		}
//		return builder.body(ResponseUtils.getResponseBody(""));
	}

	@ApiOperation(value = "更新物品规格", notes = "更新物品规格")
	@RequestMapping(value = "/spec/update", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> updateGoodsSpec(GoodsSpecRequest request) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);

//		List<HfGoodsPictrue> pictures = Lists.newArrayList();
//		FileMangeService fileMangeService = new FileMangeService();
//		String arr[];
//		for (int i=0;i<=fileInfo1.length;i++) {
//			MultipartFile fileInfo=fileInfo1[i];
//			arr = fileMangeService.uploadFile(fileInfo1.getBytes(), String.valueOf(request.getUserId()));
//			FileDesc fileDesc = new FileDesc();
//			fileDesc.setFileName(fileInfo1.getName());
//			fileDesc.setGroupName(arr[0]);
//			fileDesc.setRemoteFilename(arr[1]);
//			fileDesc.setUserId(request.getUserId());
//			fileDesc.setCreateTime(LocalDateTime.now());
//			fileDesc.setModifyTime(LocalDateTime.now());
//			fileDesc.setIsDeleted((short) 0);
//			fileDescMapper.insert(fileDesc);
//			HfGoodsPictrue picture = new HfGoodsPictrue();
//			picture.setFileId(fileDesc.getId());
//			picture.setGoodsId(request.getGoodsId());
//			picture.setHfName(fileInfo1.getName());
//			picture.setSpecDesc(request.getPrictureDesc());
//			picture.setCreateTime(LocalDateTime.now());
//			picture.setModifyTime(LocalDateTime.now());
//			picture.setLastModifier(request.getUsername());
//			picture.setIsDeleted((short) 0);
//		HfGoodsPictrueExample example1 = new HfGoodsPictrueExample();
//		example1.createCriteria().andGoodsIdEqualTo(request.getGoodsId()).andFileIdEqualTo(fileID);
//
//			hfGoodsPictrueMapper.updateByExampleSelective(picture,example1);
//			pictures.add(picture);
		HfGoodsSpecExample example = new HfGoodsSpecExample();
		example.createCriteria().andGoodsIdEqualTo(request.getGoodsId())
				.andHfSpecIdEqualTo(String.valueOf(request.getProductSpecId()));
		List<HfGoodsSpec> items = hfGoodsSpecMapper.selectByExample(example);
		if (items.isEmpty()) {
			addGoodsSpec(request);
		} else {
			HfGoodsSpec item = items.get(0);
			item.setHfValue(request.getSpecValue());
			item.setModifyTime(LocalDateTime.now());
			item.setLastModifier(request.getUsername());
			hfGoodsSpecMapper.updateByPrimaryKey(item);
		}
		return builder.body(ResponseUtils.getResponseBody(hfGoodsSpecMapper.selectByExample(example)));
//		}
//		return builder.body(ResponseUtils.getResponseBody(""));
	}

	@ApiOperation(value = "设置物品价格", notes = "设置物品价格")
	@RequestMapping(value = "/setPrice", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> setGoodsPrice(GoodsPriceInfo request) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoods goods = hfGoodsMapper.selectByPrimaryKey(request.getHfGoodsId());
		if (goods == null) {
			throw new Exception("物品不存在");
		}
		HfRespExample example = new HfRespExample();
		example.createCriteria().andGoogsIdEqualTo(goods.getId());
		List<HfResp> item = hfRespMapper.selectByExample(example);
		HfPrice price = new HfPrice();
		HfResp resp = new HfResp();
		@SuppressWarnings("unused")
		Integer respId = null;
		if (goods.getPriceId() == null) {
			price.setGoogsId(request.getHfGoodsId());
			price.setSellPrice(request.getSellPrice());
			price.setCreateTime(LocalDateTime.now());
			price.setModifyTime(LocalDateTime.now());
			price.setLastModifier(request.getUsername());
			price.setLinePrice(request.getLinePrice());
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
			if (!StringUtils.isEmpty(request.getLinePrice())) {
				hfPrice.setLinePrice(request.getLinePrice());
			}
			hfPrice.setModifyTime(LocalDateTime.now());
			if (!StringUtils.isEmpty(request.getUsername())) {
				hfPrice.setLastModifier(request.getUsername());
			}
			hfPriceMapper.updateByPrimaryKey(hfPrice);
		}
		if (goods.getRespId() == null) {
			resp.setGoogsId(goods.getId());
			resp.setHfStatus(1);
			resp.setQuantity(request.getQuantity());
			resp.setHfDesc(request.getRespDesc());
			resp.setCreateTime(LocalDateTime.now());
			resp.setModifyTime(LocalDateTime.now());
			resp.setLastModifier(request.getUsername());
			resp.setIsDeleted((short) 0);
			resp.setWarehouseId(request.getWareHouseId());
			hfRespMapper.insert(resp);
			goods.setRespId(resp.getId());
			goods.setModifyTime(LocalDateTime.now());
			hfGoodsMapper.updateByPrimaryKey(goods);
		} else {
			resp = item.get(0);
			if (!StringUtils.isEmpty(request.getQuantity())) {
				resp.setQuantity(request.getQuantity());
			}
			if (!StringUtils.isEmpty(request.getRespDesc())) {
				resp.setHfDesc(request.getRespDesc());
			}
			resp.setModifyTime(LocalDateTime.now());
			if (!StringUtils.isEmpty(request.getUsername())) {
				resp.setLastModifier(request.getUsername());
			}
			if (!StringUtils.isEmpty(request.getWareHouseId())) {
				resp.setWarehouseId(request.getWareHouseId());
			}
			hfRespMapper.updateByPrimaryKey(resp);
			respId = resp.getId();
		}
		return builder.body(ResponseUtils.getResponseBody(price.getId()));
	}

	@ApiOperation(value = "设置物品数量", notes = "设置物品数量")
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
			resp.setQuantity(request.getQuantity());
			resp.setHfDesc(request.getRespDesc());
			if (!StringUtils.isEmpty(request.getWareHouseId())) {
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
				resp.setQuantity(request.getQuantity());
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
	@RequestMapping(value = "/picturesAll", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getGoodsPictureAll(@RequestParam(name = "goodsId") Integer goodsId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoodsPictrueExample example = new HfGoodsPictrueExample();
		if (goodsId != null) {
			example.createCriteria().andGoodsIdEqualTo(goodsId);
			return builder.body(ResponseUtils.getResponseBody(hfGoodsPictrueMapper.selectByExample(example)));
		}
		return builder.body(ResponseUtils.getResponseBody(hfGoodsPictrueMapper.selectByExample(example)));
	}

	@ApiOperation(value = "添加物品图片", notes = "添加物品图片")
	@PostMapping(value = "/addPicture")
	public ResponseEntity<JSONObject> addGoodsPicture(MultipartFile fileInfo1, GoodsPictrueRequest request)
			throws JSONException, IOException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		System.out.println(request.getGoodsId());
		HfGoods goods = hfGoodsMapper.selectByPrimaryKey(request.getGoodsId());
		if (goods == null) {
		}
		List<HfGoodsPictrue> pictures = Lists.newArrayList();
		Map<String, Integer> params = new HashMap<>();
		try {
			FileMangeService fileMangeService = new FileMangeService();
			String arr[];
			MultipartFile fileInfo = fileInfo1;
			arr = fileMangeService.uploadFile(fileInfo.getBytes(), String.valueOf(request.getUserId()));
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
			params.put("fileId", fileDesc.getId());
			params.put("HfGoodsPictrueId", picture.getId());
		} catch (IOException e) {
			logger.error("add picture failed", e);
		}

		return builder.body(ResponseUtils.getResponseBody(params));
	}

	@ApiOperation(value = "获取图片", notes = "获取图片")
	@RequestMapping(value = "/getFile", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "fileId", value = "文件id", required = true, type = "Integer") })
	public void getFile(@RequestParam(name = "fileId") Integer fileId, HttpServletResponse response) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		goodsService.getFile(fileId, response);
	}

	@ApiOperation(value = "删除图片", notes = "删除图片根据物品")
	@RequestMapping(value = "/deleteFile", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "goodsId", value = "物品id", required = true, type = "Integer") })
	public void deleteFile(@RequestParam(name = "goodsId") Integer goodsId) throws Exception {
		HfGoodsPictrueExample example = new HfGoodsPictrueExample();
		example.createCriteria().andGoodsIdEqualTo(goodsId);
		List<HfGoodsPictrue> list = hfGoodsPictrueMapper.selectByExample(example);
		for (int i = 0; i < list.size(); i++) {
			FileDesc fileDesc = fileDescMapper.selectByPrimaryKey(list.get(i).getFileId());
			FileMangeService fileManageService = new FileMangeService();
			if (fileDesc != null) {
				fileManageService.deleteFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
			}
			hfGoodsPictrueMapper.deleteByPrimaryKey(list.get(i).getId());
			fileDescMapper.deleteByPrimaryKey(fileDesc.getId());
		}
	}

//
//	@ApiOperation(value = "删除单张图片", notes = "删除单张图片")
//	@RequestMapping(value = "/deletePicture", method = RequestMethod.GET)
//	@ApiImplicitParams({
//		@ApiImplicitParam(paramType = "query", name = "id", value = "物品图片id", required = true, type = "Integer") })
//	public void deletePicture(@RequestParam(name = "id") Integer id) throws Exception {
//		HfGoodsPictrue hfGoodsPictrue = hfGoodsPictrueMapper.selectByPrimaryKey(id);
//		if(hfGoodsPictrue!=null) {
//			FileDesc fileDesc = fileDescMapper.selectByPrimaryKey(hfGoodsPictrue.getFileId());
//			FileMangeService fileMangeService = new FileMangeService();
//			fileMangeService.deleteFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
//			fileDescMapper.deleteByPrimaryKey(fileDesc.getId());
//			hfGoodsPictrueMapper.deleteByPrimaryKey(id);
//		}
//	}
//	@ApiOperation(value = "设为常买", notes = "设为常买")
//	@RequestMapping(value = "/OftenBuy", method = RequestMethod.GET)
//	public ResponseEntity<JSONObject> OftenBuy(Integer userId,Integer[] goodsId)
//			throws JSONException {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		redisTemplate.opsForValue().set(userId.toString(), goodsId);
//		return builder.body(ResponseUtils.getResponseBody("设置成功"));
//	}
//	@ApiOperation(value = "取消常买", notes = "取消常买")
//	@RequestMapping(value = "/delOftenbuy", method = RequestMethod.GET)
//	@ApiImplicitParams({
//		@ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer") })
//	public ResponseEntity<JSONObject> delOftenbuy(Integer userId)
//			throws JSONException {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		if(!StringUtils.isEmpty(redisTemplate.opsForValue().get(userId.toString()))) {
//			return builder.body(ResponseUtils.getResponseBody("没有数据"));
//		}
//		return builder.body(ResponseUtils.getResponseBody(redisTemplate.delete(userId.toString())));
//	}
//	@ApiOperation(value = "设置关注", notes = "设置关注")
//	@RequestMapping(value = "/Concern", method = RequestMethod.GET)
//	public ResponseEntity<JSONObject> Concern(String openId,Integer[] goodsId)
//			throws JSONException {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		redisTemplate.opsForValue().set(openId, goodsId);
//		return builder.body(ResponseUtils.getResponseBody("关注成功"));
//	}
//	@ApiOperation(value = "取消关注", notes = "取消关注")
//	@RequestMapping(value = "/delConcern", method = RequestMethod.GET)
//	@ApiImplicitParams({
//		@ApiImplicitParam(paramType = "query", name = "openId", value = "openid", required = true, type = "String") })
//	public ResponseEntity<JSONObject> delConcern(String openId)
//			throws JSONException {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		if(StringUtils.isEmpty(redisTemplate.opsForValue().get(openId))) {
//			return builder.body(ResponseUtils.getResponseBody("没有关注商品"));
//		}		
//		return builder.body(ResponseUtils.getResponseBody(redisTemplate.delete(openId)));
//	}
	@ApiOperation(value = "批量上下架", notes = "批量上下架")
	@RequestMapping(value = "/racking", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> racking(Integer[] goodsId, Short frames) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		for (Integer goods : goodsId) {
			HfGoods hfGoods = hfGoodsMapper.selectByPrimaryKey(goods);
			hfGoods.setIsDeleted(frames);
			hfGoodsMapper.updateByPrimaryKeySelective(hfGoods);
		}
		return builder.body(ResponseUtils.getResponseBody(""));
	}

	// @ApiOperation(value = "添加活动奖项", notes = "添加活动奖项")
	// @RequestMapping(value = "/addAwardInfo", method = RequestMethod.POST)
	// public ResponseEntity<JSONObject> addAwardInfo(AwardInfo awardInfo) throws
	// JSONException{
	// BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
	// Integer row = goodsService.insertAwardInfo(awardInfo);
	// return builder.body(ResponseUtils.getResponseBody(null));
	// }
	@ApiOperation(value = "根据条件查询", notes = "根据条件查询")
	@RequestMapping(value = "/selectByValue", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> selectByValue(ProductForValue productForValue) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(hfGoodsDao.selectList(productForValue)));
	}

	@ApiOperation(value = "价格升序", notes = "价格的升序")
	@RequestMapping(value = "/Price", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> Price(PriceRanking priceRanking) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		if (priceRanking.getCategoryId() != null) {
			priceRanking.setCategoryId(priceRanking.getCategoryId());
		}
		if (priceRanking.getSeniorityId() != null) {
			priceRanking.setSeniorityId(priceRanking.getSeniorityId());
		}
		return builder.body(ResponseUtils.getResponseBody(hfGoodsDao.selectPrice(priceRanking)));
	}

	@ApiOperation(value = "价格降序", notes = "价格降序")
	@RequestMapping(value = "/desPrice", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> Pricedec(PriceRanking priceRanking) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		if (priceRanking.getCategoryId() != null) {
			priceRanking.setCategoryId(priceRanking.getCategoryId());
		}
		if (priceRanking.getSeniorityId() != null) {
			priceRanking.setSeniorityId(priceRanking.getSeniorityId());
		}
		return builder.body(ResponseUtils.getResponseBody(hfGoodsDao.selectPriceDec(priceRanking)));
	}

//	@ApiOperation(value = "添加收藏", notes = "添加收藏")
//	@RequestMapping(value = "/collect", method = RequestMethod.GET)
//	public ResponseEntity<JSONObject> collect(Integer userId,Integer[] goodsId)
//			throws JSONException {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);	
//		redisTemplate.opsForValue().set(userId.toString(), goodsId);
//		return builder.body(ResponseUtils.getResponseBody("添加成功"));
//	}
//	@ApiOperation(value = "取消收藏", notes = "取消收藏")
//	@RequestMapping(value = "/delcollect", method = RequestMethod.GET)
//	public ResponseEntity<JSONObject> delCollect(Integer userId)
//			throws JSONException {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);	
//		if(StringUtils.isEmpty(redisTemplate.opsForValue().get(userId.toString()))) {
//			return builder.body(ResponseUtils.getResponseBody("没有收藏商品"));
//		}		
//		return builder.body(ResponseUtils.getResponseBody(redisTemplate.delete(userId.toString())));
//	}
//	@ApiOperation(value = "查看收藏", notes = "查看收藏")
//	@RequestMapping(value = "/selectcollect", method = RequestMethod.GET)
//	@ApiImplicitParams({
//		@ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = true, type = "Integer") })
//	public ResponseEntity<JSONObject> selectcollect(Integer userId)
//			throws JSONException {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		return builder.body(ResponseUtils.getResponseBody(redisTemplate.opsForValue().get(userId.toString())));
//	}
	@ApiOperation(value = "出售中", notes = "出售中")
	@RequestMapping(value = "/selectFrames", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> selectFrames(Short frames) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoodsExample example = new HfGoodsExample();
		example.createCriteria().andIsDeletedEqualTo(frames);
		return builder.body(ResponseUtils.getResponseBody(hfGoodsMapper.selectByExample(example)));
	}

	@ApiOperation(value = "查询数量", notes = "查询数量")
	@RequestMapping(value = "/selectQ", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> selectQ(Short frames) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoodsExample example = new HfGoodsExample();
		example.createCriteria().andIsDeletedEqualTo(frames);
		Long count = hfGoodsMapper.countByExample(example);
		return builder.body(ResponseUtils.getResponseBody(count));
	}

	@ApiOperation(value = "查询物品总数", notes = "查询物品总数")
	@RequestMapping(value = "/queryGoods", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> quieryGoods() throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(hfGoodsMapper.countByExample(null)));
	}

	@ApiOperation(value = "查询", notes = "查询")
	@RequestMapping(value = "/queryList", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> queryList(ProductForValue productForValue) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(hfGoodsDao.selectQueryList(productForValue)));
	}

//	@ApiOperation(value = "校检库存", notes = "校检库存")
//	@RequestMapping(value = "/checkResp", method = RequestMethod.POST)
//	public ResponseEntity<JSONObject> checkResp(Integer GoodsNum,Integer goodsId)
//			throws JSONException {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		if(goodsId!=null) {
//					HfRespExample exampleResp = new HfRespExample();
//					exampleResp.createCriteria().andGoogsIdEqualTo(goodsId);
//					if (hfRespMapper.selectByExample(exampleResp).get(0).getQuantity()<GoodsNum){
//						return builder.body(ResponseUtils.getResponseBody("库存不足"));
//				}
//				HfPriceExample examplePrice= new HfPriceExample();
//				examplePrice.createCriteria().andGoogsIdEqualTo(goodsId);
//				hfPriceMapper.selectByExample(examplePrice);
//				Amount amount = new Amount();
//				amount.setGoodsId(goodsId);
//				amount.setGoodsNum(hfRespMapper.selectByExample(exampleResp).get(0).getQuantity());
//				amount.setMoney(hfPriceMapper.selectByExample(examplePrice).get(0).getSellPrice()*GoodsNum);
//				amount.setDiscountMoney(hfPriceMapper.selectByExample(examplePrice).get(0).getSellPrice()*GoodsNum);
//				return builder.body(ResponseUtils.getResponseBody(amount));
//			}
//
////		Integer goodsId=null;
////		System.out.println(checkResp.getRespList());
////        JSONObject specs = JSONObject.parseObject(checkResp.getRespList());
////		Iterator<String> iterator = specs.keySet().iterator();
////		ArrayList<String> strings = new ArrayList<>();
////		ArrayList<String> sss = new ArrayList<>();
////
////		while(iterator.hasNext()){
////// 获得key
////			String key = iterator.next();
////			String value = specs.getString(key);
////			strings.add(key);
////			System.out.println("key: "+key+",value:"+value);
////			String hfV=value;
////			sss.add(value);
////		}
////		System.out.println(sss);
////		ArrayList<Integer> productSpecList1 = new ArrayList<>();
////		ArrayList<String> hfValueList = new ArrayList<>();
////		for (int i=0;i<strings.size();i++){
////		    ProductSpecExample example = new ProductSpecExample();
////			example.createCriteria().andHfNameEqualTo(strings.get(i)).andProductIdEqualTo(checkResp.getProductId());
////			List<ProductSpec> productSpecList = productSpecMapper.selectByExample(example);
////			if(productSpecList.size()==0) {
////				return builder.body(ResponseUtils.getResponseBody("不存在"));
////			}
////			productSpecList1.add(productSpecList.get(0).getId());
////			System.out.println(productSpecList1+"qqqqq");
////			hfValueList.add(specs.getString(strings.get(i)));
////		}
////		System.out.println(hfValueList.toString()+"1233123");
////		int a;
////		int ifor;
////		List<Integer> numList = new ArrayList<Integer>();
////
////
////
////		for (ifor=0;ifor<productSpecList1.size()-1;ifor++){
////			Example example2 = new Example(HfGoodsSpec.class);
////			Example.Criteria criteria2 = example2.createCriteria();
////			criteria2.andEqualTo("hfSpecId",productSpecList1.get(ifor)).andEqualTo("hfValue",hfValueList.get(ifor));
////			numList.add(goodsSpecMapper1.selectByExample(example2).size());
////		}
////		ArrayList<String> goodsSpec = new ArrayList<>();
////		System.out.println(Collections.max(numList)+"Collections.max(numList)");
////		String abc="";
////		for (a=0;a<sss.size();a++){
////			System.out.println(productSpecList1.size());
////			System.out.println(a+"aaaaaaaaa");
////			Example example1 = new Example(HfGoodsSpec.class);
////			Example.Criteria criteria1 = example1.createCriteria();
////			criteria1.andEqualTo("hfSpecId",productSpecList1.get(a)).andEqualTo("hfValue",hfValueList.get(a));
////			System.out.println(hfValueList.get(a)+"vavavvava"+productSpecList1.get(a)+hfValueList.get(a));
////			System.out.println(goodsSpecMapper1.selectByExample(example1).toString());
////
////			System.out.println(productSpecList1.get(a)+"jieshu");
////			goodsSpec.add(String.valueOf(productSpecList1));
////		}
////
////
////
////		HashMap map = new HashMap();
////		for (int i = 0; i < productSpecList1.size(); i++) {
////	        map.put(productSpecList1.get(i), i); //将值和下标存入Map
////	    }
////		Collections.sort(productSpecList1);
////		for (a=0;a<sss.size();a++){
////			abc=abc+String.valueOf(productSpecList1.get(a));
////		}
////        System.out.println(productSpecList1);
////        ArrayList<String> originalList = new ArrayList<>();
////        for (int i = 0; i < productSpecList1.size(); i++) {
////        originalList.add(hfValueList.get((Integer) map.get(productSpecList1.get(i))));
////        System.out.println(map.get(productSpecList1.get(i)));
////        }
////        System.out.println(originalList);
////
////
////		System.out.println(abc);
////		String aaa=originalList.toString().replace("[", "");
////		aaa=aaa.replace("]", "");
////		aaa=aaa.replace(" ", "");
////		for(int i =0;i<specsService.selectSpecs().size();i++) {
////			System.out.println(specsService.selectSpecs().get(i).getHfSpecId()+"--------------------");
////			System.out.println(specsService.selectSpecs().get(i).getHfValue());
////			System.out.println(specsService.selectSpecs().get(i).getGoodsId());
////			System.out.println(specsService.selectSpecs().get(i).getHfSpecId().equals(abc));
////			System.out.println(specsService.selectSpecs().get(i).getHfValue().equals(aaa));
////			if(specsService.selectSpecs().get(i).getHfSpecId().equals(abc)&specsService.selectSpecs().get(i).getHfValue().equals(aaa)) {
////				goodsId=specsService.selectSpecs().get(i).getGoodsId();
////				System.out.println("goodsID");
////			}
////		}
////		if (goodsId==null){
////			return builder.body(ResponseUtils.getResponseBody("goods不存在"));
////		}
////		System.out.println("zzzzz");
////		System.out.println(a);
////		for (int x=0;x<a;x++){
////			System.out.println(goodsId);
////				System.out.println("aaaaaaaa");
////				Example exampleResp = new Example(HfResp.class);
////				Example.Criteria criteriaResp = exampleResp.createCriteria();
////				criteriaResp.andEqualTo("googsId",goodsId);
////			System.out.println(hfRespMapper1.selectByExample(exampleResp));
////				if (hfRespMapper1.selectByExample(exampleResp).get(0).getQuantity()<checkResp.getGoodsNum()){
////					return builder.body(ResponseUtils.getResponseBody("库存不足"));
////			}
////			Example examplePrice= new Example(HfPrice.class);
////			Example.Criteria criteriaPrice = examplePrice.createCriteria();
////			criteriaPrice.andEqualTo("googsId",goodsId);
////			hfPriceMapper1.selectByExample(examplePrice);
////			Amount amount = new Amount();
////			amount.setId(goodsId);
////			amount.setGoodsNum(checkResp.getGoodsNum());
////			amount.setMoney(hfPriceMapper1.selectByExample(examplePrice).get(0).getSellPrice()*checkResp.getGoodsNum());
////			amount.setDiscountMoney(hfPriceMapper1.selectByExample(examplePrice).get(0).getSellPrice()*checkResp.getGoodsNum());
////			return builder.body(ResponseUtils.getResponseBody(amount));
////		}
//		return builder.body(ResponseUtils.getResponseBody("ojbk"));
//
//	}
	@ApiOperation(value = "根据类目id查询商品列表", notes = "根据类目id查询商品列表")
	@RequestMapping(value = "/findProductBycategoryId", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> findProductBycategoryId(
			@RequestParam(name = "categoryId", required = false) Integer categoryId,
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "size", required = false) Integer size) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		List<HfGoodsDisplay> hfGoodsDisplays = new ArrayList<HfGoodsDisplay>();
		ProductExample example = new ProductExample();
		example.createCriteria().andCategoryIdEqualTo(categoryId);
		if (!StringUtils.isEmpty(page)) {
			if (!StringUtils.isEmpty(size)) {
				PageHelper.startPage(page, size);
			}
		}
		List<Product> list = productMapper.selectByExample(example);
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				Product product = list.get(i);
				HfGoodsDisplay display = new HfGoodsDisplay();
				display.setCategoryId(categoryId);
				display.setProductId(product.getId());
				List<HfGoodsDisplay> goodsDisplay = hfGoodsDao.selectProductBycategoryIdOrProductName(display);
				if (!goodsDisplay.isEmpty()) {
					HfGoodsPictrueExample example2 = new HfGoodsPictrueExample();
					example2.createCriteria().andGoodsIdEqualTo(goodsDisplay.get(0).getId());
					List<HfGoodsPictrue> list2 = hfGoodsPictrueMapper.selectByExample(example2);
					if (!list2.isEmpty()) {
						HfGoodsPictrue hfGoodsPictrue = list2.get(0);
						goodsDisplay.get(0).setFileId(hfGoodsPictrue.getFileId());
					}
					hfGoodsDisplays.add(goodsDisplay.get(0));
				}
			}
		}
		return builder.body(ResponseUtils.getResponseBody(hfGoodsDisplays));
	}

	@ApiOperation(value = "获取商品对应物品库存", notes = "获取商品对应物品库存")
	@RequestMapping(value = "/selectGoodsResp", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> selectGoodsResp(Integer ProductID) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(goodsRespService.selectGoodsResp(ProductID).get(0)));
	}

	@ApiOperation(value = "获取物品所在店铺的信息", notes = "根据物品获取物品所在的店铺信息")
	@RequestMapping(value = "/stones", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "goodsId", value = "物品ID", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> getStones(@RequestParam(name = "goodsId") Integer goodsId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoods hfGoods = hfGoodsMapper.selectByPrimaryKey(goodsId);
		if (hfGoods == null) {
			throw new Exception("物品不存在");
		}
		return builder.body(ResponseUtils.getResponseBody(hfStoneMapper.selectByPrimaryKey(hfGoods.getStoneId())));
	}

	@ApiOperation(value = "获取店铺所有物品", notes = "根據商鋪id獲取商鋪的所有物品")
	@RequestMapping(value = "/byStoneId", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "stoneId", value = "商鋪id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> getStoneProduct(@RequestParam(name = "stoneId") Integer stoneId)
			throws Exception {
		HfStone hfStone = hfStoneMapper.selectByPrimaryKey(stoneId);
		if (hfStone == null) {
			throw new Exception("店铺不存在");
		}
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		List<HfGoodsDisplay> list = hfGoodsDao.selectAllGoodsPartInfo(stoneId);
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getPriceId() != null) {
					HfPrice hfPrice = hfPriceMapper.selectByPrimaryKey(list.get(i).getPriceId());
					if (hfPrice != null) {
						list.get(i).setSellPrice(hfPrice.getSellPrice());
						list.get(i).setLinePrice(hfPrice.getLinePrice());
						;
					}
				}
				if (list.get(i).getRespId() != null) {
					HfResp hfResp = hfRespMapper.selectByPrimaryKey(list.get(i).getRespId());
					if (hfResp != null) {
						list.get(i).setQuantity(hfResp.getQuantity());
					}
				}
			}
		}
		return builder.body(ResponseUtils.getResponseBody(list));
	}

	@ApiOperation(value = "获取商品详情", notes = "根据商品id商品详情")
	@RequestMapping(value = "/getDetail", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "goodsId", value = "商品ID", required = true, type = "Integer"),
			@ApiImplicitParam(paramType = "query", name = "quantity", value = "数量", required = false, type = "Integer") })
	public ResponseEntity<JSONObject> getGoodsDetail(@RequestParam(name = "goodsId") Integer goodsId,
			@RequestParam(name = "quantity") Integer quantity) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);

		HfGoodsDisplayInfo hfGoods = hfGoodsDisplayDao.selectHfGoods(goodsId);
		HfGoodsPictrueExample example = new HfGoodsPictrueExample();
		example.createCriteria().andGoodsIdEqualTo(goodsId);
		List<HfGoodsPictrue> hfProductPictrues = hfGoodsPictrueMapper.selectByExample(example);
		List<Integer> fileIds = hfProductPictrues.stream().map(HfGoodsPictrue::getFileId).collect(Collectors.toList());
		hfGoods.setFileIds(fileIds);

		List<HfGoodsSpecDisplay> goodsSpec = hfGoodsDisplayDao.selectHfGoodsSpec(goodsId);
		hfGoods.setHfGoodsSpecs(goodsSpec);
		if (quantity > 0) {
			hfGoods.setSellPrice(hfGoods.getSellPrice() * quantity);
		}
		return builder.body(ResponseUtils.getResponseBody(hfGoods));
	}

	@ApiOperation(value = "获取商品详情", notes = "根据商品id商品详情")
	@RequestMapping(value = "/getGoodDetailByProductId", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "productId", value = "商品ID", required = true, type = "Integer"),
			@ApiImplicitParam(paramType = "query", name = "quantity", value = "数量", required = false, type = "Integer") })
	public ResponseEntity<JSONObject> getGoodDetailByProductId(@RequestParam(name = "productId") Integer productId,
			@RequestParam(name = "quantity") Integer quantity, Integer stoneId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);

		List<HfGoodsDisplayInfo> hfGoodsDisplay = hfGoodsDisplayDao.selectHfGoodsDisplay(productId);

		HfActivityProductExample hfActivityProductExample = new HfActivityProductExample();
		List<String> type = new ArrayList<String>();
		type.add("groupActivity");
		type.add("seckillActivity");
		ProductInstanceExample instanceExample = new ProductInstanceExample();
		instanceExample.createCriteria().andProductIdEqualTo(productId).andStoneIdEqualTo(stoneId);
		List<ProductInstance> instance = productInstanceMapper.selectByExample(instanceExample);
		hfActivityProductExample.createCriteria().andInstanceIdEqualTo(instance.get(0).getId())
				.andProductActivityTypeIn(type);
		List<HfActivityProduct> list = hfActivityProductMapper.selectByExample(hfActivityProductExample);

		for (int i = 0; i < hfGoodsDisplay.size(); i++) {
			if (!list.isEmpty()) {
				if (list.get(0).getFavoravlePrice() != null && list.get(0).getFavoravlePrice() != 0) {
					String s = String.valueOf(
							Integer.valueOf(hfGoodsDisplay.get(i).getSellPrice()) - list.get(0).getFavoravlePrice());
					if (null != s && s.indexOf(".") > 0) {
						s = s.replaceAll("0+?$", "");// 去掉多余的0
						s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
					}
					hfGoodsDisplay.get(i).setSellPrice(Integer.valueOf(s));
				} else {
					if (list.get(0).getDiscountRatio() != null) {
						if (list.get(0).getDiscountRatio() != 0) {
							String s = String.valueOf(Double.valueOf(hfGoodsDisplay.get(i).getSellPrice())
									* (list.get(0).getDiscountRatio() / 100));
							if (null != s && s.indexOf(".") > 0) {
								s = s.replaceAll("0+?$", "");// 去掉多余的0
								s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
							}
							hfGoodsDisplay.get(i).setSellPrice(Integer.valueOf(s));
						}
					}
				}
			}
		}

		List<Integer> goodsIds = hfGoodsDisplay.stream().map(HfGoodsDisplayInfo::getId).collect(Collectors.toList());

		List<HfGoodsSpecDisplay> goodsSpec = hfGoodsDisplayDao.selectHfGoodsSpec(goodsIds);
		Map<Integer, List<HfGoodsSpecDisplay>> hfGoodsSpecDisplayMap = goodsSpec.stream()
				.collect(Collectors.toMap(HfGoodsSpecDisplay::getGoodsId, item -> Lists.newArrayList(item),
						(List<HfGoodsSpecDisplay> oldList, List<HfGoodsSpecDisplay> newList) -> {
							oldList.addAll(newList);
							return oldList;
						}));

		hfGoodsDisplay.forEach(item -> item.setHfGoodsSpecs(hfGoodsSpecDisplayMap.get(item.getId())));

		HfGoodsPictrueExample example = new HfGoodsPictrueExample();
		example.createCriteria().andGoodsIdIn(goodsIds);
		List<HfGoodsPictrue> hfProductPictrues = hfGoodsPictrueMapper.selectByExample(example);
		Map<Integer, List<Integer>> hfGoodsPictureMap = hfProductPictrues.stream()
				.collect(Collectors.toMap(HfGoodsPictrue::getGoodsId, item -> Lists.newArrayList(item.getFileId()),
						(List<Integer> oldList, List<Integer> newList) -> {
							oldList.addAll(newList);
							return oldList;
						}));

		hfGoodsDisplay.forEach(item -> item.setFileIds(hfGoodsPictureMap.get(item.getId())));
		if (quantity > 0) {
			hfGoodsDisplay.forEach(item -> item.setSellPrice(item.getSellPrice() * quantity));
		}

		return builder.body(ResponseUtils.getResponseBody(hfGoodsDisplay));
	}

	@ApiOperation(value = "删除物品", notes = "删除物品")
	@RequestMapping(value = "/deleteGoods", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> deleteGoods(@RequestParam(name = "goodsId") List<Integer> goodsId)
			throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		goodsId.forEach(item -> {
			System.out.println(item);
			HfGoods hfGoods = hfGoodsMapper.selectByPrimaryKey(item);
			if (hfGoods == null) {
				try {
					throw new Exception("物品不存在");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			hfGoodsMapper.deleteByPrimaryKey(item);
			if (hfGoods.getPriceId() != null) {
				hfPriceMapper.deleteByPrimaryKey(hfGoods.getPriceId());
			}
			if (hfGoods.getRespId() != null) {
				hfRespMapper.deleteByPrimaryKey(hfGoods.getRespId());
			}
		});
		return builder.body(ResponseUtils.getResponseBody(0));
	}

	@ApiOperation(value = "删除图片", notes = "删除图片根据文件id")
	@RequestMapping(value = "/deleteGoodsFile", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "fileId", value = "文件id", required = true, type = "Integer") })
	public void deleteGoodsFile(@RequestParam(name = "fileId") Integer fileId) throws Exception {
		HfGoodsPictrueExample example = new HfGoodsPictrueExample();
		example.createCriteria().andFileIdEqualTo(fileId);
		FileDesc fileDesc = fileDescMapper.selectByPrimaryKey(fileId);
		FileMangeService fileManageService = new FileMangeService();
		if (fileDesc != null) {
			fileManageService.deleteFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
			hfGoodsPictrueMapper.deleteByExample(example);
			fileDescMapper.deleteByPrimaryKey(fileDesc.getId());
		}
	}

	@ApiOperation(value = "查询待评价物品", notes = "查询待评价物品")
	@RequestMapping(value = "/selectEvaluateGoods", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> selectEvaluateGoods(Integer userId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfOrderExample example = new HfOrderExample();
		example.createCriteria().andUserIdEqualTo(userId).andOrderStatusEqualTo("evaluate");
		List<HfOrder> list = hfOrderMapper.selectByExample(example);
		List<Integer> orderId = list.stream().map(HfOrder::getId).collect(Collectors.toList());
		HfOrderDetailExample example2 = new HfOrderDetailExample();
		example2.createCriteria().andOrderIdIn(orderId).andHfStatusEqualTo("evaluate");
		example2.setOrderByClause("create_time DESC");
		List<HfOrderDetail> list2 = hfOrderDetailMapper.selectByExample(example2);
		return builder.body(ResponseUtils.getResponseBody(list2));
	}

	@ApiOperation(value = "添加评价", notes = "添加评价", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
	@RequestMapping(value = "/addEvaluateProduct", method = RequestMethod.POST, consumes = "multipart/*", headers = {"content-type=multipart/form-data","application/x-www-form-urlencoded"})
	public ResponseEntity<JSONObject> addEvaluateProduct(Integer orderDetailId, Integer userId, Integer goodId,
			Integer stoneId, Integer star, String evaluate, @RequestParam("file") MultipartFile[] file) throws Exception {
		
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		System.out.println("111111111111111"+file);
		
		HfGoods goods = hfGoodsMapper.selectByPrimaryKey(goodId);
		ProductInstanceExample example = new ProductInstanceExample();
		example.createCriteria().andProductIdEqualTo(goods.getProductId()).andStoneIdEqualTo(stoneId);
		List<ProductInstance> instance = productInstanceMapper.selectByExample(example);
		HfEvaluate hfEvaluate = new HfEvaluate();
		hfEvaluate.setEvaluate(evaluate);
		hfEvaluate.setInstanceId(instance.get(0).getId());
		hfEvaluate.setOrderDetailId(orderDetailId);
		hfEvaluate.setPraise(0);
		hfEvaluate.setCommentCount(0);
		hfEvaluate.setUserId(userId);
		hfEvaluate.setStar(star);
		hfEvaluate.setCreateTime(LocalDateTime.now());
		hfEvaluate.setModifyTime(LocalDateTime.now());
		hfEvaluate.setIsDeleted((byte) 0);
		hfEvaluateMapper.insert(hfEvaluate);
			for (MultipartFile f : file) {
				System.out.println("插入图片");
				String arr[];
				FileMangeService fileMangeService = new FileMangeService();
				arr = fileMangeService.uploadFile(f.getBytes(), String.valueOf(userId));
				FileDesc fileDesc = new FileDesc();
				fileDesc.setFileName(f.getName());
				fileDesc.setGroupName(arr[0]);
				fileDesc.setRemoteFilename(arr[1]);
				fileDesc.setUserId(userId);
				fileDesc.setCreateTime(LocalDateTime.now());
				fileDesc.setModifyTime(LocalDateTime.now());
				fileDesc.setIsDeleted((short) 0);
				fileDescMapper.insert(fileDesc);
				EvaluatePicture picture = new EvaluatePicture();
				picture.setEvaluate(hfEvaluate.getId());
				picture.setFileId(fileDesc.getId());
				picture.setHfDesc("评价图片描述");
				picture.setHfName("评价图片");
				picture.setCreateTime(LocalDateTime.now());
				picture.setModifieyTime(LocalDateTime.now());
				picture.setIsDeleted((byte) 0);
				evaluatePictureMapper.insert(picture);
			}
		HfOrderDetail detail = hfOrderDetailMapper.selectByPrimaryKey(orderDetailId);
		detail.setHfStatus("complete");
		hfOrderDetailMapper.updateByPrimaryKey(detail);
		HfOrderDetailExample example2 = new HfOrderDetailExample();
		example2.createCriteria().andOrderIdEqualTo(detail.getOrderId()).andHfStatusEqualTo("evaluate");
		if (hfOrderDetailMapper.selectByExample(example2).isEmpty()) {
			HfOrder hfOrder = hfOrderMapper.selectByPrimaryKey(detail.getOrderId());
			hfOrder.setOrderStatus("complete");
			hfOrderMapper.updateByPrimaryKey(hfOrder);
		}
		return builder.body(ResponseUtils.getResponseBody(hfEvaluate.getId()));
	}

	@ApiOperation(value = "查询实体得评价", notes = "查询实体得评价")
	@RequestMapping(value = "/selectInstanceEvaluate", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> selectInstanceEvaluate(Integer instanceId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		List<EvaluateEntity> rs = new ArrayList<EvaluateEntity>();
		HfEvaluateExample example = new HfEvaluateExample();
		example.createCriteria().andInstanceIdEqualTo(instanceId);
		List<HfEvaluate> result = hfEvaluateMapper.selectByExample(example);
		EvaluateInstanceExample instanceExample = new EvaluateInstanceExample();
		List<EvaluateInstance> instances = new ArrayList<EvaluateInstance>();
		EvaluatePictureExample pictureExample = new EvaluatePictureExample();
		List<EvaluatePicture> pictures = new ArrayList<EvaluatePicture>();
		List<Integer> pictureId = new ArrayList<Integer>();
		EvluateInstancePictureExample instancePictureExample = new EvluateInstancePictureExample();
		List<EvluateInstancePicture> instancePictures = new ArrayList<EvluateInstancePicture>();
		List<Integer> instancePictureId = new ArrayList<Integer>();
		for (int i = 0; i < result.size(); i++) {
			Evaluate e = new Evaluate();
			List<Evaluate> es = new ArrayList<Evaluate>();
			EvaluateEntity entity = new EvaluateEntity();
			HfEvaluate evaluate = result.get(i);
			instanceExample.clear();
			pictureExample.clear();
			instanceExample.createCriteria().andParentEvaluateIdEqualTo(evaluate.getId());
			instances = evaluateInstanceMapper.selectByExample(instanceExample);
			e.setUserId(evaluate.getUserId());
			e.setStar(evaluate.getStar());
			e.setComment(evaluate.getEvaluate());
			e.setComment_count(evaluate.getCommentCount());
			e.setTime(evaluate.getCreateTime());
			pictureExample.createCriteria().andEvaluateEqualTo(evaluate.getId());
			pictures = evaluatePictureMapper.selectByExample(pictureExample);
			pictureId = pictures.stream().map(EvaluatePicture::getId).collect(Collectors.toList());
			e.setFileId(pictureId);
			entity.setParentEvaluate(e);
			for (int j = 0; j < instances.size(); j++) {
				EvaluateInstance instance = instances.get(j);
				Evaluate e1 = new Evaluate();
				e1.setOutId(instance.getOutEvaluateId());
				e1.setInId(instance.getInEvaluateId());
				e1.setComment(instance.getEvaluateContent());
				e1.setOutName(manualDao.select(instance.getOutEvaluateId()).getNickName());
				e1.setInName(manualDao.select(instance.getInEvaluateId()).getNickName());
				e1.setTime(instance.getCreateTime());
				instancePictureExample.clear();
				instancePictureExample.createCriteria().andEvaluateInstanceIdEqualTo(instance.getId());
				instancePictures = evluateInstancePictureMapper.selectByExample(instancePictureExample);
				instancePictureId = instancePictures.stream().map(EvluateInstancePicture::getFileId)
						.collect(Collectors.toList());
				e1.setFileId(instancePictureId);
				es.add(e1);
			}
			entity.setChildEvaluate(es);
			rs.add(entity);
		}
		return builder.body(ResponseUtils.getResponseBody(rs));
	}

	@ApiOperation(value = "给评价点赞", notes = "给评价点赞")
	@RequestMapping(value = "/addEvaluatePraise", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addEvaluatePraise(Integer id, Integer userId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		if (manualDao.select(userId) == null) {
			return builder.body(ResponseUtils.getResponseBody("-1"));
		}
		HfEvaluate evaluate = hfEvaluateMapper.selectByPrimaryKey(id);
		evaluate.setPraise(evaluate.getPraise() + 1);
		hfEvaluateMapper.updateByPrimaryKey(evaluate);
		return builder.body(ResponseUtils.getResponseBody(evaluate.getId()));
	}

	@ApiOperation(value = "回复评价", notes = "回复评价")
	@RequestMapping(value = "/replyEvaluate", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> replyEvaluate(Integer evaluateId, Integer outId, Integer inId, String evaluate,
			@RequestPart(required = false) MultipartFile[] file) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		EvaluateInstance evaluateInstance = new EvaluateInstance();
		evaluateInstance.setInEvaluateId(evaluateId);
		evaluateInstance.setOutEvaluateId(outId);
		evaluateInstance.setInEvaluateId(inId);
		evaluateInstance.setEvaluateContent(evaluate);
		evaluateInstance.setCreateTime(LocalDateTime.now());
		evaluateInstance.setModifyTime(LocalDateTime.now());
		evaluateInstance.setIsDeleted((byte) 0);
		evaluateInstanceMapper.insert(evaluateInstance);
		HfEvaluate hfEvaluate = hfEvaluateMapper.selectByPrimaryKey(evaluateId);
		hfEvaluate.setCommentCount(hfEvaluate.getCommentCount() + 1);
		hfEvaluateMapper.updateByPrimaryKey(hfEvaluate);
		for (MultipartFile f : file) {
			String arr[];
			FileMangeService fileMangeService = new FileMangeService();
			arr = fileMangeService.uploadFile(f.getBytes(), String.valueOf(outId));
			FileDesc fileDesc = new FileDesc();
			fileDesc.setFileName(f.getName());
			fileDesc.setGroupName(arr[0]);
			fileDesc.setRemoteFilename(arr[1]);
			fileDesc.setCreateTime(LocalDateTime.now());
			fileDesc.setModifyTime(LocalDateTime.now());
			fileDesc.setIsDeleted((short) 0);
			fileDescMapper.insert(fileDesc);
			EvluateInstancePicture picture = new EvluateInstancePicture();
			picture.setEvaluateInstanceId(evaluateId);
			picture.setFileId(fileDesc.getId());
			picture.setHfName("评价图片");
			picture.setHfDesc("评价图片描述");
			picture.setCreateTime(LocalDateTime.now());
			picture.setModifyTime(LocalDateTime.now());
			picture.setIsDeleted((byte) 0);
			evluateInstancePictureMapper.insert(picture);
		}
		return builder.body(ResponseUtils.getResponseBody(evaluateInstance.getId()));
	}

}
