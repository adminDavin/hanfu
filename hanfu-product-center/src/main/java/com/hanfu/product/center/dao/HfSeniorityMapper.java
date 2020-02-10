package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.HfSeniority;
import com.hanfu.product.center.model.HfSeniorityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HfSeniorityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_seniority
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    long countByExample(HfSeniorityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_seniority
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    int deleteByExample(HfSeniorityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_seniority
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_seniority
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    int insert(HfSeniority record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_seniority
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    int insertSelective(HfSeniority record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_seniority
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    List<HfSeniority> selectByExample(HfSeniorityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_seniority
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    HfSeniority selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_seniority
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    int updateByExampleSelective(@Param("record") HfSeniority record, @Param("example") HfSeniorityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_seniority
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    int updateByExample(@Param("record") HfSeniority record, @Param("example") HfSeniorityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_seniority
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    int updateByPrimaryKeySelective(HfSeniority record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_seniority
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    int updateByPrimaryKey(HfSeniority record);
}