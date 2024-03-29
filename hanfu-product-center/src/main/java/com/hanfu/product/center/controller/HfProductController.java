package com.hanfu.product.center.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
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
import com.hanfu.product.center.dao.*;
import com.hanfu.product.center.manual.model.IsDelete;
import com.hanfu.product.center.manual.model.ProductActivityInfo;
import com.hanfu.product.center.manual.model.ProductNameSelect;
import com.hanfu.product.center.model.*;
import com.hanfu.product.center.model.HfCategory;

import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
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
import com.hanfu.product.center.manual.model.Categories;
import com.hanfu.product.center.manual.model.CategoryInfo;
import com.hanfu.product.center.manual.model.HfGoodsDisplayInfo;
import com.hanfu.product.center.manual.model.HfProductDisplay;
import com.hanfu.product.center.manual.model.HomePageInfo;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.utils.response.handler.ResponseUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	@Autowired
	private HfProductCollectMapper hfProductCollectMapper;

	@Autowired
	private HfStoneConcernMapper hfStoneConcernMapper;

	@Autowired
	private HfGoodsMapper hfGoodsMapper;

	@Autowired
	private HfEvaluateMapper hfEvaluateMapper;
	
	@Autowired
	private HfPriceMapper hfPriceMapper;

    @Autowired
    private HttpServletResponse response;
    @Autowired
    private HfRespMapper hfRespMapper;
    @Autowired
    private ProductSpecMapper productSpecMapper;
    @Autowired
    private  ProductIntroducePictrueMapper productIntroducePictrueMapper;


