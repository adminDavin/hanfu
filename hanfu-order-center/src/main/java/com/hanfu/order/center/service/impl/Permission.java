package com.hanfu.order.center.service.impl;


import com.hanfu.order.center.dao.AccountRolesMapper;
import com.hanfu.order.center.dao.JurisdictionMapper;
import com.hanfu.order.center.dao.RoleJurisdictionMapper;
import com.hanfu.order.center.model.*;
import com.hanfu.order.center.service.PermissionService;
import com.hanfu.user.center.service.RequiredPermission;
import io.micrometer.core.instrument.util.StringUtils;
import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//import com.hanfu.user.center.service.PermissionService;

@Service
//@org.apache.dubbo.config.annotation.Service(registry = "dubboProductServer")
public class Permission implements PermissionService {

    /**
     * 是否有权限
     *
     * @param handler
     * @return
     */
    @Autowired
    private JurisdictionMapper jurisdictionMapper;
    @Autowired
    private AccountRolesMapper accountRolesMapper;
    @Autowired
    private RoleJurisdictionMapper roleJurisdictionMapper;
 @Override
    public boolean hasPermission(HttpServletRequest request, HttpServletResponse response, Object handler,Integer userId) {

     System.out.println("进入user");
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
                // redis或数据库 中获取该用户的权限信息 并判断是否有权限
                AccountRolesExample accountRolesExample = new AccountRolesExample();
                accountRolesExample.createCriteria().andAccountIdEqualTo(userId).andIsDeletedEqualTo((short) 0);
                List<AccountRoles> accountRoles = accountRolesMapper.selectByExample(accountRolesExample);
                Set<Integer> rolesId = accountRoles.stream().map(a -> a.getRolesId()).collect(Collectors.toSet());
                System.out.println(rolesId);
                boolean contains1 = false;
                if (rolesId.size()!=0){
                    RoleJurisdictionExample roleJurisdictionExample = new RoleJurisdictionExample();
                    roleJurisdictionExample.createCriteria().andRoleIdIn(Lists.newArrayList(rolesId));
                    List<RoleJurisdiction> roles =  roleJurisdictionMapper.selectByExample(roleJurisdictionExample);
                    Set<Integer> Jid = roles.stream().map(j->j.getJurisdictionId()).collect(Collectors.toSet());
                    JurisdictionExample jurisdictionExample = new JurisdictionExample();
                    jurisdictionExample.createCriteria().andIdIn(Lists.newArrayList(Jid)).andIsDeletedEqualTo((short) 0);
                    List<Jurisdiction> jurisdictions = jurisdictionMapper.selectByExample(jurisdictionExample);
                    Set<String> jurisdiction = jurisdictions.stream().map(a->a.getAccessCode()).collect(Collectors.toSet());
                    contains1 = jurisdiction.contains(requiredPermission.value());
                }

//                String permissionSet = "admin_product_list";
                System.out.println(requiredPermission.value());
                if (contains1 != true){
                    return false;
                }
//                String permissionSet = "admin_product_list";
//                System.out.println(requiredPermission.value());
//                System.out.println(permissionSet);
//                if (!requiredPermission.value().equals(permissionSet)){
//                    System.out.println("1231312421341234214124");
//                    return false;
//                }
//                return permissionSet.contains(requiredPermission.value());
                return true;
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
