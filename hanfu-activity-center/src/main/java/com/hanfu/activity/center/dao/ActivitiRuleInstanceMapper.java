package com.hanfu.activity.center.dao;

import com.hanfu.activity.center.model.ActivitiRuleInstance;
import com.hanfu.activity.center.model.ActivitiRuleInstanceExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ActivitiRuleInstanceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_rule_instance
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    long countByExample(ActivitiRuleInstanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_rule_instance
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    int deleteByExample(ActivitiRuleInstanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_rule_instance
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_rule_instance
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    int insert(ActivitiRuleInstance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_rule_instance
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    int insertSelective(ActivitiRuleInstance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_rule_instance
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    List<ActivitiRuleInstance> selectByExample(ActivitiRuleInstanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_rule_instance
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    ActivitiRuleInstance selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_rule_instance
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    int updateByExampleSelective(@Param("record") ActivitiRuleInstance record, @Param("example") ActivitiRuleInstanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_rule_instance
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    int updateByExample(@Param("record") ActivitiRuleInstance record, @Param("example") ActivitiRuleInstanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_rule_instance
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    int updateByPrimaryKeySelective(ActivitiRuleInstance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_rule_instance
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    int updateByPrimaryKey(ActivitiRuleInstance record);
}