//	@ApiOperation(value = "商品列表", notes = "根据商品id删除商品列表")
//	@RequestMapping(value = "/getProductsForRotation", method = RequestMethod.GET)
//	@ApiImplicitParams({
//			@ApiImplicitParam(paramType = "query", name = "quantity", value = "获取商品的数量限制", required = false, type = "Integer") })
//	public ResponseEntity<JSONObject> getProductsForRotation(@RequestParam(name = "quantity") Integer quantity)
//			throws JSONException {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		List<HfProductDisplay> products = hfProductDao.selectProductForRotation(quantity);
//		Set<Integer> stoneIds = products.stream().map(HfProductDisplay::getStoneId).collect(Collectors.toSet());
//		HfStoneExample hfStoneExample = new HfStoneExample();
//		hfStoneExample.createCriteria().andIdIn(Lists.newArrayList(stoneIds));
//		List<HfStone> stoneInfos = hfStoneMapper.selectByExample(hfStoneExample);
//		Map<Integer, String> stones = stoneInfos.stream().collect(Collectors.toMap(HfStone::getId, HfStone::getHfName));
//		products.forEach(product -> product.setStoneName(stones.get(product.getStoneId())));
//
//		List<Integer> productIds = products.stream().map(HfProductDisplay::getId).collect(Collectors.toList());
//		List<HfGoodsDisplayInfo> hfGoodsDisplay = hfGoodsDisplayDao.selectHfGoodsDisplay(productIds);
//		Map<Integer, List<HfGoodsDisplayInfo>> hfGoodsDisplayMap = hfGoodsDisplay.stream()
//				.collect(Collectors.toMap(HfGoodsDisplayInfo::getProductId, item -> Lists.newArrayList(item),
//						(List<HfGoodsDisplayInfo> oldList, List<HfGoodsDisplayInfo> newList) -> {
//							oldList.addAll(newList);
//							return oldList;
//						}));
//		ProductInstanceExample instanceExample = new ProductInstanceExample();
//		products.forEach(product -> {
//			if (product.getStoneId() != null) {
//				instanceExample.clear();
//				instanceExample.createCriteria().andProductIdEqualTo(product.getId())
//						.andStoneIdEqualTo(product.getStoneId());
//				product.setInstanceId(productInstanceMapper.selectByExample(instanceExample).get(0).getId());
//			}
//			List<HfGoodsDisplayInfo> hfGoods = hfGoodsDisplayMap.get(product.getId());
//			if (Optional.ofNullable(hfGoods).isPresent()) {
//				Optional<HfGoodsDisplayInfo> hfGood = hfGoods.stream()
//						.filter(goods -> Optional.ofNullable(goods.getSellPrice()).isPresent())
//						.min(Comparator.comparing(HfGoodsDisplayInfo::getSellPrice));
//				product.setPriceArea(hfGood.isPresent() ? String.valueOf(hfGood.get().getSellPrice()) : "异常");
////				product.setDefaultGoodsId(hfGood.isPresent() ? hfGood.get().getId() : );
//			}
//		});
//		products = products.stream().filter(p -> p.getInstanceId() != null).collect(Collectors.toList());
//		return builder.body(ResponseUtils.getResponseBody(products));
//	}
    
    @ApiOperation(value = "商品列表", notes = "根据商品id删除商品列表")
	@RequestMapping(value = "/getProductsForRotation", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "quantity", value = "获取商品的数量限制", required = false, type = "Integer") })
	public ResponseEntity<JSONObject> getProductsForRotation(@RequestParam(name = "quantity") Integer quantity,Integer bossId)
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
		ProductInstanceExample instanceExample = new ProductInstanceExample();
		products.forEach(product -> {
			if (product.getStoneId() != null) {
				instanceExample.clear();
				instanceExample.createCriteria().andProductIdEqualTo(product.getId())
						.andStoneIdEqualTo(product.getStoneId());
				product.setInstanceId(productInstanceMapper.selectByExample(instanceExample).get(0).getId());
			}
			List<HfGoodsDisplayInfo> hfGoods = hfGoodsDisplayMap.get(product.getId());
			if (Optional.ofNullable(hfGoods).isPresent()) {
				Optional<HfGoodsDisplayInfo> hfGood = hfGoods.stream()
						.filter(goods -> Optional.ofNullable(goods.getSellPrice()).isPresent())
						.min(Comparator.comparing(HfGoodsDisplayInfo::getSellPrice));
				product.setPriceArea(hfGood.isPresent() ? String.valueOf(hfGood.get().getSellPrice()) : "异常");
//				product.setDefaultGoodsId(hfGood.isPresent() ? hfGood.get().getId() : );
			}
		});
		products = products.stream().filter(p -> p.getInstanceId() != null).collect(Collectors.toList());
		products = products.stream().filter(p -> p.getBossId() == bossId).collect(Collectors.toList());
		return builder.body(ResponseUtils.getResponseBody(products));
	}

	@ApiOperation(value = "获取商品详情", notes = "根据商品id商品详情")
	@RequestMapping(value = "/getDetail", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "productId", value = "商品ID", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> getDetail(@RequestParam(name = "productId") Integer productId, Integer stoneId,
			@RequestParam(required = false) Integer userId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfProductDisplay product = hfProductDao.selectProduct(productId, stoneId);
		ProductInstanceExample productInstanceExample = new ProductInstanceExample();
		productInstanceExample.createCriteria().andProductIdEqualTo(product.getId()).andStoneIdEqualTo(stoneId);
		List<ProductInstance> productInstances = productInstanceMapper.selectByExample(productInstanceExample);
		ProductInstance instance = productInstances.get(0);
		HfEvaluateExample evaluateExample = new HfEvaluateExample();
		HfBoss hfBoss = hfBossMapper.selectByPrimaryKey(instance.getBossId());
		product.setBossId(hfBoss.getId());
		product.setBossName(hfBoss.getName());
		HfStone stoneInfos = hfStoneMapper.selectByPrimaryKey(stoneId);
		product.setStoneAddress(stoneInfos.getAddress());
		product.setStoneName(stoneInfos.getHfName());
		
		List<Integer> star = new ArrayList<Integer>();
		star.add(4);
		star.add(5);
		evaluateExample.clear();
		evaluateExample.createCriteria().andInstanceIdEqualTo(instance.getId()).andStarIn(star);
		if (instance.getEvaluateCount() == null || instance.getEvaluateCount() == 0) {
			product.setEvaluateRatio("0");
		} else {
			DecimalFormat format = new DecimalFormat("0.00");
			String str = format.format(hfEvaluateMapper.selectByExample(evaluateExample).size()
					/ Double.valueOf(instance.getEvaluateCount()));
			str = String.valueOf(Double.valueOf(str) * 100);

			if (null != str && str.indexOf(".") > 0) {
				str = str.replaceAll("0+?$", "");// 去掉多余的0
				str = str.replaceAll("[.]$", "");// 如最后一位是.则去掉
			}
			product.setEvaluateRatio(str);
		}
		product.setEvaluateCount(instance.getEvaluateCount());
		
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
			List<Integer> fileIds = hfProductPictrues.stream().map(HfProductPictrue::getFileId)
					.collect(Collectors.toList());
			product.setFileIds(fileIds);
		}
		if (userId != null) {
			HfProductCollectExample collectExample = new HfProductCollectExample();
			collectExample.createCriteria().andUserIdEqualTo(userId).andProductIdEqualTo(productId);
			if (hfProductCollectMapper.selectByExample(collectExample).isEmpty()) {
				product.setIsCollect(-1);
			} else {
				product.setIsCollect(1);
			}
			HfStoneConcernExample concernExample = new HfStoneConcernExample();
			System.out.println(product.getStoneId());
			concernExample.createCriteria().andUserIdEqualTo(userId).andStoneIdEqualTo(stoneId);
			if (hfStoneConcernMapper.selectByExample(concernExample).isEmpty()) {
				product.setIsConcern(-1);
			} else {
				product.setIsConcern(1);
			}

			HfUserBrowseRecord browseRecord = new HfUserBrowseRecord();
			browseRecord.setBrowseDate(LocalDateTime.now());
			browseRecord.setCreateDate(LocalDateTime.now());
			browseRecord.setIdDeleted((byte) 0);
			browseRecord.setModifyDate(LocalDateTime.now());
			browseRecord.setProductId(productId);
			browseRecord.setUserId(userId);
			browseRecord.setStoneId(stoneId);
			Product product2 = productMapper.selectByPrimaryKey(productId);
			browseRecord.setBossId(product2.getBossId());
			hfUserBrowseRecordMapper.insert(browseRecord);
			UserPersonalBrowseExample userPersonalBrowseExample = new UserPersonalBrowseExample();
			userPersonalBrowseExample.createCriteria().andUserIdEqualTo(userId)
					.andInstanceIdEqualTo(productInstances.get(0).getId());
			List<UserPersonalBrowse> browses = userPersonalBrowseMapper.selectByExample(userPersonalBrowseExample);
			if (browses.isEmpty()) {
				UserPersonalBrowse browse = new UserPersonalBrowse();
				browse.setBrowseTime(LocalDateTime.now());
				browse.setCount(1);
				browse.setInstanceId(productInstances.get(0).getId());
				browse.setCreateTime(LocalDateTime.now());
				browse.setIsDeleted((byte) 0);
				browse.setModifiyTime(LocalDateTime.now());
				browse.setProductId(productId);
				browse.setUserId(userId);
				userPersonalBrowseMapper.insert(browse);
			} else {
				UserPersonalBrowse browse = browses.get(0);
				browse.setBrowseTime(LocalDateTime.now());
				browse.setModifiyTime(LocalDateTime.now());
				browse.setCount(browse.getCount() + 1);
				userPersonalBrowseMapper.updateByPrimaryKey(browse);
			}
		}
		return builder.body(ResponseUtils.getResponseBody(product));
	}
    @ApiOperation(value = "店铺添加商品", notes = "店铺添加商品")
    @RequestMapping(value = "/addStoneProduct", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> addStoneProduct(@RequestParam(name = "productIds")Integer[] productIds,Integer stoneId,Integer userId)
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfStoneExample hfStoneExample = new HfStoneExample();
        hfStoneExample.createCriteria().andIdEqualTo(stoneId).andIsDeletedEqualTo((short) 0);
        List<HfStone> hfStones= hfStoneMapper.selectByExample(hfStoneExample);
        for (Integer productId : productIds){
            //添加商品
            Product product = productMapper.selectByPrimaryKey(productId);
            product.setId(null);
            productMapper.insertSelective(product);
            //商品规格
            ProductSpecExample productSpecExample = new ProductSpecExample();
            productSpecExample.createCriteria().andProductIdEqualTo(productId)
                    .andIsDeletedEqualTo((short) 0);
            List<ProductSpec> productSpecs = productSpecMapper.selectByExample(productSpecExample);
            productSpecs.forEach(productSpec -> {
                productSpec.setId(null);
                productSpec.setProductId(product.getId());
                productSpec.setCreateTime(LocalDateTime.now());
                productSpec.setModifyTime(LocalDateTime.now());
                productSpecMapper.insertSelective(productSpec);
            });
            //商品图片
            ProductIntroducePictrueExample productIntroducePictrueExample = new ProductIntroducePictrueExample();
            productIntroducePictrueExample.createCriteria()
                    .andIsDeletedEqualTo((short) 0)
                    .andProductIdEqualTo(productId);
            List<ProductIntroducePictrue> productIntroducePictrues =
            productIntroducePictrueMapper.selectByExample(productIntroducePictrueExample);
            productIntroducePictrues.forEach(productIntroducePictrue -> {
                productIntroducePictrue.setId(null);
                productIntroducePictrue.setProductId(product.getId());
                productIntroducePictrue.setCreateTime(LocalDateTime.now());
                productIntroducePictrue.setModifyTime(LocalDateTime.now());
                productIntroducePictrueMapper.insertSelective(productIntroducePictrue);
            });

            ProductInstanceExample productInstanceExample = new ProductInstanceExample();
            productInstanceExample.createCriteria().andStoneIdEqualTo(stoneId).andProductIdEqualTo(productId);
            productInstanceMapper.selectByExample(productInstanceExample);
            if (productInstanceMapper.selectByExample(productInstanceExample).size()==0){
                ProductInstance productInstance = new ProductInstance();
                productInstance.setCreateTime(LocalDateTime.now());
                productInstance.setModifyTime(LocalDateTime.now());
                productInstance.setLastModifier(String.valueOf(userId));
                productInstance.setIsDeleted((short) 0);
                productInstance.setStoneId(stoneId);
                productInstance.setEvaluateCount(0);
                productInstance.setProductId(product.getId());
                productInstance.setBossId(hfStones.get(0).getBossId());
                productInstance.setCategoryId(31);
                productInstance.setBrandId(1);
                productInstanceMapper.insertSelective(productInstance);
                //添加物品
                HfGoodsExample hfGoodsExample = new HfGoodsExample();
                hfGoodsExample.createCriteria().andProductIdEqualTo(productId)
                        .andIsDeletedEqualTo((short) 0);

                List<HfGoods> hfGoodsList = hfGoodsMapper.selectByExample(hfGoodsExample);
                System.out.println(hfGoodsList);
                hfGoodsList.forEach(hfGoods -> {
                    Integer yGoodsId = hfGoods.getId();
                    hfGoods.setId(null);
                    hfGoods.setProductId(product.getId());
                    hfGoods.setInstanceId(productInstance.getId());
                    hfGoods.setCreateTime(LocalDateTime.now());
                    hfGoods.setModifyTime(LocalDateTime.now());
                    HfPrice hfPrice = hfPriceMapper.selectByPrimaryKey(hfGoods.getPriceId());
                    if (hfPrice!=null){
                        hfPrice.setId(null);
                        hfPriceMapper.insertSelective(hfPrice);
                        hfGoods.setPriceId(hfPrice.getId());
                        hfPriceMapper.updateByPrimaryKeySelective(hfPrice);
                    }
                    HfResp hfResp = hfRespMapper.selectByPrimaryKey(hfGoods.getRespId());
                    if (hfResp!=null){
                        hfResp.setId(null);
                        hfRespMapper.insert(hfResp);
                        hfGoods.setRespId(hfResp.getId());
                        hfGoodsMapper.insertSelective(hfGoods);
//                        hfPrice.setGoogsId(hfGoods.getId());
                        hfResp.setGoogsId(hfGoods.getId());
                        hfRespMapper.updateByPrimaryKeySelective(hfResp);
                    }
					//价ge
					if (hfPrice != null) {
						hfPrice.setGoogsId(hfGoods.getId());
						hfPriceMapper.updateByPrimaryKeySelective(hfPrice);
					}

                    HfGoodsPictrueExample hfGoodsPictrueExample = new HfGoodsPictrueExample();
                    hfGoodsPictrueExample.createCriteria().andGoodsIdEqualTo(yGoodsId)
                            .andIsDeletedEqualTo((short) 0);
                    List<HfGoodsPictrue> hfGoodsPictrues =
                            hfGoodsPictrueMapper.selectByExample(hfGoodsPictrueExample);
                    hfGoodsPictrues.forEach(hfGoodsPictrue -> {
                        hfGoodsPictrue.setId(null);
                        hfGoodsPictrue.setCreateTime(LocalDateTime.now());
                        hfGoodsPictrue.setModifyTime(LocalDateTime.now());
                        hfGoodsPictrueMapper.insertSelective(hfGoodsPictrue);
                    });
                });
            }

        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }
	@ApiOperation(value = "获取商品信息", notes = "获取商品信息")
	@RequestMapping(value = "/getProductInfo", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "productId", value = "商品ID", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> getProductInfo(@RequestParam(name = "productId") Integer productId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfProductDisplay product = new HfProductDisplay();
		Product product2 = productMapper.selectByPrimaryKey(productId);
		product.setProductName(product2.getHfName());
		product.setProductDesc(product2.getProductDesc());
		HfCategory category = hfCategoryMapper.selectByPrimaryKey(product2.getCategoryId());
		product.setCategoryId(category.getId());
		product.setCategoryName(category.getHfName());
		return builder.body(ResponseUtils.getResponseBody(product));
	}

	@ApiOperation(value = "获取商品列表", notes = "根据类目id商品列表")
	@RequestMapping(value = "/getCategory", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "cagetoryId", value = "类目Id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> getCategory(@RequestParam(name = "cagetoryId") Integer cagetoryId,
			Integer pageNum, Integer pageSize, Integer sort, Integer priceDown, Integer priceUp, 
			@RequestParam(value = "content", required = false)List<Integer> categoryId) throws JSONException {
		if (pageNum == null) {
			pageNum = 0;
		}
		if (pageSize == null) {
			pageSize = 0;
		}
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		PageHelper.startPage(pageNum, pageSize);
		List<HfProductDisplay> products = hfProductDao.selectProductCategory(cagetoryId);
		if(!CollectionUtils.isEmpty(products)) {
		Set<Integer> stoneIds = products.stream().map(HfProductDisplay::getStoneId).collect(Collectors.toSet());
		System.out.println(stoneIds+"stoneIds");
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
		HfEvaluateExample evaluateExample = new HfEvaluateExample();
		products.forEach(product -> {
			List<HfGoodsDisplayInfo> hfGoods = hfGoodsDisplayMap.get(product.getId());

			List<Integer> star = new ArrayList<Integer>();
			star.add(4);
			star.add(5);
			evaluateExample.clear();
			evaluateExample.createCriteria().andInstanceIdEqualTo(product.getInstanceId()).andStarIn(star);
			if (product.getEvaluateCount() == null || product.getEvaluateCount() == 0) {
				product.setEvaluateRatio("0");
			} else {
				product.setEvaluateRatio(String.valueOf(
						hfEvaluateMapper.selectByExample(evaluateExample).size() / product.getEvaluateCount()));
			}

			if (Optional.ofNullable(hfGoods).isPresent()) {
				Optional<HfGoodsDisplayInfo> hfGood = hfGoods.stream()
						.min(Comparator.comparing(HfGoodsDisplayInfo::getSellPrice));
				product.setPriceArea(hfGood.isPresent() ? String.valueOf(hfGood.get().getSellPrice()) : "异常");
				product.setDefaultGoodsId(hfGood.get().getId());
			}
		});

		products = products.stream().filter(p -> p.getStoneId() != null && !StringUtils.isEmpty(p.getPriceArea()))
				.collect(Collectors.toList());

		products = sort(sort, products, priceDown, priceUp, categoryId);
		}
		PageInfo<HfProductDisplay> page = new PageInfo<HfProductDisplay>(products);
		return builder.body(ResponseUtils.getResponseBody(page));
	}
	
	
	@ApiOperation(value = "根据一级类目id获取商品列表", notes = "根据一级类目id获取商品列表")
	@RequestMapping(value = "/getCategoryByOneLevel", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "cagetoryId", value = "类目Id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> getCategoryByOneLevel(@RequestParam(name = "cagetoryId") Integer cagetoryId,
			Integer pageNum, Integer pageSize, Integer sort, Integer priceDown, Integer priceUp, 
			@RequestParam(value = "content", required = false)List<Integer> categoryId) throws JSONException {
		if (pageNum == null) {
			pageNum = 0;
		}
		if (pageSize == null) {
			pageSize = 0;
		}
		HfCategoryExample categoryExample = new HfCategoryExample();
		categoryExample.createCriteria().andParentCategoryIdEqualTo(cagetoryId);
		List<HfCategory> hfCategories = hfCategoryMapper.selectByExample(categoryExample);
		List<Integer> levelCategoryId = hfCategories.stream().map(HfCategory::getId).collect(Collectors.toList());
		categoryExample.clear();
		if(levelCategoryId.isEmpty()) {
			categoryExample.createCriteria().andParentCategoryIdEqualTo(0);
		}else {
			categoryExample.createCriteria().andParentCategoryIdIn(levelCategoryId);
		}
		hfCategories = hfCategoryMapper.selectByExample(categoryExample);
		levelCategoryId = hfCategories.stream().map(HfCategory::getId).collect(Collectors.toList());
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		System.out.println("三级类目id" + levelCategoryId);
		List<HfProductDisplay> products = new ArrayList<HfProductDisplay>();
		if(!levelCategoryId.isEmpty()) {
			PageHelper.startPage(pageNum, pageSize);
			products = hfProductDao.selectProductByCategoryId(levelCategoryId);
		}
		products = products.stream().filter(p -> p.getInstanceId() != null).collect(Collectors.toList());
		if(!CollectionUtils.isEmpty(products)) {
		Set<Integer> stoneIds = products.stream().map(HfProductDisplay::getStoneId).collect(Collectors.toSet());
		System.out.println(stoneIds+"stoneIds");
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
		HfEvaluateExample evaluateExample = new HfEvaluateExample();
		products.forEach(product -> {
			List<HfGoodsDisplayInfo> hfGoods = hfGoodsDisplayMap.get(product.getId());

			List<Integer> star = new ArrayList<Integer>();
			star.add(4);
			star.add(5);
			evaluateExample.clear();
			evaluateExample.createCriteria().andInstanceIdEqualTo(product.getInstanceId()).andStarIn(star);
			if (product.getEvaluateCount() == null || product.getEvaluateCount() == 0) {
				product.setEvaluateRatio("0");
			} else {
				product.setEvaluateRatio(String.valueOf(
						hfEvaluateMapper.selectByExample(evaluateExample).size() / product.getEvaluateCount()));
			}
			System.out.println("hfGoods"+hfGoods);
			if (Optional.ofNullable(hfGoods).isPresent()) {
				Optional<HfGoodsDisplayInfo> hfGood = hfGoods.stream()
						.min(Comparator.comparing(HfGoodsDisplayInfo::getSellPrice));
				product.setPriceArea(hfGood.isPresent() ? String.valueOf(hfGood.get().getSellPrice()) : "异常");
				product.setDefaultGoodsId(hfGood.get().getId());
			}
		});

		products = products.stream().filter(p -> p.getStoneId() != null && !StringUtils.isEmpty(p.getPriceArea()))
				.collect(Collectors.toList());

		products = sort(sort, products, priceDown, priceUp, categoryId);
		}
		PageInfo<HfProductDisplay> page = new PageInfo<HfProductDisplay>(products);
		return builder.body(ResponseUtils.getResponseBody(page));
	}

	@ApiOperation(value = "获取商品列表店铺", notes = "根据店铺id商品列表")
	@RequestMapping(value = "/getstone", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "stoneId", value = "店铺Id", required = false, type = "Integer") })
	public ResponseEntity<JSONObject> getstone(IsDelete isDelete, Integer pageNum, Integer pageSize, Integer sort
			,Integer priceDown, Integer priceUp,@RequestParam(value = "content", required = false) List<Integer> categoryId)
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
					product.setDefaultGoodsId(hfGood.isPresent() ? hfGood.get().getId() : -1);
				}
			});
			if (isDelete.getBossId() != null) {
				products = products.stream().filter(p -> p.getStoneId() == null).collect(Collectors.toList());
			}
		}
