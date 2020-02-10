package com.hanfu.user.center.service;


import java.lang.annotation.*;

/**
 * @author 皓月千里
 * @description 与拦截器结合使用 验证权限
 * @date 2019/1/7
 * @since 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RequiredPermission {
    String value();
}