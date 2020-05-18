package com.hanfu.payment.center.config;

import com.hanfu.payment.center.dao.PayBossMapper;
import com.hanfu.payment.center.model.PayBoss;
import com.hanfu.payment.center.model.PayBossExample;
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
    private static final Logger logger = LoggerFactory.getLogger(MyInterceptor.class);
//    @Autowired
//    private HfAdminMapper hfAdminMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        Cookie[] cookies = request.getCookies();
//        if (cookies==null){
//            return false;
//        }
        Object userId= request.getHeader("userId");
        System.out.println(userId+"我是请求头");
        logger.info("request请求地址path[{}] uri[{}]", request.getServletPath(),request.getRequestURI());
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
            PayBossExample payBossExample = new PayBossExample();
            payBossExample.createCriteria().andUserIdEqualTo(970).andIsDeletedEqualTo((byte) 0);
            List<PayBoss> payBosss=payBossMapper.selectByExample(payBossExample);
            //放在全局的ServletContext中，每一个web应用拥有一个ServletContext，是全局对象，具体请百度
            //把变量放在这里面，在之后什么地方都可以访问
            request.getServletContext().setAttribute("getServletContext", payBosss.get(0).getBossId());

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