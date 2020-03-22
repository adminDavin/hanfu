package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.CategorySpec;
import com.hanfu.product.center.model.CategorySpecExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CategorySpecMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category_spec
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    long countByExample(CategorySpecExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category_spec
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    int deleteByExample(CategorySpecExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category_spec
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category_spec
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    int insert(CategorySpec record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category_spec
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    int insertSelective(CategorySpec record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category_spec
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    List<CategorySpec> selectByExample(CategorySpecExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category_spec
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    CategorySpec selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category_spec
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    int updateByExampleSelective(@Param("record") CategorySpec record, @Param("example") CategorySpecExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category_spec
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    int updateByExample(@Param("record") CategorySpec record, @Param("example") CategorySpecExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category_spec
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    int updateByPrimaryKeySelective(CategorySpec record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category_spec
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    int updateByPrimaryKey(CategorySpec record);
}