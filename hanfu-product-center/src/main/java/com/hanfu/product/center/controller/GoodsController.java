package com.hanfu.product.center.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.hanfu.product.center.dao.CategorySpecMapper;
import com.hanfu.product.center.dao.FileDescMapper;
import com.hanfu.product.center.dao.GoodsSpecMapper;
import com.hanfu.product.center.dao.HfCategoryMapper;
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
import com.hanfu.product.center.manual.model.HfGoodsDisplay;
import com.hanfu.product.center.manual.model.ProductForValue;
import com.hanfu.product.center.model.CategorySpec;
import com.hanfu.product.center.model.FileDesc;
import com.hanfu.product.center.model.GoodsSpec;
import com.hanfu.product.center.model.GoodsSpecExample;
import com.hanfu.product.center.model.HfCategory;
import com.hanfu.product.center.model.HfGoods;
import com.hanfu.product.center.model.HfGoodsExample;
import com.hanfu.product.center.model.HfGoodsPictrue;
import com.hanfu.product.center.model.HfGoodsPictrueExample;
import com.hanfu.product.center.model.HfPrice;
import com.hanfu.product.center.model.HfResp;
import com.hanfu.product.center.model.HfRespExample;
import com.hanfu.product.center.model.HfStone;
import com.hanfu.product.center.model.Product;
import com.hanfu.product.center.model.ProductInstance;
import com.hanfu.product.center.model.ProductInstanceExample;
import com.hanfu.product.center.model.ProductSpec;
import com.hanfu.product.center.model.ProductSpecExample;
import com.hanfu.product.center.model.Warehouse;
import com.hanfu.product.center.request.GoodsPictrueRequest;
import com.hanfu.product.center.request.GoodsPriceInfo;
import com.hanfu.product.center.request.GoodsSpecRequest;
import com.hanfu.product.center.request.HfGoodsInfo;
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
	private WarehouseMapper warehouseMapper;

	@Autowired
	private GoodsService goodsService;
	@Autowired
	private CategorySpecMapper categorySpecMapper;
	@Autowired
	private HfCategoryMapper hfCategoryMapper;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;


	@ApiOperation(value = "获取商品实体id获取物品列表", notes = "即某商品在店铺内的所有规格")
	@RequestMapping(value = "/byInstanceId", method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "instanceId", value = "商品实体id", required = true, type = "Integer")})
	public ResponseEntity<JSONObject> listProduct(@RequestParam Integer instanceId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoodsExample example = new HfGoodsExample();
		example.createCriteria().andInstanceIdEqualTo(instanceId);
		return builder.body(ResponseUtils.getResponseBody(hfGoodsMapper.selectByExample(example)));
	}

	@ApiOperation(value = "根据物品id获取物品详情", notes = "即某物品的详细信息")
	@RequestMapping(value = "/byGoodsId", method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "goodsId", value = "物品id", required = true, type = "Integer")})
	public ResponseEntity<JSONObject> listGoodsInfo(@RequestParam Integer goodsId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(hfGoodsDao.selectGoodsInfo(goodsId)));
	}
	@ApiOperation(value = "获取物品列表", notes = "获取物品列表")
	@RequestMapping(value = "/listGoods", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> listGoods(Integer stoneId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(hfGoodsDao.selectAllGoods(stoneId)));
	}
	@ApiOperation(value = "获取商品列表", notes = "根据类目id查询商品列表")
	@RequestMapping(value = "/categoryId", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> selectProductBycategoryIdOrProductName(
			@RequestParam(name = "goodsDisplay", required = false) HfGoodsDisplay goodsDisplay,
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "size", required = false) Integer size) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		if (!StringUtils.isEmpty(page)) {
			if (!StringUtils.isEmpty(size)) {
				PageHelper.startPage(page, size);
			}
		}
		List<HfGoodsDisplay> list = hfGoodsDao.selectProductBycategoryIdOrProductName(goodsDisplay);
		if (list.isEmpty()) {
			return builder.body(ResponseUtils.getResponseBody(null));
		}
		for (int i = 0; i < list.size(); i++) {
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
				if (warehouse != null) {
					list.get(i).setWarehouseName(warehouse.getHfName());
				}
			}
			HfGoodsPictrueExample example1 = new HfGoodsPictrueExample();
			example1.createCriteria().andGoodsIdEqualTo(list.get(i).getId());
			List<HfGoodsPictrue> hfGoodsPictrue = hfGoodsPictrueMapper.selectByExample(example1);
			if (!hfGoodsPictrue.isEmpty()) {
				list.get(i).setFileId(hfGoodsPictrue.get(0).getFileId());
			}
		}
		return builder.body(ResponseUtils.getResponseBody(list));
	}

	@ApiOperation(value = "添加物品", notes = "添加物品")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> createGood(@RequestParam("fileInfo1")MultipartFile[] fileInfo1,HfGoodsInfo hfGoodsInfo) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoods record = new HfGoods();
	    GoodsSpec item1 = new GoodsSpec();
			for (String SpecValue : hfGoodsInfo.getSpecValue()) {
				record.setBossId(hfGoodsInfo.getBossId());
				record.setGoodsDesc(hfGoodsInfo.getGoodsDesc());
				record.setProductId(hfGoodsInfo.getProductId());
				record.setHfName(hfGoodsInfo.getGoodName());
				record.setStoneId(hfGoodsInfo.getHfStoreId());
				record.setBrandId(hfGoodsInfo.getBrandId());
				record.setCreateTime(LocalDateTime.now());
				record.setModifyTime(LocalDateTime.now());
				record.setMember(hfGoodsInfo.getMember());
				record.setClaim(hfGoodsInfo.getClaim());
				record.setCategoryId(hfGoodsInfo.getCatrgoryId());
				record.setIsDeleted((short) 0);
				hfGoodsMapper.insert(record);
				item1.setHfValue(SpecValue);
				item1.setGoodsId(record.getId());
				item1.setLastModifier(hfGoodsInfo.getUsername());
				item1.setCreateTime(LocalDateTime.now());
				item1.setModifyTime(LocalDateTime.now());
				item1.setIsDeleted((short) 0);
				goodsSpecMapper.insert(item1);
				ProductSpec productSpec = new ProductSpec();
				for (String specValue : hfGoodsInfo.getSpecValue()) {
					productSpec.setHfName(hfGoodsInfo.getCategoryName());
					productSpec.setSpecValue(specValue);
					productSpec.setId(record.getId());
					productSpec.setCreateTime(LocalDateTime.now());
					productSpec.setModifyTime(LocalDateTime.now());
					productSpec.setIsDeleted((short) 0);
					productSpecMapper.insert(productSpec);
					HfResp hfResp = new HfResp();
					hfResp.setQuantity(hfGoodsInfo.getQuantity());
					hfResp.setGoogsId(record.getId());
					hfResp.setCreateTime(LocalDateTime.now());
					hfResp.setModifyTime(LocalDateTime.now());
					hfResp.setIsDeleted((short) 0);
					System.out.println(fileInfo1);
					for (MultipartFile fileInfo : fileInfo1) {
						List<HfGoodsPictrue> pictures = Lists.newArrayList();
						FileMangeService fileMangeService = new FileMangeService();
						String arr[];
						arr = fileMangeService.uploadFile(fileInfo.getBytes(), String.valueOf(hfGoodsInfo.getUserId()));
						FileDesc fileDesc = new FileDesc();
						fileDesc.setFileName(fileInfo.getName());
						fileDesc.setGroupName(arr[0]);
						fileDesc.setRemoteFilename(arr[1]);
						fileDesc.setUserId(hfGoodsInfo.getUserId());
						fileDesc.setCreateTime(LocalDateTime.now());
						fileDesc.setModifyTime(LocalDateTime.now());
						fileDesc.setIsDeleted((short) 0);
						fileDescMapper.insert(fileDesc);
						HfGoodsPictrue picture = new HfGoodsPictrue();
						picture.setFileId(fileDesc.getId()); 
						picture.setGoodsId(record.getId());
						picture.setHfName(fileInfo.getName());
						picture.setSpecDesc(hfGoodsInfo.getPrictureDesc());
						picture.setCreateTime(LocalDateTime.now());
						picture.setModifyTime(LocalDateTime.now());
						picture.setLastModifier(hfGoodsInfo.getUsername());
						picture.setIsDeleted((short) 0);
						hfGoodsPictrueMapper.insert(picture);
						pictures.add(picture);
	                 }
					HfPrice price = new HfPrice();
					price.setGoogsId(record.getId());
					price.setSellPrice(hfGoodsInfo.getSellPrice());
					price.setCreateTime(LocalDateTime.now());
					price.setModifyTime(LocalDateTime.now());
					price.setLastModifier(hfGoodsInfo.getUsername());
					price.setIsDeleted((short) 0);
					hfPriceMapper.insert(price);
					}
					}
