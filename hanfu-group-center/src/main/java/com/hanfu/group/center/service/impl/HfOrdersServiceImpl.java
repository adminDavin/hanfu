package com.hanfu.group.center.service.impl;



import com.hanfu.group.center.manual.dao.GroupOpenConnectMapper;
import com.hanfu.group.center.manual.dao.HfOrderLogisticsMapper;
import com.hanfu.group.center.manual.dao.HfOrdersDetailMapper;
import com.hanfu.group.center.manual.dao.HfOrdersMapper;
import com.hanfu.group.center.manual.model.*;
import com.hanfu.group.center.service.HfOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class HfOrdersServiceImpl implements HfOrdersService {
     @Autowired
     HfOrdersMapper hfOrdersMapper;
     @Autowired
     HfOrdersDetailMapper hfOrdersDetailMapper;
     @Autowired
     HfOrderLogisticsMapper hfOrdersLogistcsMapper;
    @Autowired
    GroupOpenConnectMapper groupOpenConnectMapper;
    @Override
    public List<Object> insert(Group group, Integer userId,Integer groupOpenId ) throws ParseException {
        GroupOpenConnect groupOpenConnect = groupOpenConnectMapper.selectByGroup(userId, groupOpenId);
        HfOrders hfOrders = new HfOrders();
        hfOrders.setUserId(userId);
        Integer a=0;
        hfOrders.setPayStatus(a);
        hfOrders.setOrderType("团购");
        double   price =group.getPrice();
        int price1=(int)price;
        hfOrders.setAmount(price1);
        hfOrders.setPayMethodType(1);
        SimpleDateFormat formatter1= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String format1 = formatter1.format(date);
        Date startTime1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format1);
        hfOrders.setCreateTime(startTime1);
        hfOrders.setModifyTime(startTime1);
        Integer id = group.getId();
        String a1=String.valueOf(id);
        hfOrders.setHfRemark(a1);
        hfOrders.setIsDeleted((short) 0);
        hfOrdersMapper.insertSelective(hfOrders);
        HfOrdersDetail hfOrdersDetail = new HfOrdersDetail();
        HfOrders orders = hfOrdersMapper.selectByUserId("团购", userId);
        hfOrdersDetail.setOrdersId(orders.getId());
        hfOrdersDetail.setHfDesc(groupOpenConnect.getHfdesc());
        hfOrdersDetail.setGoogsId(group.getGoodsId());
        hfOrdersDetail.setPurchasePrice(price1);
        hfOrdersDetail.setPurchaseQuantity(1);
        hfOrdersDetail.setOrderDetailStatus("待支付");
        hfOrdersDetail.setIsDeleted((short) 0);
        hfOrdersDetail.setLastModifier("1");
        hfOrdersDetailMapper.insertSelective(hfOrdersDetail);
        HfOrdersDetail hfOrdersDetail1 = hfOrdersDetailMapper.selectByOrdersId(orders.getId());
        HfOrderLogistics hfOrderLogistics = new HfOrderLogistics();
        hfOrderLogistics.setOrderDetailId(hfOrdersDetail1.getId());
        hfOrderLogistics.setOrdersId(orders.getId());
        hfOrderLogistics.setUserId(userId);
        hfOrderLogistics.setUserAddressId(groupOpenConnect.getAddressId());
        hfOrderLogistics.setGoogsId(group.getGoodsId());
        hfOrderLogistics.setCreateTime(startTime1);
        hfOrderLogistics.setModifyTime(startTime1);
        hfOrderLogistics.setIsDeleted((short) 0);
        hfOrdersLogistcsMapper.insert(hfOrderLogistics);
        List<Object> objects = new ArrayList<>();
        objects.add(hfOrderLogistics);
        objects.add(orders);
        objects.add(hfOrdersDetail1);
        return objects;
    }

    @Override
    public boolean seckillByPay(Integer id) {
        HfOrders orders = hfOrdersMapper.selectByUserId("团购", id);
        if (orders.getPayStatus()!=1){
            Integer id1 = orders.getId();
            hfOrdersDetailMapper.deleteByPrimaryKey(id1);
            hfOrdersMapper.deleteByPrimaryKey(id1);
            return false;
        }
        return true;
    }
}
