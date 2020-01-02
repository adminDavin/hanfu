package com.hanfu.activity.center.dao;

import com.hanfu.activity.center.model.ActivityUserEvaluate;
import com.hanfu.activity.center.model.ActivityUserEvaluateExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ActivityUserEvaluateMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_user_evaluate
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    long countByExample(ActivityUserEvaluateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_user_evaluate
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    int deleteByExample(ActivityUserEvaluateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_user_evaluate
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_user_evaluate
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    int insert(ActivityUserEvaluate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_user_evaluate
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    int insertSelective(ActivityUserEvaluate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_user_evaluate
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    List<ActivityUserEvaluate> selectByExample(ActivityUserEvaluateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_user_evaluate
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    ActivityUserEvaluate selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_user_evaluate
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    int updateByExampleSelective(@Param("record") ActivityUserEvaluate record, @Param("example") ActivityUserEvaluateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_user_evaluate
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    int updateByExample(@Param("record") ActivityUserEvaluate record, @Param("example") ActivityUserEvaluateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_user_evaluate
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    int updateByPrimaryKeySelective(ActivityUserEvaluate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_user_evaluate
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    int updateByPrimaryKey(ActivityUserEvaluate record);
}