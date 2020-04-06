package com.hanfu.product.center.manual.dao;

import java.util.List;

import com.hanfu.product.center.manual.model.Categories;
import com.hanfu.product.center.manual.model.HomePageInfo;
import com.hanfu.product.center.manual.model.ProductActivityInfo;
import com.hanfu.product.center.manual.model.UserBrowseInfo;
import com.hanfu.product.center.manual.model.UserInfo;
import com.hanfu.product.center.model.HfActivity;
import com.hanfu.user.center.model.HfUser;

public interface HomePageDao {
    public List<HomePageInfo> findSalesVolume(List<Integer> orderDetailId);
    
    public List<HomePageInfo> findOrderTypeCount(List<Integer> orderId);
    
    public List<String> groupBytime(Integer userId);
    
    public List<String> groupBytimeCollect(Integer userId);
    
    public List<String> groupBytimeConcern(Integer userId);
}
