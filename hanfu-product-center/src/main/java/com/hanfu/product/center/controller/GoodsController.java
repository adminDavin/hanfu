package com.hanfu.product.center.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hanfu.product.center.dao.*;
import com.hanfu.product.center.manual.dao.*;
import com.hanfu.product.center.manual.model.*;
import com.hanfu.product.center.model.*;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.product.center.dao.FileDescMapper;
import com.hanfu.product.center.manual.model.Evaluate.EvaluateContentTypeEnum;
import com.hanfu.product.center.manual.model.Evaluate.EvaluateTypeEnum;
import com.hanfu.product.center.request.GoodsPictrueRequest;
import com.hanfu.product.center.request.GoodsPriceInfo;
import com.hanfu.product.center.request.GoodsSpecRequest;
import com.hanfu.product.center.request.HfGoodsInfo;
import com.hanfu.product.center.request.RespInfo;
import com.hanfu.product.center.service.GoodsRespService;
import com.hanfu.product.center.service.GoodsService;
import com.hanfu.product.center.service.SpecsService;
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

	private static final String LOCK = "lock";

	private static final String REST_URL_PREFIX = "https://swcloud.tjsichuang.cn:1445/api/user/";

	private static final String MODIFY_ORDER_PREFIX = "https://swcloud.tjsichuang.cn:1445/api/order/";

	@Autowired
	RestTemplate restTemplate;

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

	@Autowired
	private HfActivityMapper hfActivityMapper;

	@Autowired
	private EvaluateUserRecordMapper evaluateUserRecordMapper;

	@Autowired
	private HfIconMapper hfIconMapper;

	@Autowired
	private HfFocusPeopleMapper hfFocusPeopleMapper;

	@Autowired
	private HfEvaluateCollectMapper hfEvaluateCollectMapper;

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
	@Transactional(rollbackFor = {RuntimeException.class, Error.class})
	public ResponseEntity<JSONObject> createGood(HfGoodsInfo hfGoodsInfo, HttpServletRequest requests)
			throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoods record = new HfGoods();
		record.setProductId(hfGoodsInfo.getProductId());
		Product product = productMapper.selectByPrimaryKey(hfGoodsInfo.getProductId());
		Integer bossId = 1, stoneId = 1;
		ServletContext sc;
		if ("boss".equals((sc = requests.getServletContext()).getAttribute("getServletContextType"))) {
			bossId = (Integer) requests.getServletContext().getAttribute("getServletContext");
		}
		if ("stone".equals(sc.getAttribute("getServletContextType"))) {
			stoneId = (Integer) requests.getServletContext().getAttribute("getServletContext");
			bossId = hfStoneMapper.selectByPrimaryKey(stoneId).getBossId();
		}
		record.setBossId(bossId);
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
		List<goodsSpecs> goodsSpecsList = new ArrayList<>();
		productSpecs.stream().forEach(spec -> {
			HfGoodsSpecExample example = new HfGoodsSpecExample();
			example.createCriteria().andGoodsIdEqualTo(goodsId).andHfSpecIdEqualTo(String.valueOf(spec.getId()));
			List<HfGoodsSpec> items = hfGoodsSpecMapper.selectByExample(example);
			if (!items.isEmpty()) {
				spec.setSpecValue(items.get(0).getHfValue());
			}
			goodsSpecs goodsSpecs = new goodsSpecs();
			goodsSpecs.setId(spec.getId());
			goodsSpecs.setCategorySpecId(spec.getCategorySpecId());
			goodsSpecs.setHfName(spec.getHfName());
			goodsSpecs.setIsDeleted(spec.getIsDeleted());
			goodsSpecs.setProductId(spec.getProductId());
			goodsSpecs.setShow(true);
			goodsSpecs.setSpecType(spec.getSpecType());
			goodsSpecs.setSpecUnit(spec.getSpecUnit());
			goodsSpecs.setSpecValue(spec.getSpecValue());
			goodsSpecsList.add(goodsSpecs);
		});
		return builder.body(ResponseUtils.getResponseBody(goodsSpecsList));
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
	@Transactional(rollbackFor = {RuntimeException.class, Error.class})
	public ResponseEntity<JSONObject> updateGood(
//			@RequestParam("fileInfo1") MultipartFile[] fileInfo1,
			HfGoodsDisplay hfGoodsDisplay) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);

