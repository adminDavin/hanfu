package com.hanfu.product.center.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.hanfu.product.center.dao.*;
import com.hanfu.product.center.manual.dao.HfProductDao;
import com.hanfu.product.center.manual.dao.ManualDao;
import com.hanfu.product.center.manual.model.*;
import com.hanfu.product.center.model.*;
import com.hanfu.product.center.model.HfCategory;

import com.hanfu.user.center.config.PermissionConstants;
import com.hanfu.user.center.service.RequiredPermission;
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
import com.hanfu.product.center.manual.dao.HfMemberDao;
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

import javax.servlet.http.HttpServletRequest;

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
	@Autowired
	private HfStoneMapper hfStoneMapper;
	@Autowired
	private HfActivityProductMapper hfActivityProductMapper;
	@Autowired
	private HfProductCollectMapper hfProductCollectMapper;
	@Autowired
	private HfStoneConcernMapper hfStoneConcernMapper;
	@Autowired
	private HfMemberDao hfMemberDao;
	@Autowired
	private ManualDao manualDao;
	@Autowired
	private HfPriceMapper hfPriceMapper;

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
			@RequestParam(name = "type", required = false ,defaultValue = "0") Integer type,
			HttpServletRequest request)
					throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Integer bossId=null;
		if (request.getServletContext().getAttribute("getServletContext")!=null){
			if (request.getServletContext().getAttribute("getServletContextType").equals("boss")){
				bossId = (Integer) request.getServletContext().getAttribute("getServletContext");
			}
		}
		if(bossId == null) {
			bossId = Integer.valueOf((String) request.getHeader("bossId"));
		}
		HfCategoryExample example = new HfCategoryExample();
		if(type == 1) {
			if(parentCategoryId != null) {
				List<CategoryInfo> hfCategories = new ArrayList<CategoryInfo>();
				example.createCriteria().andParentCategoryIdEqualTo(parentCategoryId).andBossIdEqualTo(bossId);
				List<HfCategory> list = hfCategoryMapper.selectByExample(example);
				for (int i = 0; i < list.size(); i++) {
					List<Categories> categoriesList = new ArrayList<Categories>();
					HfCategory twoCategory = list.get(i);
					CategoryInfo info = new CategoryInfo();
					info.setTwoLevelName(twoCategory.getHfName());
					info.setTwoLevelId(twoCategory.getId());
					example.clear();
					example.createCriteria().andParentCategoryIdEqualTo(twoCategory.getId()).andBossIdEqualTo(bossId);
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
        	example.createCriteria().andParentCategoryIdEqualTo(parentCategoryId).andBossIdEqualTo(bossId);
        	return builder.body(ResponseUtils.getResponseBody(hfCategoryMapper.selectByExample(example)));
        }
        if(levelId == 1) {
        	hfCategoryMapper.selectByExample(null);
        }
        return builder.body(ResponseUtils.getResponseBody(hfCategoryMapper.selectByExample(null)));
    }
	
	@ApiOperation(value = "获取类目根据条件", notes = "获取类目根据条件")
	@RequestMapping(value = "/getCategoryByInfo", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getCategoryByInfo(HttpServletRequest request,Integer level, String name) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Integer bossId =1;
		if (request.getServletContext().getAttribute("getServletContextType").equals("boss")){
			System.out.println("request.getServletContext().getAttribute得到全局数据："+request.getServletContext().getAttribute("getServletContext"));
			if (request.getServletContext().getAttribute("getServletContext")!=null){
				bossId=(Integer) request.getServletContext().getAttribute("getServletContext");
			}
		}
		HfCategory h = new HfCategory();
		h.setLevelId(level);
		h.setHfName(name);
		h.setBossId(bossId);
		List<Categories> result  = new ArrayList<Categories>();
		List<HfCategory> list = manualDao.findCategoryByInfo(h);
		for (int i = 0; i < list.size(); i++) {
			Categories categories = new Categories();
			categories.setDate(list.get(i).getCreateTime());
			categories.setHfName(list.get(i).getHfName());
			categories.setLevel(list.get(i).getLevelId()+1);
			result.add(categories);
		}
        return builder.body(ResponseUtils.getResponseBody(result));
    }	

	@ApiOperation(value = "获取所有类目", notes = "获取所有类目全部数据")
	@RequestMapping(value = "/categoryAll", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> listCategory(HttpServletRequest request) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Integer bossId =1;
		if (request.getServletContext().getAttribute("getServletContextType").equals("boss")){
			System.out.println("request.getServletContext().getAttribute得到全局数据："+request.getServletContext().getAttribute("getServletContext"));
			if (request.getServletContext().getAttribute("getServletContext")!=null){
				bossId=(Integer) request.getServletContext().getAttribute("getServletContext");
			}
		}
		List<CategoryInfo> result = new ArrayList<CategoryInfo>();
		List<Categories> categoriesList = null;
		List<Categories> categorieList = null;
		HfCategoryExample example = new HfCategoryExample();
		example.createCriteria().andLevelIdEqualTo(0).andBossIdEqualTo(bossId);
		List<HfCategory> list = hfCategoryMapper.selectByExample(example);
		for (int i = 0; i < list.size(); i++) {
			HfCategory hfCategory = list.get(i);
			CategoryInfo info = new CategoryInfo();
			info.setId(hfCategory.getId());
			info.setFileId(hfCategory.getFileId());
			info.setHfName(hfCategory.getHfName());
			info.setDate(hfCategory.getCreateTime().plusHours(8));
			info.setLevel(1);
			example.clear();
			example.createCriteria().andParentCategoryIdEqualTo(hfCategory.getId()).andBossIdEqualTo(bossId);
			List<HfCategory> list2 = hfCategoryMapper.selectByExample(example);
			categoriesList = new ArrayList<Categories>();
			for (int j = 0; j < list2.size(); j++) {
				HfCategory hfCategory2 = list2.get(j);
				Categories categories = new Categories();
				categories.setLevel(2);
				categories.setFileId(hfCategory2.getFileId());
				categories.setId(hfCategory2.getId());
				categories.setHfName(hfCategory2.getHfName());
				categories.setDate(hfCategory2.getCreateTime().plusHours(8));
				example.clear();
				example.createCriteria().andParentCategoryIdEqualTo(hfCategory2.getId()).andBossIdEqualTo(bossId);
				categorieList = new ArrayList<Categories>();
				List<HfCategory> list3 = hfCategoryMapper.selectByExample(example);
				for (int k = 0; k < list3.size(); k++) {
					HfCategory hfCategory3 = list3.get(k);
					Categories categorie = new Categories();
					categorie.setId(hfCategory3.getId());
					categorie.setHfName(hfCategory3.getHfName());
					categorie.setFileId(hfCategory3.getFileId());
					categorie.setLevel(3);
					categorie.setDate(hfCategory3.getCreateTime().plusHours(8));
					categorieList.add(categorie);
				}
				categories.setCategories(categorieList);
				categoriesList.add(categories);
			}
			info.setCategories(categoriesList);
			result.add(info);
		}
        return builder.body(ResponseUtils.getResponseBody(result));
    }
	
	@ApiOperation(value = "简单获取所有类目app", notes = "简单获取所有类目app")
	@RequestMapping(value = "/getCategoryAll", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getCategoryAll(Integer bossId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfCategoryExample example = new HfCategoryExample();
		example.createCriteria().andBossIdEqualTo(bossId).andIsDeletedEqualTo((short) 0);
		List<HfCategory> list = hfCategoryMapper.selectByExample(example);
        return builder.body(ResponseUtils.getResponseBody(list));
    }
	
	@RequiredPermission(PermissionConstants.ADMIN_PRODUCT_INSERT)
	@ApiOperation(value = "添加商品", notes = "根据商家录入的商品")
	@RequestMapping(value = "/addproduct", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addProduct(HttpServletRequest requests,ProductRequest request, Integer cancelId) throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		if (requests.getServletContext().getAttribute("getServletContext")!=null&&requests.getServletContext().getAttribute("getServletContextType")!=null){
			if (requests.getServletContext().getAttribute("getServletContextType").equals("boss")) {
				request.setBossId((Integer) requests.getServletContext().getAttribute("getServletContext"));
			} else if (requests.getServletContext().getAttribute("getServletContextType").equals("stone")){
				request.setStoneId((Integer) requests.getServletContext().getAttribute("getServletContext"));
				HfStone hfStone = hfStoneMapper.selectByPrimaryKey((Integer) requests.getServletContext().getAttribute("getServletContext"));
				request.setBossId(hfStone.getBossId());
			}
		}
		Product product = new Product();
		product.setBossId(request.getBossId());
		product.setBrandId(1);
		System.out.println(request.getCategoryId()+"getLEI");
		System.out.println(request.getHfName());
		product.setCategoryId(request.getCategoryId()[request.getCategoryId().length-1]);
		product.setHfName(request.getHfName());
		
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
		LastModifier.setLastModifier(request.getUserId(), product.getId(), productMapper, hfMemberDao);
//		if (request.getClaim()==1){
//			if (cancelId==null){
//				productMapper.deleteByPrimaryKey(product.getId());
//				return builder.body(ResponseUtils.getResponseBody("自提商品请选择核销员"));
//			}
//			addCancel(product.getId(),cancelId);
//		}
		ProductInstance productInstance = new ProductInstance();
		productInstance.setBossId(request.getBossId());
		productInstance.setStoneId(request.getStoneId());
		productInstance.setProductId(product.getId());
		productInstance.setCategoryId(request.getCategoryId()[request.getCategoryId().length-1]);
		productInstance.setBrandId(1);
		productInstance.setEvaluateCount(0);
		productInstance.setCreateTime(LocalDateTime.now());
		productInstance.setLastModifier(request.getLastModifier());
		productInstance.setModifyTime(LocalDateTime.now());
		productInstance.setIsDeleted((short) 0);
		productInstanceMapper.insert(productInstance);
		Map<String, Object> map = new HashMap<>();
		map.put("bossName", hfBossMapper.selectByPrimaryKey(product.getBossId()).getName());
		map.put("productId",product.getId());
		map.put("createTime",product.getCreateTime());
		map.put("productName",request.getHfName());
		map.put("CategoryId",request.getCategoryId());
		map.put("stoneId", productInstance.getStoneId());
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
	@RequiredPermission(PermissionConstants.ADMIN_PRODUCT_DELETE)
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
		HfActivityProductExample example3 = new HfActivityProductExample();
		example3.createCriteria().andProductIdEqualTo(productId);
		hfActivityProductMapper.deleteByExample(example3);
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
	@RequiredPermission(PermissionConstants.ADMIN_CATRGORY_INSERT)
	@ApiOperation(value = "添加类目", notes = "添加系统支持的商品类目")
	@RequestMapping(value = "/addCategory", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> AddCategory(CategoryRequest request,MultipartFile fileInfo,HttpServletRequest requests) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfCategory category = new HfCategory();
		String uuid = UUID.randomUUID().toString();
//		uuid = uuid.replace("-", "");
//		if(fileInfo != null) {
//			category.setFileId(updateCategoryPicture(fileInfo,uuid,"无"));
//		}
		Integer bossId=null;
		if (requests.getServletContext().getAttribute("getServletContext")!=null){
			if (requests.getServletContext().getAttribute("getServletContextType").equals("boss")){
				bossId = (Integer) requests.getServletContext().getAttribute("getServletContext");
			}
		}
		category.setBossId(bossId);
		category.setLevelId(request.getLevelId());
		category.setHfName(request.getCategory());
		category.setParentCategoryId(request.getParentCategoryId());
		category.setCreateTime(LocalDateTime.now());
		category.setModifyTime(LocalDateTime.now());
		category.setIsDeleted((short) 0);
		return builder.body(ResponseUtils.getResponseBody(hfCategoryMapper.insert(category)));
	}
	@RequiredPermission(PermissionConstants.ADMIN_CATRGORY_DELETE)
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
//		LastModifier.setLastModifier(userId, productId, productMapper, hfMemberDao);
		String arr[];
		arr = FileMangeService.uploadFile(fileInfo.getBytes(), String.valueOf(userId));
		System.out.println(arr);
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
	
	@ApiOperation(value = "删除商品图片", notes = "删除商品图片")
	@RequestMapping(value = "/deleteProductPictrue", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addProductPictrue(Integer fileId,Integer productId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		Product product = productMapper.selectByPrimaryKey(productId);
		if(product == null) {
			return builder.body(ResponseUtils.getResponseBody(""));
		}
		FileDesc fileDesc = fileDescMapper.selectByPrimaryKey(fileId);
		FileMangeService fileMangeService = new FileMangeService();
		fileMangeService.deleteFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
		fileDescMapper.deleteByPrimaryKey(fileId);
		HfProductPictrueExample example = new HfProductPictrueExample();
		example.createCriteria().andFileIdEqualTo(fileId).andProductIdEqualTo(productId);
		hfProductPictrueMapper.deleteByExample(example);
		example.clear();
		example.createCriteria().andProductIdEqualTo(productId);
		List<HfProductPictrue> list = hfProductPictrueMapper.selectByExample(example);
		if(list.isEmpty()) {
			product.setFileId(null);
			productMapper.updateByPrimaryKey(product);
		}else {
			product.setFileId(list.get(list.size()-1).getFileId());
			productMapper.updateByPrimaryKey(product);
		}
		return builder.body(ResponseUtils.getResponseBody("删除成功"));
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

//		String uuid = UUID.randomUUID().toString();
//		uuid = uuid.replace("-", "");
		if(fileInfo != null) {
			FileMangeService fileMangeService = new FileMangeService();
			String arr[];
			arr = fileMangeService.uploadFile(fileInfo.getBytes(), String.valueOf(request.getUserId()));
			if(hfCategory.getFileId() == null) {
				FileDesc fileDesc = new FileDesc();
				fileDesc.setFileName(fileInfo.getName());
				fileDesc.setGroupName(arr[0]);
				fileDesc.setRemoteFilename(arr[1]);
				fileDesc.setCreateTime(LocalDateTime.now());
				fileDesc.setModifyTime(LocalDateTime.now());
				fileDesc.setIsDeleted((short) 0);
				fileDescMapper.insert(fileDesc);
				hfCategory.setFileId(fileDesc.getId());
			}else {
				FileDesc desc = fileDescMapper.selectByPrimaryKey(hfCategory.getFileId());
				fileMangeService.deleteFile(desc.getGroupName(), desc.getRemoteFilename());
				desc.setGroupName(arr[0]);
				desc.setRemoteFilename(arr[1]);
				desc.setModifyTime(LocalDateTime.now());
				fileDescMapper.updateByPrimaryKey(desc);
			}
			
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
	
//	@RequestMapping(value = "/updateCategoryPicture", method = RequestMethod.POST)
//	@ApiOperation(value = "更新类目图片", notes = "更新类目图片")
//	public Integer updateCategoryPicture(MultipartFile fileInfo, @RequestParam(required = false) String uuid ,@RequestParam String type) throws Exception {
//		FileMangeService fileMangeService = new FileMangeService();
//		String arr[];
//		arr = fileMangeService.uploadFile(fileInfo.getBytes(),"-1");
//		if("类目页面图片".equals(type)) {
//			FileDesc fileDesc = new FileDesc();
//			fileDesc.setFileName("类目页面图片");
//			fileDesc.setGroupName(arr[0]);
//			fileDesc.setRemoteFilename(arr[1]);
//			fileDesc.setUserId(-1);
//			fileDesc.setCreateTime(LocalDateTime.now());
//			fileDesc.setModifyTime(LocalDateTime.now());
//			fileDesc.setIsDeleted((short) 0);
//			fileDescMapper.insert(fileDesc);
//			return -1;
//		}
//		Integer fileId = null;
//		FileDescExample example = new FileDescExample();
//		example.createCriteria().andFileNameEqualTo(uuid);
//		List<FileDesc> list = fileDescMapper.selectByExample(example);
//		if (list.isEmpty()) {
//			FileDesc fileDesc = new FileDesc();
//			fileDesc.setFileName(uuid);
//			fileDesc.setGroupName(arr[0]);
//			fileDesc.setRemoteFilename(arr[1]);
//			fileDesc.setUserId(-1);
//			fileDesc.setCreateTime(LocalDateTime.now());
//			fileDesc.setModifyTime(LocalDateTime.now());
//			fileDesc.setIsDeleted((short) 0);
//			fileDescMapper.insert(fileDesc);
//			fileId = fileDesc.getId();
//		} else {
//			FileDesc fileDesc = list.get(0);
//			fileMangeService.deleteFile(fileDesc.getGroupName(),fileDesc.getRemoteFilename() );
//			fileDesc.setGroupName(arr[0]);
//			fileDesc.setRemoteFilename(arr[1]);
//			fileDesc.setModifyTime(LocalDateTime.now());
//			fileDescMapper.updateByPrimaryKey(fileDesc);
//			fileId = fileDesc.getId();
//		}
//		return fileId;
//	}

//	@ApiOperation(value = "查询类目页面图片", notes = "查询类目页面图片")
//	@RequestMapping(value = "/findCategoryPagePicture", method = RequestMethod.GET)
//	public ResponseEntity<JSONObject> findCategoryPagePicture() throws Exception {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		FileDescExample example = new FileDescExample();
//		example.createCriteria().andFileNameEqualTo("类目页面图片");
//		List<FileDesc> list = fileDescMapper.selectByExample(example);
//		return builder.body(ResponseUtils.getResponseBody(list));
//	}

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
	@RequiredPermission(PermissionConstants.ADMIN_PRODUCT_DELETE)
	@ApiOperation(value = "选中删除商品列表", notes = "根据商品id删除商品列表")
	@RequestMapping(value = "/deleteSelectProductId", method = RequestMethod.GET)
//	@ApiImplicitParams({
//		@ApiImplicitParam(paramType = "query", name = "productId", value = "商品ID", required = true, type = "Integer") })
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
			productMapper.deleteByPrimaryKey(productId[i]);
		}
		return builder.body(ResponseUtils.getResponseBody(0));
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
		List<ProductSpec> productSpecs = productSpecMapper.selectByExample(example);
		List<ProductSpecs> productSpecsList = new ArrayList<>();
		productSpecs.forEach(productSpec -> {
			ProductSpecs productSpecs1 = new ProductSpecs();
			productSpecs1.setHfName(productSpec.getHfName());
			productSpecs1.setCategorySpecId(productSpec.getCategorySpecId());
			productSpecs1.setId(productSpec.getId());
			productSpecs1.setIsDeleted(productSpec.getIsDeleted());
			productSpecs1.setProductId(productSpec.getProductId());
			productSpecs1.setShow(true);
			productSpecs1.setSpecType(productSpec.getSpecType());
			productSpecs1.setSpecValue(productSpec.getSpecValue());
			productSpecs1.setSpecUnit(productSpec.getSpecUnit());
			productSpecsList.add(productSpecs1);
		});

		return builder.body(ResponseUtils.getResponseBody(productSpecsList));
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
	public ResponseEntity<JSONObject> updatespec(Integer productSpecId,String specName,Integer categorySpecId,Integer productId,String specUnit,String specValue)
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
		productSpec.setSpecValue(specValue);
		productSpecMapper.updateByPrimaryKeySelective(productSpec);
		return builder.body(ResponseUtils.getResponseBody(productSpec));
	}

	@ApiOperation(value = "获取商品所有物品", notes = "获取商品所有物品")
	@RequestMapping(value = "/selectProductGoods", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> selectProductGoods(SelectProductGoods productId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		if(productId.getStoneId() == null) {
			return builder.body(ResponseUtils.getResponseBody(hfProductDao.selectProductGoods(productId)));
		}
		ProductInstanceExample example = new ProductInstanceExample();
		example.createCriteria().andProductIdEqualTo(productId.getProductId()).andStoneIdEqualTo(productId.getStoneId());
		List<ProductInstance> list = productInstanceMapper.selectByExample(example);
		List<ProductGoods> result = hfProductDao.selectProductGoods(productId);
		result = result.stream().filter(r -> r.getInstanceId() == list.get(0).getId() || r.getInstanceId() == null)
				.collect(Collectors.toList());
		return builder.body(ResponseUtils.getResponseBody(result));
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

	@ApiOperation(value = "删除图片", notes = "删除图片根据文件id")
	@RequestMapping(value = "/deleteProductFile", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "fileId", value = "文件id", required = true, type = "Integer") })
	public void deleteProductFile(@RequestParam(name = "fileId") Integer fileId) throws Exception {
		HfProductPictrueExample example = new HfProductPictrueExample();
		example.createCriteria().andFileIdEqualTo(fileId);
			FileDesc fileDesc = fileDescMapper.selectByPrimaryKey(fileId);
			FileMangeService fileManageService = new FileMangeService();
			if(fileDesc!=null) {
			fileManageService.deleteFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
			hfProductPictrueMapper.deleteByExample(example);
			fileDescMapper.deleteByPrimaryKey(fileDesc.getId());
		}
	}


//	@ApiOperation(value = "店铺添加商品", notes = "店铺添加商品")
//	@RequestMapping(value = "/addStoneProduct", method = RequestMethod.POST)
//	public ResponseEntity<JSONObject> addStoneProduct(@RequestParam(name = "productIds")Integer[] productIds,Integer stoneId,Integer userId)
//			throws JSONException {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		HfStoneExample hfStoneExample = new HfStoneExample();
//		hfStoneExample.createCriteria().andIdEqualTo(stoneId).andIsDeletedEqualTo((short) 0);
//		List<HfStone> hfStones= hfStoneMapper.selectByExample(hfStoneExample);
//		for (Integer productId : productIds){
//			ProductInstanceExample productInstanceExample = new ProductInstanceExample();
//			productInstanceExample.createCriteria().andStoneIdEqualTo(stoneId).andProductIdEqualTo(productId);
//			productInstanceMapper.selectByExample(productInstanceExample);
//			if (productInstanceMapper.selectByExample(productInstanceExample).size()==0){
//				ProductInstance productInstance = new ProductInstance();
//				productInstance.setCreateTime(LocalDateTime.now());
//				productInstance.setModifyTime(LocalDateTime.now());
//				productInstance.setLastModifier(String.valueOf(userId));
//				productInstance.setIsDeleted((short) 0);
//				productInstance.setStoneId(stoneId);
//				productInstance.setEvaluateCount(0);
//				productInstance.setProductId(productId);
//				productInstance.setBossId(hfStones.get(0).getBossId());
//
//				productInstance.setCategoryId(31);
//				productInstance.setBrandId(1);
//				productInstanceMapper.insertSelective(productInstance);
//			}
//		}
//		return builder.body(ResponseUtils.getResponseBody(0));
//	}

	@ApiOperation(value = "店铺删除商品", notes = "店铺删除商品")
	@RequestMapping(value = "/deletedStoneProduct", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> deletedStoneProduct(@RequestParam(name = "productIds")Integer[] productIds,Integer stoneId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		for (Integer productId : productIds){
			ProductInstanceExample productInstanceExample = new ProductInstanceExample();
			productInstanceExample.createCriteria().andStoneIdEqualTo(stoneId).andProductIdEqualTo(productId);
			productInstanceMapper.deleteByExample(productInstanceExample);
		}
		return builder.body(ResponseUtils.getResponseBody(0));
	}
	
	@ApiOperation(value = "添加商品收藏", notes = "添加商品收藏")
	@RequestMapping(value = "/addProductCollect", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addProductCollect(Integer userId,Integer productId,Integer stoneId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ProductInstanceExample instanceExample = new ProductInstanceExample();
		instanceExample.createCriteria().andProductIdEqualTo(productId).andStoneIdEqualTo(stoneId);
		List<ProductInstance> instanceId = productInstanceMapper.selectByExample(instanceExample);
		HfProductCollectExample example = new HfProductCollectExample();
		HfProductCollect collect = null;
		example.createCriteria().andUserIdEqualTo(userId).andInstanceIdEqualTo(instanceId.get(0).getId());
		List<HfProductCollect> list = hfProductCollectMapper.selectByExample(example);
		if(list.isEmpty()) {
			collect = new HfProductCollect();
			collect.setInstanceId(instanceId.get(0).getId());
			collect.setCollectTime(LocalDateTime.now());
			collect.setCreateTime(LocalDateTime.now());
			collect.setModifyTime(LocalDateTime.now());
			collect.setIsDeleted((byte) 0);
			collect.setProductId(productId);
			collect.setUserId(userId);
			hfProductCollectMapper.insert(collect);
		}else {
			return builder.body(ResponseUtils.getResponseBody(-1));
		}
		return builder.body(ResponseUtils.getResponseBody(collect.getId()));
	}
	
	@ApiOperation(value = "删除商品收藏", notes = "删除商品收藏")
	@RequestMapping(value = "/deleteProductCollect", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> deleteProductCollect(Integer userId,Integer stoneId,Integer productId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		ProductInstanceExample instanceExample = new ProductInstanceExample();
		instanceExample.createCriteria().andProductIdEqualTo(productId).andStoneIdEqualTo(stoneId);
		List<ProductInstance> instanceId = productInstanceMapper.selectByExample(instanceExample);
		HfProductCollectExample example = new HfProductCollectExample();
		example.createCriteria().andUserIdEqualTo(userId).andInstanceIdEqualTo(instanceId.get(0).getId());
		hfProductCollectMapper.deleteByExample(example);
		return builder.body(ResponseUtils.getResponseBody("删除成功"));
	}
	
	@ApiOperation(value = "关注店铺", notes = "关注店铺")
	@RequestMapping(value = "/addStoneConcern", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addStoneConcern(Integer userId,Integer stoneId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfStone hfStone = hfStoneMapper.selectByPrimaryKey(stoneId);
		HfStoneConcernExample example = new HfStoneConcernExample();
		HfStoneConcern concern = null;
		example.createCriteria().andUserIdEqualTo(userId).andStoneIdEqualTo(stoneId);
		List<HfStoneConcern> list = hfStoneConcernMapper.selectByExample(example);
		if(list.isEmpty()) {
			concern = new HfStoneConcern();
			concern.setConcernTime(LocalDateTime.now());
			concern.setCreateTime(LocalDateTime.now());
			concern.setIsDeleted((byte) 0);
			concern.setModifyTime(LocalDateTime.now());
			concern.setUserId(userId);
			concern.setStoneId(stoneId);
			hfStoneConcernMapper.insert(concern);
			hfStone.setConcernCount(hfStone.getConcernCount()+1);
			hfStoneMapper.updateByPrimaryKey(hfStone);
		}else {
			return builder.body(ResponseUtils.getResponseBody(-1));
		}
		return builder.body(ResponseUtils.getResponseBody(concern.getId()));
	}
	
	@ApiOperation(value = "删除店铺关注", notes = "删除店铺关注")
	@RequestMapping(value = "/deleteStoneConcern", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> deleteStoneConcern(Integer userId,Integer stoneId)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfStone hfStone = hfStoneMapper.selectByPrimaryKey(stoneId);
		if(hfStone.getConcernCount()-1 <0) {
			return builder.body(ResponseUtils.getResponseBody("数据异常"));
		}
		hfStone.setConcernCount(hfStone.getConcernCount()-1);
		hfStoneMapper.updateByPrimaryKey(hfStone);
		HfStoneConcernExample example = new HfStoneConcernExample();
		example.createCriteria().andUserIdEqualTo(userId).andStoneIdEqualTo(stoneId);
		hfStoneConcernMapper.deleteByExample(example);
		return builder.body(ResponseUtils.getResponseBody("删除成功"));
	}
	
}
