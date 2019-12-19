package com.hanfu.group.center.manual.dao;

import java.util.List;

import com.hanfu.group.center.manual.model.Categories;
import com.hanfu.group.center.manual.model.UserInfo;

public interface ManualDao {
	public List<UserInfo> getSimpleUserInfo();

	public void selectProductByStone(Integer stoneId);

	public List<Categories> selectCategories();
}