//				CategorySpec categorySpec = new CategorySpec(); x
//				for (String specValue : hfGoodsInfo.getSpecValue()) {
//					categorySpec.setSpecValue(specValue);
//					categorySpec.setHfName(hfGoodsInfo.getCategoryName());
//					categorySpec.setSpecUnit(hfGoodsInfo.getSpecUnit());
//					categorySpecMapper.insert(categorySpec);
//				}
		return builder.body(ResponseUtils.getResponseBody(""));
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
		pExample.createCriteria().andProductIdEqualTo(goods.getId());
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
	@ApiOperation(value = "获取物品规格", notes = "获取物品规格")
	@RequestMapping(value = "/specifiess", method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "goodsId", value = "物品id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> getGoodsSpecs(@RequestParam(name = "goodsId") Integer goodsId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoods goods = hfGoodsMapper.selectByPrimaryKey(goodsId);
		return builder.body(ResponseUtils.getResponseBody(hfGoodsDao.selectGoodsSpec(goods.getProductId())));
	}
	@ApiOperation(value = "编辑物品", notes = "编辑物品")
	@RequestMapping(value = "/updategood", method = RequestMethod.POST)

	public ResponseEntity<JSONObject> updateGood( @RequestParam("fileInfo1")MultipartFile[] fileInfo1,HfGoodsDisplay hfGoodsDisplay) throws Exception {
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
		if (!StringUtils.isEmpty(hfGoodsDisplay.getHfName())) {
			hfGoods.setHfName(hfGoodsDisplay.getHfName());
		}
		if (!StringUtils.isEmpty(hfGoodsDisplay.getGoodsDesc())) {
			hfGoods.setGoodsDesc(hfGoodsDisplay.getGoodsDesc());
			;
		}
		hfGoods.setModifyTime(LocalDateTime.now());
		if(hfGoods.getPriceId()==null) {
			GoodsPriceInfo goodsPriceInfo = new GoodsPriceInfo();
			goodsPriceInfo.setHfGoodsId(hfGoods.getId());
			goodsPriceInfo.setSellPrice(hfGoodsDisplay.getSellPrice());
			//        	goodsPriceInfo.setUsername(hfGoodsDisplay.getUsername());
			setGoodsPrice(goodsPriceInfo);
		}else {
			HfPrice hfPrice = hfPriceMapper.selectByPrimaryKey(hfGoods.getPriceId());
			if(!StringUtils.isEmpty(hfGoodsDisplay.getSellPrice())) {
				hfPrice.setSellPrice(hfGoodsDisplay.getSellPrice());
			}
			hfPriceMapper.updateByPrimaryKey(hfPrice);
		}
		HfResp hfResp = new HfResp();
		GoodsSpec goodsSpec = new GoodsSpec();
		goodsSpec.setHfValue(hfGoodsDisplay.getSpecValue());
		goodsSpec.setGoodsId(hfGoodsDisplay.getId());
		//        goodsSpec.setHfSpecId(String.valueOf(hfGoodsDisplay.getProductSpecId()));
		hfRespMapper.insert(hfResp);
		return builder.body(ResponseUtils.getResponseBody(hfGoodsMapper.updateByPrimaryKey(hfGoods)));
	}

