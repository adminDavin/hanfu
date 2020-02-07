package com.hanfu.order.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.order.center.cancel.dao.*;
import com.hanfu.order.center.cancel.model.*;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@Api
@RequestMapping("/Demo")
@CrossOrigin
public class DemoController {
    @Autowired
    private HfUserMapper hfUserMapper;
    @Autowired
    private HfGoodsMapper hfGoodsMapper;
    @Autowired
    private CancelsMapper cancelsMapper;
    @Autowired
    private HfOrdersCancelDetailMapper hfOrdersCancelDetailMapper;
    @Autowired
    private HfLogMapper hfLogMapper;
    @Autowired
    private HfPriceMapper hfPriceMapper;

    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    public String demo() {

        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "/testCancel", method = RequestMethod.GET)
    @ApiOperation(value = "核销逻辑测试", notes = "核销逻辑测试")
    public ResponseEntity<JSONObject> testCancel(
            @RequestParam(value = "userId", required = true) Integer userId,
            @RequestParam(value = "goodsId", required = true) Integer goodsId,
            @RequestParam(value = "orderId", required = true) Integer orderId
    ) throws Exception {
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
//        if (hfGoodsMapper.selectByPrimaryKey(goodsId).getClaim().equals(0)) {
//            return builder.body(ResponseUtils.getResponseBody("该商品不是自提商品"));
//        }
        //判断核销员是否为该商品的核销员
        System.out.println("UserId" + hfUserList.get(0).getId());//123
        Example example1 = new Example(cancel.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("userId", hfUserList.get(0).getId());
        List<cancel> cancelList = cancelsMapper.selectByExample(example1);
        System.out.println("cancelList" + cancelList);//123
        HfGoods hfGoods = hfGoodsMapper.selectByPrimaryKey(goodsId);
        cancel cancel1 = cancelsMapper.selectByPrimaryKey(cancelList.get(0).getId());
        System.out.println(cancelList.get(0).getId() + "getId");//123
//        if (!hfGoods.getCancelId().equals(cancel1.getId())) {
//            return builder.body(ResponseUtils.getResponseBody("你不是该商品的核销员"));
//        }
        //判断订单的商品与核销商品是否一致
        //价格，根据订单id，设置订单状态
        Example example2 = new Example(HfOrderDetail.class);
        Example.Criteria criteria2 = example2.createCriteria();
        criteria2.andEqualTo("orderId", orderId).andEqualTo("goodsId",goodsId);
        List<HfOrderDetail> hfPriceList = hfOrdersCancelDetailMapper.selectByExample(example2);
        if (hfPriceList == null) {
            return builder.body(ResponseUtils.getResponseBody("订单不不存在"));
        }
        System.out.println(hfPriceList);
        System.out.println(hfPriceList.get(0).getId());
        HfOrderDetail hfPrice = hfOrdersCancelDetailMapper.selectByPrimaryKey(hfPriceList.get(0).getId());
        System.out.println(hfPrice);
        System.out.println(hfPrice.getHfStatus());
        if (hfPrice.getHfStatus().equals("已完成")) {
            return builder.body(ResponseUtils.getResponseBody("该订单已被核销"));
        }
        if (!hfPrice.getGoodsId().equals(goodsId)) {
            return builder.body(ResponseUtils.getResponseBody("订单核销的商品与实际不符合"));
        }
        HfOrderDetail hfOrdersDetail = new HfOrderDetail();
        hfOrdersDetail.setModifyTime(LocalDateTime.now());
        hfOrdersDetail.setId(hfPrice.getId());
        hfOrdersDetail.setHfStatus("已完成");
        hfOrdersCancelDetailMapper.updateByPrimaryKeySelective(hfOrdersDetail);
        //添加核销记录
        CancelRecord cancelRecord = new CancelRecord();
        cancelRecord.setCreateDate(LocalDateTime.now());
        cancelRecord.setModifyDate(LocalDateTime.now());
        cancelRecord.setGoodsId(goodsId);
        cancelRecord.setCancelId(cancel1.getId());
        System.out.println(cancel1.getId() + "---cancel1.getId():");
        Example example3 = new Example(HfPrice.class);
        Example.Criteria criteria3 = example3.createCriteria();
        criteria3.andEqualTo("googsId", goodsId);
        List<HfPrice> hfPriceList1 = hfPriceMapper.selectByExample(example3);
        System.out.println("hfPriceList1:" + hfPriceList1);
        System.out.println(hfPriceList1.get(0).getSellPrice());
        cancelRecord.setAmount(hfPriceList1.get(0).getSellPrice() * hfPrice.getQuantity());
        hfLogMapper.insert(cancelRecord);
        //添加核销员核销额记录
        cancel cancel = new cancel();
        cancel.setId(cancel1.getId());
        cancel.setMoney(hfPriceList1.get(0).getSellPrice() * hfPrice.getQuantity() + cancel1.getMoney());
        cancel.setPresentMoney(hfPriceList1.get(0).getSellPrice() * hfPrice.getQuantity() + cancel1.getPresentMoney());
        cancelsMapper.updateByPrimaryKeySelective(cancel);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
}
