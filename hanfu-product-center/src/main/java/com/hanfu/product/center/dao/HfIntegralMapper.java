package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.HfIntegral;
import com.hanfu.product.center.model.HfIntegralExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HfIntegralMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_integral
     *
     * @mbg.generated Mon Apr 13 13:58:27 CST 2020
     */
    long countByExample(HfIntegralExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_integral
     *
     * @mbg.generated Mon Apr 13 13:58:27 CST 2020
     */
    int deleteByExample(HfIntegralExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_integral
     *
     * @mbg.generated Mon Apr 13 13:58:27 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_integral
     *
     * @mbg.generated Mon Apr 13 13:58:27 CST 2020
     */
    int insert(HfIntegral record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_integral
     *
     * @mbg.generated Mon Apr 13 13:58:27 CST 2020
     */
    int insertSelective(HfIntegral record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_integral
     *
     * @mbg.generated Mon Apr 13 13:58:27 CST 2020
     */
    List<HfIntegral> selectByExample(HfIntegralExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_integral
     *
     * @mbg.generated Mon Apr 13 13:58:27 CST 2020
     */
    HfIntegral selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_integral
     *
     * @mbg.generated Mon Apr 13 13:58:27 CST 2020
     */
    int updateByExampleSelective(@Param("record") HfIntegral record, @Param("example") HfIntegralExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_integral
     *
     * @mbg.generated Mon Apr 13 13:58:27 CST 2020
     */
    int updateByExample(@Param("record") HfIntegral record, @Param("example") HfIntegralExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_integral
     *
     * @mbg.generated Mon Apr 13 13:58:27 CST 2020
     */
    int updateByPrimaryKeySelective(HfIntegral record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_integral
     *
     * @mbg.generated Mon Apr 13 13:58:27 CST 2020
     */
    int updateByPrimaryKey(HfIntegral record);
}