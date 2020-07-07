package com.hanfu.dichan.center.dao;

import com.hanfu.dichan.center.model.DcCategory;
import com.hanfu.dichan.center.model.DcCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DcCategoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_category
     *
     * @mbg.generated Tue Jul 07 09:40:36 CST 2020
     */
    long countByExample(DcCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_category
     *
     * @mbg.generated Tue Jul 07 09:40:36 CST 2020
     */
    int deleteByExample(DcCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_category
     *
     * @mbg.generated Tue Jul 07 09:40:36 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_category
     *
     * @mbg.generated Tue Jul 07 09:40:36 CST 2020
     */
    int insert(DcCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_category
     *
     * @mbg.generated Tue Jul 07 09:40:36 CST 2020
     */
    int insertSelective(DcCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_category
     *
     * @mbg.generated Tue Jul 07 09:40:36 CST 2020
     */
    List<DcCategory> selectByExample(DcCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_category
     *
     * @mbg.generated Tue Jul 07 09:40:36 CST 2020
     */
    DcCategory selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_category
     *
     * @mbg.generated Tue Jul 07 09:40:36 CST 2020
     */
    int updateByExampleSelective(@Param("record") DcCategory record, @Param("example") DcCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_category
     *
     * @mbg.generated Tue Jul 07 09:40:36 CST 2020
     */
    int updateByExample(@Param("record") DcCategory record, @Param("example") DcCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_category
     *
     * @mbg.generated Tue Jul 07 09:40:36 CST 2020
     */
    int updateByPrimaryKeySelective(DcCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_category
     *
     * @mbg.generated Tue Jul 07 09:40:36 CST 2020
     */
    int updateByPrimaryKey(DcCategory record);
}