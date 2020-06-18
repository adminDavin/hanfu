package com.hanfu.user.center.dao;

import com.hanfu.user.center.model.HfMessageInstance;
import com.hanfu.user.center.model.HfMessageInstanceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HfMessageInstanceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_message_instance
     *
     * @mbg.generated Thu Jun 18 14:32:13 CST 2020
     */
    long countByExample(HfMessageInstanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_message_instance
     *
     * @mbg.generated Thu Jun 18 14:32:13 CST 2020
     */
    int deleteByExample(HfMessageInstanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_message_instance
     *
     * @mbg.generated Thu Jun 18 14:32:13 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_message_instance
     *
     * @mbg.generated Thu Jun 18 14:32:13 CST 2020
     */
    int insert(HfMessageInstance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_message_instance
     *
     * @mbg.generated Thu Jun 18 14:32:13 CST 2020
     */
    int insertSelective(HfMessageInstance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_message_instance
     *
     * @mbg.generated Thu Jun 18 14:32:13 CST 2020
     */
    List<HfMessageInstance> selectByExample(HfMessageInstanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_message_instance
     *
     * @mbg.generated Thu Jun 18 14:32:13 CST 2020
     */
    HfMessageInstance selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_message_instance
     *
     * @mbg.generated Thu Jun 18 14:32:13 CST 2020
     */
    int updateByExampleSelective(@Param("record") HfMessageInstance record, @Param("example") HfMessageInstanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_message_instance
     *
     * @mbg.generated Thu Jun 18 14:32:13 CST 2020
     */
    int updateByExample(@Param("record") HfMessageInstance record, @Param("example") HfMessageInstanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_message_instance
     *
     * @mbg.generated Thu Jun 18 14:32:13 CST 2020
     */
    int updateByPrimaryKeySelective(HfMessageInstance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_message_instance
     *
     * @mbg.generated Thu Jun 18 14:32:13 CST 2020
     */
    int updateByPrimaryKey(HfMessageInstance record);
}