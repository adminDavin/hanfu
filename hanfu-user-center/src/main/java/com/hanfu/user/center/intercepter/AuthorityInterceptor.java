package com.hanfu.user.center.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.hanfu.user.center.manual.model.RequiredPermission;

import io.micrometer.core.instrument.util.StringUtils;

@Component
public class AuthorityInterceptor extends HandlerInterceptorAdapter {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info(request.getRequestURI() + JSON.toJSONString(request.getParameterMap()));
		HttpSession session = request.getSession();
		System.out.println(session.getAttribute("uid")+"111111111111111");
//		if (hasPermission(handler) == true) {
//			return true;
//		}
//		response.sendError(HttpStatus.FORBIDDEN.value(), "无权限");
//		HandlerMethod handlerMethod = (HandlerMethod) handler;
//		Method method = handlerMethod.getMethod();
//		String methodName = method.getName();
//		logger.info("====拦截到了方法：{}，在该方法执行之前执行====", methodName);
//		System.out.println(handler);
//		System.out.println(request);
//		// 返回 true 才会继续执行，返回 false 则取消当前请求
		return true;
	}

//	private boolean hasPermission(Object handler) {
//		if (handler instanceof HandlerMethod) {
//			HandlerMethod handlerMethod = (HandlerMethod) handler;
//			// 获取方法上的注解
//			RequiredPermission requiredPermission = handlerMethod.getMethod().getAnnotation(RequiredPermission.class);
//			System.out.println(requiredPermission);
//			// 如果方法上的注解为空 则获取类的注解
//			if (requiredPermission == null) {
//				requiredPermission = handlerMethod.getMethod().getDeclaringClass()
//						.getAnnotation(RequiredPermission.class);
//			}
//			// 如果标记了注解，则判断权限
//			if (requiredPermission != null && StringUtils.isBlank(requiredPermission.value())) {
//				// redis或数据库 中获取该用户的权限信息 并判断是否有权限
//				String permissionSet = "admin_product_list1";
//				if (!requiredPermission.value().equals(permissionSet)) {
//					return false;
//				}
//				return permissionSet.contains(requiredPermission.value());
//			}
//		}
//		return true;
//	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info("执行完方法之后进执行(Controller方法调用之后)，但是此时还没进行视图渲染");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.info("整个请求都处理完咯，DispatcherServlet也渲染了对应的视图咯，此时我可以做一些清理的工作了");
	}

}
