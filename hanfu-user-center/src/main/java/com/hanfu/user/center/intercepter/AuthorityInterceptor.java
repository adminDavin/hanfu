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
		return super.preHandle(request, response, handler);
	}

}
