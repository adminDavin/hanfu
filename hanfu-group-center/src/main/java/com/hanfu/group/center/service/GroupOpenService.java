package com.hanfu.group.center.service;


import com.hanfu.group.center.manual.dao.GroupOpenMapper;

import java.util.List;

/**
 * @author:gyj
 * @date: 2019/12/13
 * @time: 11:05
 */
public interface GroupOpenService {
    int deleteByPrimaryKey(Integer id);

    int insert(GroupOpenMapper groupOpen);

    int insertSelective(GroupOpenMapper groupOpen);

    GroupOpenMapper selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupOpenMapper groupOpen);

    int updateByPrimaryKey(GroupOpenMapper groupOpen);
    int selectNumber(Integer groupId);
    List<Integer> selectUserId(Integer groupId);
}
