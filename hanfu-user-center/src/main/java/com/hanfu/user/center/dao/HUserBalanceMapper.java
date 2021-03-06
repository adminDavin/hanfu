package com.hanfu.user.center.dao;

import com.hanfu.user.center.model.HUserBalance;
import com.hanfu.user.center.model.HUserBalanceExample;

import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HUserBalanceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_user_balance
     *
     * @mbg.generated Tue Mar 17 14:56:16 CST 2020
     */
    long countByExample(HUserBalanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_user_balance
     *
     * @mbg.generated Tue Mar 17 14:56:16 CST 2020
     */
    int deleteByExample(HUserBalanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_user_balance
     *
     * @mbg.generated Tue Mar 17 14:56:16 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_user_balance
     *
     * @mbg.generated Tue Mar 17 14:56:16 CST 2020
     */
    int insert(HUserBalance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_user_balance
     *
     * @mbg.generated Tue Mar 17 14:56:16 CST 2020
     */
    int insertSelective(HUserBalance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_user_balance
     *
     * @mbg.generated Tue Mar 17 14:56:16 CST 2020
     */
    List<HUserBalance> selectByExample(HUserBalanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_user_balance
     *
     * @mbg.generated Tue Mar 17 14:56:16 CST 2020
     */
    HUserBalance selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_user_balance
     *
     * @mbg.generated Tue Mar 17 14:56:16 CST 2020
     */
    int updateByExampleSelective(@Param("record") HUserBalance record, @Param("example") HUserBalanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_user_balance
     *
     * @mbg.generated Tue Mar 17 14:56:16 CST 2020
     */
    int updateByExample(@Param("record") HUserBalance record, @Param("example") HUserBalanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_user_balance
     *
     * @mbg.generated Tue Mar 17 14:56:16 CST 2020
     */
    int updateByPrimaryKeySelective(HUserBalance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_user_balance
     *
     * @mbg.generated Tue Mar 17 14:56:16 CST 2020
     */
    int updateByPrimaryKey(HUserBalance record);
    
    void balanceCutTotal(@Param("userId") Integer userId, @Param("hfBalance") Integer hfBalance, @Param("total") Integer total);

    HUserBalance itExistUserById(@Param("userId") Integer userId);//判断用户是不是第一次充钱

    void memberTime(@Param("userId") Integer userId,
                    @Param("time") LocalDateTime time,
                    @Param("thirtyTime") LocalDateTime thirtyTime,
                    @Param("seasonTime") LocalDateTime seasonTime,
                    @Param("yearTime") LocalDateTime yearTime,
                    @Param("total") Integer total);//成为会员时间

    void insertBalance(@Param("userId") Integer userId, @Param("total") Integer total);//把用户充的钱放入余额

    String getModifyTime(@Param("userId") Integer userId);

    void updateBalance(@Param("userId") Integer userId, @Param("total") Integer total);

    void updateModifyTime(@Param("userId") Integer userId,
			@Param("time") LocalDateTime time,
                          @Param("thirtyTime") LocalDateTime thirtyTime,
                          @Param("seasonTime") LocalDateTime seasonTime,
                          @Param("yearTime") LocalDateTime yearTime,
                          @Param("total") Integer total);

    String getCreateTime(@Param("userId") Integer userId);//判断用户是不是第一次购买会员

    void buymemberTime(@Param("userId") Integer userId,
                       @Param("time") LocalDateTime time,
                       @Param("thirtyTime") LocalDateTime thirtyTime,
                       @Param("seasonTime") LocalDateTime seasonTime,
                       @Param("yearTime") LocalDateTime yearTime,
                       @Param("total") Integer total);//第一次购买会员

    void updateTime(@Param("userId") Integer userId,
                    @Param("time") LocalDateTime time,
                    @Param("thirtyTime") LocalDateTime thirtyTime,
                    @Param("seasonTime") LocalDateTime seasonTime,
                    @Param("yearTime") LocalDateTime yearTime,
                    @Param("total") Integer total);//购买会员 没有过期的添加时间
}