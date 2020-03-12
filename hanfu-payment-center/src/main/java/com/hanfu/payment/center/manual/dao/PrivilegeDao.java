package com.hanfu.payment.center.manual.dao;

import com.hanfu.payment.center.manual.model.hfUserPrivilege;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PrivilegeDao {
    List<hfUserPrivilege> selectPrivilege(@Param("userId")Integer userId, @Param("status") String status);
}
