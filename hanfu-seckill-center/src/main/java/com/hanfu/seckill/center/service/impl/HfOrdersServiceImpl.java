package com.hanfu.seckill.center.service.impl;



import com.hanfu.seckill.center.dao.HfOrderStatusMapper;
import com.hanfu.seckill.center.dao.HfOrdersDetailMapper;
import com.hanfu.seckill.center.dao.HfOrdersMapper;
import com.hanfu.seckill.center.model.HfOrders;
import com.hanfu.seckill.center.model.HfOrdersDetail;
import com.hanfu.seckill.center.model.Seckill;
import com.hanfu.seckill.center.service.HfOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HfOrdersServiceImpl implements HfOrdersService {
     @Autowired
    HfOrdersMapper  hfOrdersMapper;
     @Autowired
    HfOrderStatusMapper hfOrderStatusMapper;
     @Autowired
    HfOrdersDetailMapper  hfOrdersDetailMapper;
    @Override
    public List<Object> insert(Seckill seckill, Integer userId) throws ParseException {
        HfOrders hfOrders = new HfOrders();
        hfOrders.setUserId(userId);
        Integer a=0;
        hfOrders.setPayStatus(a);
        hfOrders.setOrderType("秒杀");
        double   price =seckill.getPrice();
        int price1=(int)price;
        hfOrders.setAmount(price1);
        hfOrders.setPayMethodType(1);
        SimpleDateFormat formatter1= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String format1 = formatter1.format(date);
        Date startTime1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format1);
        hfOrders.setCreateTime(startTime1);
        hfOrders.setModifyTime(startTime1);
        Integer id = seckill.getId();
        String a1=String.valueOf(id);
        hfOrders.setHfRemark(a1);
        hfOrders.setIsDeleted((short) 0);
        hfOrdersMapper.insertSelective(hfOrders);
        HfOrdersDetail hfOrdersDetail = new HfOrdersDetail();
        HfOrders orders = hfOrdersMapper.selectByUserId("秒杀", userId);
        hfOrdersDetail.setOrdersId(orders.getId());
        hfOrdersDetail.setGoogsId(seckill.getGoodsId());
        hfOrdersDetail.setPurchasePrice(price1);
        hfOrdersDetail.setPurchaseQuantity(1);
        hfOrdersDetail.setOrderDetailStatus(hfOrderStatusMapper.selectByPrimaryKey(10).getHfName());
        hfOrdersDetail.setIsDeleted((short) 0);
        hfOrdersDetail.setLastModifier("1");
        hfOrdersDetailMapper.insertSelective(hfOrdersDetail);
        List<Object> objects = new ArrayList<>();
        objects.add(hfOrders);
        objects.add(hfOrdersDetail);
        return objects;
    }

    @Override
    public boolean seckillByPay(Integer id) {
        HfOrders orders = hfOrdersMapper.selectByUserId("秒杀", id);
        if (orders.getPayStatus()!=1){
            Integer id1 = orders.getId();
            hfOrdersDetailMapper.deleteByPrimaryKey(id1);
            hfOrdersMapper.deleteByPrimaryKey(id1);
            return false;
        }
        return true;
    }
}
