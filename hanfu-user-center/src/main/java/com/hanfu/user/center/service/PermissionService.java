package com.hanfu.user.center.service;

import com.hanfu.user.center.Jurisdiction.model.AccountRoles;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface PermissionService {
   boolean hasPermission(@Param("request")HttpServletRequest request,@Param("response") HttpServletResponse response,@Param("handler") Object handler,Integer userId);
   Integer test();
}