//		products = products.stream().filter(l -> l.getPriceArea() != null).collect(Collectors.toList());
		products = sort(sort, products, priceDown, priceUp, categoryId);
		PageInfo<HfProductDisplay> page = new PageInfo<HfProductDisplay>(products);
		return builder.body(ResponseUtils.getResponseBody(page));
	}
	
	@ApiOperation(value = "获取一级类目通过店铺商品", notes = "获取一级类目通过店铺商品")
	@RequestMapping(value = "/getParentCategory", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "stoneId", value = "店铺Id", required = false, type = "Integer") })
	public ResponseEntity<JSONObject> getParentCategory(IsDelete isDelete,Integer parentCategoryId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
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
					product.setDefaultGoodsId(hfGood.isPresent() ? hfGood.get().getId() : -1);
				}
			});
			if (isDelete.getBossId() != null) {
				products = products.stream().filter(p -> p.getStoneId() == null).collect(Collectors.toList());
			}
		}
		products = products.stream().filter(l -> l.getPriceArea() != null).collect(Collectors.toList());
		List<Integer> categoryId = products.stream().map(HfProductDisplay::getCategoryId).collect(Collectors.toList());
		
		if(parentCategoryId != null) {
			HfCategoryExample categoryExample = new HfCategoryExample();
			categoryExample.createCriteria().andIdIn(categoryId);
			List<HfCategory> result = hfCategoryMapper.selectByExample(categoryExample);
			categoryId = result.stream().map(HfCategory::getParentCategoryId).collect(Collectors.toList());
			HfCategoryExample example = new HfCategoryExample();
			List<CategoryInfo> hfCategories = new ArrayList<CategoryInfo>();
			example.createCriteria().andParentCategoryIdEqualTo(parentCategoryId).andIdIn(categoryId);
			List<HfCategory> list = hfCategoryMapper.selectByExample(example);
			for (int i = 0; i < list.size(); i++) {
				categoryId = products.stream().map(HfProductDisplay::getCategoryId).collect(Collectors.toList());
				List<Categories> categoriesList = new ArrayList<Categories>();
				HfCategory twoCategory = list.get(i);
				CategoryInfo info = new CategoryInfo();
				info.setTwoLevelName(twoCategory.getHfName());
				info.setTwoLevelId(twoCategory.getId());
				example.clear();
				example.createCriteria().andParentCategoryIdEqualTo(twoCategory.getId()).andIdIn(categoryId);
				List<HfCategory> list2 = hfCategoryMapper.selectByExample(example);
				for (int j = 0; j < list2.size(); j++) {
					Categories categories = new Categories();
					HfCategory threeCategory = list2.get(j);
					categories.setFileId(threeCategory.getFileId());
					categories.setHfName(threeCategory.getHfName());
					categories.setId(threeCategory.getId());
					categories.setLevelId(threeCategory.getLevelId());
					categoriesList.add(categories);
				}
        		info.setCategories(categoriesList);
        		System.out.println(info);
        		hfCategories.add(info);
			}
        	return builder.body(ResponseUtils.getResponseBody(hfCategories));
        }
		
		HfCategoryExample categoryExample = new HfCategoryExample();
		categoryExample.createCriteria().andIdIn(categoryId);
		List<HfCategory> result = hfCategoryMapper.selectByExample(categoryExample);
		categoryId = result.stream().map(HfCategory::getParentCategoryId).collect(Collectors.toList());
		categoryExample.clear();
		categoryExample.createCriteria().andIdIn(categoryId);
		result = hfCategoryMapper.selectByExample(categoryExample);
		categoryId = result.stream().map(HfCategory::getParentCategoryId).collect(Collectors.toList());
		categoryExample.clear();
		categoryExample.createCriteria().andIdIn(categoryId);
		result = hfCategoryMapper.selectByExample(categoryExample);
		return builder.body(ResponseUtils.getResponseBody(result));
	}

	@ApiOperation(value = "获取商品列表boss", notes = "获取商品列表boss")
	@RequestMapping(value = "/getProductListBoss", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getProductListBoss(HttpServletRequest request,IsDelete isDelete, Integer pageNum, Integer pageSize)
			throws JSONException, IOException {
		if (pageNum == null) {
			pageNum = 0;
		}
		if (pageSize == null) {
			pageSize = 0;
		}

		System.out.println("request.getServletContext().getAttribute得到全局数据："+request.getServletContext().getAttribute("getServletContext"));
		if (request.getServletContext().getAttribute("getServletContext")!=null){
			if (request.getServletContext().getAttribute("getServletContextType").equals("boss")){
				isDelete.setBossId((Integer) request.getServletContext().getAttribute("getServletContext"));
			}else if (request.getServletContext().getAttribute("getServletContextType").equals("stone")){
				isDelete.setStoneId((Integer) request.getServletContext().getAttribute("getServletContext"));
			}
		}
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		PageHelper.startPage(pageNum, pageSize);
		List<HfProductDisplay> products = hfProductDao.selectProductByStoneId(isDelete);
		if (isDelete.getIsBoss() != null && isDelete.getIsBoss() == 0){
			products = products.stream().filter(a->a.getStoneId()==null).collect(Collectors.toList());
		}
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
			products = products.stream().sorted(
					Comparator.comparing(HfProductDisplay::getStoneId, Comparator.nullsFirst(Integer::compareTo)))
					.collect(Collectors.toList());
		}
		PageInfo<HfProductDisplay> page = new PageInfo<HfProductDisplay>(products);
		return builder.body(ResponseUtils.getResponseBody(page));
	}


	@ApiOperation(value = "获取商品列表商品名称", notes = "获取商品列表商品名称")
	@RequestMapping(value = "/getHfName", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "hfName", value = "商品名称", required = false, type = "Integer") })
	public ResponseEntity<JSONObject> getHfName(HttpServletRequest request, ProductNameSelect productNameSelect, Integer pageNum, Integer pageSize,
			Integer sort, Integer stoneId, Integer priceDown, Integer priceUp, @RequestParam(value = "content", required = false) List<Integer> categoryId) throws JSONException {
		if (pageNum == null) {
			pageNum = 0;
		}
		if (pageSize == null) {
			pageSize = 0;
		}
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Object bossId= request.getHeader("bossId");
		productNameSelect.setBossId(Integer.valueOf((String)bossId));
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
		HfEvaluateExample evaluateExample = new HfEvaluateExample();
		products.forEach(product -> {

			List<HfGoodsDisplayInfo> hfGoods = hfGoodsDisplayMap.get(product.getId());
			if (Optional.ofNullable(hfGoods).isPresent()) {
				Optional<HfGoodsDisplayInfo> hfGood = hfGoods.stream()
						.min(Comparator.comparing(HfGoodsDisplayInfo::getSellPrice));
				product.setPriceArea(hfGood.isPresent() ? String.valueOf(hfGood.get().getSellPrice()) : "异常");
				product.setDefaultGoodsId(hfGood.get().getId());
			}

		});
		products = products.stream().filter(p -> p.getInstanceId() != null && !StringUtils.isEmpty(p.getPriceArea()))
				.collect(Collectors.toList());
		if (stoneId != null) {
			products = products.stream().filter(p -> p.getStoneId() == stoneId).collect(Collectors.toList());
		}
		sort(sort, products, priceDown, priceUp, categoryId);
		PageInfo<HfProductDisplay> page = new PageInfo<HfProductDisplay>(products);
		return builder.body(ResponseUtils.getResponseBody(page));
	}

	@ApiOperation(value = "获取商品列小程序活动", notes = "获取商品列小程序活动")
	@RequestMapping(value = "/getActivityProductList", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "activityType", value = "活动类型", required = false, type = "String") })
	public ResponseEntity<JSONObject> getActivityProductList(HttpServletRequest request, String activityType, Integer pageNum, Integer pageSize)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ProductActivityInfo productInfo = new ProductActivityInfo();
		productInfo.setActivityType(activityType);
		Object bossId= request.getHeader("bossId");
		productInfo.setBossId(Integer.valueOf((String)bossId));
		HfEvaluateExample evaluateExample = new HfEvaluateExample();
		Integer index = 0;
		List<ProductActivityInfo> result = new ArrayList<ProductActivityInfo>();
		List<ProductActivityInfo> list = manualDao.selectProductActivityList(productInfo);
		for (int i = 0; i < list.size(); i++) {
			List<HfProductDisplay> displays = new ArrayList<HfProductDisplay>();
			ProductActivityInfo activity = list.get(i);
			Date date = new Date();
			Instant instant = date.toInstant();
			ZoneId zoneId = ZoneId.systemDefault();
			LocalDateTime now = instant.atZone(zoneId).toLocalDateTime();
			LocalDateTime dayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
			if(date.after(activity.getEndTime()) || now.isAfter(dayEnd)) {
				continue;
			}
			HfActivity productActivityInfo = hfActivityMapper.selectByPrimaryKey(activity.getId());
			SimpleDateFormat sdf = new SimpleDateFormat("HH:ss:mm");
			activity.setStartTimes(sdf.format(activity.getStartTime()));
			activity.setEndTimes(sdf.format(activity.getEndTime()));
			
			if (date.before(activity.getStartTime())) {
				System.out.println("活动未开始");
				activity.setActivityState(-1);
				index = -1;
			}
			if (date.after(activity.getStartTime()) && date.before(activity.getEndTime())) {
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
				if (product != null) {
					List<HfGoodsDisplayInfo> hfGoodsDisplay = hfGoodsDisplayDao
							.selectHfGoodsDisplay(hfactivityProduct.getProductId());
					ProductInstance instance = productInstanceMapper
							.selectByPrimaryKey(hfactivityProduct.getInstanceId());
					HfStone hfStone = hfStoneMapper.selectByPrimaryKey(instance.getStoneId());
					display.setStoneName(hfStone.getHfName());
					display.setInstanceId(hfactivityProduct.getInstanceId());
					display.setStoneId(hfStone.getId());
					
					Map<Integer, List<HfGoodsDisplayInfo>> hfGoodsDisplayMap = hfGoodsDisplay.stream().collect(
							Collectors.toMap(HfGoodsDisplayInfo::getProductId, item -> Lists.newArrayList(item),
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
						String s = String.valueOf(
								Integer.valueOf(display.getPriceArea()) - hfactivityProduct.getFavoravlePrice());
						if (null != s && s.indexOf(".") > 0) {
							s = s.replaceAll("0+?$", "");// 去掉多余的0
							s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
						}
						display.setPriceArea(s);
					} else {
						if (hfactivityProduct.getDiscountRatio() != null) {
							if (hfactivityProduct.getDiscountRatio() != 0) {
								String s = String.valueOf(Double.valueOf(display.getPriceArea())
										* (hfactivityProduct.getDiscountRatio() / 100));
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
	public ResponseEntity<JSONObject> getProductListSeniority(Integer activityId, Integer pageNum, Integer pageSize,
			Integer sort, Integer priceUp, Integer priceDown, @RequestParam(value = "content", required = false) List<Integer> categoryId) throws JSONException {
		if (pageNum == null) {
			pageNum = 0;
		}
		if (pageSize == null) {
			pageSize = 0;
		}
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfEvaluateExample evaluateExample = new HfEvaluateExample();
		List<HfProductDisplay> displays = new ArrayList<HfProductDisplay>();
		HfActivityProductExample example = new HfActivityProductExample();
		example.createCriteria().andActivityIdEqualTo(activityId);
		PageHelper.startPage(pageNum, pageSize);
		List<HfActivityProduct> products = hfActivityProductMapper.selectByExample(example);
		for (int j = 0; j < products.size(); j++) {
			HfActivityProduct hfactivityProduct = products.get(j);
			HfProductDisplay display = new HfProductDisplay();
			Product product = productMapper.selectByPrimaryKey(hfactivityProduct.getProductId());
//			System.out.println("goods"+hfactivityProduct.getProductId());
			List<HfGoodsDisplayInfo> hfGoodsDisplay = hfGoodsDisplayDao
					.selectHfGoodsDisplay(hfactivityProduct.getProductId());
			System.out.println("good:"+hfGoodsDisplay);
			System.out.println("good:"+hfactivityProduct.getInstanceId());
			ProductInstance instance = productInstanceMapper.selectByPrimaryKey(hfactivityProduct.getInstanceId());
			HfStone hfStone = hfStoneMapper.selectByPrimaryKey(instance.getStoneId());
			display.setStoneName(hfStone.getHfName());
			display.setStoneId(hfStone.getId());
			display.setInstanceId(hfactivityProduct.getInstanceId());

			hfGoodsDisplay = hfGoodsDisplay.stream()
					.filter(h -> h.getInstanceId() == null || h.getInstanceId().equals(hfactivityProduct.getInstanceId()))
					.collect(Collectors.toList());
			System.out.println(hfGoodsDisplay);
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
			displays.add(display);
		}
		displays = displays.stream().filter(p -> !StringUtils.isEmpty(p.getPriceArea())).collect(Collectors.toList());
		displays = sort(sort, displays, priceDown, priceUp, categoryId);
		PageInfo<HfProductDisplay> page = new PageInfo<HfProductDisplay>(displays);
		return builder.body(ResponseUtils.getResponseBody(page));
	}
	
	public List<HfProductDisplay> sort(Integer sort, List<HfProductDisplay> list, Integer priceDown,Integer priceUp
			,List<Integer> categoryId){
		if (sort != null) {
			
			if (sort == 1) {
				for (int i = 0; i < list.size(); i++) {
					Integer saleCount = 0;
					System.out.println("list.get(i).getId()"+list.get(i).getId());
					HfGoodsExample goodsExample = new HfGoodsExample();
					goodsExample.createCriteria().andProductIdEqualTo(list.get(i).getId());
					List<HfGoods> hfGoods = hfGoodsMapper.selectByExample(goodsExample);
					if(!hfGoods.isEmpty()) {
						List<Integer> goodsId = hfGoods.stream().map(HfGoods::getId).collect(Collectors.toList());
						System.out.println("goodsId"+goodsId);
						List<HomePageInfo> pageInfos = hfProductDao.selectProductCount(goodsId);
						for (int j = 0; j < pageInfos.size(); j++) {
							saleCount += pageInfos.get(j).getSalesCount();
						}
						list.get(i).setSaleCount(saleCount);
					}
				}
				list = list.stream().filter(l -> l.getSaleCount() != null && l.getDefaultGoodsId() != null).collect(Collectors.toList());
				list.sort(new Comparator<HfProductDisplay>() {
					@Override
					public int compare(HfProductDisplay o1, HfProductDisplay o2) {
						return o2.getSaleCount() - o1.getSaleCount();
					}
				});
			}
			if (sort == -1) {
				list.sort(new Comparator<HfProductDisplay>() {
					@Override
					public int compare(HfProductDisplay o1, HfProductDisplay o2) {
						return Integer.valueOf(o1.getPriceArea()) - Integer.valueOf(o2.getPriceArea());
					}
				});
			}
			if (sort == 0) {
				list.sort(new Comparator<HfProductDisplay>() {
					@Override
					public int compare(HfProductDisplay o1, HfProductDisplay o2) {
						return Integer.valueOf(o2.getPriceArea()) - Integer.valueOf(o1.getPriceArea());
					}
				});
			}
			if(sort == 2) {
				if(priceUp != null) {
					if(priceDown>priceUp) {
						return list;
					}
					if(CollectionUtils.isEmpty(categoryId)) {
						list = list.stream().filter(l -> Integer.valueOf(l.getPriceArea()) >= priceDown && 
								Integer.valueOf(l.getPriceArea()) <= priceUp).collect(Collectors.toList());
					}else {
						list = list.stream().filter(l -> Integer.valueOf(l.getPriceArea()) >= priceDown && 
								Integer.valueOf(l.getPriceArea()) <= priceUp && categoryId.contains(l.getCategoryId())).collect(Collectors.toList());
					}
					
				}else {
					if(CollectionUtils.isEmpty(categoryId)) {
						list = list.stream().filter(l -> Integer.valueOf(l.getPriceArea()) >= priceDown).collect(Collectors.toList());
					}else {
						list = list.stream().filter(l -> Integer.valueOf(l.getPriceArea()) >= priceDown && categoryId.contains(l.getCategoryId())).collect(Collectors.toList());
					}
					
				}
			}
		}
		return list;
	}
	
	
	
	
//	@ApiOperation(value = "获取商品列表收藏", notes = "根据用户id收藏商品列表")
//	@RequestMapping(value = "/getcollect", method = RequestMethod.GET)
//	@ApiImplicitParams({
//			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = true, type = "Integer") })
//	public ResponseEntity<JSONObject> getcollect(@RequestParam(name = "userId") Integer userId, Integer pageNum,
//			Integer pageSize) throws JSONException {
//		if (pageNum == null) {
//			pageNum = 0;
//		}
//		if (pageSize == null) {
//			pageSize = 0;
//		}
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		PageHelper.startPage(pageNum, pageSize);
//		List<HfProductDisplay> products = hfProductDao.selectProductByUserId(userId);
//		System.out.println(products);
//		Set<Integer> stoneIds = products.stream().map(HfProductDisplay::getStoneId).collect(Collectors.toSet());
//		System.out.println(stoneIds);
//		HfStoneExample hfStoneExample = new HfStoneExample();
//		hfStoneExample.createCriteria().andIdIn(Lists.newArrayList(stoneIds));
//		List<HfStone> stoneInfos = hfStoneMapper.selectByExample(hfStoneExample);
//		System.out.println(stoneInfos);
//		Map<Integer, String> stones = stoneInfos.stream().collect(Collectors.toMap(HfStone::getId, HfStone::getHfName));
//		products.forEach(product -> product.setStoneName(stones.get(product.getStoneId())));
//
//		List<Integer> productIds = products.stream().map(HfProductDisplay::getId).collect(Collectors.toList());
//		List<HfGoodsDisplayInfo> hfGoodsDisplay = hfGoodsDisplayDao.selectHfGoodsDisplay(productIds);
//		Map<Integer, List<HfGoodsDisplayInfo>> hfGoodsDisplayMap = hfGoodsDisplay.stream()
//				.collect(Collectors.toMap(HfGoodsDisplayInfo::getProductId, item -> Lists.newArrayList(item),
//						(List<HfGoodsDisplayInfo> oldList, List<HfGoodsDisplayInfo> newList) -> {
//							oldList.addAll(newList);
//							return oldList;
//						}));
//		products.forEach(product -> {
//			List<HfGoodsDisplayInfo> hfGoods = hfGoodsDisplayMap.get(product.getId());
//			if (Optional.ofNullable(hfGoods).isPresent()) {
//				Optional<HfGoodsDisplayInfo> hfGood = hfGoods.stream()
//						.min(Comparator.comparing(HfGoodsDisplayInfo::getSellPrice));
//				product.setPriceArea(hfGood.isPresent() ? String.valueOf(hfGood.get().getSellPrice()) : "异常");
//				product.setDefaultGoodsId(hfGood.get().getId());
//			}
//
//		});
//		PageInfo<HfProductDisplay> page = new PageInfo<HfProductDisplay>(products);
//		return builder.body(ResponseUtils.getResponseBody(page));
//	}

//	@ApiOperation(value = "获取商品列表榜单", notes = "根据用户id收藏商品榜单")
//	@RequestMapping(value = "/getseniority", method = RequestMethod.GET)
//	@ApiImplicitParams({
//			@ApiImplicitParam(paramType = "query", name = "seniorityId", value = "榜单Id", required = true, type = "Integer") })
//	public ResponseEntity<JSONObject> getseniority(@RequestParam(name = "seniorityId") Integer seniorityId,
//			Integer pageNum, Integer pageSize) throws JSONException {
//		if (pageNum == null) {
//			pageNum = 0;
//		}
//		if (pageSize == null) {
//			pageSize = 0;
//		}
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		PageHelper.startPage(pageNum, pageSize);
//		List<HfProductDisplay> products = hfProductDao.selectProductSeniorityId(seniorityId);
//		System.out.println(products);
//		Set<Integer> stoneIds = products.stream().map(HfProductDisplay::getStoneId).collect(Collectors.toSet());
//		System.out.println(stoneIds);
//		HfStoneExample hfStoneExample = new HfStoneExample();
//		hfStoneExample.createCriteria().andIdIn(Lists.newArrayList(stoneIds));
//		List<HfStone> stoneInfos = hfStoneMapper.selectByExample(hfStoneExample);
//		System.out.println(stoneInfos);
//		Map<Integer, String> stones = stoneInfos.stream().collect(Collectors.toMap(HfStone::getId, HfStone::getHfName));
//		products.forEach(product -> product.setStoneName(stones.get(product.getStoneId())));
//
//		List<Integer> productIds = products.stream().map(HfProductDisplay::getId).collect(Collectors.toList());
//		List<HfGoodsDisplayInfo> hfGoodsDisplay = hfGoodsDisplayDao.selectHfGoodsDisplay(productIds);
//		Map<Integer, List<HfGoodsDisplayInfo>> hfGoodsDisplayMap = hfGoodsDisplay.stream()
//				.collect(Collectors.toMap(HfGoodsDisplayInfo::getProductId, item -> Lists.newArrayList(item),
//						(List<HfGoodsDisplayInfo> oldList, List<HfGoodsDisplayInfo> newList) -> {
//							oldList.addAll(newList);
//							return oldList;
//						}));
//		products.forEach(product -> {
//			List<HfGoodsDisplayInfo> hfGoods = hfGoodsDisplayMap.get(product.getId());
//			if (Optional.ofNullable(hfGoods).isPresent()) {
//				Optional<HfGoodsDisplayInfo> hfGood = hfGoods.stream()
//						.min(Comparator.comparing(HfGoodsDisplayInfo::getSellPrice));
//				product.setPriceArea(hfGood.isPresent() ? String.valueOf(hfGood.get().getSellPrice()) : "异常");
//				product.setDefaultGoodsId(hfGood.get().getId());
//			}
//
//		});
//		PageInfo<HfProductDisplay> page = new PageInfo<HfProductDisplay>(products);
//		return builder.body(ResponseUtils.getResponseBody(page));
//	}

//	@ApiOperation(value = "获取商品列表轮播图", notes = "根据用户id收藏商品轮播图")
//	@RequestMapping(value = "/getslideshow", method = RequestMethod.GET)
//	@ApiImplicitParams({
//			@ApiImplicitParam(paramType = "query", name = "seniorityId", value = "榜单Id", required = true, type = "Integer") })
//	public ResponseEntity<JSONObject> getslideshow(@RequestParam(name = "seniorityId") Integer seniorityId,
//			Integer pageNum, Integer pageSize) throws JSONException {
//		if (pageNum == null) {
//			pageNum = 0;
//		}
//		if (pageSize == null) {
//			pageSize = 0;
//		}
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		PageHelper.startPage(pageNum, pageSize);
//		List<HfProductDisplay> products = hfProductDao.selectProductSeniorityId(seniorityId);
//		Set<Integer> stoneIds = products.stream().map(HfProductDisplay::getStoneId).collect(Collectors.toSet());
//		System.out.println(stoneIds);
//		HfStoneExample hfStoneExample = new HfStoneExample();
//		hfStoneExample.createCriteria().andIdIn(Lists.newArrayList(stoneIds));
//		List<HfStone> stoneInfos = hfStoneMapper.selectByExample(hfStoneExample);
//		System.out.println(stoneInfos);
//		Map<Integer, String> stones = stoneInfos.stream().collect(Collectors.toMap(HfStone::getId, HfStone::getHfName));
//		products.forEach(product -> product.setStoneName(stones.get(product.getStoneId())));
//
//		List<Integer> productIds = products.stream().map(HfProductDisplay::getId).collect(Collectors.toList());
//		List<HfGoodsDisplayInfo> hfGoodsDisplay = hfGoodsDisplayDao.selectHfGoodsDisplay(productIds);
//		Map<Integer, List<HfGoodsDisplayInfo>> hfGoodsDisplayMap = hfGoodsDisplay.stream()
//				.collect(Collectors.toMap(HfGoodsDisplayInfo::getProductId, item -> Lists.newArrayList(item),
//						(List<HfGoodsDisplayInfo> oldList, List<HfGoodsDisplayInfo> newList) -> {
//							oldList.addAll(newList);
//							return oldList;
//						}));
//		products.forEach(product -> {
//			List<HfGoodsDisplayInfo> hfGoods = hfGoodsDisplayMap.get(product.getId());
//			if (Optional.ofNullable(hfGoods).isPresent()) {
//				Optional<HfGoodsDisplayInfo> hfGood = hfGoods.stream()
//						.min(Comparator.comparing(HfGoodsDisplayInfo::getSellPrice));
//				product.setPriceArea(hfGood.isPresent() ? String.valueOf(hfGood.get().getSellPrice()) : "异常");
//				product.setDefaultGoodsId(hfGood.get().getId());
//			}
//
//		});
//		PageInfo<HfProductDisplay> page = new PageInfo<HfProductDisplay>(products);
//		return builder.body(ResponseUtils.getResponseBody(page));
//	}

}
