package com.hanfu.user.center.service;

/**
 * @author shihao
 * @Title: HfVipService
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
public interface HfVipService {
    /**
     * 减剩余会员天数
     * @param hfVipUserId
     * @param vipDay
     * @return
     */
    Integer minusDay(Integer hfVipUserId,Integer vipDay);
}
