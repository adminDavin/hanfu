package com.hanfu.group.center.service;

import com.hanfu.group.center.manual.model.HfUser;

/**
 * @author:gyj
 * @date: 2019/12/26
 * @time: 14:04
 */
public interface HfUserService {
    HfUser selectByPrimaryKey(Integer id);
}
