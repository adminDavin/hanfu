package com.hanfu.order.center.dao;

import com.hanfu.order.center.model.HfActivityProduct;
import com.hanfu.order.center.model.HfActivityProductExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HfActivityProductMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_activity_product
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    long countByExample(HfActivityProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_activity_product
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    int deleteByExample(HfActivityProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_activity_product
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_activity_product
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    int insert(HfActivityProduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_activity_product
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    int insertSelective(HfActivityProduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_activity_product
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    List<HfActivityProduct> selectByExample(HfActivityProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_activity_product
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    HfActivityProduct selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_activity_product
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    int updateByExampleSelective(@Param("record") HfActivityProduct record, @Param("example") HfActivityProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_activity_product
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    int updateByExample(@Param("record") HfActivityProduct record, @Param("example") HfActivityProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_activity_product
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    int updateByPrimaryKeySelective(HfActivityProduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_activity_product
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    int updateByPrimaryKey(HfActivityProduct record);
}