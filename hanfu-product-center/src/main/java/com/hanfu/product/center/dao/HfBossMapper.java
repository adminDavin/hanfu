package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.HfBoss;
import com.hanfu.product.center.model.HfBossExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface HfBossMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_boss
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    long countByExample(HfBossExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_boss
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    int deleteByExample(HfBossExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_boss
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_boss
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    int insert(HfBoss record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_boss
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    int insertSelective(HfBoss record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_boss
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    List<HfBoss> selectByExample(HfBossExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_boss
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    HfBoss selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_boss
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    int updateByExampleSelective(@Param("record") HfBoss record, @Param("example") HfBossExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_boss
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    int updateByExample(@Param("record") HfBoss record, @Param("example") HfBossExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_boss
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    int updateByPrimaryKeySelective(HfBoss record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_boss
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    int updateByPrimaryKey(HfBoss record);
}