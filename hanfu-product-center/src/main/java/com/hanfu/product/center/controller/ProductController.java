package com.hanfu.product.center.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import com.hanfu.product.center.dao.*;
import com.hanfu.product.center.manual.dao.HfProductDao;
import com.hanfu.product.center.manual.model.*;
import com.hanfu.product.center.model.*;
import com.hanfu.product.center.model.HfCategory;

import io.swagger.models.auth.In;
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
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.product.center.manual.dao.HfGoodsDao;
import com.hanfu.product.center.manual.dao.ProductDao;
import com.hanfu.product.center.request.CategoryRequest;
import com.hanfu.product.center.request.ProductInfoRequest;
import com.hanfu.product.center.request.ProductRequest;
import com.hanfu.product.center.request.ProductSpecRequest;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.utils.response.handler.ResponseUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/product")
@Api
public class ProductController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HfCategoryMapper hfCategoryMapper;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private ProductInfoMapper productInfoMapper;

	@Autowired
	private ProductSpecMapper productSpecMapper;

	@Autowired
	private HfGoodsMapper hfGoodsMapper;

	@Autowired
	private HfGoodsDao hfGoodsDao;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private FileDescMapper fileDescMapper;
	@Autowired
	private ProductInstanceMapper productInstanceMapper;
	@Autowired
	private HfProductDao hfProductDao;
	@Autowired
	private HfProductPictrueMapper hfProductPictrueMapper;
	@Autowired
	private HfBossMapper hfBossMapper;
	@Autowired
	private cancelProductMapper cancelProductMappers;
	@Autowired
	private ProductIntroducePictrueMapper productIntroducePictrueMapper;

	@ApiOperation(value = "获取类目列表", notes = "获取系统支持的商品类目")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "parentCategoryId", value = "上级的类目id", required = false, type = "Integer"),
		@ApiImplicitParam(paramType = "query", name = "categoryId", value = "类目id", required = false, type = "Integer"),
		@ApiImplicitParam(paramType = "query", name = "levelId", value = "类目级别", required = false, type = "Integer")})
	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> listCategory(
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "size", required = false) Integer size,
			@RequestParam(name = "parentCategoryId", required = false, defaultValue = "-1") Integer parentCategoryId,
			@RequestParam(name = "categoryId", required = false) Integer categoryId,
			@RequestParam(name = "levelId", required = false, defaultValue = "0") Integer levelId,
			@RequestParam(name = "type", required = false ,defaultValue = "0") Integer type)
					throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfCategoryExample example = new HfCategoryExample();
		if(type == 1) {
			if(parentCategoryId != null) {
				List<CategoryInfo> hfCategories = new ArrayList<CategoryInfo>();
				example.createCriteria().andParentCategoryIdEqualTo(parentCategoryId);
				List<HfCategory> list = hfCategoryMapper.selectByExample(example);
				for (int i = 0; i < list.size(); i++) {
					List<Categories> categoriesList = new ArrayList<Categories>();
					HfCategory twoCategory = list.get(i);
					CategoryInfo info = new CategoryInfo();
					info.setTwoLevelName(twoCategory.getHfName());
					info.setTwoLevelId(twoCategory.getId());
					example.clear();
					example.createCriteria().andParentCategoryIdEqualTo(twoCategory.getId());
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
        	return builder.body(ResponseUtils.getResponseBody(hfCategoryMapper.selectByExample(null)));
        }
        if(parentCategoryId != null) {
        	example.createCriteria().andParentCategoryIdEqualTo(parentCategoryId);
        	return builder.body(ResponseUtils.getResponseBody(hfCategoryMapper.selectByExample(example)));
        }
        if(levelId == 1) {
        	hfCategoryMapper.selectByExample(null);
        }
        return builder.body(ResponseUtils.getResponseBody(hfCategoryMapper.selectByExample(null)));
    }



	@ApiOperation(value = "添加商品", notes = "根据商家录入的商品")
	@RequestMapping(value = "/addproduct", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addProduct(ProductRequest request, Integer cancelId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Product product = new Product();
		product.setBossId(request.getBossId());
		product.setBrandId(1);
		product.setCategoryId(request.getCategoryId());
		product.setHfName(request.getHfName());
		product.setLastModifier(request.getLastModifier());
		product.setCreateTime(LocalDateTime.now());
		product.setModifyTime(LocalDateTime.now());
		product.setIsDeleted((short) 0);
		product.setProductDesc(request.getProductDesc());
		if (request.getClaim()!=null){
			product.setClaim(request.getClaim());
		} else {
			product.setClaim((short) 0);
		}
		if (request.getVip()!=null){
			product.setProductVip(request.getVip());
		}else {
			product.setProductVip((short) 0);
		}
		productMapper.insert(product);
//		if (request.getClaim()==1){
//			if (cancelId==null){
//				productMapper.deleteByPrimaryKey(product.getId());
//				return builder.body(ResponseUtils.getResponseBody("自提商品请选择核销员"));
//			}
//			addCancel(product.getId(),cancelId);
//		}
		ProductInstance productInstance = new ProductInstance();
		productInstance.setBossId(request.getBossId());
		productInstance.setStoneId(1);
		productInstance.setProductId(product.getId());
		productInstance.setCategoryId(request.getCategoryId());
		productInstance.setBrandId(1);
		productInstance.setCreateTime(LocalDateTime.now());
		productInstance.setLastModifier(request.getLastModifier());
		productInstance.setModifyTime(LocalDateTime.now());
		productInstance.setIsDeleted((short) 0);
		productInstanceMapper.insert(productInstance);
		Map<String, Object> map = new HashMap<>();
		map.put("bossName",hfBossMapper.selectByPrimaryKey(product.getBossId()).getName());
		map.put("productId",product.getId());
		map.put("createTime",product.getCreateTime());
		map.put("productName",request.getHfName());
		map.put("CategoryId",request.getCategoryId());
		return builder.body(ResponseUtils.getResponseBody(map));
	}

	private void addCancel(Integer productId,Integer cancelId){
		cancelProduct cancelProduct = new cancelProduct();
		cancelProduct.setCancelId(cancelId);
		cancelProduct.setClaim((short) 0);
		cancelProduct.setCreateTime(LocalDateTime.now());
		cancelProduct.setModifyTime(LocalDateTime.now());
		cancelProduct.setProductId(productId);
		cancelProduct.setIsDeleted((short) 0);
		cancelProductMappers.insert(cancelProduct);
	}
	@ApiOperation(value = "删除商品列表", notes = "根据商品id删除商品列表")
	@RequestMapping(value = "/deleteProductId", method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "productId", value = "商品ID", required = true, type = "Integer")})
	public ResponseEntity<JSONObject> deleteProduct(@RequestParam(name = "productId") Integer productId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ProductSpecExample example = new ProductSpecExample();
		example.createCriteria().andProductIdEqualTo(productId);
		ProductInfoExample example2 = new ProductInfoExample();
		example2.createCriteria().andProductIdEqualTo(productId);
		productSpecMapper.deleteByExample(example);
		productInfoMapper.deleteByExample(example2);
		return builder.body(ResponseUtils.getResponseBody(productMapper.deleteByPrimaryKey(productId)));
	}
	@ApiOperation(value = "查询商品列表", notes = "根据类目id查询商品列表")
	@RequestMapping(value = "/selectProductId", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "categoryId", value = "类目ID", required = true, type = "Integer")})
	public ResponseEntity<JSONObject> selectProduct(@RequestParam(name = "categoryId") Integer categoryId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ProductExample example = new ProductExample();
		example.createCriteria().andCategoryIdEqualTo(categoryId);
		return builder.body(ResponseUtils.getResponseBody(productMapper.selectByExample(example)));
	}
	@ApiOperation(value = "添加类目", notes = "添加系统支持的商品类目")
	@RequestMapping(value = "/addCategory", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> AddCategory(CategoryRequest request,MultipartFile fileInfo) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfCategory category = new HfCategory();
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replace("-", "");
		if(fileInfo != null) {
			category.setFileId(updateCategoryPicture(fileInfo,uuid,"无"));
		}
		category.setLevelId(request.getLevelId());
		category.setHfName(request.getCategory());
		category.setParentCategoryId(request.getParentCategoryId());
		category.setCreateTime(LocalDateTime.now());
		category.setModifyTime(LocalDateTime.now());
		category.setIsDeleted((short) 0);
		return builder.body(ResponseUtils.getResponseBody(hfCategoryMapper.insert(category)));
	}

	@ApiOperation(value = "删除类目", notes = "删除类目")
	@RequestMapping(value = "/deleteCategory", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> deleteCategory(Integer categoryId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		FileMangeService fileMangeService = new FileMangeService();
		HfCategory hfCategory = hfCategoryMapper.selectByPrimaryKey(categoryId);
		if(hfCategory.getFileId() != null) {
			FileDesc fileDesc = fileDescMapper.selectByPrimaryKey(hfCategory.getFileId());
			fileMangeService.deleteFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
			fileDescMapper.deleteByPrimaryKey(hfCategory.getFileId());
		}
		return builder.body(ResponseUtils.getResponseBody(hfCategoryMapper.deleteByPrimaryKey(categoryId)));
	}

	@ApiOperation(value = "添加商品图片", notes = "添加商品图片")
	@RequestMapping(value = "/addProductPictrue", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addProductPictrue(MultipartFile fileInfo,Integer productId, Integer userId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Product product = productMapper.selectByPrimaryKey(productId);
		if(product == null) {
			return builder.body(ResponseUtils.getResponseBody(""));
		}
		String arr[];
		arr = FileMangeService.uploadFile(fileInfo.getBytes(), String.valueOf(userId));
		FileDesc fileDesc = new FileDesc();
		fileDesc.setFileName(fileInfo.getName());
		fileDesc.setGroupName(arr[0]);
		fileDesc.setRemoteFilename(arr[1]);
		fileDesc.setUserId(userId);
		fileDesc.setCreateTime(LocalDateTime.now());
		fileDesc.setModifyTime(LocalDateTime.now());
		fileDescMapper.insertSelective(fileDesc);
		product.setFileId(fileDesc.getId());
		
		HfProductPictrue productPicture = new HfProductPictrue();
		productPicture.setProductId(productId);
		productPicture.setCreateTime(LocalDateTime.now());
		productPicture.setFileId(fileDesc.getId());
		productPicture.setLastModifier(String.valueOf(userId));
		productPicture.setHfName(fileInfo.getName());
		productPicture.setModifyTime(LocalDateTime.now());
		hfProductPictrueMapper.insertSelective(productPicture);
		productMapper.updateByPrimaryKeySelective(product);

		Map<String, Integer> params = new HashMap<>();
		params.put("fileId", fileDesc.getId());
		params.put("hfProductPictrueId",productPicture.getId());
		return builder.body(ResponseUtils.getResponseBody(params));
	}
	@ApiOperation(value = "查询所有类目", notes = "查询所有类目")
	@RequestMapping(value = "/findAllCategory", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> findAllCategory() throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(hfCategoryMapper.selectByExample(null)));
	}
	@ApiOperation(value = "编辑类目", notes = "编辑类目")
	@RequestMapping(value = "/updateCategory", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> updateCategory(CategoryRequest request, @RequestParam Integer catrgoryId,MultipartFile fileInfo) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);

		HfCategory hfCategory = hfCategoryMapper.selectByPrimaryKey(catrgoryId);

		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replace("-", "");
		if(fileInfo != null) {
			hfCategory.setFileId(updateCategoryPicture(fileInfo,uuid,"无"));
		}
		if(!StringUtils.isEmpty(request.getLevelId())) {
			hfCategory.setLevelId(request.getLevelId());
		}
		if(!StringUtils.isEmpty(request.getCategory())) {
			hfCategory.setHfName(request.getCategory());
		}
		if(!StringUtils.isEmpty(request.getParentCategoryId())) {
			hfCategory.setParentCategoryId(request.getParentCategoryId());
		}
		hfCategory.setModifyTime(LocalDateTime.now());
		return builder.body(ResponseUtils.getResponseBody(hfCategoryMapper.updateByPrimaryKey(hfCategory)));
	}

	@RequestMapping(value = "/updateCategoryPicture", method = RequestMethod.POST)
	@ApiOperation(value = "更新类目图片", notes = "更新类目图片")
	public Integer updateCategoryPicture(MultipartFile fileInfo, @RequestParam(required = false) String uuid ,@RequestParam String type) throws Exception {
		FileMangeService fileMangeService = new FileMangeService();
		String arr[];
		arr = fileMangeService.uploadFile(fileInfo.getBytes(),"-1");
		if("类目页面图片".equals(type)) {
			FileDesc fileDesc = new FileDesc();
			fileDesc.setFileName("类目页面图片");
			fileDesc.setGroupName(arr[0]);
			fileDesc.setRemoteFilename(arr[1]);
			fileDesc.setUserId(-1);
			fileDesc.setCreateTime(LocalDateTime.now());
			fileDesc.setModifyTime(LocalDateTime.now());
			fileDesc.setIsDeleted((short) 0);
			fileDescMapper.insert(fileDesc);
			return -1;
		}
		Integer fileId = null;
		FileDescExample example = new FileDescExample();
		example.createCriteria().andFileNameEqualTo(uuid);
		List<FileDesc> list = fileDescMapper.selectByExample(example);
		if (list.isEmpty()) {
			FileDesc fileDesc = new FileDesc();
			fileDesc.setFileName(uuid);
			fileDesc.setGroupName(arr[0]);
			fileDesc.setRemoteFilename(arr[1]);
			fileDesc.setUserId(-1);
			fileDesc.setCreateTime(LocalDateTime.now());
			fileDesc.setModifyTime(LocalDateTime.now());
			fileDesc.setIsDeleted((short) 0);
			fileDescMapper.insert(fileDesc);
			fileId = fileDesc.getId();
		} else {
			FileDesc fileDesc = list.get(0);
			fileMangeService.deleteFile(fileDesc.getGroupName(),fileDesc.getRemoteFilename() );
			fileDesc.setGroupName(arr[0]);
			fileDesc.setRemoteFilename(arr[1]);
			fileDesc.setModifyTime(LocalDateTime.now());
			fileDescMapper.updateByPrimaryKey(fileDesc);
			fileId = fileDesc.getId();
		}
		return fileId;
	}

	@ApiOperation(value = "查询类目页面图片", notes = "查询类目页面图片")
	@RequestMapping(value = "/findCategoryPagePicture", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> findCategoryPagePicture() throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		FileDescExample example = new FileDescExample();
		example.createCriteria().andFileNameEqualTo("类目页面图片");
		List<FileDesc> list = fileDescMapper.selectByExample(example);
		return builder.body(ResponseUtils.getResponseBody(list));
	}

	@ApiOperation(value = "获取商品列表", notes = "根据类目id查询商品列表")
	@RequestMapping(value = "/categoryId", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> listProductBycategoryId(ProductDispaly productDispaly) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(productDao.selectProductBycategoryId(productDispaly)));
	}

	@ApiOperation(value = "获取商品列表", notes = "根据商家获取商家录入的商品列表")
	@RequestMapping(value = "/byBossId", method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "bossId", value = "商家ID", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> listProduct(@RequestParam(name = "bossId") Integer bossId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ProductExample example = null;
		if (bossId != null) {
			example = new ProductExample();
			example.createCriteria().andBossIdEqualTo(bossId);
		}
		return builder.body(ResponseUtils.getResponseBody(productMapper.selectByExample(example)));
	}

	@ApiOperation(value = "获取商品列表加类目名称", notes = "根据商家获取商家录入的商品列表及类目名称")
	@RequestMapping(value = "/listProductAndCategoryName", method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "bossId", value = "商家ID", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> listProductAndCategoryName(@RequestParam(name = "bossId") Integer bossId,
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "size", required = false) Integer size) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ProductExample example = new ProductExample();
		example.createCriteria().andBossIdEqualTo(bossId);
		if (!StringUtils.isEmpty(page)) {
			if (!StringUtils.isEmpty(size)) {
				PageHelper.startPage(page, size);
			}
		}

		return builder.body(ResponseUtils.getResponseBody(productDao.selectProductDisplay(bossId)));
	}

	@ApiOperation(value = "选中删除商品列表", notes = "根据商品id删除商品列表")
	@RequestMapping(value = "/deleteSelectProductId", method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "productId", value = "商品ID", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> deleteAllProduct(@RequestParam(name = "productId") Integer[] productId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ProductSpecExample example = new ProductSpecExample();
		ProductInfoExample example2 = new ProductInfoExample();
		for (int i = 0; i < productId.length; i++) {
			example.createCriteria().andProductIdEqualTo(productId[i]);
			example2.createCriteria().andProductIdEqualTo(productId[i]);
			productSpecMapper.deleteByExample(example);
			productInfoMapper.deleteByExample(example2);
		}
		return builder.body(ResponseUtils.getResponseBody(productDao.deleteSelectProduct(productId)));
	}

	@ApiOperation(value = "修改商品列表", notes = "根据商品修改商品列表")
	@RequestMapping(value = "/updateProductId", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> updateProductId(ProductRequest request) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Product product = productMapper.selectByPrimaryKey(request.getId());
		if (product == null) {
			throw new Exception("商品不存在");
		}
		if (!StringUtils.isEmpty(request.getProductDesc())) {
			product.setProductDesc(request.getProductDesc());
		}
		if (!StringUtils.isEmpty(request.getLastModifier())) {
			product.setLastModifier((request.getLastModifier()));
		}
		product.setModifyTime(LocalDateTime.now());
		return builder.body(ResponseUtils.getResponseBody(productMapper.updateByPrimaryKey(product)));
	}

	@ApiOperation(value = "获取商品属性", notes = "根据商品id获取商品的属性值")
	@RequestMapping(value = "/attributes", method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "productId", value = "商品ID", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> getProductInfo(@RequestParam(name = "productId") Integer productId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ProductInfoExample example = new ProductInfoExample();
		example.createCriteria().andProductIdEqualTo(productId);
		return builder.body(ResponseUtils.getResponseBody(productInfoMapper.selectByExample(example)));
	}

	@ApiOperation(value = "添加商品属性", notes = "为某一个商品添加属性")
	@RequestMapping(value = "/addAttribute", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addProductInfo(ProductInfoRequest request) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ProductInfo item = new ProductInfo();
		item.setHfName(request.getHfName());
		item.setHfRemark(request.getHfRemark());
		item.setHfValue(request.getInfoValue());
		item.setCategoryId(request.getCategoryId());
		item.setCreateTime(LocalDateTime.now());
		item.setModifyTime(LocalDateTime.now());
		item.setIsDeleted((short) 0);
		item.setLastModifier(request.getUsername());
		item.setProductId(request.getProductId());
		return builder.body(ResponseUtils.getResponseBody(productInfoMapper.insert(item)));
	}

	@ApiOperation(value = "删除商品属性", notes = "根据商品属性id删除商品属性")
	@RequestMapping(value = "/deleteattributes", method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "productInfoId", value = "商品属性ID", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> deleteattributes(@RequestParam(name = "productInfoId") Integer productInfoId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(productInfoMapper.deleteByPrimaryKey(productInfoId)));
	}

	@ApiOperation(value = "获取商品规格", notes = "根据商品id获取商品的规格描述")
	@RequestMapping(value = "/specifies", method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "productId", value = "商品ID", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> getProductSpec(@RequestParam(name = "productId") Integer productId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ProductSpecExample example = new ProductSpecExample();
		example.createCriteria().andProductIdEqualTo(productId);
		return builder.body(ResponseUtils.getResponseBody(productSpecMapper.selectByExample(example)));
	}

	@ApiOperation(value = "添加商品规格", notes = "为某一个商品添加规格")
	@RequestMapping(value = "/addSpecify", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addProductSpec(ProductSpecRequest request) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ProductSpec item = new ProductSpec();
			item.setHfName(request.getHfName());
			item.setProductId(request.getProductId());
			item.setSpecType("类型");
			item.setSpecUnit(request.getSpecUnit());
			item.setSpecValue(request.getSpecValue());
			item.setCreateTime(LocalDateTime.now());
			item.setModifyTime(LocalDateTime.now());
			item.setIsDeleted((short) 0);
		return builder.body(ResponseUtils.getResponseBody(productSpecMapper.insert(item)));
	}

	@ApiOperation(value = "删除商品规格", notes = "根据规格id删除商品的规格描述")
	@RequestMapping(value = "/deleteSpecifies", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> deleteSpecifies(@RequestParam(name = "productSpecId") Integer productSpecId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(productSpecMapper.deleteByPrimaryKey(productSpecId)));
	}
	@ApiOperation(value = "根据商品id查询此商品是否添加过", notes = "根据商品id查询此商品是否添加过")
	@RequestMapping(value = "/selectProductIdIsExists", method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "productId", value = "商品id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> selectProductIdIsExists(@RequestParam(name = "productId") Integer productId)
			throws Exception {
		Integer result = 1;
		Product product = productMapper.selectByPrimaryKey(productId);
		if (product == null) {
			throw new Exception("该商品不存在");
		}
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfGoodsExample example = new HfGoodsExample();
		example.createCriteria().andProductIdEqualTo(product.getId());
		List<HfGoods> list = hfGoodsMapper.selectByExample(example);
		if (list.isEmpty()) {
			result = 0;
			return builder.body(ResponseUtils.getResponseBody(result));
		} else {
			return builder.body(ResponseUtils.getResponseBody(result));
		}
	}
	@ApiOperation(value = "轮播图", notes = "轮播图")
	@RequestMapping(value = "/slideshow", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> slideshow() throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(hfGoodsDao.selectSlideshow()));
	}
//	@ApiOperation(value = "到店支付", notes = "到店支付")
//	@RequestMapping(value = "/pay", method = RequestMethod.GET)
//	public ResponseEntity<JSONObject> pay(Integer productId) throws Exception {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		List<Object> list = new ArrayList<>();
//		Product product = productMapper.selectByPrimaryKey(productId);
//		list.add(product.getHfName());
//		list.add(product.getCancelId());
//		return builder.body(ResponseUtils.getResponseBody(list));
//	}
@ApiOperation(value = "批量上下架", notes = "批量上下架")
@RequestMapping(value = "/racking", method = RequestMethod.GET)
public ResponseEntity<JSONObject> racking(Integer[] productId,Short frames)
		throws JSONException {
	BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
	for (Integer pId : productId) {
		Product product = productMapper.selectByPrimaryKey(pId);
		product.setIsDeleted(frames);
		productMapper.updateByPrimaryKeySelective(product);
	}
	return builder.body(ResponseUtils.getResponseBody("0"));
}

	@ApiOperation(value = "修改商品规格", notes = "修改商品规格")
	@RequestMapping(value = "/updatespec", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> updatespec(Integer productSpecId,String specName,Integer categorySpecId,Integer productId,String specUnit)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ProductSpec productSpec = new ProductSpec();
		productSpec.setId(productSpecId);
		productSpec.setHfName(specName);
		productSpec.setCategorySpecId(categorySpecId);
		productSpec.setProductId(productId);
		productSpec.setSpecUnit(specUnit);
		productSpec.setCreateTime(LocalDateTime.now());
		productSpec.setModifyTime(LocalDateTime.now());
		productSpec.setIsDeleted((short) 0);
		productSpecMapper.updateByPrimaryKeySelective(productSpec);
		return builder.body(ResponseUtils.getResponseBody(productSpec));
	}

	@ApiOperation(value = "获取商品所有物品", notes = "获取商品所有物品")
	@RequestMapping(value = "/selectProductGoods", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> selectProductGoods(SelectProductGoods productId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(hfProductDao.selectProductGoods(productId)));
	}

	@ApiOperation(value = "获取商品图片", notes = "获取商品图片")
	@RequestMapping(value = "/selectProductPictures", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> selectProductPictures(Integer productId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfProductPictrueExample example = new HfProductPictrueExample();
		example.createCriteria().andProductIdEqualTo(productId);

		return builder.body(ResponseUtils.getResponseBody(hfProductPictrueMapper.selectByExample(example)));
	}

	@ApiOperation(value = "添加商品介绍图", notes = "添加商品介绍图")
	@RequestMapping(value = "/addProductIntroducePictrue", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addProductIntroducePictrue(MultipartFile fileInfo,Integer productId,String introduceDesc,Integer userId)
			throws JSONException, IOException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Product product = productMapper.selectByPrimaryKey(productId);
		if(product == null) {
			return builder.body(ResponseUtils.getResponseBody(""));
		}
		String arr[];
		arr = FileMangeService.uploadFile(fileInfo.getBytes(), String.valueOf(productId));
		FileDesc fileDesc = new FileDesc();
		fileDesc.setFileName(fileInfo.getName());
		fileDesc.setGroupName(arr[0]);
		fileDesc.setRemoteFilename(arr[1]);
		fileDesc.setUserId(productId);
		fileDesc.setCreateTime(LocalDateTime.now());
		fileDesc.setModifyTime(LocalDateTime.now());
		fileDescMapper.insertSelective(fileDesc);
		ProductIntroducePictrue productIntroducePictrue = new ProductIntroducePictrue();
		productIntroducePictrue.setCreateTime(LocalDateTime.now());
		productIntroducePictrue.setModifyTime(LocalDateTime.now());
		productIntroducePictrue.setIsDeleted((short) 0);
		productIntroducePictrue.setLastModifier(String.valueOf(userId));
		productIntroducePictrue.setFileId(fileDesc.getId());
		productIntroducePictrue.setIntroduceDesc(introduceDesc);
		productIntroducePictrue.setHfName(fileInfo.getName());
		productIntroducePictrue.setProductId(productId);
		productIntroducePictrueMapper.insert(productIntroducePictrue);
		Map<String, Integer> params = new HashMap<>();
		params.put("fileId", fileDesc.getId());
		params.put("productIntroducePictrueId", productIntroducePictrue.getId());
		return builder.body(ResponseUtils.getResponseBody(params));
	}

	@ApiOperation(value = "删除商品介绍图片", notes = "删除商品介绍图片")
	@RequestMapping(value = "/deletedPictrue", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> deletedPictrue(Integer fileId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ProductIntroducePictrue productIntroducePictrue = new ProductIntroducePictrue();
		productIntroducePictrue.setIsDeleted((short) 1);
		ProductIntroducePictrueExample productIntroducePictrueExample =new ProductIntroducePictrueExample();
		productIntroducePictrueExample.createCriteria().andFileIdEqualTo(fileId).andIsDeletedEqualTo((short) 0);
		productIntroducePictrueMapper.updateByExampleSelective(productIntroducePictrue,productIntroducePictrueExample);
		return builder.body(ResponseUtils.getResponseBody(0));
	}

	@ApiOperation(value = "获取商品介绍图片", notes = "获取商品介绍图片")
	@RequestMapping(value = "/selectProductIntroducePictrue", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> selectProductIntroducePictrue(Integer productId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ProductIntroducePictrueExample example = new ProductIntroducePictrueExample();
		example.createCriteria().andProductIdEqualTo(productId).andIsDeletedEqualTo((short) 0);

		return builder.body(ResponseUtils.getResponseBody(productIntroducePictrueMapper.selectByExample(example)));
	}
}
