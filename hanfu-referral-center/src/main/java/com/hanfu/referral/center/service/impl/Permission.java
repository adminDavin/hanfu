package com.hanfu.referral.center.service.impl;

import com.hanfu.referral.center.service.PermissionService;
import org.apache.dubbo.config.annotation.Reference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Permission implements PermissionService {
    @Reference(registry = "dubboProductServer", url = "dubbo://127.0.0.1:1900/com.hanfu.inner.sdk.product.center.HelloTestService")
    private PermissionService permissionService;
    @Override
    public boolean hasPermission(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return false;
    }

    @Override
    public int test() {
        permissionService.test();
        return 0;
    }
}
