package com.hanfu.product.center.manual.dao;

import com.hanfu.product.center.manual.model.HfGroup;
import io.swagger.models.auth.In;

import java.util.List;

public interface HfGroupDao {
    List<HfGroup> groupList(Integer groupId);
}
