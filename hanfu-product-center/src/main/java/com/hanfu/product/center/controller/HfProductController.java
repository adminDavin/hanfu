package com.hanfu.product.center.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject; 
import com.hanfu.product.center.dao.HfGoodsPictrueMapper;
import com.hanfu.product.center.dao.HfStoneMapper;
import com.hanfu.product.center.manual.dao.HfGoodsDisplayDao;
import com.hanfu.product.center.manual.dao.HfProductDao;
import com.hanfu.product.center.manual.model.HfGoodsDisplayInfo;
import com.hanfu.product.center.manual.model.HfProductDisplay;

import com.hanfu.product.center.model.HfGoodsPictrue;
import com.hanfu.product.center.model.HfGoodsPictrueExample;
import com.hanfu.product.center.model.HfStone;
import com.hanfu.product.center.model.HfStoneExample;
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
    private HfProductDao hfProductDao;

    @Autowired
    private HfGoodsDisplayDao hfGoodsDisplayDao;

    @Autowired
    private HfStoneMapper hfStoneMapper;

    @ApiOperation(value = "删除商品列表", notes = "根据商品id删除商品列表")
    @RequestMapping(value = "/getProductsForRotation", method = RequestMethod.GET)
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "quantity", value = "获取商品的数量限制", required = false,
            type = "Integer") })
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
               Optional<HfGoodsDisplayInfo> hfGood = hfGoods.stream().min(Comparator.comparing(HfGoodsDisplayInfo::getSellPrice));
               product.setPriceArea(hfGood.isPresent() ? String.valueOf(hfGood.get().getSellPrice()) : "异常");    
               product.setDefaultGoodsId(hfGood.get().getId());
           }
           
       });
        return builder.body(ResponseUtils.getResponseBody(products));
    }

    @ApiOperation(value = "获取商品详情", notes = "根据商品id商品详情")
    @RequestMapping(value = "/getDetail", method = RequestMethod.GET)
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "productId", value = "商品ID", required = true,
            type = "Integer") })
    public ResponseEntity<JSONObject> getDetail(@RequestParam(name = "productId") Integer productId)
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfProductDisplay product = hfProductDao.selectProduct(productId);

        HfStoneExample hfStoneExample = new HfStoneExample();
        hfStoneExample.createCriteria().andIdIn(Lists.newArrayList(product.getStoneId()));
        List<HfStone> stoneInfos = hfStoneMapper.selectByExample(hfStoneExample);
        Map<Integer, String> stones = stoneInfos.stream().collect(Collectors.toMap(HfStone::getId, HfStone::getHfName));
        product.setStoneName(stones.get(product.getStoneId()));

        List<HfGoodsDisplayInfo> hfGoods = hfGoodsDisplayDao.selectHfGoodsDisplay(product.getId());
        if (Optional.ofNullable(hfGoods).isPresent()) {
           Optional<HfGoodsDisplayInfo> hfGood = hfGoods.stream().min(Comparator.comparing(HfGoodsDisplayInfo::getSellPrice));
           product.setPriceArea(hfGood.isPresent() ? String.valueOf(hfGood.get().getSellPrice()) : "异常");    
           product.setDefaultGoodsId(hfGood.get().getId());    
       }
       
       HfGoodsPictrueExample example = new HfGoodsPictrueExample();
       example.createCriteria().andGoodsIdEqualTo(product.getDefaultGoodsId());
       List<HfGoodsPictrue> hfProductPictrues = hfGoodsPictrueMapper.selectByExample(example);
       List<Integer> fileIds = hfProductPictrues.stream().map(HfGoodsPictrue::getFileId).collect(Collectors.toList());
       product.setFileIds(fileIds);
       
       return builder.body(ResponseUtils.getResponseBody(product));
    }    

    
}

