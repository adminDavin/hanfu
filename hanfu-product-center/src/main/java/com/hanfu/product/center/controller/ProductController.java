package com.hanfu.product.center.controller;

import java.time.LocalDateTime;
import java.util.List;

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
import com.cedarsoftware.util.io.JsonObject;
import com.github.pagehelper.PageHelper;
import com.hanfu.product.center.dao.FileDescMapper;
import com.hanfu.product.center.dao.GoodsSpecMapper;
import com.hanfu.product.center.dao.HfCategoryMapper;
import com.hanfu.product.center.dao.HfGoodsMapper;
import com.hanfu.product.center.dao.HfGoodsPictrueMapper;
import com.hanfu.product.center.dao.HfPriceMapper;
import com.hanfu.product.center.dao.HfRespMapper;
import com.hanfu.product.center.dao.HfStoneMapper;
import com.hanfu.product.center.dao.ProductInfoMapper;
import com.hanfu.product.center.dao.ProductInstanceMapper;
import com.hanfu.product.center.dao.ProductMapper;
import com.hanfu.product.center.dao.ProductSpecMapper;
import com.hanfu.product.center.manual.dao.HfGoodsDao;
import com.hanfu.product.center.manual.dao.ManualDao;
import com.hanfu.product.center.manual.dao.ProductDao;
import com.hanfu.product.center.manual.dao.ProductInstanceDao;
import com.hanfu.product.center.manual.model.HfGoodsDisplay;
import com.hanfu.product.center.manual.model.ProductDispaly;
import com.hanfu.product.center.model.FileDescExample;
import com.hanfu.product.center.model.GoodsSpecExample;
import com.hanfu.product.center.model.HfCategory;
import com.hanfu.product.center.model.HfCategoryExample;
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
import com.hanfu.product.center.model.ProductExample;
import com.hanfu.product.center.model.ProductInfo;
import com.hanfu.product.center.model.ProductInfoExample;
import com.hanfu.product.center.model.ProductInstance;
import com.hanfu.product.center.model.ProductInstanceExample;
import com.hanfu.product.center.model.ProductSpec;
import com.hanfu.product.center.model.ProductSpecExample;
import com.hanfu.product.center.request.CategoryRequest;
import com.hanfu.product.center.request.ProductInfoRequest;
import com.hanfu.product.center.request.ProductInstanceRequest;
import com.hanfu.product.center.request.ProductRequest;
import com.hanfu.product.center.request.ProductSpecRequest;
import com.hanfu.product.center.response.handler.GoodsNotExistException;
import com.hanfu.product.center.response.handler.ProductNotExistException;
import com.hanfu.product.center.service.ProductService;
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
    private ProductInstanceMapper productInstanceMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private ManualDao manualDao;

    @Autowired
    private HfGoodsMapper hfGoodsMapper;

    @Autowired
    private HfGoodsDao hfGoodsDao;

    @Autowired
    private HfStoneMapper hfStoneMapper;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductInstanceDao productInstanceDao;

    @Autowired
    private HfPriceMapper hfPriceMapper;

    @Autowired
    private HfRespMapper hfRespMapper;

    @Autowired
    private GoodsSpecMapper goodsSpecMapper;

    @Autowired
    private HfGoodsPictrueMapper hfGoodsPictrueMapper;

    @Autowired
    private FileDescMapper fileDescMapper;

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
            @RequestParam(name = "levelId", required = false, defaultValue = "0") Integer levelId)
            throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfCategoryExample example = new HfCategoryExample();	
        example.createCriteria().andParentCategoryIdEqualTo(parentCategoryId);
        if (categoryId != null) {   
            example.clear();
            example.createCriteria().andIdEqualTo(categoryId);
        }
