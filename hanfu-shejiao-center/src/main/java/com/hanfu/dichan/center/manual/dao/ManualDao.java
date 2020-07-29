package com.hanfu.dichan.center.manual.dao;

import java.util.List;

import com.hanfu.dichan.center.model.DcCategory;

public interface ManualDao {
    public List<DcCategory> findCategoryByInfo(DcCategory h);
}
