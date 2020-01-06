package com.hanfu.user.center.service.impl;

import com.hanfu.user.center.dao.HfsetMemeberMapper;
import com.hanfu.user.center.model.HfSetBuyMember;
import com.hanfu.user.center.service.HfsetMemeberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName HfsetMemeberServiceImpl
 * @Date 2020/1/6 17:42
 * @Author CONSAK
 **/
@Service
public class HfsetMemeberServiceImpl implements HfsetMemeberService {

    @Autowired
    private HfsetMemeberMapper hfsetMemeberMapper;

    @Override
    public HfSetBuyMember select(Integer number) {
        return hfsetMemeberMapper.select(number);
    }

    @Override
    public void insert(Integer number, Integer total) {
        hfsetMemeberMapper.insert(number,total);
    }

    @Override
    public void update(Integer number, Integer total) {
        hfsetMemeberMapper.update(number,total);
    }

    @Override
    public HfSetBuyMember newselect(Integer number) {
        return hfsetMemeberMapper.newselect(number);
    }

    @Override
    public void newinsert(Integer number, Integer total) {
        hfsetMemeberMapper.newinsert(number,total);
    }

    @Override
    public void newupdate(Integer number, Integer total) {
        hfsetMemeberMapper.newupdate(number,total);
    }

    @Override
    public List<HfSetBuyMember> selectBuy() {
        return hfsetMemeberMapper.selectBuy();
    }

    @Override
    public List<HfSetBuyMember> selectRe() {
        return hfsetMemeberMapper.selectRe();
    }
}
