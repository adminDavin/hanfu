package com.hanfu.cancel.dao;

import com.hanfu.cancel.model.cancel;
import com.hanfu.cancel.model.record;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;
@Mapper
public interface CancelMapper{
    List<record> select();
    List<record> selectDate(LocalDateTime createData,LocalDateTime create1);
    List<record> selectRegion(String site);
    List<record> selectCancelId(int cancelId);
}
