package com.hanfu.shejiao.center.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.hanfu.shejiao.center.model.SjUser;
import com.hanfu.shejiao.center.model.SjUserExample;

public interface SjUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sj_user
     *
     * @mbg.generated Wed Jul 29 17:10:37 CST 2020
     */
    long countByExample(SjUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sj_user
     *
     * @mbg.generated Wed Jul 29 17:10:37 CST 2020
     */
    int deleteByExample(SjUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sj_user
     *
     * @mbg.generated Wed Jul 29 17:10:37 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sj_user
     *
     * @mbg.generated Wed Jul 29 17:10:37 CST 2020
     */
    int insert(SjUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sj_user
     *
     * @mbg.generated Wed Jul 29 17:10:37 CST 2020
     */
    int insertSelective(SjUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sj_user
     *
     * @mbg.generated Wed Jul 29 17:10:37 CST 2020
     */
    List<SjUser> selectByExample(SjUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sj_user
     *
     * @mbg.generated Wed Jul 29 17:10:37 CST 2020
     */
    SjUser selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sj_user
     *
     * @mbg.generated Wed Jul 29 17:10:37 CST 2020
     */
    int updateByExampleSelective(@Param("record") SjUser record, @Param("example") SjUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sj_user
     *
     * @mbg.generated Wed Jul 29 17:10:37 CST 2020
     */
    int updateByExample(@Param("record") SjUser record, @Param("example") SjUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sj_user
     *
     * @mbg.generated Wed Jul 29 17:10:37 CST 2020
     */
    int updateByPrimaryKeySelective(SjUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sj_user
     *
     * @mbg.generated Wed Jul 29 17:10:37 CST 2020
     */
    int updateByPrimaryKey(SjUser record);
}