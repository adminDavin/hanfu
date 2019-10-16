package com.hanfu.payment.center.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanfu.payment.center.manual.dao.ManualDao;
import com.hanfu.payment.center.service.WeChatService;

@Service("weChatService")
@org.apache.dubbo.config.annotation.Service(registry = "dubboProductServer")
public class WeChatServiceImpl implements WeChatService {
	@Autowired
	private ManualDao manualDao;

	@Override
	public void getProductByStone(Integer stoneId) {
		manualDao.selectProductByStone(stoneId);
		System.out.println("hello word");
	}



}