//		return builder.body(ResponseUtils.getResponseBody(manualDao.selectCategories()));
        return productService.listCategory(parentCategoryId, categoryId, levelId, page, size);
    }

    @ApiOperation(value = "添加类目", notes = "添加系统支持的商品类目")
    @RequestMapping(value = "/addCategory", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> AddCategory(CategoryRequest request) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfCategory category = new HfCategory();
        for (Integer s : request.getLevelId()) {
            category.setLevelId(s);
		}
        for (String s : request.getCategory()) {
            category.setHfName(s);
		}
        for (Integer s : request.getParentCategoryId()) {
            category.setParentCategoryId(s);
		}
        category.setCreateTime(LocalDateTime.now());
        category.setModifyTime(LocalDateTime.now());
        category.setIsDeleted((short) 0);
        return builder.body(ResponseUtils.getResponseBody(hfCategoryMapper.insert(category)));
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
            @ApiImplicitParam(paramType = "query", name = "bossId", value = "商家ID", required = true, type = "Integer")})
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
            @ApiImplicitParam(paramType = "query", name = "bossId", value = "商家ID", required = true, type = "Integer")})
    public ResponseEntity<JSONObject> listProductAndCategoryName(@RequestParam(name = "bossId") Integer bossId,
                                                                 @RequestParam(name = "page", required = false) Integer page,
                                                                 @RequestParam(name = "size", required = false) Integer size)
            throws JSONException {
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

    @ApiOperation(value = "添加商品", notes = "根据商家录入的商品")
    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addProduct(ProductRequest request) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        Product product = new Product();
        product.setBossId(request.getBossId());
        product.setBrandId(request.getBrandId());
        product.setCategoryId(request.getCategoryId());
        product.setHfName(request.getHfName());
        product.setLastModifier(request.getLastModifier());
        product.setCreateTime(LocalDateTime.now());
        product.setModifyTime(LocalDateTime.now());
        product.setIsDeleted((short) 0);
        product.setProductDesc(request.getProductDesc());
        product.setCancel_id(request.getCancelId());
        product.setClaim(request.getClaim());
        product.setMemeber(request.getMember());
        return builder.body(ResponseUtils.getResponseBody(productMapper.insert(product)));
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

    @ApiOperation(value = "选中删除商品列表", notes = "根据商品id删除商品列表")
    @RequestMapping(value = "/deleteSelectProductId", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "productId", value = "商品ID", required = true, type = "Integer")})
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
            @ApiImplicitParam(paramType = "query", name = "productId", value = "商品ID", required = true, type = "Integer")})
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
            @ApiImplicitParam(paramType = "query", name = "productInfoId", value = "商品属性ID", required = true, type = "Integer")})
    public ResponseEntity<JSONObject> deleteattributes(@RequestParam(name = "productInfoId") Integer productInfoId)
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(productInfoMapper.deleteByPrimaryKey(productInfoId)));
    }

    @ApiOperation(value = "获取商品规格", notes = "根据商品id获取商品的规格描述")
    @RequestMapping(value = "/specifies", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "productId", value = "商品ID", required = true, type = "Integer")})
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
        item.setProductId(request.getProductId());
        item.setHfName(request.getHfName());
        item.setSpecType(request.getSpecType());
        item.setSpecUnit(request.getSpecUnit());
        item.setSpecValue(request.getSpecValue());
        item.setCategorySpecId(request.getCategorySpecId());
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

//	@ApiOperation(value = "修改商品规格", notes = "根据规格id修改商品的规格描述")
//	@RequestMapping(value = "/updateSpecifies", method = RequestMethod.POST)
//	public ResponseEntity<JSONObject> updateSpecifies(ProductSpec productSpec) throws JSONException {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		ProductSpecExample example = new ProductSpecExample();
//		return builder.body(ResponseUtils.getResponseBody(productSpecMapper.updateByExample(productSpec, example)));
//	}

    @ApiOperation(value = "获取物品所在店铺的信息", notes = "根据物品获取物品所在的店铺信息")
    @RequestMapping(value = "/stones", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "物品ID", required = true, type = "Integer")})
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
            @ApiImplicitParam(paramType = "query", name = "stoneId", value = "商鋪id", required = true, type = "Integer")})
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
                    }
                }
            }
        }
        return builder.body(ResponseUtils.getResponseBody(list));
    }

//	@ApiOperation(value = "商品添加到店铺", notes = "将商品添加到某一个店铺")
//	@RequestMapping(value = "/addToStone", method = RequestMethod.POST)
//	public ResponseEntity<JSONObject> addStone(ProductInstanceRequest request) throws Exception {
//		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		Product product = productMapper.selectByPrimaryKey(request.getProductId());
//		if (product == null) {
//			return builder.body(ResponseUtils.getResponseBody(new ProductNotExistException("product is invalid")));
//		}
//		ProductInstance item = new ProductInstance();
//		item.setProductId(request.getProductId());
//		item.setStoneId(request.getStoneId());
//		item.setLastModifier(request.getLastModifier());
//		HfPrice hfPrice = new HfPrice();
//		HfResp hfResp = new HfResp();
//		productInstanceMapper.insert(item);
//		HfGoods record = new HfGoods();
//		record.setInstanceId(item.getId());
//		record.setProductId(request.getProductId());
//		record.setStoneId(request.getStoneId());
//		record.setBossId(product.getBossId());
//		record.setBrandId(product.getBrandId());
//		record.setCategoryId(product.getCategoryId());
//		hfGoodsMapper.insert(record);
//		hfPrice.setGoogsId(record.getId());
//		hfResp.setGoogsId(record.getId());
//		hfPriceMapper.insert(hfPrice);
//		hfRespMapper.insert(hfResp);
//		record.setPriceId(hfPrice.getId());
//		record.setRespId(hfPrice.getId());
//		record.setIsDeleted((short) 0);
//		record.setCreateTime(LocalDateTime.now());
//		hfGoodsMapper.updateByPrimaryKey(record);
//		return builder.body(ResponseUtils.getResponseBody("product insert success"));
//	}


    @ApiOperation(value = "根据商品id查询此商品是否添加过", notes = "根据商品id查询此商品是否添加过")
    @RequestMapping(value = "/selectProductIdIsExists", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "productId", value = "商品id", required = true, type = "Integer")})
    public ResponseEntity<JSONObject> selectProductIdIsExists(@RequestParam(name = "productId") Integer productId) throws Exception {
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

}
