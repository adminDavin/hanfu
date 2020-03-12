package com.hanfu.user.center.manual.model;

import java.lang.annotation.*;

	/**
	 * @author blueriver
	 * @description 与拦截器结合使用 验证权限
	 * @date 2017/11/17
	 * @since 1.0
	 */
	@Target({ElementType.TYPE, ElementType.METHOD})
	@Retention(RetentionPolicy.RUNTIME)
	@Inherited
	@Documented
	public @interface RequiredPermission {
	    String value();
	}
