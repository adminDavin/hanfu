package com.hanfu.dichan.center.dao;

import com.hanfu.dichan.center.model.DcCategoryDetail;
import com.hanfu.dichan.center.model.DcCategoryDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DcCategoryDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_category_detail
     *
     * @mbg.generated Fri Jul 10 16:00:27 CST 2020
     */
    long countByExample(DcCategoryDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_category_detail
     *
     * @mbg.generated Fri Jul 10 16:00:27 CST 2020
     */
    int deleteByExample(DcCategoryDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_category_detail
     *
     * @mbg.generated Fri Jul 10 16:00:27 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_category_detail
     *
     * @mbg.generated Fri Jul 10 16:00:27 CST 2020
     */
    int insert(DcCategoryDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_category_detail
     *
     * @mbg.generated Fri Jul 10 16:00:27 CST 2020
     */
    int insertSelective(DcCategoryDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_category_detail
     *
     * @mbg.generated Fri Jul 10 16:00:27 CST 2020
     */
    List<DcCategoryDetail> selectByExample(DcCategoryDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_category_detail
     *
     * @mbg.generated Fri Jul 10 16:00:27 CST 2020
     */
    DcCategoryDetail selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_category_detail
     *
     * @mbg.generated Fri Jul 10 16:00:27 CST 2020
     */
    int updateByExampleSelective(@Param("record") DcCategoryDetail record, @Param("example") DcCategoryDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_category_detail
     *
     * @mbg.generated Fri Jul 10 16:00:27 CST 2020
     */
    int updateByExample(@Param("record") DcCategoryDetail record, @Param("example") DcCategoryDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_category_detail
     *
     * @mbg.generated Fri Jul 10 16:00:27 CST 2020
     */
    int updateByPrimaryKeySelective(DcCategoryDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_category_detail
     *
     * @mbg.generated Fri Jul 10 16:00:27 CST 2020
     */
    int updateByPrimaryKey(DcCategoryDetail record);
}