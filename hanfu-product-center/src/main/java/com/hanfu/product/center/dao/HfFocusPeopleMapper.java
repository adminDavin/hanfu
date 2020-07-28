package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.HfFocusPeople;
import com.hanfu.product.center.model.HfFocusPeopleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HfFocusPeopleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_focus_people
     *
     * @mbg.generated Sat Jul 25 09:28:22 CST 2020
     */
    long countByExample(HfFocusPeopleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_focus_people
     *
     * @mbg.generated Sat Jul 25 09:28:22 CST 2020
     */
    int deleteByExample(HfFocusPeopleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_focus_people
     *
     * @mbg.generated Sat Jul 25 09:28:22 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_focus_people
     *
     * @mbg.generated Sat Jul 25 09:28:22 CST 2020
     */
    int insert(HfFocusPeople record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_focus_people
     *
     * @mbg.generated Sat Jul 25 09:28:22 CST 2020
     */
    int insertSelective(HfFocusPeople record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_focus_people
     *
     * @mbg.generated Sat Jul 25 09:28:22 CST 2020
     */
    List<HfFocusPeople> selectByExample(HfFocusPeopleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_focus_people
     *
     * @mbg.generated Sat Jul 25 09:28:22 CST 2020
     */
    HfFocusPeople selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_focus_people
     *
     * @mbg.generated Sat Jul 25 09:28:22 CST 2020
     */
    int updateByExampleSelective(@Param("record") HfFocusPeople record, @Param("example") HfFocusPeopleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_focus_people
     *
     * @mbg.generated Sat Jul 25 09:28:22 CST 2020
     */
    int updateByExample(@Param("record") HfFocusPeople record, @Param("example") HfFocusPeopleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_focus_people
     *
     * @mbg.generated Sat Jul 25 09:28:22 CST 2020
     */
    int updateByPrimaryKeySelective(HfFocusPeople record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_focus_people
     *
     * @mbg.generated Sat Jul 25 09:28:22 CST 2020
     */
    int updateByPrimaryKey(HfFocusPeople record);
}