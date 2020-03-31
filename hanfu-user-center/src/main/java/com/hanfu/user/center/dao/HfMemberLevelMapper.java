package com.hanfu.user.center.dao;

import com.hanfu.user.center.model.HfMemberLevel;
import com.hanfu.user.center.model.HfMemberLevelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HfMemberLevelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_member_level
     *
     * @mbg.generated Tue Mar 31 11:53:11 CST 2020
     */
    long countByExample(HfMemberLevelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_member_level
     *
     * @mbg.generated Tue Mar 31 11:53:11 CST 2020
     */
    int deleteByExample(HfMemberLevelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_member_level
     *
     * @mbg.generated Tue Mar 31 11:53:11 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_member_level
     *
     * @mbg.generated Tue Mar 31 11:53:11 CST 2020
     */
    int insert(HfMemberLevel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_member_level
     *
     * @mbg.generated Tue Mar 31 11:53:11 CST 2020
     */
    int insertSelective(HfMemberLevel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_member_level
     *
     * @mbg.generated Tue Mar 31 11:53:11 CST 2020
     */
    List<HfMemberLevel> selectByExample(HfMemberLevelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_member_level
     *
     * @mbg.generated Tue Mar 31 11:53:11 CST 2020
     */
    HfMemberLevel selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_member_level
     *
     * @mbg.generated Tue Mar 31 11:53:11 CST 2020
     */
    int updateByExampleSelective(@Param("record") HfMemberLevel record, @Param("example") HfMemberLevelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_member_level
     *
     * @mbg.generated Tue Mar 31 11:53:11 CST 2020
     */
    int updateByExample(@Param("record") HfMemberLevel record, @Param("example") HfMemberLevelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_member_level
     *
     * @mbg.generated Tue Mar 31 11:53:11 CST 2020
     */
    int updateByPrimaryKeySelective(HfMemberLevel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_member_level
     *
     * @mbg.generated Tue Mar 31 11:53:11 CST 2020
     */
    int updateByPrimaryKey(HfMemberLevel record);
}