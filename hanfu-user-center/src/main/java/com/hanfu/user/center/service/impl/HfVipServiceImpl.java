package com.hanfu.user.center.service.impl;

import com.hanfu.user.center.manual.dao.HfVipDao;
import com.hanfu.user.center.service.HfVipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shihao
 * @Title: HfVipServiceImpl
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
@Service
public class HfVipServiceImpl implements HfVipService {
    @Autowired
    private HfVipDao hfVipDao;

    @Override
    public Integer minusDay(Integer hfVipUserId, Integer vipDay) {
        return hfVipDao.minusDay(hfVipUserId,vipDay);
    }
}
