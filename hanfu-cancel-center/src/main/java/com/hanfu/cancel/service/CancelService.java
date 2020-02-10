package com.hanfu.cancel.service;

import com.hanfu.cancel.model.record;

import java.util.Date;
import java.util.List;

public interface CancelService {
    List<record> select();

    List<record> selectDate(Date createData, Date createDate1);

    List<record> selectRegion(String site);

    List<record> selectCancelId(int cancelId);

    List<record> Test(String site, Date createData, Date createDate1);


}
