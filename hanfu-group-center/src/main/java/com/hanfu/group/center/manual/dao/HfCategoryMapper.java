package com.hanfu.group.center.manual.dao;


import com.hanfu.group.center.manual.model.HfCategory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HfCategoryMapper {


    int selectByName(String name);
}