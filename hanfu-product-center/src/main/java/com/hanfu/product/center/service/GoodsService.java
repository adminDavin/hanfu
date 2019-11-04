package com.hanfu.product.center.service;

import javax.servlet.http.HttpServletResponse;

public interface GoodsService {
	public void getFile(Integer FileDescId ,HttpServletResponse response) throws Exception;
}
