package com.hanfu.cancel.dao;

import com.hanfu.cancel.model.cancel;
import com.hanfu.cancel.model.record;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CancelMapper{
    List<record> select();
}
