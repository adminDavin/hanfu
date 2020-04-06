package com.hanfu.product.center.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hanfu.inner.model.product.center.HfCategory;
import com.hanfu.product.center.dao.*;
import com.hanfu.product.center.manual.model.IsDelete;
import com.hanfu.product.center.manual.model.ProductActivityInfo;
import com.hanfu.product.center.manual.model.ProductNameSelect;
import com.hanfu.product.center.model.*;
import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.hanfu.product.center.manual.dao.HfGoodsDisplayDao;
import com.hanfu.product.center.manual.dao.HfProductDao;
import com.hanfu.product.center.manual.dao.ManualDao;
import com.hanfu.product.center.manual.model.HfGoodsDisplayInfo;
import com.hanfu.product.center.manual.model.HfProductDisplay;

import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.utils.response.handler.ResponseUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/hfProduct")
@Api
public class HfProductController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HfGoodsPictrueMapper hfGoodsPictrueMapper;

	@Autowired
	private HfProductPictrueMapper hfProductPictrueMapper;

	@Autowired
	private HfProductDao hfProductDao;

	@Autowired
	private HfGoodsDisplayDao hfGoodsDisplayDao;

	@Autowired
	private HfStoneMapper hfStoneMapper;

	@Autowired
	private HfActivityMapper hfActivityMapper;

	@Autowired
	private ManualDao manualDao;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private HfUserBrowseRecordMapper hfUserBrowseRecordMapper;

	@Autowired
	private HfBossMapper hfBossMapper;

	@Autowired
	private ProductInstanceMapper productInstanceMapper;

	@Autowired
	private HfActivityProductMapper hfActivityProductMapper;

	@Autowired
	private HfCategoryMapper hfCategoryMapper;
	
	@Autowired
	private UserPersonalBrowseMapper userPersonalBrowseMapper;

	@ApiOperation(value = "商品列表", notes = "根据商品id删除商品列表")
	@RequestMapping(value = "/getProductsForRotation", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "quantity", value = "获取商品的数量限制", required = false, type = "Integer") })
	public ResponseEntity<JSONObject> getProductsForRotation(@RequestParam(name = "quantity") Integer quantity)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		List<HfProductDisplay> products = hfProductDao.selectProductForRotation(quantity);
		Set<Integer> stoneIds = products.stream().map(HfProductDisplay::getStoneId).collect(Collectors.toSet());
		HfStoneExample hfStoneExample = new HfStoneExample();
		hfStoneExample.createCriteria().andIdIn(Lists.newArrayList(stoneIds));
		List<HfStone> stoneInfos = hfStoneMapper.selectByExample(hfStoneExample);
		Map<Integer, String> stones = stoneInfos.stream().collect(Collectors.toMap(HfStone::getId, HfStone::getHfName));
		products.forEach(product -> product.setStoneName(stones.get(product.getStoneId())));

		List<Integer> productIds = products.stream().map(HfProductDisplay::getId).collect(Collectors.toList());
		List<HfGoodsDisplayInfo> hfGoodsDisplay = hfGoodsDisplayDao.selectHfGoodsDisplay(productIds);
		Map<Integer, List<HfGoodsDisplayInfo>> hfGoodsDisplayMap = hfGoodsDisplay.stream()
				.collect(Collectors.toMap(HfGoodsDisplayInfo::getProductId, item -> Lists.newArrayList(item),
						(List<HfGoodsDisplayInfo> oldList, List<HfGoodsDisplayInfo> newList) -> {
							oldList.addAll(newList);
							return oldList;
						}));
		products.forEach(product -> {
			List<HfGoodsDisplayInfo> hfGoods = hfGoodsDisplayMap.get(product.getId());
			if (Optional.ofNullable(hfGoods).isPresent()) {
				Optional<HfGoodsDisplayInfo> hfGood = hfGoods.stream()
						.filter(goods -> Optional.ofNullable(goods.getSellPrice()).isPresent())
						.min(Comparator.comparing(HfGoodsDisplayInfo::getSellPrice));
				product.setPriceArea(hfGood.isPresent() ? String.valueOf(hfGood.get().getSellPrice()) : "异常");
				product.setDefaultGoodsId(hfGood.get().getId());
			}

		});
		return builder.body(ResponseUtils.getResponseBody(products));
	}

	@ApiOperation(value = "获取商品详情", notes = "根据商品id商品详情")
	@RequestMapping(value = "/getDetail", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "productId", value = "商品ID", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> getDetail(@RequestParam(name = "productId") Integer productId, Integer stoneId,
			@RequestParam(required = false) Integer userId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfProductDisplay product = hfProductDao.selectProduct(productId, stoneId);
		ProductInstanceExample productInstanceExample = new ProductInstanceExample();
		productInstanceExample.createCriteria().andProductIdEqualTo(product.getId()).andStoneIdEqualTo(stoneId);
		List<ProductInstance> productInstances = productInstanceMapper.selectByExample(productInstanceExample);
		HfBoss hfBoss = hfBossMapper.selectByPrimaryKey(productInstances.get(0).getBossId());
		product.setBossId(hfBoss.getId());
		product.setBossName(hfBoss.getName());
		HfStoneExample hfStoneExample = new HfStoneExample();
		hfStoneExample.createCriteria().andIdIn(Lists.newArrayList(product.getStoneId()));
		List<HfStone> stoneInfos = hfStoneMapper.selectByExample(hfStoneExample);
		Map<Integer, String> stones = stoneInfos.stream().collect(Collectors.toMap(HfStone::getId, HfStone::getHfName));
		product.setStoneName(stones.get(product.getStoneId()));

		List<HfGoodsDisplayInfo> hfGoods = hfGoodsDisplayDao.selectHfGoodsDisplay(product.getId());
		if (hfGoods.size() != 0) {
			if (Optional.ofNullable(hfGoods).isPresent()) {
				Optional<HfGoodsDisplayInfo> hfGood = Optional.ofNullable(
						hfGoods.stream().min(Comparator.comparing(HfGoodsDisplayInfo::getSellPrice)).orElse(null));
				product.setPriceArea(hfGood.isPresent() ? String.valueOf(hfGood.get().getSellPrice()) : "异常");
				product.setDefaultGoodsId(hfGood.get().getId());
				if (hfGood.get().getLinePrice() != null) {
					product.setLinePrice(hfGood.isPresent() ? hfGood.get().getLinePrice() : -1);
				}
			}

			HfProductPictrueExample example = new HfProductPictrueExample();
			example.createCriteria().andProductIdEqualTo(productId);
			List<HfProductPictrue> hfProductPictrues = hfProductPictrueMapper.selectByExample(example);
//            HfGoodsPictrueExample example = new HfGoodsPictrueExample();
//            example.createCriteria().andGoodsIdEqualTo(product.getDefaultGoodsId());
//            List<HfGoodsPictrue> hfProductPictrues = hfGoodsPictrueMapper.selectByExample(example);
			List<Integer> fileIds = hfProductPictrues.stream().map(HfProductPictrue::getFileId)
					.collect(Collectors.toList());
			product.setFileIds(fileIds);
		}
		HfUserBrowseRecord browseRecord = new HfUserBrowseRecord();
		browseRecord.setBrowseDate(LocalDateTime.now());
		browseRecord.setCreateDate(LocalDateTime.now());
		browseRecord.setIdDeleted((byte) 0);
		browseRecord.setModifyDate(LocalDateTime.now());
		browseRecord.setProductId(productId);
		browseRecord.setUserId(userId);
		Product product2 = productMapper.selectByPrimaryKey(productId);
		browseRecord.setBossId(product2.getBossId());
		hfUserBrowseRecordMapper.insert(browseRecord);
		UserPersonalBrowseExample userPersonalBrowseExample = new UserPersonalBrowseExample();
		userPersonalBrowseExample.createCriteria().andUserIdEqualTo(userId).andProductIdEqualTo(productId);
		List<UserPersonalBrowse> browses = userPersonalBrowseMapper.selectByExample(userPersonalBrowseExample);
		if(browses.isEmpty()) {
			UserPersonalBrowse browse = new UserPersonalBrowse();
			browse.setBrowseTime(LocalDateTime.now());
			browse.setCount(1);
			browse.setCreateTime(LocalDateTime.now());
			browse.setIsDeleted((byte) 0);
			browse.setModifiyTime(LocalDateTime.now());
			browse.setProductId(productId);
			browse.setUserId(userId);
			userPersonalBrowseMapper.insert(browse);
		}else {
			UserPersonalBrowse browse = browses.get(0);
			browse.setBrowseTime(LocalDateTime.now());
			browse.setModifiyTime(LocalDateTime.now());
			browse.setCount(browse.getCount()+1);
			userPersonalBrowseMapper.updateByPrimaryKey(browse);
		}
		return builder.body(ResponseUtils.getResponseBody(product));
	}

	@ApiOperation(value = "获取商品列表", notes = "根据类目id商品列表")
	@RequestMapping(value = "/getCategory", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "cagetoryId", value = "类目Id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> getCategory(@RequestParam(name = "cagetoryId") Integer cagetoryId,
			Integer pageNum, Integer pageSize) throws JSONException {
		if (pageNum == null) {
			pageNum = 0;
		}
		if (pageSize == null) {
			pageSize = 0;
		}
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		PageHelper.startPage(pageNum, pageSize);
		List<HfProductDisplay> products = hfProductDao.selectProductCategory(cagetoryId);
		Set<Integer> stoneIds = products.stream().map(HfProductDisplay::getStoneId).collect(Collectors.toSet());
		System.out.println(stoneIds);
		HfStoneExample hfStoneExample = new HfStoneExample();
		hfStoneExample.createCriteria().andIdIn(Lists.newArrayList(stoneIds));
		List<HfStone> stoneInfos = hfStoneMapper.selectByExample(hfStoneExample);
		System.out.println(stoneInfos);
		Map<Integer, String> stones = stoneInfos.stream().collect(Collectors.toMap(HfStone::getId, HfStone::getHfName));
		products.forEach(product -> product.setStoneName(stones.get(product.getStoneId())));

		List<Integer> productIds = products.stream().map(HfProductDisplay::getId).collect(Collectors.toList());
		List<HfGoodsDisplayInfo> hfGoodsDisplay = hfGoodsDisplayDao.selectHfGoodsDisplay(productIds);
		Map<Integer, List<HfGoodsDisplayInfo>> hfGoodsDisplayMap = hfGoodsDisplay.stream()
				.collect(Collectors.toMap(HfGoodsDisplayInfo::getProductId, item -> Lists.newArrayList(item),
						(List<HfGoodsDisplayInfo> oldList, List<HfGoodsDisplayInfo> newList) -> {
							oldList.addAll(newList);
							return oldList;
						}));
		products.forEach(product -> {
			List<HfGoodsDisplayInfo> hfGoods = hfGoodsDisplayMap.get(product.getId());
			if (Optional.ofNullable(hfGoods).isPresent()) {
				Optional<HfGoodsDisplayInfo> hfGood = hfGoods.stream()
						.min(Comparator.comparing(HfGoodsDisplayInfo::getSellPrice));
				product.setPriceArea(hfGood.isPresent() ? String.valueOf(hfGood.get().getSellPrice()) : "异常");
				product.setDefaultGoodsId(hfGood.get().getId());
			}

		});
		PageInfo<HfProductDisplay> page = new PageInfo<HfProductDisplay>(products);
		return builder.body(ResponseUtils.getResponseBody(page));
	}

	@ApiOperation(value = "获取商品列表店铺", notes = "根据店铺id商品列表")
	@RequestMapping(value = "/getstone", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "stoneId", value = "店铺Id", required = false, type = "Integer") })
	public ResponseEntity<JSONObject> getstone(IsDelete isDelete, Integer pageNum, Integer pageSize)
			throws JSONException {
		if (pageNum == null) {
			pageNum = 0;
		}
		if (pageSize == null) {
			pageSize = 0;
		}
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		PageHelper.startPage(pageNum, pageSize);
		List<HfProductDisplay> products = hfProductDao.selectProductByStoneId(isDelete);
		products.forEach(hfProductDisplay -> {
			System.out.println(hfProductDisplay.getStoneId());
		});
		if (products.size() != 0) {
			Set<Integer> stoneIds = products.stream().map(HfProductDisplay::getStoneId).collect(Collectors.toSet());
			System.out.println(stoneIds);
			HfStoneExample hfStoneExample = new HfStoneExample();
			hfStoneExample.createCriteria().andIdIn(Lists.newArrayList(stoneIds));
			List<HfStone> stoneInfos = hfStoneMapper.selectByExample(hfStoneExample);
			Map<Integer, String> stones = stoneInfos.stream()
					.collect(Collectors.toMap(HfStone::getId, HfStone::getHfName));
			products.forEach(product -> product.setStoneName(stones.get(product.getStoneId())));

			List<Integer> productIds = products.stream().map(HfProductDisplay::getId).collect(Collectors.toList());
			List<HfGoodsDisplayInfo> hfGoodsDisplay = hfGoodsDisplayDao.selectHfGoodsDisplay(productIds);
			Map<Integer, List<HfGoodsDisplayInfo>> hfGoodsDisplayMap = hfGoodsDisplay.stream()
					.collect(Collectors.toMap(HfGoodsDisplayInfo::getProductId, item -> Lists.newArrayList(item),
							(List<HfGoodsDisplayInfo> oldList, List<HfGoodsDisplayInfo> newList) -> {
								oldList.addAll(newList);
								return oldList;
							}));
			products.forEach(product -> {
				List<HfGoodsDisplayInfo> hfGoods = hfGoodsDisplayMap.get(product.getId());
				if (Optional.ofNullable(hfGoods).isPresent()) {
					Optional<HfGoodsDisplayInfo> hfGood = hfGoods.stream()
							.filter(goods -> Optional.ofNullable(goods.getSellPrice()).isPresent())
							.min(Comparator.comparing(HfGoodsDisplayInfo::getSellPrice));
					product.setPriceArea(hfGood.isPresent() ? String.valueOf(hfGood.get().getSellPrice()) : "异常");
					product.setDefaultGoodsId(hfGood.get().getId());
				}
			});
		}
		PageInfo<HfProductDisplay> page = new PageInfo<HfProductDisplay>(products);
		return builder.body(ResponseUtils.getResponseBody(page));
	}

	@ApiOperation(value = "获取商品列表收藏", notes = "根据用户id收藏商品列表")
	@RequestMapping(value = "/getcollect", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> getcollect(@RequestParam(name = "userId") Integer userId, Integer pageNum,
			Integer pageSize) throws JSONException {
		if (pageNum == null) {
			pageNum = 0;
		}
		if (pageSize == null) {
			pageSize = 0;
		}
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		PageHelper.startPage(pageNum, pageSize);
		List<HfProductDisplay> products = hfProductDao.selectProductByUserId(userId);
		System.out.println(products);
		Set<Integer> stoneIds = products.stream().map(HfProductDisplay::getStoneId).collect(Collectors.toSet());
		System.out.println(stoneIds);
		HfStoneExample hfStoneExample = new HfStoneExample();
		hfStoneExample.createCriteria().andIdIn(Lists.newArrayList(stoneIds));
		List<HfStone> stoneInfos = hfStoneMapper.selectByExample(hfStoneExample);
		System.out.println(stoneInfos);
		Map<Integer, String> stones = stoneInfos.stream().collect(Collectors.toMap(HfStone::getId, HfStone::getHfName));
		products.forEach(product -> product.setStoneName(stones.get(product.getStoneId())));

		List<Integer> productIds = products.stream().map(HfProductDisplay::getId).collect(Collectors.toList());
		List<HfGoodsDisplayInfo> hfGoodsDisplay = hfGoodsDisplayDao.selectHfGoodsDisplay(productIds);
		Map<Integer, List<HfGoodsDisplayInfo>> hfGoodsDisplayMap = hfGoodsDisplay.stream()
				.collect(Collectors.toMap(HfGoodsDisplayInfo::getProductId, item -> Lists.newArrayList(item),
						(List<HfGoodsDisplayInfo> oldList, List<HfGoodsDisplayInfo> newList) -> {
							oldList.addAll(newList);
							return oldList;
						}));
		products.forEach(product -> {
			List<HfGoodsDisplayInfo> hfGoods = hfGoodsDisplayMap.get(product.getId());
			if (Optional.ofNullable(hfGoods).isPresent()) {
				Optional<HfGoodsDisplayInfo> hfGood = hfGoods.stream()
						.min(Comparator.comparing(HfGoodsDisplayInfo::getSellPrice));
				product.setPriceArea(hfGood.isPresent() ? String.valueOf(hfGood.get().getSellPrice()) : "异常");
				product.setDefaultGoodsId(hfGood.get().getId());
			}

		});
		PageInfo<HfProductDisplay> page = new PageInfo<HfProductDisplay>(products);
		return builder.body(ResponseUtils.getResponseBody(page));
	}

	@ApiOperation(value = "获取商品列表榜单", notes = "根据用户id收藏商品榜单")
	@RequestMapping(value = "/getseniority", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "seniorityId", value = "榜单Id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> getseniority(@RequestParam(name = "seniorityId") Integer seniorityId,
			Integer pageNum, Integer pageSize) throws JSONException {
		if (pageNum == null) {
			pageNum = 0;
		}
		if (pageSize == null) {
			pageSize = 0;
		}
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		PageHelper.startPage(pageNum, pageSize);
		List<HfProductDisplay> products = hfProductDao.selectProductSeniorityId(seniorityId);
		System.out.println(products);
		Set<Integer> stoneIds = products.stream().map(HfProductDisplay::getStoneId).collect(Collectors.toSet());
		System.out.println(stoneIds);
		HfStoneExample hfStoneExample = new HfStoneExample();
		hfStoneExample.createCriteria().andIdIn(Lists.newArrayList(stoneIds));
		List<HfStone> stoneInfos = hfStoneMapper.selectByExample(hfStoneExample);
		System.out.println(stoneInfos);
		Map<Integer, String> stones = stoneInfos.stream().collect(Collectors.toMap(HfStone::getId, HfStone::getHfName));
		products.forEach(product -> product.setStoneName(stones.get(product.getStoneId())));

		List<Integer> productIds = products.stream().map(HfProductDisplay::getId).collect(Collectors.toList());
		List<HfGoodsDisplayInfo> hfGoodsDisplay = hfGoodsDisplayDao.selectHfGoodsDisplay(productIds);
		Map<Integer, List<HfGoodsDisplayInfo>> hfGoodsDisplayMap = hfGoodsDisplay.stream()
				.collect(Collectors.toMap(HfGoodsDisplayInfo::getProductId, item -> Lists.newArrayList(item),
						(List<HfGoodsDisplayInfo> oldList, List<HfGoodsDisplayInfo> newList) -> {
							oldList.addAll(newList);
							return oldList;
						}));
		products.forEach(product -> {
			List<HfGoodsDisplayInfo> hfGoods = hfGoodsDisplayMap.get(product.getId());
			if (Optional.ofNullable(hfGoods).isPresent()) {
				Optional<HfGoodsDisplayInfo> hfGood = hfGoods.stream()
						.min(Comparator.comparing(HfGoodsDisplayInfo::getSellPrice));
				product.setPriceArea(hfGood.isPresent() ? String.valueOf(hfGood.get().getSellPrice()) : "异常");
				product.setDefaultGoodsId(hfGood.get().getId());
			}

		});
		PageInfo<HfProductDisplay> page = new PageInfo<HfProductDisplay>(products);
		return builder.body(ResponseUtils.getResponseBody(page));
	}

	@ApiOperation(value = "获取商品列表轮播图", notes = "根据用户id收藏商品轮播图")
	@RequestMapping(value = "/getslideshow", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "seniorityId", value = "榜单Id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> getslideshow(@RequestParam(name = "seniorityId") Integer seniorityId,
			Integer pageNum, Integer pageSize) throws JSONException {
		if (pageNum == null) {
			pageNum = 0;
		}
		if (pageSize == null) {
			pageSize = 0;
		}
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		PageHelper.startPage(pageNum, pageSize);
		List<HfProductDisplay> products = hfProductDao.selectProductSeniorityId(seniorityId);
		System.out.println(products);
		Set<Integer> stoneIds = products.stream().map(HfProductDisplay::getStoneId).collect(Collectors.toSet());
		System.out.println(stoneIds);
		HfStoneExample hfStoneExample = new HfStoneExample();
		hfStoneExample.createCriteria().andIdIn(Lists.newArrayList(stoneIds));
		List<HfStone> stoneInfos = hfStoneMapper.selectByExample(hfStoneExample);
		System.out.println(stoneInfos);
		Map<Integer, String> stones = stoneInfos.stream().collect(Collectors.toMap(HfStone::getId, HfStone::getHfName));
		products.forEach(product -> product.setStoneName(stones.get(product.getStoneId())));

		List<Integer> productIds = products.stream().map(HfProductDisplay::getId).collect(Collectors.toList());
		List<HfGoodsDisplayInfo> hfGoodsDisplay = hfGoodsDisplayDao.selectHfGoodsDisplay(productIds);
		Map<Integer, List<HfGoodsDisplayInfo>> hfGoodsDisplayMap = hfGoodsDisplay.stream()
				.collect(Collectors.toMap(HfGoodsDisplayInfo::getProductId, item -> Lists.newArrayList(item),
						(List<HfGoodsDisplayInfo> oldList, List<HfGoodsDisplayInfo> newList) -> {
							oldList.addAll(newList);
							return oldList;
						}));
		products.forEach(product -> {
			List<HfGoodsDisplayInfo> hfGoods = hfGoodsDisplayMap.get(product.getId());
			if (Optional.ofNullable(hfGoods).isPresent()) {
				Optional<HfGoodsDisplayInfo> hfGood = hfGoods.stream()
						.min(Comparator.comparing(HfGoodsDisplayInfo::getSellPrice));
				product.setPriceArea(hfGood.isPresent() ? String.valueOf(hfGood.get().getSellPrice()) : "异常");
				product.setDefaultGoodsId(hfGood.get().getId());
			}

		});
		PageInfo<HfProductDisplay> page = new PageInfo<HfProductDisplay>(products);
		return builder.body(ResponseUtils.getResponseBody(page));
	}

	@ApiOperation(value = "获取商品列表商品名称", notes = "获取商品列表商品名称")
	@RequestMapping(value = "/getHfName", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "hfName", value = "商品名称", required = false, type = "Integer") })
	public ResponseEntity<JSONObject> getHfName(ProductNameSelect productNameSelect, Integer pageNum, Integer pageSize)
			throws JSONException {
		if (pageNum == null) {
			pageNum = 0;
		}
		if (pageSize == null) {
			pageSize = 0;
		}
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		PageHelper.startPage(pageNum, pageSize);
		List<HfProductDisplay> products = hfProductDao.selectProductName(productNameSelect);
		System.out.println(products);
		Set<Integer> stoneIds = products.stream().map(HfProductDisplay::getStoneId).collect(Collectors.toSet());
		System.out.println(stoneIds);
		HfStoneExample hfStoneExample = new HfStoneExample();
		hfStoneExample.createCriteria().andIdIn(Lists.newArrayList(stoneIds));
		List<HfStone> stoneInfos = hfStoneMapper.selectByExample(hfStoneExample);
		System.out.println(stoneInfos);
		Map<Integer, String> stones = stoneInfos.stream().collect(Collectors.toMap(HfStone::getId, HfStone::getHfName));
		products.forEach(product -> product.setStoneName(stones.get(product.getStoneId())));

		List<Integer> productIds = products.stream().map(HfProductDisplay::getId).collect(Collectors.toList());
		List<HfGoodsDisplayInfo> hfGoodsDisplay = hfGoodsDisplayDao.selectHfGoodsDisplay(productIds);
		Map<Integer, List<HfGoodsDisplayInfo>> hfGoodsDisplayMap = hfGoodsDisplay.stream()
				.collect(Collectors.toMap(HfGoodsDisplayInfo::getProductId, item -> Lists.newArrayList(item),
						(List<HfGoodsDisplayInfo> oldList, List<HfGoodsDisplayInfo> newList) -> {
							oldList.addAll(newList);
							return oldList;
						}));
		products.forEach(product -> {
			List<HfGoodsDisplayInfo> hfGoods = hfGoodsDisplayMap.get(product.getId());
			if (Optional.ofNullable(hfGoods).isPresent()) {
				Optional<HfGoodsDisplayInfo> hfGood = hfGoods.stream()
						.min(Comparator.comparing(HfGoodsDisplayInfo::getSellPrice));
				product.setPriceArea(hfGood.isPresent() ? String.valueOf(hfGood.get().getSellPrice()) : "异常");
				product.setDefaultGoodsId(hfGood.get().getId());
			}

		});
		PageInfo<HfProductDisplay> page = new PageInfo<HfProductDisplay>(products);
		return builder.body(ResponseUtils.getResponseBody(page));
	}

	@ApiOperation(value = "获取商品列小程序活动", notes = "获取商品列小程序活动")
	@RequestMapping(value = "/getActivityProductList", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "activityType", value = "活动类型", required = false, type = "String") })
	public ResponseEntity<JSONObject> getActivityProductList(String activityType, Integer pageNum, Integer pageSize)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Integer index = 0;
		List<ProductActivityInfo> result = new ArrayList<ProductActivityInfo>();
		List<ProductActivityInfo> list = manualDao.selectProductActivityList(activityType);
		for (int i = 0; i < list.size(); i++) {
			List<HfProductDisplay> displays = new ArrayList<HfProductDisplay>();
			ProductActivityInfo activity = list.get(i);
			HfActivity productActivityInfo = hfActivityMapper.selectByPrimaryKey(activity.getId());
			SimpleDateFormat sdf = new SimpleDateFormat("HH:ss:mm");
			activity.setStartTimes(sdf.format(activity.getStartTime()));
			activity.setEndTimes(sdf.format(activity.getEndTime()));
			Date date = new Date();
			if (date.before(activity.getStartTime())) {
				System.out.println("活动未开始");
				activity.setActivityState(-1);
				index = -1;
			}
			if (date.after(activity.getStartTime()) && date.after(activity.getEndTime())) {
				System.out.println("活动开始中");
				activity.setActivityState(0);
				index = 0;
			}
			if (date.after(activity.getEndTime())) {
				System.out.println("活动结束了");
				activity.setActivityState(1);
				index = 1;
			}
			manualDao.updateActivityState(activity);
			HfActivityProductExample example = new HfActivityProductExample();
			example.createCriteria().andActivityIdEqualTo(activity.getId());
			List<HfActivityProduct> products = hfActivityProductMapper.selectByExample(example);
			for (int j = 0; j < products.size(); j++) {
				HfActivityProduct hfactivityProduct = products.get(j);
				HfProductDisplay display = new HfProductDisplay();
				Product product = productMapper.selectByPrimaryKey(hfactivityProduct.getProductId());
				List<HfGoodsDisplayInfo> hfGoodsDisplay = hfGoodsDisplayDao
						.selectHfGoodsDisplay(hfactivityProduct.getProductId());
				if (!hfGoodsDisplay.isEmpty()) {
					if (hfGoodsDisplay.get(0).getStoneId() != null) {
						display.setStoneId(hfGoodsDisplay.get(0).getStoneId());
						HfStone hfStone = hfStoneMapper.selectByPrimaryKey(hfGoodsDisplay.get(0).getStoneId());
						display.setStoneName(hfStone.getHfName());
					}
				}
				Map<Integer, List<HfGoodsDisplayInfo>> hfGoodsDisplayMap = hfGoodsDisplay.stream()
						.collect(Collectors.toMap(HfGoodsDisplayInfo::getProductId, item -> Lists.newArrayList(item),
								(List<HfGoodsDisplayInfo> oldList, List<HfGoodsDisplayInfo> newList) -> {
									oldList.addAll(newList);
									return oldList;
								}));
				List<HfGoodsDisplayInfo> hfGoods = hfGoodsDisplayMap.get(hfactivityProduct.getProductId());
				Optional<HfGoodsDisplayInfo> hfGood = hfGoods.stream()
						.min(Comparator.comparing(HfGoodsDisplayInfo::getSellPrice));
				if (hfGood.get().getLinePrice() != null) {
					display.setLinePrice(hfGood.isPresent() ? hfGood.get().getLinePrice() : -1);
				}
				display.setId(hfactivityProduct.getProductId());
				display.setPriceArea(hfGood.isPresent() ? String.valueOf(hfGood.get().getSellPrice()) : "异常");
				display.setDefaultGoodsId(hfGood.get().getId());
				display.setActivityId(activity.getId());
				display.setActivityState(index);
				display.setStartTime(activity.getStartTime());
				display.setEndTime(activity.getEndTime());
				display.setProductName(product.getHfName());
				display.setProductDesc(product.getProductDesc());
				display.setFileId(product.getFileId());
				display.setCategoryId(product.getCategoryId());
				com.hanfu.product.center.model.HfCategory category = hfCategoryMapper
						.selectByPrimaryKey(product.getCategoryId());
				display.setCategoryName(category.getHfName());
				display.setBossId(product.getBossId());
				display.setDiscountRatio(hfactivityProduct.getDiscountRatio());
				display.setDistributionRatio(hfactivityProduct.getDistributionRatio());
				display.setFavoravlePrice(hfactivityProduct.getFavoravlePrice());
				display.setInventoryCelling(hfactivityProduct.getInventoryCelling());
				if (hfactivityProduct.getFavoravlePrice() != null && hfactivityProduct.getFavoravlePrice() != 0) {
						String s = String.valueOf(hfactivityProduct.getFavoravlePrice());
						if (null != s && s.indexOf(".") > 0) {
							s = s.replaceAll("0+?$", "");// 去掉多余的0
							s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
						}
						display.setPriceArea(s);
				} else {
					if (hfactivityProduct.getDiscountRatio() != null) {
						if (hfactivityProduct.getDiscountRatio() != 0) {
							String s = String.valueOf(
									Double.valueOf(display.getPriceArea()) * (hfactivityProduct.getDiscountRatio()/100));
							if (null != s && s.indexOf(".") > 0) {
								s = s.replaceAll("0+?$", "");// 去掉多余的0
								s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
							}
							display.setPriceArea(s);
						}
					}
				}
				displays.add(display);
			}

			activity.setProductList(displays);
			result.add(activity);
		}
		return builder.body(ResponseUtils.getResponseBody(result));
	}

	@ApiOperation(value = "获取商品列表精选", notes = "获取商品列表精选")
	@RequestMapping(value = "/getProductListSeniority", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "activityId", value = "活动id", required = false, type = "Integer") })
	public ResponseEntity<JSONObject> getProductListSeniority(Integer activityId, Integer pageNum, Integer pageSize)
			throws JSONException {
		if (pageNum == null) {
			pageNum = 0;
		}
		if (pageSize == null) {
			pageSize = 0;
		}
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		List<HfProductDisplay> displays = new ArrayList<HfProductDisplay>();
		HfActivityProductExample example = new HfActivityProductExample();
		example.createCriteria().andActivityIdEqualTo(activityId);
		PageHelper.startPage(pageNum, pageSize);
		List<HfActivityProduct> products = hfActivityProductMapper.selectByExample(example);
		for (int j = 0; j < products.size(); j++) {
			HfActivityProduct hfactivityProduct = products.get(j);
			HfProductDisplay display = new HfProductDisplay();
			Product product = productMapper.selectByPrimaryKey(hfactivityProduct.getProductId());
			List<HfGoodsDisplayInfo> hfGoodsDisplay = hfGoodsDisplayDao
					.selectHfGoodsDisplay(hfactivityProduct.getProductId());
			if (!hfGoodsDisplay.isEmpty()) {
				if (hfGoodsDisplay.get(0).getStoneId() != null) {
					display.setStoneId(hfGoodsDisplay.get(0).getStoneId());
					HfStone hfStone = hfStoneMapper.selectByPrimaryKey(hfGoodsDisplay.get(0).getStoneId());
					display.setStoneName(hfStone.getHfName());
				}
			}
			Map<Integer, List<HfGoodsDisplayInfo>> hfGoodsDisplayMap = hfGoodsDisplay.stream()
					.collect(Collectors.toMap(HfGoodsDisplayInfo::getProductId, item -> Lists.newArrayList(item),
							(List<HfGoodsDisplayInfo> oldList, List<HfGoodsDisplayInfo> newList) -> {
								oldList.addAll(newList);
								return oldList;
							}));
			List<HfGoodsDisplayInfo> hfGoods = hfGoodsDisplayMap.get(hfactivityProduct.getProductId());
			Optional<HfGoodsDisplayInfo> hfGood = hfGoods.stream()
					.min(Comparator.comparing(HfGoodsDisplayInfo::getSellPrice));
			if (hfGood.get().getLinePrice() != null) {
				display.setLinePrice(hfGood.isPresent() ? hfGood.get().getLinePrice() : -1);
			}
			display.setId(hfactivityProduct.getProductId());
			display.setPriceArea(hfGood.isPresent() ? String.valueOf(hfGood.get().getSellPrice()) : "异常");
			display.setDefaultGoodsId(hfGood.get().getId());
			display.setActivityId(activityId);
			display.setProductName(product.getHfName());
			display.setProductDesc(product.getProductDesc());
			display.setFileId(product.getFileId());
			display.setCategoryId(product.getCategoryId());
			com.hanfu.product.center.model.HfCategory category = hfCategoryMapper
					.selectByPrimaryKey(product.getCategoryId());
			display.setCategoryName(category.getHfName());
			display.setBossId(product.getBossId());
			display.setDiscountRatio(hfactivityProduct.getDiscountRatio());
			display.setDistributionRatio(hfactivityProduct.getDistributionRatio());
			display.setFavoravlePrice(hfactivityProduct.getFavoravlePrice());
			display.setInventoryCelling(hfactivityProduct.getInventoryCelling());
//			if (hfactivityProduct.getFavoravlePrice() != null) {
//				if (hfactivityProduct.getFavoravlePrice() != 0) {
//					display.setPriceArea(String.valueOf(hfactivityProduct.getFavoravlePrice()));
//				}
//			} else {
//				if (hfactivityProduct.getDiscountRatio() != null) {
//					if (hfactivityProduct.getDiscountRatio() != 0) {
//						String s = String
//								.valueOf(Double.valueOf(display.getPriceArea()) * hfactivityProduct.getDiscountRatio());
//						if (null != s && s.indexOf(".") > 0) {
//							s = s.replaceAll("0+?$", "");// 去掉多余的0
//							s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
//						}
//						display.setPriceArea(s);
//					}
//				}
//			}
			displays.add(display);
		}

		PageInfo<HfProductDisplay> page = new PageInfo<HfProductDisplay>(displays);
		return builder.body(ResponseUtils.getResponseBody(page));
	}
}
