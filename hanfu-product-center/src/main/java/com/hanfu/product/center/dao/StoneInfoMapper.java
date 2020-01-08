package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.StoneInfo;
import com.hanfu.product.center.model.StoneInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StoneInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stone_info
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    long countByExample(StoneInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stone_info
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    int deleteByExample(StoneInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stone_info
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stone_info
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    int insert(StoneInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stone_info
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    int insertSelective(StoneInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stone_info
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    List<StoneInfo> selectByExample(StoneInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stone_info
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    StoneInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stone_info
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    int updateByExampleSelective(@Param("record") StoneInfo record, @Param("example") StoneInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stone_info
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    int updateByExample(@Param("record") StoneInfo record, @Param("example") StoneInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stone_info
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    int updateByPrimaryKeySelective(StoneInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stone_info
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    int updateByPrimaryKey(StoneInfo record);
}