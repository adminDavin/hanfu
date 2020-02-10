package com.hanfu.product.center.dao;


import org.apache.ibatis.annotations.Mapper;


public interface HfCategory {


    int selectByName(String name);
}