//		List<HfGoodsPictrue> pictures = Lists.newArrayList();
//		FileMangeService fileMangeService = new FileMangeService();
//		String arr[];
//		for (MultipartFile fileInfo : fileInfo1) {
//			arr = fileMangeService.uploadFile(fileInfo.getBytes(), String.valueOf(hfGoodsDisplay.getUserId()));
//			FileDesc fileDesc = new FileDesc();
//			fileDesc.setFileName(fileInfo.getName());
//			fileDesc.setGroupName(arr[0]);
//			fileDesc.setRemoteFilename(arr[1]);
//			fileDesc.setUserId(hfGoodsDisplay.getUserId());
//			fileDesc.setCreateTime(LocalDateTime.now());
//			fileDesc.setModifyTime(LocalDateTime.now());
//			fileDesc.setIsDeleted((short) 0);
//			fileDescMapper.insert(fileDesc);
//			HfGoodsPictrue picture = new HfGoodsPictrue();
//			picture.setFileId(fileDesc.getId());
//			picture.setGoodsId(hfGoodsDisplay.getId());
//			picture.setHfName(fileInfo.getName());
//			picture.setSpecDesc(hfGoodsDisplay.getPrictureDesc());
//			picture.setCreateTime(LocalDateTime.now());
//			picture.setModifyTime(LocalDateTime.now());
//			picture.setLastModifier(hfGoodsDisplay.getUsername());
//			picture.setIsDeleted((short) 0);
//			hfGoodsPictrueMapper.insert(picture);
//			pictures.add(picture);
//		}
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
		GoodsPriceInfo goodsPriceInfo = new GoodsPriceInfo();
		goodsPriceInfo.setHfGoodsId(hfGoods.getId());
		goodsPriceInfo.setSellPrice(hfGoodsDisplay.getSellPrice());
		goodsPriceInfo.setQuantity(hfGoodsDisplay.getQuantity());
		if (hfGoodsDisplay.getSellPrice()==null){
			hfGoodsDisplay.setSellPrice(1);
		}
		if (hfGoodsDisplay.getQuantity()==null){
			hfGoodsDisplay.setQuantity(1);
		}
			setGoodsPrice(goodsPriceInfo);
		return builder.body(ResponseUtils.getResponseBody(hfGoodsMapper.updateByPrimaryKey(hfGoods)));
	}

	@ApiOperation(value = "添加物品规格", notes = "添加物品规格")
	@RequestMapping(value = "/addSpecify", method = RequestMethod.POST)
	@Transactional(rollbackFor = {RuntimeException.class, Error.class})
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
	@Transactional(rollbackFor = {RuntimeException.class, Error.class})
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
	@Transactional(rollbackFor = {RuntimeException.class, Error.class})
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
	@Transactional(rollbackFor = {RuntimeException.class, Error.class})
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
			hfRespMapper.updateByPrimaryKey(resp);
			respId = resp.getId();
		}
		return builder.body(ResponseUtils.getResponseBody(price.getId()));
	}

	@ApiOperation(value = "设置物品数量", notes = "设置物品数量")
	@RequestMapping(value = "/setGoodsQuantity", method = RequestMethod.POST)
	@Transactional(rollbackFor = {RuntimeException.class, Error.class})
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
	@Transactional(rollbackFor = {RuntimeException.class, Error.class})
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
			hfGoods.setSellPrice(String.valueOf(Double.valueOf(hfGoods.getSellPrice()) * quantity));
		}
		return builder.body(ResponseUtils.getResponseBody(hfGoods));
	}

	@ApiOperation(value = "获取商品详情", notes = "根据商品id商品详情")
	@RequestMapping(value = "/getGoodDetailByProductId", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "productId", value = "商品ID", required = true, type = "Integer"),
			@ApiImplicitParam(paramType = "query", name = "quantity", value = "数量", required = false, type = "Integer") })
	public ResponseEntity<JSONObject> getGoodDetailByProductId(@RequestParam(name = "productId") Integer productId,
			@RequestParam(name = "quantity") Integer quantity, Integer stoneId, Integer activityId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);

		List<HfGoodsDisplayInfo> hfGoodsDisplay = hfGoodsDisplayDao.selectHfGoodsDisplay(productId);

		if (activityId != null && activityId != 0) {
			HfActivity activity = hfActivityMapper.selectByPrimaryKey(activityId);
			if ("groupActivity".equals(activity.getActivityType())
					|| "seckillActivity".equals(activity.getActivityType())) {
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
							String s = String.valueOf(Integer.valueOf(hfGoodsDisplay.get(i).getSellPrice())
									- list.get(0).getFavoravlePrice());
							if (null != s && s.indexOf(".") > 0) {
								s = s.replaceAll("0+?$", "");// 去掉多余的0
								s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
							}
							hfGoodsDisplay.get(i).setSellPrice(s);
						} else {
							if (list.get(0).getDiscountRatio() != null) {
								if (list.get(0).getDiscountRatio() != 0) {
									String s = String.valueOf(Double.valueOf(hfGoodsDisplay.get(i).getSellPrice())
											* (list.get(0).getDiscountRatio() / 100));
									if (null != s && s.indexOf(".") > 0) {
										s = s.replaceAll("0+?$", "");// 去掉多余的0
										s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
										System.out.println("ssssssssssssss" + s);
									}
									hfGoodsDisplay.get(i).setSellPrice(s);
								}
							}
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
			hfGoodsDisplay
					.forEach(item -> item.setSellPrice(String.valueOf(Double.valueOf(item.getSellPrice()) * quantity)));
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

//	@ApiOperation(value = "查询待评价物品", notes = "查询待评价物品")
//	@RequestMapping(value = "/selectEvaluateGoods", method = RequestMethod.GET)
//	public ResponseEntity<JSONObject> selectEvaluateGoods(Integer userId) throws Exception {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		List<HfOrderDetail> list2 = new ArrayList<HfOrderDetail>();
//		HfOrderExample example = new HfOrderExample();
//		example.createCriteria().andUserIdEqualTo(userId).andOrderStatusEqualTo("evaluate");
//		List<HfOrder> list = hfOrderMapper.selectByExample(example);
//		if (!list.isEmpty()) {
//			List<Integer> orderId = list.stream().map(HfOrder::getId).collect(Collectors.toList());
//			HfOrderDetailExample example2 = new HfOrderDetailExample();
//			example2.createCriteria().andOrderIdIn(orderId).andHfStatusEqualTo("evaluate");
//			example2.setOrderByClause("create_time DESC");
//			list2 = hfOrderDetailMapper.selectByExample(example2);
//		}
//		return builder.body(ResponseUtils.getResponseBody(list2));
//	}
//
//	@ApiOperation(value = "查询已经评价物品", notes = "查询已经评价物品")
//	@RequestMapping(value = "/selectEvaluateCompleteGoods", method = RequestMethod.GET)
//	public ResponseEntity<JSONObject> selectEvaluateCompleteGoods(Integer userId) throws Exception {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		List<String> status = new ArrayList<String>();
//		status.add("complete");
//		status.add("evaluate");
//		List<HfOrderDetail> list2 = new ArrayList<HfOrderDetail>();
//		List<Evaluate> result = new ArrayList<Evaluate>();
//		HfEvaluateExample evaluateExample = new HfEvaluateExample();
//		HfOrderExample example = new HfOrderExample();
//		example.createCriteria().andUserIdEqualTo(userId).andOrderStatusIn(status);
//		List<HfOrder> list = hfOrderMapper.selectByExample(example);
//		if (!CollectionUtils.isEmpty(list)) {
//			List<Integer> orderId = list.stream().map(HfOrder::getId).collect(Collectors.toList());
//			HfOrderDetailExample example2 = new HfOrderDetailExample();
//			example2.createCriteria().andOrderIdIn(orderId).andHfStatusEqualTo("complete");
//			example2.setOrderByClause("create_time DESC");
//			list2 = hfOrderDetailMapper.selectByExample(example2);
//		}
//		for (int i = 0; i < list2.size(); i++) {
//			evaluateExample.clear();
//			Evaluate evaluate = new Evaluate();
//			evaluate.setList(list2.get(i));
//			evaluateExample.createCriteria().andOrderDetailIdEqualTo(list2.get(i).getId());
//			if (!hfEvaluateMapper.selectByExample(evaluateExample).isEmpty()) {
//				HfEvaluate hfEvaluate = hfEvaluateMapper.selectByExample(evaluateExample).get(0);
//				evaluate.setComment(hfEvaluate.getEvaluate());
//				evaluate.setStar(hfEvaluate.getStar());
//				evaluate.setId(hfEvaluate.getId());
//				evaluate.setTime(hfEvaluate.getCreateTime());
//			}
//			result.add(evaluate);
//		}
//		result = result.stream().filter(r -> r.getId() != null).collect(Collectors.toList());
//		return builder.body(ResponseUtils.getResponseBody(result));
//	}
//
//	@ApiOperation(value = "添加评价", notes = "添加评价")
//	@RequestMapping(value = "/addEvaluateProduct", method = RequestMethod.POST)
//
//	public ResponseEntity<JSONObject> addEvaluateProduct(DiscoverDisplay dis, String type, String typeContent,
//			Integer orderDetailId, Integer userId, Integer goodId, Integer stoneId, Integer star, String evaluate,
//			Integer... fileId) throws Exception {
//
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		HfEvaluate hfEvaluate = new HfEvaluate();
//		if (goodId != null && stoneId != null) {
//			HfGoods goods = hfGoodsMapper.selectByPrimaryKey(goodId);
//			System.out.println("物品" + goods + "-----------" + "店铺id" + stoneId);
//			ProductInstanceExample example = new ProductInstanceExample();
//			example.createCriteria().andProductIdEqualTo(goods.getProductId()).andStoneIdEqualTo(stoneId);
//			List<ProductInstance> instance = productInstanceMapper.selectByExample(example);
//			hfEvaluate.setInstanceId(instance.get(0).getId());
//			
//			if (EvaluateTypeEnum.EVALUATE.getEvaluateType().equals(type)) {
//				hfEvaluate.setOrderDetailId(orderDetailId);
//				hfEvaluate.setStar(star);
//				if (instance.get(0).getEvaluateCount() == null) {
//					instance.get(0).setEvaluateCount(1);
//				} else {
//					instance.get(0).setEvaluateCount(instance.get(0).getEvaluateCount() + 1);
//				}
//				productInstanceMapper.updateByPrimaryKey(instance.get(0));
//				HfOrderDetail detail = hfOrderDetailMapper.selectByPrimaryKey(orderDetailId);
//				detail.setHfStatus("complete");
//				hfOrderDetailMapper.updateByPrimaryKey(detail);
//				HfOrderDetailExample example2 = new HfOrderDetailExample();
//				example2.createCriteria().andOrderIdEqualTo(detail.getOrderId()).andHfStatusEqualTo("evaluate");
//				if (hfOrderDetailMapper.selectByExample(example2).isEmpty()) {
//					HfOrder hfOrder = hfOrderMapper.selectByPrimaryKey(detail.getOrderId());
//					restTemplate.getForEntity(MODIFY_ORDER_PREFIX + "/hf-order/modifyStatus?Id={Id}"
//							+ "&orderCode={orderCode}&originOrderStatus={originOrderStatus}&targetOrderStatus={targetOrderStatus}&stoneId={stoneId}",
//							String.class, detail.getOrderId(), hfOrder.getOrderCode(), "evaluate", "complete", stoneId);
//				}
//			}
//
//		}
//		hfEvaluate.setType(EvaluateTypeEnum.getEvaluateTypeEnum(type).getEvaluateType());
//		hfEvaluate.setTypeContent(
//				EvaluateContentTypeEnum.getEvaluateContentTypeEnum(typeContent).getEvaluateContentType());
//		hfEvaluate.setEvaluate(evaluate);
//		hfEvaluate.setPraise(0);
//		hfEvaluate.setCommentCount(0);
//		hfEvaluate.setTransmit(0);
//		hfEvaluate.setUserId(userId);
//		hfEvaluate.setCreateTime(LocalDateTime.now());
//		hfEvaluate.setModifyTime(LocalDateTime.now());
//		hfEvaluate.setIsDeleted((byte) 0);
//		hfEvaluateMapper.insert(hfEvaluate);
//		for (Integer f : fileId) {
//			EvaluatePicture picture = new EvaluatePicture();
//			picture.setEvaluate(hfEvaluate.getId());
//			picture.setFileId(f);
//			picture.setHfDesc("评价图片描述");
//			picture.setHfName("评价图片");
//			picture.setCreateTime(LocalDateTime.now());
//			picture.setModifieyTime(LocalDateTime.now());
//			picture.setIsDeleted((byte) 0);
//			evaluatePictureMapper.insert(picture);
//		}
//		return builder.body(ResponseUtils.getResponseBody(hfEvaluate.getId()));
//	}
//
//	@ApiOperation(value = "查询实体得评价", notes = "查询实体得评价")
//	@RequestMapping(value = "/selectInstanceEvaluate", method = RequestMethod.GET)
//	public ResponseEntity<JSONObject> selectInstanceEvaluate(Integer userId, Integer id, Integer stoneId,
//			Integer productId, Integer pageNum, Integer pageSize) throws Exception {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		if (pageNum == null) {
//			pageNum = 0;
//		}
//		if (pageSize == null) {
//			pageSize = 0;
//		}
//		List<EvaluateEntity> rs = new ArrayList<EvaluateEntity>();
//		HfEvaluateExample example = new HfEvaluateExample();
//		EvaluateInstanceExample instanceExample = new EvaluateInstanceExample();
//		List<EvaluateInstance> instances = new ArrayList<EvaluateInstance>();
//		EvaluatePictureExample pictureExample = new EvaluatePictureExample();
//		List<EvaluatePicture> pictures = new ArrayList<EvaluatePicture>();
//		List<Integer> pictureId = new ArrayList<Integer>();
//		EvluateInstancePictureExample instancePictureExample = new EvluateInstancePictureExample();
//		List<EvluateInstancePicture> instancePictures = new ArrayList<EvluateInstancePicture>();
//		List<HfEvaluate> result = new ArrayList<HfEvaluate>();
//		List<Integer> instancePictureId = new ArrayList<Integer>();
//		EvaluateUserRecordExample recordExample = new EvaluateUserRecordExample();
//
//		if (id == null) {
//			ProductInstanceExample productInstanceExample = new ProductInstanceExample();
//			productInstanceExample.createCriteria().andStoneIdEqualTo(stoneId).andProductIdEqualTo(productId);
//			List<ProductInstance> instanceList = productInstanceMapper.selectByExample(productInstanceExample);
//			example.createCriteria().andInstanceIdEqualTo(instanceList.get(0).getId());
//			PageHelper.startPage(pageNum, pageSize);
//			result = hfEvaluateMapper.selectByExample(example);
//		} else {
//			HfEvaluate evaluate = hfEvaluateMapper.selectByPrimaryKey(id);
//			result.add(evaluate);
//		}
//
//		for (int i = 0; i < result.size(); i++) {
//			Evaluate e = new Evaluate();
//			List<Evaluate> es = new ArrayList<Evaluate>();
//			EvaluateEntity entity = new EvaluateEntity();
//			HfEvaluate evaluate = result.get(i);
//			instanceExample.clear();
//			pictureExample.clear();
//			instanceExample.createCriteria().andParentEvaluateIdEqualTo(evaluate.getId());
//			instances = evaluateInstanceMapper.selectByExample(instanceExample);
//			e.setUserId(evaluate.getUserId());
//			if(id == null) {
//				HfOrderDetail detail = hfOrderDetailMapper.selectByPrimaryKey(evaluate.getOrderDetailId());
//				e.setHfDesc(detail.getHfDesc());
//			}
//			JSONObject js = restTemplate.getForObject(REST_URL_PREFIX + "hf-auth/findInfoByUserId?userId={userId}",
//					JSONObject.class, evaluate.getUserId());
//			JSONObject js1 = restTemplate.getForObject(REST_URL_PREFIX + "hf-auth/findUserDetails?userId={userId}",
//					JSONObject.class, evaluate.getUserId());
//			e.setLevelName(js.getJSONObject("data").getString("prerogative"));
//			e.setUsername(js1.getJSONObject("data").getString("nickName"));
//			if(!StringUtils.isEmpty(js1.getJSONObject("data").getString("fileId"))){
//				e.setAvatar(Integer.valueOf(js1.getJSONObject("data").getString("fileId")));
//			}
//			if(userId != null) {
//				recordExample.clear();
//				recordExample.createCriteria().andUserIdEqualTo(userId).andEvaluateEqualTo(evaluate.getId())
//						.andIsDeletedEqualTo((byte) 1).andTypeEqualTo("1");
//				if (!evaluateUserRecordMapper.selectByExample(recordExample).isEmpty()) {
//					e.setIsPraise(1);
//				} else {
//					e.setIsPraise(0);
//				}
//			}
//			
//			e.setId(evaluate.getId());
//			e.setStar(evaluate.getStar());
//			e.setComment(evaluate.getEvaluate());
//			e.setComment_count(evaluate.getCommentCount());
//			e.setPraise(evaluate.getPraise());
//			e.setTransmitCount(evaluate.getTransmit());
//			e.setTime(evaluate.getCreateTime());
//			e.setType(evaluate.getType());
//			e.setTypeContent(evaluate.getTypeContent());
//			pictureExample.createCriteria().andEvaluateEqualTo(evaluate.getId());
//			pictures = evaluatePictureMapper.selectByExample(pictureExample);
//			pictureId = pictures.stream().map(EvaluatePicture::getFileId).collect(Collectors.toList());
//			e.setFileId(pictureId);
//			entity.setParentEvaluate(e);
//			for (int j = 0; j < instances.size(); j++) {
//				EvaluateInstance instance = instances.get(j);
//				Evaluate e1 = new Evaluate();
//				e1.setOutId(instance.getOutEvaluateId());
//				e1.setInId(instance.getInEvaluateId());
//				e1.setComment(instance.getEvaluateContent());
//				e1.setOutName(manualDao.select(instance.getOutEvaluateId()).getNickName());
//				e1.setInName(manualDao.select(instance.getInEvaluateId()).getNickName());
//				e1.setTime(instance.getCreateTime());
//				instancePictureExample.clear();
//				instancePictureExample.createCriteria().andEvaluateInstanceIdEqualTo(instance.getId());
//				instancePictures = evluateInstancePictureMapper.selectByExample(instancePictureExample);
//				instancePictureId = instancePictures.stream().map(EvluateInstancePicture::getFileId)
//						.collect(Collectors.toList());
//				e1.setFileId(instancePictureId);
//				es.add(e1);
//			}
//			entity.setChildEvaluate(es);
//			rs.add(entity);
//		}
//		PageInfo<EvaluateEntity> page = new PageInfo<EvaluateEntity>(rs);
//		return builder.body(ResponseUtils.getResponseBody(page));
//	}
//
//	@ApiOperation(value = "给评价点赞", notes = "给评价点赞")
//	@RequestMapping(value = "/addEvaluatePraise", method = RequestMethod.POST)
//	public ResponseEntity<JSONObject> addEvaluatePraise(Integer id, Integer userId, Integer type) throws Exception {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		HfEvaluate evaluate = hfEvaluateMapper.selectByPrimaryKey(id);
//		JSONObject js1 = restTemplate.getForObject(REST_URL_PREFIX + "hf-auth/findUserDetails?userId={userId}",
//				JSONObject.class, userId);
//		if ("此用户不存在".equals(js1.get("data"))) {
//			return builder.body(ResponseUtils.getResponseBody("-1"));
//		}
//		
//		EvaluateUserRecordExample example = new EvaluateUserRecordExample();
//		example.createCriteria().andUserIdEqualTo(userId).andEvaluateEqualTo(id).andTypeEqualTo(String.valueOf(1));
//		List<EvaluateUserRecord> records = evaluateUserRecordMapper.selectByExample(example);
//		if (!records.isEmpty()) {
//			EvaluateUserRecord record = records.get(0);
//			if (record.getIsDeleted() == (byte) 0) {
//				System.out.println("更改点赞状态");
//				record.setIsDeleted((byte) 1);
//				evaluate.setPraise(evaluate.getPraise()+1);
//				record.setModifyTime(LocalDateTime.now());
//				evaluateUserRecordMapper.updateByPrimaryKey(record);
//				hfEvaluateMapper.updateByPrimaryKey(evaluate);
//				return builder.body(ResponseUtils.getResponseBody(evaluate.getId()));
//			}
//			if (record.getIsDeleted() == (byte) 1) {
//				System.out.println("11111111111");
//				record.setIsDeleted((byte) 0);
//				evaluate.setPraise(evaluate.getPraise()-1);
//				record.setModifyTime(LocalDateTime.now());
//				evaluateUserRecordMapper.updateByPrimaryKey(record);
//				hfEvaluateMapper.updateByPrimaryKey(evaluate);
//				return builder.body(ResponseUtils.getResponseBody(evaluate.getId()));
//			}
//			
//		} else {
//			EvaluateUserRecord record = new EvaluateUserRecord();
//			record.setUserId(userId);
//			record.setEvaluate(id);
//			record.setCreateTime(LocalDateTime.now());
//			record.setModifyTime(LocalDateTime.now());
//			record.setIsDeleted((byte) 1);
//			record.setType(String.valueOf(1));
//			evaluateUserRecordMapper.insert(record);
//			evaluate.setPraise(evaluate.getPraise() + 1);
//			hfEvaluateMapper.updateByPrimaryKey(evaluate);
//		}
//		return builder.body(ResponseUtils.getResponseBody(evaluate.getId()));
//	}
//
//	@ApiOperation(value = "回复评价", notes = "回复评价")
//	@RequestMapping(value = "/replyEvaluate", method = RequestMethod.POST)
//	public ResponseEntity<JSONObject> replyEvaluate(Integer evaluateId, Integer outId, Integer inId, String evaluate,
//			Integer... fileId) throws Exception {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		EvaluateInstance evaluateInstance = new EvaluateInstance();
//		evaluateInstance.setInEvaluateId(evaluateId);
//		evaluateInstance.setOutEvaluateId(outId);
//		evaluateInstance.setInEvaluateId(inId);
//		evaluateInstance.setEvaluateContent(evaluate);
//		evaluateInstance.setCreateTime(LocalDateTime.now());
//		evaluateInstance.setModifyTime(LocalDateTime.now());
//		evaluateInstance.setIsDeleted((byte) 0);
//		evaluateInstanceMapper.insert(evaluateInstance);
//		HfEvaluate hfEvaluate = hfEvaluateMapper.selectByPrimaryKey(evaluateId);
//		hfEvaluate.setCommentCount(hfEvaluate.getCommentCount() + 1);
//		hfEvaluateMapper.updateByPrimaryKey(hfEvaluate);
//
//		for (int i = 0; i < fileId.length; i++) {
//			EvluateInstancePicture picture = new EvluateInstancePicture();
//			picture.setEvaluateInstanceId(evaluateId);
//			picture.setFileId(fileId[i]);
//			picture.setHfName("评价图片");
//			picture.setHfDesc("评价图片描述");
//			picture.setCreateTime(LocalDateTime.now());
//			picture.setModifyTime(LocalDateTime.now());
//			picture.setIsDeleted((byte) 0);
//			evluateInstancePictureMapper.insert(picture);
//		}
//		return builder.body(ResponseUtils.getResponseBody(evaluateInstance.getId()));
//	}
//
////	@ApiOperation(value = "查询单个评价详情", notes = "查询单个评价详情")
////	@RequestMapping(value = "/findEvaluateDetail", method = RequestMethod.GET)
////	public ResponseEntity<JSONObject> findEvaluateDetail(Integer id) throws Exception {
////		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
////		HfEvaluate evaluate = hfEvaluateMapper.selectByPrimaryKey(id);
////		HfOrderDetail detail = hfOrderDetailMapper.selectByPrimaryKey(evaluate.getOrderDetailId());
////		Evaluate evaluateInstance = new Evaluate();
////		evaluateInstance.setList(detail);
////		evaluateInstance.setComment(evaluate.getEvaluate());
////		evaluateInstance.setStar(evaluate.getStar());
////		evaluateInstance.setTime(evaluate.getCreateTime());
////		evaluateInstance.setComment_count(evaluate.getCommentCount());
////		return builder.body(ResponseUtils.getResponseBody(evaluateInstance));
////	}
//
//	@ApiOperation(value = "上传图片", notes = "上传图片")
//	@RequestMapping(value = "/fileUpLoad", method = RequestMethod.POST)
//	public ResponseEntity<JSONObject> fileUpLoad(MultipartFile file) throws Exception {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		String arr[];
//		FileMangeService fileMangeService = new FileMangeService();
//		arr = fileMangeService.uploadFile(file.getBytes(), String.valueOf("-1"));
//		FileDesc fileDesc = new FileDesc();
//		fileDesc.setFileName(file.getName());
//		fileDesc.setGroupName(arr[0]);
//		fileDesc.setRemoteFilename(arr[1]);
//		fileDesc.setUserId(-1);
//		fileDesc.setCreateTime(LocalDateTime.now());
//		fileDesc.setModifyTime(LocalDateTime.now());
//		fileDesc.setIsDeleted((short) 0);
//		fileDescMapper.insert(fileDesc);
//		return builder.body(ResponseUtils.getResponseBody(fileDesc.getId()));
//	}
//
//	@RequestMapping(value = "/selectDiscover", method = RequestMethod.GET)
//	@ApiOperation(value = "根据类型查询评价", notes = "根据类型查询评价")
//	public ResponseEntity<JSONObject> selectDiscover(Integer userId, String type) throws JSONException {
//		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
//		EvaluateUserRecordExample recordExample = new EvaluateUserRecordExample();
//		HfEvaluateExample evaluateExample = new HfEvaluateExample();
//		evaluateExample.createCriteria().andTypeEqualTo(type);
//		List<Evaluate> result = new ArrayList<Evaluate>();
////		List<Integer> productId = new ArrayList<Integer>();
//		List<Integer> fileId = new ArrayList<Integer>();
//		List<HfEvaluate> discovers = hfEvaluateMapper.selectByExample(evaluateExample);
//
//		EvaluatePictureExample pictrueExample = new EvaluatePictureExample();
//		List<EvaluatePicture> pictrues = new ArrayList<EvaluatePicture>();
////		DiscoverProductExample productExample = new DiscoverProductExample();  多个实体之后
////		List<DiscoverProduct> products = new ArrayList<DiscoverProduct>();
//		for (int i = 0; i < discovers.size(); i++) {
//			pictrueExample.clear();
//// 			productExample.clear();
//			HfEvaluate d = discovers.get(i);
//			Evaluate display = new Evaluate();
//			display.setComment(d.getEvaluate());
////			display.setDiscoverDesc(d.getDiscoverDesc());
////			display.setDiscoverHeadline(d.getDiscoverHeadline());
//			if(userId != null) {
//				recordExample.clear();
//				recordExample.createCriteria().andUserIdEqualTo(userId).andEvaluateEqualTo(d.getId())
//						.andIsDeletedEqualTo((byte) 1).andTypeEqualTo("1");
//				if (!evaluateUserRecordMapper.selectByExample(recordExample).isEmpty()) {
//					display.setIsPraise(1);
//				} else {
//					display.setIsPraise(0);
//				}
//			}
//			display.setId(d.getId());
//			display.setType(d.getType());
//			display.setTypeContent(d.getTypeContent());
//			display.setUserId(d.getUserId());
//			JSONObject js1 = restTemplate.getForObject(REST_URL_PREFIX + "hf-auth/findUserDetails?userId={userId}",
//					JSONObject.class, d.getUserId());
//			display.setUsername(js1.getJSONObject("data").getString("nickName"));
//			display.setTime(d.getCreateTime());
//			pictrueExample.createCriteria().andEvaluateEqualTo(d.getId());
//			pictrues = evaluatePictureMapper.selectByExample(pictrueExample);
//			fileId = pictrues.stream().map(EvaluatePicture::getFileId).collect(Collectors.toList());
//			display.setFileId(fileId);
//			display.setComment_count(d.getCommentCount());
//			display.setPraise(d.getPraise());
//			display.setTransmitCount(d.getTransmit());
//			result.add(display);
////			products = discoverProductMapper.selectByExample(productExample);
////			productId = products.stream().map(DiscoverProduct :: getProductId).collect(Collectors.toList());
////			display.setProductId(productId);
////			productExample.createCriteria().andDiscoverIdEqualTo(d.getId());
//		}
//		if (userId != null) {
//
//		}
//		return builder.body(ResponseUtils.getResponseBody(result));
//	}
//
//	@ApiOperation(value = "获取视频", notes = "获取视频")
//	@RequestMapping(value = "/getVedio", method = RequestMethod.GET)
//	@ApiImplicitParams({
//			@ApiImplicitParam(paramType = "query", name = "fileId", value = "文件id", required = true, type = "Integer") })
//	public void getVedio(@RequestParam(name = "fileId") Integer fileId, HttpServletResponse response) throws Exception {
//		response.addHeader("Access-Control-Allow-Origin", "*");
//		response.addHeader("Content-Type", "audio/mp4;charset=UTF-8");
//		FileDesc fileDesc = fileDescMapper.selectByPrimaryKey(fileId);
//		if (fileDesc == null) {
//			throw new Exception("file not exists");
//		}
//		FileMangeService fileManageService = new FileMangeService();
//		synchronized (LOCK) {
//			byte[] file = fileManageService.downloadFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
////            ByteArrayInputStream stream = new ByteArrayInputStream(file);
////            BufferedImage readImg = ImageIO.read(stream);
////            stream.reset();
//			OutputStream outputStream = response.getOutputStream();
////            ImageIO.write(readImg, "png", outputStream);
//			IOUtils.write(file, outputStream);
//			outputStream.close();
//		}
//	}

	@ApiOperation(value = "查询待评价物品", notes = "查询待评价物品")
	@RequestMapping(value = "/selectEvaluateGoods", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> selectEvaluateGoods(Integer userId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		List<HfOrderDetail> list2 = new ArrayList<HfOrderDetail>();
		HfOrderExample example = new HfOrderExample();
		example.createCriteria().andUserIdEqualTo(userId).andOrderStatusEqualTo("evaluate");
		List<HfOrder> list = hfOrderMapper.selectByExample(example);
		if (!list.isEmpty()) {
			List<Integer> orderId = list.stream().map(HfOrder::getId).collect(Collectors.toList());
			HfOrderDetailExample example2 = new HfOrderDetailExample();
			example2.createCriteria().andOrderIdIn(orderId).andHfStatusEqualTo("evaluate");
			example2.setOrderByClause("create_time DESC");
			list2 = hfOrderDetailMapper.selectByExample(example2);
		}
		return builder.body(ResponseUtils.getResponseBody(list2));
	}

	@ApiOperation(value = "查询已经评价物品", notes = "查询已经评价物品")
	@RequestMapping(value = "/selectEvaluateCompleteGoods", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> selectEvaluateCompleteGoods(Integer userId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		List<String> status = new ArrayList<String>();
		status.add("complete");
		status.add("evaluate");
		List<HfOrderDetail> list2 = new ArrayList<HfOrderDetail>();
		List<Evaluate> result = new ArrayList<Evaluate>();
		HfEvaluateExample evaluateExample = new HfEvaluateExample();
		HfOrderExample example = new HfOrderExample();
		example.createCriteria().andUserIdEqualTo(userId).andOrderStatusIn(status);
		List<HfOrder> list = hfOrderMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(list)) {
			List<Integer> orderId = list.stream().map(HfOrder::getId).collect(Collectors.toList());
			HfOrderDetailExample example2 = new HfOrderDetailExample();
			example2.createCriteria().andOrderIdIn(orderId).andHfStatusEqualTo("complete");
			example2.setOrderByClause("create_time DESC");
			list2 = hfOrderDetailMapper.selectByExample(example2);
		}
		for (int i = 0; i < list2.size(); i++) {
			evaluateExample.clear();
			Evaluate evaluate = new Evaluate();
			evaluate.setList(list2.get(i));
			evaluateExample.createCriteria().andOrderDetailIdEqualTo(list2.get(i).getId());
			if (!hfEvaluateMapper.selectByExample(evaluateExample).isEmpty()) {
				HfEvaluate hfEvaluate = hfEvaluateMapper.selectByExample(evaluateExample).get(0);
				evaluate.setComment(hfEvaluate.getEvaluate());
				evaluate.setStar(hfEvaluate.getStar());
				evaluate.setId(hfEvaluate.getId());
				evaluate.setTime(hfEvaluate.getCreateTime());
			}
			result.add(evaluate);
		}
		result = result.stream().filter(r -> r.getId() != null).collect(Collectors.toList());
		return builder.body(ResponseUtils.getResponseBody(result));
	}

	@ApiOperation(value = "添加评价", notes = "添加评价")
	@RequestMapping(value = "/addEvaluateProduct", method = RequestMethod.POST)
	@Transactional(rollbackFor = {RuntimeException.class, Error.class})
	public ResponseEntity<JSONObject> addEvaluateProduct(HttpServletRequest request, String type, String typeContent,
			Integer orderDetailId, Integer userId, Integer goodId, Integer stoneId, Integer star, String evaluate,
			Integer levelId, Integer parentEvaluateId, Integer... fileId) throws Exception {

		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);

		HfEvaluate hfEvaluate = new HfEvaluate();
		if (goodId != null && stoneId != null) {
			HfGoods goods = hfGoodsMapper.selectByPrimaryKey(goodId);
			System.out.println("物品" + goods + "-----------" + "店铺id" + stoneId);
			ProductInstanceExample example = new ProductInstanceExample();
			example.createCriteria().andProductIdEqualTo(goods.getProductId()).andStoneIdEqualTo(stoneId);
			List<ProductInstance> instance = productInstanceMapper.selectByExample(example);
			hfEvaluate.setInstanceId(instance.get(0).getId());

			if (EvaluateTypeEnum.EVALUATE.getEvaluateType().equals(type)) {
				hfEvaluate.setOrderDetailId(orderDetailId);
				hfEvaluate.setStar(star);
				if (instance.get(0).getEvaluateCount() == null) {
					instance.get(0).setEvaluateCount(1);
				} else {
					instance.get(0).setEvaluateCount(instance.get(0).getEvaluateCount() + 1);
				}
				productInstanceMapper.updateByPrimaryKey(instance.get(0));
				HfOrderDetail detail = hfOrderDetailMapper.selectByPrimaryKey(orderDetailId);
				detail.setHfStatus("complete");
				hfOrderDetailMapper.updateByPrimaryKey(detail);
				HfOrderDetailExample example2 = new HfOrderDetailExample();
				example2.createCriteria().andOrderIdEqualTo(detail.getOrderId()).andHfStatusEqualTo("evaluate");
				if (hfOrderDetailMapper.selectByExample(example2).isEmpty()) {
					HfOrder hfOrder = hfOrderMapper.selectByPrimaryKey(detail.getOrderId());
					restTemplate.getForEntity(MODIFY_ORDER_PREFIX + "/hf-order/modifyStatus?Id={Id}"
							+ "&orderCode={orderCode}&originOrderStatus={originOrderStatus}&targetOrderStatus={targetOrderStatus}&stoneId={stoneId}",
							String.class, detail.getOrderId(), hfOrder.getOrderCode(), "evaluate", "complete", stoneId);
				}
			}

		}
		hfEvaluate.setType(EvaluateTypeEnum.getEvaluateTypeEnum(type).getEvaluateType());
		hfEvaluate.setTypeContent(
				EvaluateContentTypeEnum.getEvaluateContentTypeEnum(typeContent).getEvaluateContentType());
		Object bossId = request.getHeader("bossId");
		hfEvaluate.setBossId(Integer.valueOf((String) bossId));
		hfEvaluate.setEvaluate(evaluate);
		hfEvaluate.setLevelId(levelId);
		hfEvaluate.setParentEvaluateId(parentEvaluateId);
		hfEvaluate.setPraise(0);
		hfEvaluate.setCommentCount(0);
		hfEvaluate.setTransmit(0);
		hfEvaluate.setUserId(userId);
		hfEvaluate.setStoneId(stoneId);
		hfEvaluate.setCreateTime(LocalDateTime.now());
		hfEvaluate.setModifyTime(LocalDateTime.now());
		hfEvaluate.setIsDeleted((byte) 0);
		hfEvaluateMapper.insert(hfEvaluate);
		if (parentEvaluateId != -1) {
			HfEvaluate evaluateParent = hfEvaluateMapper.selectByPrimaryKey(parentEvaluateId);
			evaluateParent.setCommentCount(evaluateParent.getCommentCount() + 1);
			hfEvaluateMapper.updateByPrimaryKey(evaluateParent);
		}
		if (fileId != null) {
			for (Integer f : fileId) {
				EvaluatePicture picture = new EvaluatePicture();
				picture.setEvaluate(hfEvaluate.getId());
				picture.setFileId(f);
				picture.setHfDesc("评价图片描述");
				picture.setHfName("评价图片");
				picture.setCreateTime(LocalDateTime.now());
				picture.setModifieyTime(LocalDateTime.now());
				picture.setIsDeleted((byte) 0);
				evaluatePictureMapper.insert(picture);
			}
		}
		return builder.body(ResponseUtils.getResponseBody(hfEvaluate.getId()));
	}

	@ApiOperation(value = "查询实体得评价", notes = "查询实体得评价")
	@RequestMapping(value = "/selectInstanceEvaluate", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> selectInstanceEvaluate(Integer userId, Integer id, Integer stoneId,
			Integer productId, Integer pageNum, Integer pageSize, String type, Integer levelId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		if (pageNum == null) {
			pageNum = 0;
		}
		if (pageSize == null) {
			pageSize = 0;
		}
		List<EvaluateEntity> rs = new ArrayList<EvaluateEntity>();
		HfEvaluateExample example = new HfEvaluateExample();
		EvaluatePictureExample pictureExample = new EvaluatePictureExample();
		List<EvaluatePicture> pictures = new ArrayList<EvaluatePicture>();
		List<Integer> pictureId = new ArrayList<Integer>();
		EvluateInstancePictureExample instancePictureExample = new EvluateInstancePictureExample();
		List<EvluateInstancePicture> instancePictures = new ArrayList<EvluateInstancePicture>();
		List<HfEvaluate> result = new ArrayList<HfEvaluate>();
		List<Integer> instancePictureId = new ArrayList<Integer>();
		EvaluateUserRecordExample recordExample = new EvaluateUserRecordExample();

		if (id == null) {
			ProductInstanceExample productInstanceExample = new ProductInstanceExample();
			productInstanceExample.createCriteria().andStoneIdEqualTo(stoneId).andProductIdEqualTo(productId);
			List<ProductInstance> instanceList = productInstanceMapper.selectByExample(productInstanceExample);
			example.createCriteria().andInstanceIdEqualTo(instanceList.get(0).getId());
			PageHelper.startPage(pageNum, pageSize);
			result = hfEvaluateMapper.selectByExample(example);
		} else {
			HfEvaluate evaluate = hfEvaluateMapper.selectByPrimaryKey(id);
			result.add(evaluate);
		}

		for (int i = 0; i < result.size(); i++) {
			Evaluate e = new Evaluate();
			List<Evaluate> es = new ArrayList<Evaluate>();
			EvaluateEntity entity = new EvaluateEntity();
			HfEvaluate evaluate = result.get(i);
			pictureExample.clear();
			e.setUserId(evaluate.getUserId());
			if (id == null) {
				HfOrderDetail detail = hfOrderDetailMapper.selectByPrimaryKey(evaluate.getOrderDetailId());
				e.setHfDesc(detail.getHfDesc());
			}
			JSONObject js = restTemplate.getForObject(REST_URL_PREFIX + "hf-auth/findInfoByUserId?userId={userId}",
					JSONObject.class, evaluate.getUserId());
			JSONObject js1 = restTemplate.getForObject(REST_URL_PREFIX + "hf-auth/findUserDetails?userId={userId}",
					JSONObject.class, evaluate.getUserId());
			e.setLevelName(js.getJSONObject("data").getString("prerogative"));
			e.setUsername(js1.getJSONObject("data").getString("nickName"));
			if (!StringUtils.isEmpty(js1.getJSONObject("data").getString("fileId"))) {
				e.setAvatar(Integer.valueOf(js1.getJSONObject("data").getString("fileId")));
			}
			if (userId != null) {
				recordExample.clear();
				recordExample.createCriteria().andUserIdEqualTo(userId).andEvaluateEqualTo(evaluate.getId())
						.andIsDeletedEqualTo((byte) 1).andTypeEqualTo(type);
				if (!evaluateUserRecordMapper.selectByExample(recordExample).isEmpty()) {
					e.setIsPraise(1);
				} else {
					e.setIsPraise(0);
				}
			}

			e.setId(evaluate.getId());
			e.setLevelId(evaluate.getLevelId());
			e.setParentEvaluateId(evaluate.getParentEvaluateId());
			e.setStar(evaluate.getStar());
			e.setComment(evaluate.getEvaluate());
			e.setComment_count(evaluate.getCommentCount());
			e.setPraise(evaluate.getPraise());
			e.setTransmitCount(evaluate.getTransmit());
			e.setTime(evaluate.getCreateTime());
			e.setType(evaluate.getType());
			e.setTypeContent(evaluate.getTypeContent());
			pictureExample.createCriteria().andEvaluateEqualTo(evaluate.getId());
			pictures = evaluatePictureMapper.selectByExample(pictureExample);
			pictureId = pictures.stream().map(EvaluatePicture::getFileId).collect(Collectors.toList());
			e.setFileId(pictureId);
			entity.setParentEvaluate(e);
			rs.add(entity);
		}
		PageInfo<EvaluateEntity> page = new PageInfo<EvaluateEntity>(rs);
		return builder.body(ResponseUtils.getResponseBody(page));
	}

	@ApiOperation(value = "给评价点赞", notes = "给评价点赞")
	@RequestMapping(value = "/addEvaluatePraise", method = RequestMethod.POST)
	@Transactional(rollbackFor = {RuntimeException.class, Error.class})
	public ResponseEntity<JSONObject> addEvaluatePraise(Integer id, Integer userId, String type, Integer levelId)
			throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfEvaluate evaluate = hfEvaluateMapper.selectByPrimaryKey(id);
		JSONObject js1 = restTemplate.getForObject(REST_URL_PREFIX + "hf-auth/findUserDetails?userId={userId}",
				JSONObject.class, userId);
		if ("此用户不存在".equals(js1.get("data"))) {
			return builder.body(ResponseUtils.getResponseBody("-1"));
		}

		EvaluateUserRecordExample example = new EvaluateUserRecordExample();
		example.createCriteria().andUserIdEqualTo(userId).andEvaluateEqualTo(id).andTypeEqualTo(type);
		List<EvaluateUserRecord> records = evaluateUserRecordMapper.selectByExample(example);
		if (!records.isEmpty()) {
			EvaluateUserRecord record = records.get(0);
			if (record.getIsDeleted() == (byte) 0) {
				System.out.println("更改点赞状态");
				record.setIsDeleted((byte) 1);
				evaluate.setPraise(evaluate.getPraise() + 1);
				record.setModifyTime(LocalDateTime.now());
				evaluateUserRecordMapper.updateByPrimaryKey(record);
				hfEvaluateMapper.updateByPrimaryKey(evaluate);
				return builder.body(ResponseUtils.getResponseBody(evaluate.getId()));
			}
			if (record.getIsDeleted() == (byte) 1) {
				System.out.println("11111111111");
				record.setIsDeleted((byte) 0);
				evaluate.setPraise(evaluate.getPraise() - 1);
				record.setModifyTime(LocalDateTime.now());
				evaluateUserRecordMapper.updateByPrimaryKey(record);
				hfEvaluateMapper.updateByPrimaryKey(evaluate);
				return builder.body(ResponseUtils.getResponseBody(evaluate.getId()));
			}

		} else {
			EvaluateUserRecord record = new EvaluateUserRecord();
			record.setUserId(userId);
			record.setEvaluate(id);
			record.setCreateTime(LocalDateTime.now());
			record.setModifyTime(LocalDateTime.now());
			record.setIsDeleted((byte) 1);
			record.setType(type);
			evaluateUserRecordMapper.insert(record);
			evaluate.setPraise(evaluate.getPraise() + 1);
			hfEvaluateMapper.updateByPrimaryKey(evaluate);
		}
		return builder.body(ResponseUtils.getResponseBody(evaluate.getId()));
	}

//	@ApiOperation(value = "回复评价", notes = "回复评价")
//	@RequestMapping(value = "/replyEvaluate", method = RequestMethod.POST)
//	public ResponseEntity<JSONObject> replyEvaluate(Integer evaluateId, Integer outId, Integer inId, String evaluate,
//			Integer... fileId) throws Exception {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		EvaluateInstance evaluateInstance = new EvaluateInstance();
//		evaluateInstance.setInEvaluateId(evaluateId);
//		evaluateInstance.setOutEvaluateId(outId);
//		evaluateInstance.setInEvaluateId(inId);
//		evaluateInstance.setEvaluateContent(evaluate);
//		evaluateInstance.setCreateTime(LocalDateTime.now());
//		evaluateInstance.setModifyTime(LocalDateTime.now());
//		evaluateInstance.setIsDeleted((byte) 0);
//		evaluateInstanceMapper.insert(evaluateInstance);
//		HfEvaluate hfEvaluate = hfEvaluateMapper.selectByPrimaryKey(evaluateId);
//		hfEvaluate.setCommentCount(hfEvaluate.getCommentCount() + 1);
//		hfEvaluateMapper.updateByPrimaryKey(hfEvaluate);
//
//		for (int i = 0; i < fileId.length; i++) {
//			EvluateInstancePicture picture = new EvluateInstancePicture();
//			picture.setEvaluateInstanceId(evaluateId);
//			picture.setFileId(fileId[i]);
//			picture.setHfName("评价图片");
//			picture.setHfDesc("评价图片描述");
//			picture.setCreateTime(LocalDateTime.now());
//			picture.setModifyTime(LocalDateTime.now());
//			picture.setIsDeleted((byte) 0);
//			evluateInstancePictureMapper.insert(picture);
//		}
//		return builder.body(ResponseUtils.getResponseBody(evaluateInstance.getId()));
//	}

	@ApiOperation(value = "上传图片", notes = "上传图片")
	@RequestMapping(value = "/fileUpLoad", method = RequestMethod.POST)
	@Transactional(rollbackFor = {RuntimeException.class, Error.class})
	public ResponseEntity<JSONObject> fileUpLoad(MultipartFile file) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		String arr[];
		FileMangeService fileMangeService = new FileMangeService();
		arr = fileMangeService.uploadFile(file.getBytes(), String.valueOf("-1"));
		FileDesc fileDesc = new FileDesc();
		fileDesc.setFileName(file.getName());
		fileDesc.setGroupName(arr[0]);
		fileDesc.setRemoteFilename(arr[1]);
		fileDesc.setUserId(-1);
		fileDesc.setCreateTime(LocalDateTime.now());
		fileDesc.setModifyTime(LocalDateTime.now());
		fileDesc.setIsDeleted((short) 0);
		fileDescMapper.insert(fileDesc);
		return builder.body(ResponseUtils.getResponseBody(fileDesc.getId()));
	}

	@RequestMapping(value = "/selectDiscover", method = RequestMethod.GET)
	@ApiOperation(value = "根据类型查询评价", notes = "根据类型查询评价")
	public ResponseEntity<JSONObject> selectDiscover(HttpServletRequest request, Integer userId, String type,
			Integer levelId, Integer parentEvaluateId, Integer stoneId) throws JSONException {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
		EvaluateUserRecordExample recordExample = new EvaluateUserRecordExample();
		HfEvaluateExample evaluateExample = new HfEvaluateExample();
		Object bossId = request.getHeader("bossId");
		evaluateExample.createCriteria().andTypeEqualTo(type).andParentEvaluateIdEqualTo(parentEvaluateId)
				.andBossIdEqualTo(Integer.valueOf((String) bossId));
		evaluateExample.setOrderByClause("create_time DESC");
		if (stoneId != null) {
			evaluateExample.clear();
			evaluateExample.createCriteria().andTypeEqualTo(type).andParentEvaluateIdEqualTo(parentEvaluateId)
					.andBossIdEqualTo(Integer.valueOf((String) bossId)).andStoneIdEqualTo(stoneId);
			evaluateExample.setOrderByClause("create_time DESC");
		}
		List<Evaluate> result = new ArrayList<Evaluate>();
//		List<Integer> productId = new ArrayList<Integer>();
		List<Integer> fileId = new ArrayList<Integer>();
		List<HfEvaluate> discovers = hfEvaluateMapper.selectByExample(evaluateExample);

		EvaluatePictureExample pictrueExample = new EvaluatePictureExample();
		List<EvaluatePicture> pictrues = new ArrayList<EvaluatePicture>();
//		DiscoverProductExample productExample = new DiscoverProductExample();  多个实体之后
//		List<DiscoverProduct> products = new ArrayList<DiscoverProduct>();
		for (int i = 0; i < discovers.size(); i++) {
			pictrueExample.clear();
// 			productExample.clear();
			HfEvaluate d = discovers.get(i);
			Evaluate display = new Evaluate();
			display.setComment(d.getEvaluate());
//			display.setDiscoverDesc(d.getDiscoverDesc());
//			display.setDiscoverHeadline(d.getDiscoverHeadline());
			if (userId != null) {
				recordExample.clear();
				recordExample.createCriteria().andUserIdEqualTo(userId).andEvaluateEqualTo(d.getId())
						.andIsDeletedEqualTo((byte) 1).andTypeEqualTo(type);
				if (!evaluateUserRecordMapper.selectByExample(recordExample).isEmpty()) {
					display.setIsPraise(1);
				} else {
					display.setIsPraise(0);
				}
			}
			display.setId(d.getId());
			display.setType(d.getType());
			display.setTypeContent(d.getTypeContent());
			display.setUserId(d.getUserId());
			JSONObject js = restTemplate.getForObject(REST_URL_PREFIX + "hf-auth/findInfoByUserId?userId={userId}",
					JSONObject.class, d.getUserId());
			JSONObject js1 = restTemplate.getForObject(REST_URL_PREFIX + "hf-auth/findUserDetails?userId={userId}",
					JSONObject.class, d.getUserId());
			display.setLevelName(js.getJSONObject("data").getString("prerogative"));
			display.setUsername(js1.getJSONObject("data").getString("nickName"));
			if (!StringUtils.isEmpty(js1.getJSONObject("data").getString("fileId"))) {
				display.setAvatar(Integer.valueOf(js1.getJSONObject("data").getString("fileId")));
			}
			display.setTime(d.getCreateTime());
			pictrueExample.createCriteria().andEvaluateEqualTo(d.getId());
			pictrues = evaluatePictureMapper.selectByExample(pictrueExample);
			fileId = pictrues.stream().map(EvaluatePicture::getFileId).collect(Collectors.toList());
			display.setFileId(fileId);
			display.setComment_count(d.getCommentCount());
			display.setPraise(d.getPraise());
			display.setTransmitCount(d.getTransmit());
			result.add(display);
//			products = discoverProductMapper.selectByExample(productExample);
//			productId = products.stream().map(DiscoverProduct :: getProductId).collect(Collectors.toList());
//			display.setProductId(productId);
//			productExample.createCriteria().andDiscoverIdEqualTo(d.getId());
		}
		if (userId != null) {

		}
		return builder.body(ResponseUtils.getResponseBody(result));
	}

	@ApiOperation(value = "获取视频", notes = "获取视频")
	@RequestMapping(value = "/getVedio", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "fileId", value = "文件id", required = true, type = "Integer") })
	public void getVedio(@RequestParam(name = "fileId") Integer fileId, HttpServletResponse response) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Content-Type", "audio/mp4;charset=UTF-8");
		FileDesc fileDesc = fileDescMapper.selectByPrimaryKey(fileId);
		if (fileDesc == null) {
			throw new Exception("file not exists");
		}
		FileMangeService fileManageService = new FileMangeService();
		synchronized (LOCK) {
			byte[] file = fileManageService.downloadFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
//            ByteArrayInputStream stream = new ByteArrayInputStream(file);
//            BufferedImage readImg = ImageIO.read(stream);
//            stream.reset();
			OutputStream outputStream = response.getOutputStream();
//            ImageIO.write(readImg, "png", outputStream);
			IOUtils.write(file, outputStream);
			outputStream.close();
		}
	}

	@ApiOperation(value = "转发", notes = "转发")
	@RequestMapping(value = "/transmit", method = RequestMethod.POST)
	@Transactional(rollbackFor = {RuntimeException.class, Error.class})
	public ResponseEntity<JSONObject> transmit(Integer id) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfEvaluate evaluate = hfEvaluateMapper.selectByPrimaryKey(id);
		evaluate.setTransmit(evaluate.getTransmit() + 1);
		hfEvaluateMapper.updateByPrimaryKey(evaluate);
		return builder.body(ResponseUtils.getResponseBody(evaluate.getId()));
	}

	@ApiOperation(value = "添加图标绑定链接", notes = "添加图标绑定链接")
	@RequestMapping(value = "/addIconAndUrl", method = RequestMethod.POST)
	@Transactional(rollbackFor = {RuntimeException.class, Error.class})
	public ResponseEntity<JSONObject> addIconAndUrl(MultipartFile file, String iconName, String url, Integer bossId)
			throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfIcon icon = new HfIcon();
		if (file != null) {
			String arr[];
			FileMangeService fileMangeService = new FileMangeService();
			arr = fileMangeService.uploadFile(file.getBytes(), String.valueOf("-1"));
			FileDesc fileDesc = new FileDesc();
			fileDesc.setFileName(file.getName());
			fileDesc.setGroupName(arr[0]);
			fileDesc.setRemoteFilename(arr[1]);
			fileDesc.setUserId(-1);
			fileDesc.setCreateTime(LocalDateTime.now());
			fileDesc.setModifyTime(LocalDateTime.now());
			fileDesc.setIsDeleted((short) 0);
			fileDescMapper.insert(fileDesc);
			icon.setFileId(fileDesc.getId());
		}

		icon.setIconName(iconName);
		icon.setBossId(bossId);
		icon.setUrl(url);
		icon.setCreateTime(LocalDateTime.now());
		icon.setModifyTime(LocalDateTime.now());
		icon.setIsDeleted((byte) 0);
		hfIconMapper.insert(icon);
		return builder.body(ResponseUtils.getResponseBody(icon.getId()));
	}

	@ApiOperation(value = "删除图标绑定链接", notes = "删除图标绑定链接")
	@RequestMapping(value = "/deleteIconAndUrl", method = RequestMethod.POST)
	@Transactional(rollbackFor = {RuntimeException.class, Error.class})
	public ResponseEntity<JSONObject> deleteIconAndUrl(Integer id) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		hfIconMapper.deleteByPrimaryKey(id);
		return builder.body(ResponseUtils.getResponseBody(id));
	}

	@ApiOperation(value = "修改图标绑定链接", notes = "修改图标绑定链接")
	@RequestMapping(value = "/updateIconAndUrl", method = RequestMethod.POST)
	@Transactional(rollbackFor = {RuntimeException.class, Error.class})
	public ResponseEntity<JSONObject> updateIconAndUrl(Integer id, MultipartFile file, String iconName, String url)
			throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfIcon hfIcon = hfIconMapper.selectByPrimaryKey(id);
		if (file != null) {
			String arr[];
			FileMangeService fileMangeService = new FileMangeService();
			arr = fileMangeService.uploadFile(file.getBytes(), String.valueOf("-1"));
			if (hfIcon.getFileId() != null) {
				FileDesc fileDesc = new FileDesc();
				fileDesc.setFileName(file.getName());
				fileDesc.setGroupName(arr[0]);
				fileDesc.setRemoteFilename(arr[1]);
				fileDesc.setUserId(-1);
				fileDesc.setCreateTime(LocalDateTime.now());
				fileDesc.setModifyTime(LocalDateTime.now());
				fileDesc.setIsDeleted((short) 0);
				fileDescMapper.insert(fileDesc);
				hfIcon.setFileId(fileDesc.getId());
			} else {
				FileDesc fileDesc = fileDescMapper.selectByPrimaryKey(hfIcon.getFileId());
				fileMangeService.deleteFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
				fileDesc.setGroupName(arr[0]);
				fileDesc.setRemoteFilename(arr[1]);
				fileDescMapper.updateByPrimaryKey(fileDesc);
			}

		}
		if (!StringUtils.isEmpty(iconName)) {
			hfIcon.setIconName(iconName);
		}
		if (!StringUtils.isEmpty(url)) {
			hfIcon.setUrl(url);
		}
		hfIconMapper.updateByPrimaryKey(hfIcon);
		return builder.body(ResponseUtils.getResponseBody(id));
	}

	@ApiOperation(value = "查询图标绑定链接", notes = "查询图标绑定链接")
	@RequestMapping(value = "/selectIconAndUrl", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> selectIconAndUrl(Integer bossId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfIconExample example = new HfIconExample();
		example.createCriteria().andBossIdEqualTo(bossId);
		List<HfIcon> list = hfIconMapper.selectByExample(example);
		return builder.body(ResponseUtils.getResponseBody(list));
	}

	@ApiOperation(value = "关注或取消关注他人", notes = "关注他人")
	@RequestMapping(value = "/focusPeople", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> focusPeople(Integer myUserId, Integer userId, String type) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfFocusPeopleExample example = new HfFocusPeopleExample();
		example.createCriteria().andMyUserIdEqualTo(myUserId).andUserIdEqualTo(userId).andTypeEqualTo(type);
		List<HfFocusPeople> list = hfFocusPeopleMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(list)) {
			HfFocusPeople people = new HfFocusPeople();
			people.setMyUserId(myUserId);
			people.setUserId(userId);
			people.setType(type);
			people.setCreateTime(LocalDateTime.now());
			people.setModifyTime(LocalDateTime.now());
			people.setIsDeleted((byte) 1);
			hfFocusPeopleMapper.insert(people);
			return builder.body(ResponseUtils.getResponseBody(people.getId()));
		} else {
			HfFocusPeople people = list.get(0);
			if (people.getIsDeleted() == 1) {
				people.setIsDeleted((byte) 0);
				hfFocusPeopleMapper.updateByPrimaryKey(people);
				return builder.body(ResponseUtils.getResponseBody(people.getId()));
			} else {
				people.setIsDeleted((byte) 1);
				hfFocusPeopleMapper.updateByPrimaryKey(people);
				return builder.body(ResponseUtils.getResponseBody(people.getId()));
			}
		}
	}

	@ApiOperation(value = "我的关注", notes = "我的关注")
	@RequestMapping(value = "/myFocus", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> myFocus(Integer userId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		EvaluateUserRecordExample recordExample = new EvaluateUserRecordExample();
		EvaluatePictureExample pictrueExample = new EvaluatePictureExample();
		List<Integer> fileId = new ArrayList<Integer>();
		List<Evaluate> result = new ArrayList<Evaluate>();
		List<EvaluatePicture> pictrues = new ArrayList<EvaluatePicture>();
		HfFocusPeopleExample example = new HfFocusPeopleExample();
		example.createCriteria().andMyUserIdEqualTo(userId).andIsDeletedEqualTo((byte) 1);
		List<HfFocusPeople> list = hfFocusPeopleMapper.selectByExample(example);
		List<Integer> uIds = list.stream().map(HfFocusPeople::getUserId).collect(Collectors.toList());
		HfEvaluateExample evaluateExample = new HfEvaluateExample();
		evaluateExample.createCriteria().andUserIdIn(uIds).andTypeEqualTo("discover").andParentEvaluateIdEqualTo(-1);
		List<HfEvaluate> evaluates = hfEvaluateMapper.selectByExample(evaluateExample);
		for (int i = 0; i < evaluates.size(); i++) {
			pictrueExample.clear();
// 			productExample.clear();
			HfEvaluate d = evaluates.get(i);
			Evaluate display = new Evaluate();
			display.setComment(d.getEvaluate());
//			display.setDiscoverDesc(d.getDiscoverDesc());
//			display.setDiscoverHeadline(d.getDiscoverHeadline());
			if (userId != null) {
				recordExample.clear();
				recordExample.createCriteria().andUserIdEqualTo(userId).andEvaluateEqualTo(d.getId())
						.andIsDeletedEqualTo((byte) 1).andTypeEqualTo("discover");
				if (!evaluateUserRecordMapper.selectByExample(recordExample).isEmpty()) {
					display.setIsPraise(1);
				} else {
					display.setIsPraise(0);
				}
			}
			display.setId(d.getId());
			display.setType(d.getType());
			display.setTypeContent(d.getTypeContent());
			display.setUserId(d.getUserId());
			JSONObject js = restTemplate.getForObject(REST_URL_PREFIX + "hf-auth/findInfoByUserId?userId={userId}",
					JSONObject.class, d.getUserId());
			JSONObject js1 = restTemplate.getForObject(REST_URL_PREFIX + "hf-auth/findUserDetails?userId={userId}",
					JSONObject.class, d.getUserId());
			display.setLevelName(js.getJSONObject("data").getString("prerogative"));
			display.setUsername(js1.getJSONObject("data").getString("nickName"));
			if (!StringUtils.isEmpty(js1.getJSONObject("data").getString("fileId"))) {
				display.setAvatar(Integer.valueOf(js1.getJSONObject("data").getString("fileId")));
			}
			display.setTime(d.getCreateTime());
			pictrueExample.createCriteria().andEvaluateEqualTo(d.getId());
			pictrues = evaluatePictureMapper.selectByExample(pictrueExample);
			fileId = pictrues.stream().map(EvaluatePicture::getFileId).collect(Collectors.toList());
			display.setFileId(fileId);
			display.setComment_count(d.getCommentCount());
			display.setPraise(d.getPraise());
			display.setTransmitCount(d.getTransmit());
			result.add(display);
		}
		return builder.body(ResponseUtils.getResponseBody(result));
	}

	@ApiOperation(value = "收藏发现", notes = "收藏发现")
	@RequestMapping(value = "/collectDiscover", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> collectDiscover(Integer userId, Integer discoverId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfEvaluateCollectExample example = new HfEvaluateCollectExample();
		example.createCriteria().andUserIdEqualTo(userId).andEvaluateIdEqualTo(discoverId);
		List<HfEvaluateCollect> list = hfEvaluateCollectMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(list)) {
			HfEvaluateCollect collect = new HfEvaluateCollect();
			collect.setUserId(userId);
			collect.setEvaluateId(discoverId);
			collect.setCreateTime(LocalDateTime.now());
			collect.setModifyTime(LocalDateTime.now());
			collect.setIsDeleted((byte) 1);
			hfEvaluateCollectMapper.insert(collect);
			return builder.body(ResponseUtils.getResponseBody(1));
		} else {
			HfEvaluateCollect collect = list.get(0);
			if (collect.getIsDeleted() == 1) {
				collect.setIsDeleted((byte) 0);
				hfEvaluateCollectMapper.updateByPrimaryKey(collect);
				return builder.body(ResponseUtils.getResponseBody(collect.getId()));
			} else {
				collect.setIsDeleted((byte) 1);
				hfEvaluateCollectMapper.updateByPrimaryKey(collect);
				return builder.body(ResponseUtils.getResponseBody(collect.getId()));
			}
		}
	}

	@ApiOperation(value = "我的收藏", notes = "我的收藏")
	@RequestMapping(value = "/myCollect", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> myCollect(Integer userId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		EvaluateUserRecordExample recordExample = new EvaluateUserRecordExample();
		EvaluatePictureExample pictrueExample = new EvaluatePictureExample();
		List<Integer> fileId = new ArrayList<Integer>();
		List<Evaluate> result = new ArrayList<Evaluate>();
		List<EvaluatePicture> pictrues = new ArrayList<EvaluatePicture>();
		HfEvaluateCollectExample example = new HfEvaluateCollectExample();
		example.createCriteria().andUserIdEqualTo(userId).andIsDeletedEqualTo((byte) 1);
		List<HfEvaluateCollect> list = hfEvaluateCollectMapper.selectByExample(example);
		if (!list.isEmpty()) {
			List<Integer> disId = list.stream().map(HfEvaluateCollect::getEvaluateId).collect(Collectors.toList());
			HfEvaluateExample evaluateExample = new HfEvaluateExample();
			evaluateExample.createCriteria().andIdIn(disId).andTypeEqualTo("discover").andParentEvaluateIdEqualTo(-1);
			List<HfEvaluate> evaluates = hfEvaluateMapper.selectByExample(evaluateExample);
			for (int i = 0; i < evaluates.size(); i++) {
				pictrueExample.clear();
//	 			productExample.clear();
				HfEvaluate d = evaluates.get(i);
				Evaluate display = new Evaluate();
				display.setComment(d.getEvaluate());
//				display.setDiscoverDesc(d.getDiscoverDesc());
//				display.setDiscoverHeadline(d.getDiscoverHeadline());
				if (userId != null) {
					recordExample.clear();
					recordExample.createCriteria().andUserIdEqualTo(userId).andEvaluateEqualTo(d.getId())
							.andIsDeletedEqualTo((byte) 1).andTypeEqualTo("discover");
					if (!evaluateUserRecordMapper.selectByExample(recordExample).isEmpty()) {
						display.setIsPraise(1);
					} else {
						display.setIsPraise(0);
					}
				}
				display.setId(d.getId());
				display.setType(d.getType());
				display.setTypeContent(d.getTypeContent());
				display.setUserId(d.getUserId());
				JSONObject js = restTemplate.getForObject(REST_URL_PREFIX + "hf-auth/findInfoByUserId?userId={userId}",
						JSONObject.class, d.getUserId());
				JSONObject js1 = restTemplate.getForObject(REST_URL_PREFIX + "hf-auth/findUserDetails?userId={userId}",
						JSONObject.class, d.getUserId());
				display.setLevelName(js.getJSONObject("data").getString("prerogative"));
				display.setUsername(js1.getJSONObject("data").getString("nickName"));
				if (!StringUtils.isEmpty(js1.getJSONObject("data").getString("fileId"))) {
					display.setAvatar(Integer.valueOf(js1.getJSONObject("data").getString("fileId")));
				}
				display.setTime(d.getCreateTime());
				pictrueExample.createCriteria().andEvaluateEqualTo(d.getId());
				pictrues = evaluatePictureMapper.selectByExample(pictrueExample);
				fileId = pictrues.stream().map(EvaluatePicture::getFileId).collect(Collectors.toList());
				display.setFileId(fileId);
				display.setComment_count(d.getCommentCount());
				display.setPraise(d.getPraise());
				display.setTransmitCount(d.getTransmit());
				result.add(display);
			}
		}
		return builder.body(ResponseUtils.getResponseBody(result));
	}

}
