package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.Jurisdiction;
import com.hanfu.product.center.model.JurisdictionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JurisdictionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jurisdiction
     *
     * @mbg.generated Mon Jun 08 14:48:57 CST 2020
     */
    long countByExample(JurisdictionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jurisdiction
     *
     * @mbg.generated Mon Jun 08 14:48:57 CST 2020
     */
    int deleteByExample(JurisdictionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jurisdiction
     *
     * @mbg.generated Mon Jun 08 14:48:57 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jurisdiction
     *
     * @mbg.generated Mon Jun 08 14:48:57 CST 2020
     */
    int insert(Jurisdiction record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jurisdiction
     *
     * @mbg.generated Mon Jun 08 14:48:57 CST 2020
     */
    int insertSelective(Jurisdiction record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jurisdiction
     *
     * @mbg.generated Mon Jun 08 14:48:57 CST 2020
     */
    List<Jurisdiction> selectByExample(JurisdictionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jurisdiction
     *
     * @mbg.generated Mon Jun 08 14:48:57 CST 2020
     */
    Jurisdiction selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jurisdiction
     *
     * @mbg.generated Mon Jun 08 14:48:57 CST 2020
     */
    int updateByExampleSelective(@Param("record") Jurisdiction record, @Param("example") JurisdictionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jurisdiction
     *
     * @mbg.generated Mon Jun 08 14:48:57 CST 2020
     */
    int updateByExample(@Param("record") Jurisdiction record, @Param("example") JurisdictionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jurisdiction
     *
     * @mbg.generated Mon Jun 08 14:48:57 CST 2020
     */
    int updateByPrimaryKeySelective(Jurisdiction record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jurisdiction
     *
     * @mbg.generated Mon Jun 08 14:48:57 CST 2020
     */
    int updateByPrimaryKey(Jurisdiction record);
}