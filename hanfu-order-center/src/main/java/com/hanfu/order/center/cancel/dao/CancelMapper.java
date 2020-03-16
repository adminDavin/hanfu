package com.hanfu.order.center.cancel.dao;

import com.hanfu.order.center.cancel.model.SelectCancelProduct;
import com.hanfu.order.center.cancel.model.record;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface CancelMapper {
    List<record> select();

    List<record> selectDate(@Param("createData") Date createData, @Param("createDate1") Date createDate1);

    List<record> selectRegion(String site);

    List<record> selectCancelId(int cancelId);

    List<record> Test(@Param("site") String site, @Param("createData") Date createData, @Param("createDate1") Date createDate1);

    List<SelectCancelProduct> selectProductCancel(Integer productId);
}
