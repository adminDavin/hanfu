package com.hanfu.dichan.center.dao;

import com.hanfu.dichan.center.model.DcCompany;
import com.hanfu.dichan.center.model.DcCompanyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DcCompanyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_company
     *
     * @mbg.generated Mon Jul 06 14:04:21 CST 2020
     */
    long countByExample(DcCompanyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_company
     *
     * @mbg.generated Mon Jul 06 14:04:21 CST 2020
     */
    int deleteByExample(DcCompanyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_company
     *
     * @mbg.generated Mon Jul 06 14:04:21 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_company
     *
     * @mbg.generated Mon Jul 06 14:04:21 CST 2020
     */
    int insert(DcCompany record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_company
     *
     * @mbg.generated Mon Jul 06 14:04:21 CST 2020
     */
    int insertSelective(DcCompany record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_company
     *
     * @mbg.generated Mon Jul 06 14:04:21 CST 2020
     */
    List<DcCompany> selectByExample(DcCompanyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_company
     *
     * @mbg.generated Mon Jul 06 14:04:21 CST 2020
     */
    DcCompany selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_company
     *
     * @mbg.generated Mon Jul 06 14:04:21 CST 2020
     */
    int updateByExampleSelective(@Param("record") DcCompany record, @Param("example") DcCompanyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_company
     *
     * @mbg.generated Mon Jul 06 14:04:21 CST 2020
     */
    int updateByExample(@Param("record") DcCompany record, @Param("example") DcCompanyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_company
     *
     * @mbg.generated Mon Jul 06 14:04:21 CST 2020
     */
    int updateByPrimaryKeySelective(DcCompany record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_company
     *
     * @mbg.generated Mon Jul 06 14:04:21 CST 2020
     */
    int updateByPrimaryKey(DcCompany record);
}