package com.hanfu.user.center.config;

import com.hanfu.user.center.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class MyInterceptor implements HandlerInterceptor {
    @Autowired
    PermissionService permissionService;

    private static final Logger logger = LoggerFactory.getLogger(MyInterceptor.class);
//    @Autowired
//    private HfAdminMapper hfAdminMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println(request.getSession());
//        System.out.println(request.getParameter("name"));
//        System.out.println(request.getSession().getAttribute("userId"));
//        Example example = new Example(HfAuth.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("authKey",request.getParameter("userId"));
//        List<HfAuth> hfAuthList=hfAdminMapper.selectByExample(example);
//        hfAuthList.get(0).getUserId();
        permissionService.test();
        System.out.println(permissionService.test());
        System.out.println(request);
        System.out.println(response);
        System.out.println(handler);
        if (permissionService.hasPermission(request,response,handler)==true) {
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