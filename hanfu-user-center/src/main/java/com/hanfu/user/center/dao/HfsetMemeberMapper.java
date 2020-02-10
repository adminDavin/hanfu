package com.hanfu.user.center.dao;

import com.hanfu.user.center.model.HfSetBuyMember;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HfsetMemeberMapper {

    HfSetBuyMember select(Integer number);//先看数据库里面有没有设置会员月份和钱数

    void insert(@Param("number") Integer number, @Param("total") Integer total);//没有就增加

    void update(@Param("number") Integer number, @Param("total") Integer total);//修改

    HfSetBuyMember newselect(Integer number);//购买会员和充值会员是两个数据库

    void newinsert(@Param("number") Integer number, @Param("total") Integer total);//没有就增加

    void newupdate(@Param("number") Integer number, @Param("total") Integer total);//修改

    List<HfSetBuyMember> selectBuy();//查询购买会员

    List<HfSetBuyMember> selectRe();//查询充值会员
}