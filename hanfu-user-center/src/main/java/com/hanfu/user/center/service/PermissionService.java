package com.hanfu.user.center.service;

import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 皓月千里
 *
 * @param 
 * @return
 */
public interface PermissionService {
   boolean hasPermission(@Param("request")HttpServletRequest request,@Param("response") HttpServletResponse response,@Param("handler") Object handler);
   int test();
}
