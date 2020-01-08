package com.hanfu.user.center.service.impl;


import com.hanfu.user.center.service.PermissionService;
//import com.hanfu.user.center.service.PermissionService;
import com.hanfu.user.center.service.RequiredPermission;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Service
//@org.apache.dubbo.config.annotation.Service(registry = "dubboProductServer")
public class Permission implements PermissionService {
    /**
     * 是否有权限
     *
     * @param handler
     * @return
     */
 @Override
    public boolean hasPermission(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取方法上的注解
            RequiredPermission requiredPermission = handlerMethod.getMethod().getAnnotation(RequiredPermission.class);
            System.out.println(requiredPermission);
            // 如果方法上的注解为空 则获取类的注解
            if (requiredPermission == null) {
                requiredPermission = handlerMethod.getMethod().getDeclaringClass().getAnnotation(RequiredPermission.class);
            }
            // 如果标记了注解，则判断权限
            if (requiredPermission != null && StringUtils.isNotBlank(requiredPermission.value())) {
                Cookie[] cookies = request.getCookies();
                if (cookies==null){
                    return false;
                }
                System.out.println(cookies);
                for(Cookie cookie1 : cookies){
                    if (cookie1.getName().equals("autologin")) {
                        System.out.println("name:" + cookie1.getName() + ",value:" + cookie1.getValue());
                    }
                }
                // redis或数据库 中获取该用户的权限信息 并判断是否有权限
                String permissionSet = "admin_product_list";
                System.out.println(requiredPermission.value());
                System.out.println(permissionSet);
                if (!requiredPermission.value().equals(permissionSet)){
                    System.out.println("1231312421341234214124");
                    return false;
                }
                return permissionSet.contains(requiredPermission.value());
            }
        }else {
            return true;
        }
        return true;
    }

    @Override
    public int test() {
        System.out.println("123456");
        return 0;
    }


}
