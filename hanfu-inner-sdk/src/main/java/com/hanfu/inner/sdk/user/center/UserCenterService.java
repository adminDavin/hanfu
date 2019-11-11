package com.hanfu.inner.sdk.user.center;

import java.util.List;

import com.hanfu.inner.model.product.center.HfUser;
import com.hanfu.inner.model.product.center.HfUserAddresse;

public interface UserCenterService {


	List<HfUser> getUserById(Integer id);

	List<HfUserAddresse> getUserAddressById(Integer id);

}
