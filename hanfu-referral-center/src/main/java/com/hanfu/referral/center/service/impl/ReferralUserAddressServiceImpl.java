package com.hanfu.referral.center.service.impl;

import java.util.List;

import org.apache.dubbo.config.annotation.Reference;

import com.hanfu.inner.model.product.center.HfUserAddresse;
import com.hanfu.inner.sdk.user.center.UserCenterService;
import com.hanfu.referral.center.service.ReferralUserAddressService;

public class ReferralUserAddressServiceImpl implements ReferralUserAddressService{
    @Reference(registry = "dubboProductServer", url = "dubbo://127.0.0.1:1900/com.hanfu.inner.sdk.user.center.UserCenterService")
    private UserCenterService userCenterService;
	@Override
	public List<HfUserAddresse> getUserAddressById(Integer id) {
		return userCenterService.getUserAddressById(id);
	}

}
