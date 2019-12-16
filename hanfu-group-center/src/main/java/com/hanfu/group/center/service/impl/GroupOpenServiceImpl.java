package com.hanfu.group.center.service.impl;


import com.hanfu.group.center.manual.dao.GroupOpenMapper;
import com.hanfu.group.center.service.GroupOpenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:gyj
 * @date: 2019/12/13
 * @time: 11:06
 */
@Service
public class GroupOpenServiceImpl implements GroupOpenService {
    @Autowired
    GroupOpenMapper groupOpenConnect;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return groupOpenConnect.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(GroupOpenMapper groupOpen) {
        groupOpenConnect.insert(groupOpen);
        return 0;
    }

    @Override
    public int insertSelective(GroupOpenMapper groupOpen) {
        groupOpenConnect.insertSelective(groupOpen);
        return 0;
    }

    @Override
    public GroupOpenMapper selectByPrimaryKey(Integer id) {
        return groupOpenConnect.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(GroupOpenMapper groupOpen) {
        return groupOpenConnect.updateByPrimaryKeySelective(groupOpen);
    }

    @Override
    public int updateByPrimaryKey(GroupOpenMapper groupOpen) {
        return groupOpenConnect.updateByPrimaryKey(groupOpen);
    }

    @Override
    public int selectNumber(Integer groupId) {
        return groupOpenConnect.selectNumber(groupId);
    }

    @Override
    public List<Integer> selectUserId(Integer groupId) {
        return groupOpenConnect.selectUserId(groupId);
    }
}
