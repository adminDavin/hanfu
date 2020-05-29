package com.hanfu.product.center.service;

import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface PermissionService {
   boolean hasPermission(@Param("request") HttpServletRequest request, @Param("response") HttpServletResponse response, @Param("handler") Object handler, Integer userId);
   int test();
}