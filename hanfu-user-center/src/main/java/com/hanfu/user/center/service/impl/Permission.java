package com.hanfu.user.center.service.impl;


import com.hanfu.user.center.Jurisdiction.dao.*;
import com.hanfu.user.center.Jurisdiction.model.*;
import com.hanfu.user.center.service.PermissionService;
//import com.hanfu.user.center.service.PermissionService;
import com.hanfu.user.center.service.RequiredPermission;
import io.micrometer.core.instrument.util.StringUtils;
import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
//@org.apache.dubbo.config.annotation.Service(registry = "dubboProductServer")
public class Permission implements PermissionService {

    /**
     * 是否有权限
     *
     * @param handler
     * @return
     */
//    @Autowired
//    private DepartmentPersonnelMapper departmentPersonnelMapper;
//    @Autowired
//    private DepartmentMapper departmentMapper;
//    @Autowired
//    private RolesMapper rolesMapper;
//    @Autowired
//    private RoleJurisdictionMapper roleJurisdictionMapper;
//    @Autowired
//    private JurisdictionMapper jurisdictionMapper;
//    @Autowired
//    private AccountRolesMapper accountRolesMapper;
 @Override
    public boolean hasPermission(HttpServletRequest request, HttpServletResponse response, Object handler,Integer userId) {
//     Set<Integer> jur= new HashSet<Integer>();
//     AccountRolesExample accountRolesExample = new AccountRolesExample();
//     accountRolesExample.createCriteria().andAccountIdEqualTo(userId).andIsDeletedEqualTo((short) 0);
//     List<AccountRoles> accountRolesList= accountRolesMapper.selectByExample(accountRolesExample);
//     if (accountRolesList.size()!=0){
//             Set<Integer> role= accountRolesList.stream().map(a->a.getRolesId()).collect(Collectors.toSet());
//             RoleJurisdictionExample roleJurisdictionExample = new RoleJurisdictionExample();
//             roleJurisdictionExample.createCriteria().andRoleIdIn(Lists.newArrayList(role)).andIsDeletedEqualTo((short) 0);
//             List<RoleJurisdiction> roleJurisdictions= roleJurisdictionMapper.selectByExample(roleJurisdictionExample);
//             jur= roleJurisdictions.stream().map(a->a.getJurisdictionId()).collect(Collectors.toSet());
//     }
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
                String permissionSet = "admin_product_list";
//                if (jur!=null){
//                    JurisdictionExample jurisdictionExample = new JurisdictionExample();
//                    jurisdictionExample.createCriteria().andIsDeletedEqualTo((short) 0).andIdIn(Lists.newArrayList(jur));
//                    List<Jurisdiction> jurisdictions= jurisdictionMapper.selectByExample(jurisdictionExample);
//                    Set<String> codes= jurisdictions.stream().map(a->a.getAccessCode()).collect(Collectors.toSet());
//                    for (String code:codes){
//                        if (requiredPermission.value().equals(code)){
//                            permissionSet = code;
//                        }
//                    }
//                }
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
    public Integer test() {
//        RoleJurisdictionExample roleJurisdictionExample = new RoleJurisdictionExample();
//        roleJurisdictionExample.createCriteria().andIsDeletedEqualTo((short) 0);
//        List<RoleJurisdiction> roleJurisdictions= roleJurisdictionMapper.selectByExample(roleJurisdictionExample);
//        System.out.println(roleJurisdictions);
//        AccountRolesExample accountRolesExample = new AccountRolesExample();
//        accountRolesExample.createCriteria().andAccountIdEqualTo(1).andIsDeletedEqualTo((short) 0);
//        List<AccountRoles> accountRolesList= accountRolesMapper.selectByExample(accountRolesExample);
//        System.out.println(accountRolesList);
        return 0;
    }


}
