package com.hanfu.user.center.manual.dao;

/**
 * @author shihao
 * @Title: HfVipDao
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
public interface HfVipDao {
    /**
     * 减剩余会员天数
     * @param hfVipUserId
     * @param vipDay
     * @return
     */
    Integer minusDay(Integer hfVipUserId,Integer vipDay);
}
