package com.hanfu.cancel.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.cancel.dao.*;
import com.hanfu.cancel.model.*;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Mapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api
@RequestMapping("/cancel")
@CrossOrigin
public class logicController {
    protected final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private HfUserMapper hfUserMapper;
    @Autowired
    private HfGoodsMapper hfGoodsMapper;
    @Autowired
    private CancelsMapper cancelsMapper;
    @Autowired
    private HfOrdersDetailMapper hfOrdersDetailMapper;
    @Autowired
    private HfLogMapper hfLogMapper;
    @Autowired
    private HfPriceMapper hfPriceMapper;
    @Autowired
    private CancelProductMapper cancelProductMapper;
    @Autowired
    private ProductMapper productMapper;
    @ResponseBody
    @RequestMapping(value = "/testCancel", method = RequestMethod.GET)
    @ApiOperation(value = "核销逻辑测试", notes = "核销逻辑测试")
    public ResponseEntity<JSONObject> testCancel(
            @RequestParam(value = "userId", required = true) Integer userId,
            @RequestParam(value = "goodsId", required = true) Integer goodsId,
            @RequestParam(value = "orderId", required = true) Integer orderId
    ) throws Exception {
        //核销员查询
        Example example1 = new Example(cancel.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("userId", userId);
        List<cancel> cancelList = cancelsMapper.selectByExample(example1);
        //
        HfUser hfUser = hfUserMapper.selectByPrimaryKey(userId);
        System.out.println(hfUser.getUsername());
        String unionId = hfUser.getUsername();
        System.out.println(userId + goodsId + orderId);
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        if (goodsId == null) {
            return builder.body(ResponseUtils.getResponseBody("goodsId为空"));
        }
        if (orderId == null) {
            return builder.body(ResponseUtils.getResponseBody("orderId为空"));
        }
        Example example = new Example(HfUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", unionId);
        List<HfUser> hfUserList = hfUserMapper.selectByExample(example);
        if (hfUserList.size() == 0) {
            return builder.body(ResponseUtils.getResponseBody("请登录后操作"));
        }
        HfUser hfUser1 = hfUserMapper.selectByPrimaryKey(hfUserList.get(0));
        System.out.println(hfUser1);
        if (hfUser1.getCancelId() == 0) {
            return builder.body(ResponseUtils.getResponseBody("对不起你不是核销员无法核销商品"));
        }
        System.out.println(hfUser1.getCancelId() + "hfUser1.getCancelId()");
        //判断核销的商品是否为自提商品
        int productId=hfGoodsMapper.selectByPrimaryKey(goodsId).getProductId();
        Example examplecancel = new Example(CancelProduct.class);
        Example.Criteria criteriacancel = examplecancel.createCriteria();
        criteriacancel.andEqualTo("productId",productId).andEqualTo("cancelId",cancelList.get(0).getId());
        if (cancelProductMapper.selectByExample(examplecancel).size()==0) {
            return builder.body(ResponseUtils.getResponseBody("该商品不是自提商品"));
        }
        //判断核销员是否为该商品的核销员
        cancel cancel1 = cancelsMapper.selectByPrimaryKey(cancelList.get(0).getId());
        CancelProduct cancelProduct = cancelProductMapper.selectByExample(examplecancel).get(0);
        if (!cancelProduct.getCancelId().equals(cancel1.getId())) {
            return builder.body(ResponseUtils.getResponseBody("你不是该商品的核销员"));
        }
        //判断订单的商品与核销商品是否一致
        //价格，根据订单id，设置订单状态
        Example example2 = new Example(HfOrdersDetail.class);
        Example.Criteria criteria2 = example2.createCriteria();
        criteria2.andEqualTo("ordersId", orderId).andEqualTo("googsId",goodsId);
        List<HfOrdersDetail> hfPriceList = hfOrdersDetailMapper.selectByExample(example2);
        if (hfPriceList.size() == 0) {
            return builder.body(ResponseUtils.getResponseBody("订单不不存在"));
        }
        HfOrdersDetail hfPrice = hfOrdersDetailMapper.selectByPrimaryKey(hfPriceList.get(0).getId());
        if (hfPrice.getOrderDetailStatus().equals("已完成")) {
            return builder.body(ResponseUtils.getResponseBody("该订单已被核销"));
        }
        if (!hfPrice.getGoogsId().equals(goodsId)) {
            return builder.body(ResponseUtils.getResponseBody("订单核销的商品与实际不符合"));
        }
        HfOrdersDetail hfOrdersDetail = new HfOrdersDetail();
        hfOrdersDetail.setModifyTime(LocalDateTime.now());
        hfOrdersDetail.setId(hfPrice.getId());
        hfOrdersDetail.setOrderDetailStatus("已完成");
        hfOrdersDetailMapper.updateByPrimaryKeySelective(hfOrdersDetail);
        //添加核销记录
        CancelRecord cancelRecord = new CancelRecord();
        cancelRecord.setCreateDate(LocalDateTime.now());
        cancelRecord.setModifyDate(LocalDateTime.now());
        cancelRecord.setGoodsId(goodsId);
        cancelRecord.setCancelId(cancel1.getId());
        Example example3 = new Example(HfPrice.class);
        Example.Criteria criteria3 = example3.createCriteria();
        criteria3.andEqualTo("googsId", goodsId);
        List<HfPrice> hfPriceList1 = hfPriceMapper.selectByExample(example3);
        cancelRecord.setAmount(hfPriceList1.get(0).getSellPrice() * hfPrice.getPurchaseQuantity());
        hfLogMapper.insert(cancelRecord);
        //添加核销员核销额记录
        cancel cancel = new cancel();
        cancel.setId(cancel1.getId());
        cancel.setMoney(hfPriceList1.get(0).getSellPrice() * hfPrice.getPurchaseQuantity() + cancel1.getMoney());
        cancel.setPresentMoney(hfPriceList1.get(0).getSellPrice() * hfPrice.getPurchaseQuantity() + cancel1.getPresentMoney());
        cancelsMapper.updateByPrimaryKeySelective(cancel);
        return builder.body(ResponseUtils.getResponseBody(0));
    }

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    @ApiOperation(value = "时间检查查询没有核销员的商品", notes = "时间检查查询没有核销员的商品")
    public ResponseEntity<JSONObject> product() throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        List<Product> list = new ArrayList<>();
        List<Product> productList = productMapper.selectAll();
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getClaim() != 0) {
                Example example = new Example(CancelProduct.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("productId",productList.get(i).getId());
                if (cancelProductMapper.selectByExample(example).size()==0){
                    list.add(productList.get(i));
                }
            }
        }
        logger.info("时间检查" + LocalDateTime.now());
        logger.info("没有核销员的物品Id:" + list);
        return builder.body(ResponseUtils.getResponseBody(list));
    }
}
