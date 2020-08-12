package com.hanfu.shejiao.center.service;


import javax.servlet.http.HttpServletRequest;

import com.hanfu.shejiao.center.manual.model.TenPayVO;

public interface PayService {
	
	public TenPayVO tenPayCallBack(HttpServletRequest request) throws Exception;
	
	public TenPayVO tenPayQueryCallBack(String payNo) throws Exception;
	
	public void initiativeOrderPayCallBack() throws Exception;
}
