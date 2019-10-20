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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
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
import com.hanfu.product.center.manual.model.HfGoodsDisplay;
import com.hanfu.product.center.model.FileDesc;
import com.hanfu.product.center.model.GoodsSpec;
import com.hanfu.product.center.model.GoodsSpecExample;
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
import com.hanfu.product.center.model.ProductSpec;
import com.hanfu.product.center.model.ProductSpecExample;
import com.hanfu.product.center.request.GoodsPictrueRequest;
import com.hanfu.product.center.request.GoodsPriceInfo;
import com.hanfu.product.center.request.GoodsSpecRequest;
import com.hanfu.product.center.request.HfGoodsInfo;
import com.hanfu.product.center.request.HfRespRequest;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.utils.response.handler.ResponseUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/goods")
@Api
public class GoodsController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HfGoodsMapper hfGoodsMapper;

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

    @ApiOperation(value = "获取商品实体id获取物品列表", notes = "即某商品在店铺内的所有规格")
    @RequestMapping(value = "/byInstanceId", method = RequestMethod.GET)
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "instanceId", value = "商品实体id", required = true,
            type = "Integer") })
    public ResponseEntity<JSONObject> listProduct(@RequestParam Integer instanceId) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfGoodsExample example = new HfGoodsExample();
        example.createCriteria().andInstanceIdEqualTo(instanceId);
        return builder.body(ResponseUtils.getResponseBody(hfGoodsMapper.selectByExample(example)));
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
        item.setProductId(product.getId());
        item.setStoneId(hfStone.getId());
        productInstanceMapper.insert(item);
        HfGoods record = new HfGoods();
        record.setInstanceId(item.getId());
        record.setBossId(hfGoodsInfo.getBossId());
        record.setCategoryId(product.getCategoryId());
        record.setProductId(product.getId());
        record.setGoodsDesc(hfGoodsInfo.getHfDesc());
        record.setHfName(hfGoodsInfo.getHfName());
        return builder.body(ResponseUtils.getResponseBody(hfGoodsMapper.insert(record)));
    }

    @ApiOperation(value = "编辑物品", notes = "编辑物品")
    @RequestMapping(value = "/updategood", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> updateGood(HfGoodsDisplay hfGoodsDisplay) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfGoods hfGoods = hfGoodsMapper.selectByPrimaryKey(hfGoodsDisplay.getId());
        HfGoodsExample example = new HfGoodsExample();
        example.createCriteria().andBrandIdEqualTo(hfGoodsDisplay.getId());
        HfPrice hfPrice = new HfPrice();
        HfResp hfResp = new HfResp();
        GoodsSpec goodsSpec = new GoodsSpec();
        hfPrice.setSellPrice(hfGoodsDisplay.getSellPrice());
        hfPrice.setGoogsId(hfGoodsDisplay.getId());
        hfPriceMapper.insert(hfPrice);
        hfResp.setQuantity(String.valueOf(hfGoodsDisplay.getQuantity()));
        hfResp.setGoogsId(hfGoodsDisplay.getId());
        hfRespMapper.insert(hfResp);
        goodsSpec.setHfValue(hfGoodsDisplay.getSpecValue());
        goodsSpec.setGoodsId(hfGoodsDisplay.getId());
        goodsSpec.setHfSpecId(String.valueOf(hfGoodsDisplay.getProductSpecId()));
        goodsSpecMapper.insert(goodsSpec);
        hfGoods.setRespId(goodsSpec.getId());

        return builder.body(ResponseUtils.getResponseBody(hfGoodsMapper.updateByExample(hfGoods, example)));
    }

    @ApiOperation(value = "获取物品规格", notes = "获取物品规格")
    @RequestMapping(value = "/specifies", method = RequestMethod.GET)
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "goodsId", value = "物品id", required = true,
            type = "Integer") })
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

    @ApiOperation(value = "更新物品规格", notes = "更新物品规格")
    @RequestMapping(value = "/spec/update", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> updateGoodsSpec(GoodsSpecRequest request) throws JSONException {
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
            goodsSpecMapper.updateByPrimaryKey(item);
        }
        return builder.body(ResponseUtils.getResponseBody(goodsSpecMapper.selectByExample(example)));
    }

    @ApiOperation(value = "获取物品图片", notes = "获取物品图片")
    @RequestMapping(value = "/resp/list", method = RequestMethod.GET)
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "goodsId", value = "物品id", required = true,
            type = "Integer") })
    public ResponseEntity<JSONObject> listResp(@RequestParam(name = "goodsId") Integer goodsId)
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfRespExample example = new HfRespExample();
        example.createCriteria().andGoogsIdEqualTo(goodsId);
        return builder.body(ResponseUtils.getResponseBody(hfRespMapper.selectByExample(example)));
    }
    
    @ApiOperation(value = "更新物品存储", notes = "更新物品存储")
    @RequestMapping(value = "/resp/update", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> updateResp(HfRespRequest request) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfRespExample example = new HfRespExample();
        example.createCriteria().andGoogsIdEqualTo(request.getGoodsId()).andWarehouseIdEqualTo(request.getWareHouseId());
        List<HfResp> items = hfRespMapper.selectByExample(example);
        if (items.isEmpty()) {
            addResp(request);
        } else {
            HfResp item = items.get(0);
            item.setQuantity(request.getQuatity());
            hfRespMapper.updateByPrimaryKey(item);
        }
        return builder.body(ResponseUtils.getResponseBody(hfRespMapper.selectByExample(example)));
    }
    
    
    @ApiOperation(value = "添加物品存储", notes = "添加物品存储")
    @RequestMapping(value = "/resp/add", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addResp(HfRespRequest request) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfRespExample example = new HfRespExample();
        example.createCriteria().andGoogsIdEqualTo(request.getGoodsId()).andWarehouseIdEqualTo(request.getWareHouseId());
        List<HfResp> items = hfRespMapper.selectByExample(example);
        if (items.isEmpty()) {
            HfResp record = new HfResp();
            record.setCreateTime(LocalDateTime.now());
            record.setGoogsId(request.getGoodsId());
            record.setQuantity(request.getQuatity());
            record.setWarehouseId(record.getWarehouseId());
            hfRespMapper.insert(record);
        } else {
            HfResp item = items.get(0);
            item.setQuantity(request.getQuatity());
            hfRespMapper.updateByPrimaryKey(item);
        }
        return builder.body(ResponseUtils.getResponseBody(hfRespMapper.selectByExample(example)));
    }
    
    
    @ApiOperation(value = "设置物品价格", notes = "设置物品价格")
    @RequestMapping(value = "/setPrice", method = RequestMethod.POST)
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "goodsId", value = "物品id", required = true,
            type = "Integer") })
    public ResponseEntity<JSONObject> setGoodsPrice(GoodsPriceInfo request) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfGoods goods = hfGoodsMapper.selectByPrimaryKey(request.getHfGoodsId());
        HfPrice price = new HfPrice();
        price.setGoogsId(request.getHfGoodsId());
        price.setSellPrice(request.getHfPrice());
        Integer priceInsId = hfPriceMapper.insert(price);
        goods.setPriceId(price.getId());
        return builder.body(ResponseUtils.getResponseBody(priceInsId));
    }
    
    

    @ApiOperation(value = "添加物品规格", notes = "添加物品规格")
    @RequestMapping(value = "/addSpecify", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addGoodsSpec(GoodsSpecRequest request) throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        ProductSpec productSpec = productSpecMapper.selectByPrimaryKey(request.getProductSpecId());
        GoodsSpec item = new GoodsSpec();
        item.setGoodsId(request.getGoodsId());
        item.setLastModifier(request.getUsername());
        item.setHfSpecId(String.valueOf(productSpec.getId()));
        item.setHfValue(request.getSpecValue());
        item.setCreateTime(LocalDateTime.now());
        item.setModifyTime(LocalDateTime.now());
        item.setLastModifier(request.getUsername());
        item.setIsDeleted((short) 0);
        return builder.body(ResponseUtils.getResponseBody(goodsSpecMapper.insert(item)));
    }

    @ApiOperation(value = "获取物品图片", notes = "获取物品图片")
    @RequestMapping(value = "/pictures", method = RequestMethod.GET)
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "goodsId", value = "物品id", required = true,
            type = "Integer") })
    public ResponseEntity<JSONObject> getGoodsPicture(@RequestParam(name = "goodsId") Integer goodsId)
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfGoodsPictrueExample example = new HfGoodsPictrueExample();
        example.createCriteria().andGoodsIdEqualTo(goodsId);
        return builder.body(ResponseUtils.getResponseBody(hfGoodsPictrueMapper.selectByExample(example)));
    }

    @ApiOperation(value = "添加物品图片", notes = "添加物品图片")
    @RequestMapping(value = "/addPicture", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> addGoodsPicture(GoodsPictrueRequest request) throws JSONException, IOException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfGoods goods = hfGoodsMapper.selectByPrimaryKey(request.getGoodsId());
        if (goods == null) {
        }
        List<HfGoodsPictrue> pictures = Lists.newArrayList();
        Arrays.asList(request.getFileInfo()).stream().forEach(fileInfo -> {
            try {
                FileMangeService fileMangeService = new FileMangeService();
                String arr[];
                arr = fileMangeService.uploadFile(fileInfo.getBytes(), String.valueOf(request.getUserId()));
                FileDesc fileDesc = new FileDesc();
                fileDesc.setFileName(fileInfo.getName());
                fileDesc.setGroupName(arr[0]);
                fileDesc.setRemoteFilename(arr[1]);
                fileDesc.setUserId(request.getUserId());
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
        });
        return builder.body(ResponseUtils.getResponseBody(pictures));
    }

    @ApiOperation(value = "获取图片", notes = "获取图片")
    @RequestMapping(value = "/getFile", method = RequestMethod.GET)
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "fileId", value = "文件id", required = true,
            type = "Integer") })
    public void getFile(@RequestParam(name = "fileId") Integer fileId, HttpServletResponse response) throws Exception {
        FileDesc fileDesc = fileDescMapper.selectByPrimaryKey(fileId);
        if (fileDesc == null) {
            throw new Exception("file not exists");
        }
        FileMangeService fileManageService = new FileMangeService();
        byte[] file = fileManageService.downloadFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
        BufferedImage readImg = ImageIO.read(new ByteArrayInputStream(file));
        ImageIO.write(readImg, "png", response.getOutputStream());
    }

}
