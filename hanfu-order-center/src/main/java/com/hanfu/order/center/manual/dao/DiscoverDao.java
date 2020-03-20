package com.hanfu.order.center.manual.dao;

import com.hanfu.order.center.manual.model.DiscoverUser;

import java.util.List;

public interface DiscoverDao {
    List<DiscoverUser> selectDiscoverAll(Integer userId);
}
