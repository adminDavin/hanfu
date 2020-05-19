package com.hanfu.product.center.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.hanfu.product.center.dao.AccountMapper;
import com.hanfu.product.center.dao.HfModuleMapper;
import com.hanfu.product.center.dao.HfUsersMapper;
import com.hanfu.product.center.dao.PayBossMapper;
import com.hanfu.product.center.model.*;
import com.hanfu.product.center.tool.Decrypt;
import com.hanfu.user.center.dao.HfAuthMapper;
import com.hanfu.user.center.dao.HfUserMapper;
import com.hanfu.user.center.model.HfAuth;
import com.hanfu.user.center.service.PermissionService;
import com.hanfu.user.center.service.impl.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

public class MyInterceptor implements HandlerInterceptor {

    Permission permissionService = new Permission();
    @Autowired
    private PayBossMapper payBossMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private HfModuleMapper hfModuleMapper;
    @Autowired
    private HfUsersMapper hfUsersMapper;
//    @Autowired
//    private HfAuthMapper hfAuthMapper;
//    @Autowired
//    private HfUserMapper hfUserMapper;
    private static final Logger logger = LoggerFactory.getLogger(MyInterceptor.class);
//    @Autowired
//    private HfAdminMapper hfAdminMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        Cookie[] cookies = request.getCookies();
//        if (cookies==null){
//            return false;
//        }
//        Object userId= request.getHeader("userId");

        logger.info("request请求地址path[{}] uri[{}]", request.getServletPath(),request.getRequestURI());
        Object token= request.getHeader("token");
        System.out.println(token+"我是请求头");
//        if (token==null){
//            return false;
//        }
        if (token!=null){
            Decrypt decrypt = new Decrypt();
            DecodedJWT jwt = decrypt.deToken((String) token);
            System.out.println("issuer: " + jwt.getIssuer());
            System.out.println("isVip:  " + jwt.getClaim("isVip").asBoolean());
            System.out.println("userId: " + jwt.getClaim("userId").asInt());
            System.out.println("type:     " + jwt.getClaim("Type").asString());
            System.out.println("过期时间：      " + jwt.getExpiresAt());

            if (redisTemplate.opsForValue().get(jwt.getClaim("userId").asInt()+jwt.getClaim("Type").asString()+"token")==null){
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "无权限");
            }
            if (redisTemplate.opsForValue().get(String.valueOf(jwt.getClaim("userId").asInt())+jwt.getClaim("Type").asString()+"token")!=token){
                System.out.println(redisTemplate.opsForValue().get(String.valueOf(jwt.getClaim("userId").asInt())+jwt.getClaim("Type").asString()+"token"));
                System.out.println("此账号在别处登陆了");
//                response.sendError(HttpStatus.UNAUTHORIZED.value(), "无权限");
            }
        }


        //        System.out.println(cookies+"cookies-----------------");
//        for(Cookie cookie1 : cookies){
//            if (cookie1.getName()==null){
//                System.out.println(cookie1.getName()+"cookie Name");
//                response.sendRedirect("http://39.100.237.144:3001/");
//            }
//            if (cookie1.getName()!=redisTemplate.opsForValue().get("autologin")){
//                System.out.println(redisTemplate.opsForValue().get("autologin")+"redis au");
//                response.sendRedirect("http://39.100.237.144:3001/");
//            }
//            redisTemplate.opsForValue().get("autologin");
//            if (cookie1.getName().equals("autologin")) {
//                System.out.println("name:" + cookie1.getName() + ",value:" + cookie1.getValue());
//            }
//        }
//        permissionService.test();
        if (permissionService.hasPermission(request,response,handler)==true) {
            //把变量放在request请求域中，仅可以被这次请求，即同一个requerst使用
//            request.setAttribute("getAttribute", "getAttribute");
if (token!=null){
    Decrypt decrypt = new Decrypt();
    DecodedJWT jwt = decrypt.deToken((String) token);
    String type = jwt.getClaim("Type").asString();
//    if (type.equals("boss")){
//        HfUsersExample hfUsersExample = new HfUsersExample();
//        hfUsersExample.createCriteria().andBossIdEqualTo()
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andUserIdEqualTo(Integer.valueOf(jwt.getClaim("userId").asInt())).andIsDeletedEqualTo(0).andAccountTypeEqualTo(type);
        List<Account> accounts= accountMapper.selectByExample(accountExample);
        if (accounts.size()==0){
            response.sendError(HttpStatus.FORBIDDEN.value(), "无权限");
        }
        request.getServletContext().setAttribute("getServletContext", accounts.get(0).getMerchantId());
        request.getServletContext().setAttribute("getServletContextType", type);
//    }
}
            //放在全局的ServletContext中，每一个web应用拥有一个ServletContext，是全局对象
            //把变量放在这里面，在之后什么地方都可以访问
            //把自己的变量放在头部
//            reflectSetHeader(request, "header", "header");
            return true;
        }
        response.sendError(HttpStatus.FORBIDDEN.value(), "无权限");
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        String methodName = method.getName();
        logger.info("====拦截到了方法：{}，在该方法执行之前执行====", methodName);
        System.out.println(handler);
        System.out.println(request);
        // 返回 true 才会继续执行，返回 false 则取消当前请求
        return false;
    }



    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("执行完方法之后进执行(Controller方法调用之后)，但是此时还没进行视图渲染");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("整个请求都处理完咯，DispatcherServlet也渲染了对应的视图咯，此时我可以做一些清理的工作了");
    }
}