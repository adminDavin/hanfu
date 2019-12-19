package com.hanfu.group.center.service;

import java.util.Map;

public interface WeChatService {

	public void getProductByStone(Integer stoneId);

	public Map<String, String> dounifiedOrder(String attach, String totalFee) throws Exception;

	public String payBack(String notifyData);

}
