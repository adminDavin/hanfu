package com.hanfu.referral.center.service.impl;

import java.util.List;

import org.apache.dubbo.config.annotation.Reference;

import com.hanfu.inner.model.product.center.HfUser;
import com.hanfu.inner.sdk.user.center.UserCenterService;
import com.hanfu.referral.center.service.ReferralUserService;

public class ReferralUserServiceImpl implements ReferralUserService{
    @Reference(registry = "dubboProductServer", url = "dubbo://127.0.0.1:1900/com.hanfu.inner.sdk.user.center.UserCenterService")
    private UserCenterService userCenterService;
	@Override
	public List<HfUser> getUserById(Integer id) {
		return userCenterService.getUserById(id);
	}

}
