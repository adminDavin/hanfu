//package com.hanfu.cancel.service.impl;
//
//import com.hanfu.cancel.model.Admin;
//import com.hanfu.cancel.model.Employee;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class UserDetailServiceImpl implements UserDetailsService {
//    @Resource
//    private RedisTemplate<String, Object> redisTemplate;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        System.out.println("授权:"+username);
//        System.out.println("授权:"+redisTemplate.opsForValue().get(username));
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        String a = String.valueOf(redisTemplate.opsForValue().get(username));
//        //生成环境是查询数据库获取username的角色用于后续权限判断（如：张三 admin)
//        //这里不做数据库操作，给定假数据，有兴趣的人可以使内存模式。
//        if (username.equals("17612219746")) {
//            Employee employee = new Employee();
//            employee.setUsername(username);
//            employee.setPassword(a);
//            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_EMPLOYEE");
//            grantedAuthorities.add(grantedAuthority);
//            //创建一个用户，用于判断权限，请注意此用户名和方法参数中的username一致；BCryptPasswordEncoder是用来演示加密使用。
//            return new User(employee.getUsername(), new BCryptPasswordEncoder().encode(employee.getPassword()), grantedAuthorities);
//        }
//        if (username.equals("admin")) {
//            Admin admin = new Admin();
//            admin.setUsername("admin");
//            admin.setPassword("123456");
//            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
//            grantedAuthorities.add(grantedAuthority);
//            return new User(admin.getUsername(), new BCryptPasswordEncoder().encode(admin.getPassword()), grantedAuthorities);
//        }
//        else {
//            return null;
//        }
//
//
//    }
//}