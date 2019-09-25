package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.HfCategory;
import com.hanfu.product.center.model.HfCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HfCategoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_category
     *
     * @mbg.generated Wed Sep 25 09:59:01 CST 2019
     */
    long countByExample(HfCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_category
     *
     * @mbg.generated Wed Sep 25 09:59:01 CST 2019
     */
    int deleteByExample(HfCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_category
     *
     * @mbg.generated Wed Sep 25 09:59:01 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_category
     *
     * @mbg.generated Wed Sep 25 09:59:01 CST 2019
     */
    int insert(HfCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_category
     *
     * @mbg.generated Wed Sep 25 09:59:01 CST 2019
     */
    int insertSelective(HfCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_category
     *
     * @mbg.generated Wed Sep 25 09:59:01 CST 2019
     */
    List<HfCategory> selectByExample(HfCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_category
     *
     * @mbg.generated Wed Sep 25 09:59:01 CST 2019
     */
    HfCategory selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_category
     *
     * @mbg.generated Wed Sep 25 09:59:01 CST 2019
     */
    int updateByExampleSelective(@Param("record") HfCategory record, @Param("example") HfCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_category
     *
     * @mbg.generated Wed Sep 25 09:59:01 CST 2019
     */
    int updateByExample(@Param("record") HfCategory record, @Param("example") HfCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_category
     *
     * @mbg.generated Wed Sep 25 09:59:01 CST 2019
     */
    int updateByPrimaryKeySelective(HfCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_category
     *
     * @mbg.generated Wed Sep 25 09:59:01 CST 2019
     */
    int updateByPrimaryKey(HfCategory record);
}