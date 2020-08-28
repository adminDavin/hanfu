package com.hanfu.activity.center.dao;

import com.hanfu.activity.center.model.Activity;
import com.hanfu.activity.center.model.ActivityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActivityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity
     *
     * @mbg.generated Fri Aug 28 11:13:19 CST 2020
     */
    long countByExample(ActivityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity
     *
     * @mbg.generated Fri Aug 28 11:13:19 CST 2020
     */
    int deleteByExample(ActivityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity
     *
     * @mbg.generated Fri Aug 28 11:13:19 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity
     *
     * @mbg.generated Fri Aug 28 11:13:19 CST 2020
     */
    int insert(Activity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity
     *
     * @mbg.generated Fri Aug 28 11:13:19 CST 2020
     */
    int insertSelective(Activity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity
     *
     * @mbg.generated Fri Aug 28 11:13:19 CST 2020
     */
    List<Activity> selectByExample(ActivityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity
     *
     * @mbg.generated Fri Aug 28 11:13:19 CST 2020
     */
    Activity selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity
     *
     * @mbg.generated Fri Aug 28 11:13:19 CST 2020
     */
    int updateByExampleSelective(@Param("record") Activity record, @Param("example") ActivityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity
     *
     * @mbg.generated Fri Aug 28 11:13:19 CST 2020
     */
    int updateByExample(@Param("record") Activity record, @Param("example") ActivityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity
     *
     * @mbg.generated Fri Aug 28 11:13:19 CST 2020
     */
    int updateByPrimaryKeySelective(Activity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity
     *
     * @mbg.generated Fri Aug 28 11:13:19 CST 2020
     */
    int updateByPrimaryKey(Activity record);
}