//	@ApiOperation(value = "获取物品规格", notes = "获取物品规格")
//	@RequestMapping(value = "/specifies", method = RequestMethod.GET)
//	@ApiImplicitParams({
//		@ApiImplicitParam(paramType = "query", name = "goodsId", value = "物品id", required = true, type = "Integer") })
//	public ResponseEntity<JSONObject> getGoodsSpec(@RequestParam(name = "goodsId") Integer goodsId)
//			throws JSONException {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		HfGoods goods = hfGoodsMapper.selectByPrimaryKey(goodsId);
//		ProductSpecExample pExample = new ProductSpecExample();
//		pExample.createCriteria().andProductIdEqualTo(goods.getProductId());
//		List<ProductSpec> productSpecs = productSpecMapper.selectByExample(pExample);
//		productSpecs.stream().forEach(spec -> {
//			GoodsSpecExample example = new GoodsSpecExample();
//			example.createCriteria().andGoodsIdEqualTo(goodsId).andHfSpecIdEqualTo(String.valueOf(spec.getId()));
//			List<GoodsSpec> items = goodsSpecMapper.selectByExample(example);
//			if (!items.isEmpty()) {
//				spec.setSpecValue(items.get(0).getHfValue());
//			}
//		});
//		return builder.body(ResponseUtils.getResponseBody(productSpecs));
//	}
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
		item.setIsDeleted((short) 0);
		return builder.body(ResponseUtils.getResponseBody(goodsSpecMapper.insert(item)));
	}
	@ApiOperation(value = "更新物品规格", notes = "更新物品规格")
	@RequestMapping(value = "/spec/update", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> updateGoodsSpec( @RequestParam("fileInfo1")MultipartFile[] fileInfo1, GoodsSpecRequest request) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		
		List<HfGoodsPictrue> pictures = Lists.newArrayList();
		FileMangeService fileMangeService = new FileMangeService();
		String arr[];
		for (MultipartFile fileInfo : fileInfo1) {
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
			picture.setGoodsId(request.getGoodsId());
			picture.setHfName(fileInfo.getName());
			picture.setSpecDesc(request.getPrictureDesc());
			picture.setCreateTime(LocalDateTime.now());
			picture.setModifyTime(LocalDateTime.now());
			picture.setLastModifier(request.getUsername());
			picture.setIsDeleted((short) 0);
			hfGoodsPictrueMapper.updateByPrimaryKey(picture);
			pictures.add(picture);
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
		return builder.body(ResponseUtils.getResponseBody(""));
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
		@ApiImplicitParam(paramType = "query", name = "goodsId", value = "物品id", required = true, type = "Integer")})
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
	public ResponseEntity<JSONObject> addGoodsPicture(MultipartFile fileInfo1,GoodsPictrueRequest request) throws JSONException, IOException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		System.out.println(request.getGoodsId());
		HfGoods goods = hfGoodsMapper.selectByPrimaryKey(request.getGoodsId());

		if (goods == null) {
		}
		List<HfGoodsPictrue> pictures = Lists.newArrayList();
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
		} catch (IOException e) {
			logger.error("add picture failed", e);
		}
		return builder.body(ResponseUtils.getResponseBody(pictures));
	}

	@ApiOperation(value = "获取图片", notes = "获取图片")
	@RequestMapping(value = "/getFile", method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "fileId", value = "文件id", required = true, type = "Integer")})
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
		HfGoodsPictrue hfGoodsPictrue = hfGoodsPictrueMapper.selectByPrimaryKey(id);
		if(hfGoodsPictrue!=null) {
			FileDesc fileDesc = fileDescMapper.selectByPrimaryKey(hfGoodsPictrue.getFileId());
			FileMangeService fileMangeService = new FileMangeService();
			fileMangeService.deleteFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
			fileDescMapper.deleteByPrimaryKey(fileDesc.getId());
			hfGoodsPictrueMapper.deleteByPrimaryKey(id);
		}
	}
	@ApiOperation(value = "设为常买", notes = "设为常买")
	@RequestMapping(value = "/OftenBuy", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> OftenBuy(Integer userId,Integer[] goodsId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		redisTemplate.opsForValue().set(userId.toString(), goodsId);
		return builder.body(ResponseUtils.getResponseBody("设置成功"));
	}
	@ApiOperation(value = "取消常买", notes = "取消常买")
	@RequestMapping(value = "/delOftenbuy", method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> delOftenbuy(Integer userId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		if(!StringUtils.isEmpty(redisTemplate.opsForValue().get(userId.toString()))) {
			return builder.body(ResponseUtils.getResponseBody("没有数据"));
		}
		return builder.body(ResponseUtils.getResponseBody(redisTemplate.delete(userId.toString())));
	}
	@ApiOperation(value = "设置关注", notes = "设置关注")
	@RequestMapping(value = "/Concern", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> Concern(String openId,Integer[] goodsId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		redisTemplate.opsForValue().set(openId, goodsId);
		return builder.body(ResponseUtils.getResponseBody("关注成功"));
	}
	@ApiOperation(value = "取消关注", notes = "取消关注")
	@RequestMapping(value = "/delConcern", method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "openId", value = "openid", required = true, type = "String") })
	public ResponseEntity<JSONObject> delConcern(String openId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		if(StringUtils.isEmpty(redisTemplate.opsForValue().get(openId))) {
			return builder.body(ResponseUtils.getResponseBody("没有关注商品"));
		}		
		return builder.body(ResponseUtils.getResponseBody(redisTemplate.delete(openId)));
	}
	@ApiOperation(value = "批量上下架", notes = "批量上下架")
	@RequestMapping(value = "/racking", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> racking(Integer[] goodsId,Short frames)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);	
		for (Integer goods : goodsId) {
			HfGoods hfGoods = hfGoodsMapper.selectByPrimaryKey(goods);
			hfGoods.setIsDeleted(frames);
			hfGoodsMapper.updateByPrimaryKeySelective(hfGoods);
		}
		return builder.body(ResponseUtils.getResponseBody(""));
	}
	//	@ApiOperation(value = "添加活动奖项", notes = "添加活动奖项")
	//	@RequestMapping(value = "/addAwardInfo", method = RequestMethod.POST)
	//	public ResponseEntity<JSONObject> addAwardInfo(AwardInfo awardInfo) throws JSONException{
	//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
	//		Integer row = goodsService.insertAwardInfo(awardInfo);
	//		return builder.body(ResponseUtils.getResponseBody(null));
	//	}
	@ApiOperation(value = "根据条件查询", notes = "根据条件查询")
	@RequestMapping(value = "/selectByValue", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> selectByValue(ProductForValue productForValue)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);	
		return builder.body(ResponseUtils.getResponseBody(hfGoodsDao.selectList(productForValue)));
	}
	@ApiOperation(value = "价格升序", notes = "价格的升序")
	@RequestMapping(value = "/Price", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> Price()
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);	
		return builder.body(ResponseUtils.getResponseBody(hfGoodsDao.selectPrice()));
	}
	@ApiOperation(value = "价格降序", notes = "价格降序")
	@RequestMapping(value = "/desPrice", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> Pricedec()
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);	
		return builder.body(ResponseUtils.getResponseBody(hfGoodsDao.selectPriceDec()));
	}
	@ApiOperation(value = "添加收藏", notes = "添加收藏")
	@RequestMapping(value = "/collect", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> collect(Integer userId,Integer[] goodsId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);	
		redisTemplate.opsForValue().set(userId.toString(), goodsId);
		return builder.body(ResponseUtils.getResponseBody("添加成功"));
	}
	@ApiOperation(value = "取消收藏", notes = "取消收藏")
	@RequestMapping(value = "/delcollect", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> delCollect(Integer userId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);	
		if(StringUtils.isEmpty(redisTemplate.opsForValue().get(userId.toString()))) {
			return builder.body(ResponseUtils.getResponseBody("没有收藏商品"));
		}		
		return builder.body(ResponseUtils.getResponseBody(redisTemplate.delete(userId.toString())));
	}
	@ApiOperation(value = "查看收藏", notes = "查看收藏")
	@RequestMapping(value = "/selectcollect", method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> selectcollect(Integer userId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(redisTemplate.opsForValue().get(userId.toString())));
	}
	@ApiOperation(value = "出售中", notes = "出售中")
	@RequestMapping(value = "/selectFrames", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> selectFrames(Short frames)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoodsExample example = new HfGoodsExample();
		example.createCriteria().andIsDeletedEqualTo(frames);
		return builder.body(ResponseUtils.getResponseBody(hfGoodsMapper.selectByExample(example)));
	}
	@ApiOperation(value = "查询数量", notes = "查询数量")
	@RequestMapping(value = "/selectQ", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> selectQ(Short frames)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoodsExample example = new HfGoodsExample();
		example.createCriteria().andIsDeletedEqualTo(frames);
		Long count = hfGoodsMapper.countByExample(example);
			return builder.body(ResponseUtils.getResponseBody(count));
	}
	@ApiOperation(value = "查询商品总数", notes = "查询商品总数")
	@RequestMapping(value = "/queryGoods", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> quieryGoods()
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(hfGoodsMapper.countByExample(null)));
	}
	@ApiOperation(value = "查询", notes = "查询")
	@RequestMapping(value = "/queryList", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> queryList(ProductForValue productForValue)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(hfGoodsDao.selectQueryList(productForValue)));
	}
}
