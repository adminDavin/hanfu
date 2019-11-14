package com.hanfu.activity.center.dao;

import com.hanfu.activity.center.model.ActivitiStrategy;
import com.hanfu.activity.center.model.ActivitiStrategyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActivitiStrategyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_strategy
     *
     * @mbg.generated Wed Nov 13 07:21:45 CST 2019
     */
    long countByExample(ActivitiStrategyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_strategy
     *
     * @mbg.generated Wed Nov 13 07:21:45 CST 2019
     */
    int deleteByExample(ActivitiStrategyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_strategy
     *
     * @mbg.generated Wed Nov 13 07:21:45 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_strategy
     *
     * @mbg.generated Wed Nov 13 07:21:45 CST 2019
     */
    int insert(ActivitiStrategy record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_strategy
     *
     * @mbg.generated Wed Nov 13 07:21:45 CST 2019
     */
    int insertSelective(ActivitiStrategy record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_strategy
     *
     * @mbg.generated Wed Nov 13 07:21:45 CST 2019
     */
    List<ActivitiStrategy> selectByExample(ActivitiStrategyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_strategy
     *
     * @mbg.generated Wed Nov 13 07:21:45 CST 2019
     */
    ActivitiStrategy selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_strategy
     *
     * @mbg.generated Wed Nov 13 07:21:45 CST 2019
     */
    int updateByExampleSelective(@Param("record") ActivitiStrategy record, @Param("example") ActivitiStrategyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_strategy
     *
     * @mbg.generated Wed Nov 13 07:21:45 CST 2019
     */
    int updateByExample(@Param("record") ActivitiStrategy record, @Param("example") ActivitiStrategyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_strategy
     *
     * @mbg.generated Wed Nov 13 07:21:45 CST 2019
     */
    int updateByPrimaryKeySelective(ActivitiStrategy record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_strategy
     *
     * @mbg.generated Wed Nov 13 07:21:45 CST 2019
     */
    int updateByPrimaryKey(ActivitiStrategy record);
}