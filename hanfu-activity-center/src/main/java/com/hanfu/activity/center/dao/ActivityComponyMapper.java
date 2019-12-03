package com.hanfu.activity.center.dao;

import com.hanfu.activity.center.model.ActivityCompony;
import com.hanfu.activity.center.model.ActivityComponyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActivityComponyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_compony
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    long countByExample(ActivityComponyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_compony
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    int deleteByExample(ActivityComponyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_compony
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_compony
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    int insert(ActivityCompony record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_compony
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    int insertSelective(ActivityCompony record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_compony
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    List<ActivityCompony> selectByExample(ActivityComponyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_compony
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    ActivityCompony selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_compony
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    int updateByExampleSelective(@Param("record") ActivityCompony record, @Param("example") ActivityComponyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_compony
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    int updateByExample(@Param("record") ActivityCompony record, @Param("example") ActivityComponyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_compony
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    int updateByPrimaryKeySelective(ActivityCompony record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_compony
     *
     * @mbg.generated Tue Dec 03 07:29:15 CST 2019
     */
    int updateByPrimaryKey(ActivityCompony record);
}