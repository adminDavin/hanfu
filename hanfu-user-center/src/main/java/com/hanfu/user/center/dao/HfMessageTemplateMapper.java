package com.hanfu.user.center.dao;

import com.hanfu.user.center.model.HfMessageTemplate;
import com.hanfu.user.center.model.HfMessageTemplateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HfMessageTemplateMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_message_template
     *
     * @mbg.generated Fri May 22 14:36:55 CST 2020
     */
    long countByExample(HfMessageTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_message_template
     *
     * @mbg.generated Fri May 22 14:36:55 CST 2020
     */
    int deleteByExample(HfMessageTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_message_template
     *
     * @mbg.generated Fri May 22 14:36:55 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_message_template
     *
     * @mbg.generated Fri May 22 14:36:55 CST 2020
     */
    int insert(HfMessageTemplate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_message_template
     *
     * @mbg.generated Fri May 22 14:36:55 CST 2020
     */
    int insertSelective(HfMessageTemplate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_message_template
     *
     * @mbg.generated Fri May 22 14:36:55 CST 2020
     */
    List<HfMessageTemplate> selectByExample(HfMessageTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_message_template
     *
     * @mbg.generated Fri May 22 14:36:55 CST 2020
     */
    HfMessageTemplate selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_message_template
     *
     * @mbg.generated Fri May 22 14:36:55 CST 2020
     */
    int updateByExampleSelective(@Param("record") HfMessageTemplate record, @Param("example") HfMessageTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_message_template
     *
     * @mbg.generated Fri May 22 14:36:55 CST 2020
     */
    int updateByExample(@Param("record") HfMessageTemplate record, @Param("example") HfMessageTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_message_template
     *
     * @mbg.generated Fri May 22 14:36:55 CST 2020
     */
    int updateByPrimaryKeySelective(HfMessageTemplate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_message_template
     *
     * @mbg.generated Fri May 22 14:36:55 CST 2020
     */
    int updateByPrimaryKey(HfMessageTemplate record);
}