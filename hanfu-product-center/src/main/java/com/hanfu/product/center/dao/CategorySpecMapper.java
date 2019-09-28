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
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    long countByExample(CategorySpecExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category_spec
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    int deleteByExample(CategorySpecExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category_spec
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category_spec
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    int insert(CategorySpec record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category_spec
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    int insertSelective(CategorySpec record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category_spec
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    List<CategorySpec> selectByExample(CategorySpecExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category_spec
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    CategorySpec selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category_spec
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    int updateByExampleSelective(@Param("record") CategorySpec record, @Param("example") CategorySpecExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category_spec
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    int updateByExample(@Param("record") CategorySpec record, @Param("example") CategorySpecExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category_spec
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    int updateByPrimaryKeySelective(CategorySpec record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table category_spec
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    int updateByPrimaryKey(CategorySpec record);
}