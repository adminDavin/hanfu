package com.hanfu.cancel.service;

import com.github.pagehelper.PageInfo;
import com.hanfu.cancel.model.HfOrdersDetail;
import com.hanfu.cancel.model.record;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface CancelService {
    List<record> select();

    List<record> selectDate(Date createData, Date createDate1);

    List<record> selectRegion(String site);

    List<record> selectCancelId(int cancelId);

    PageInfo<record> Test(String site, Date createData, Date createDate1);
}
