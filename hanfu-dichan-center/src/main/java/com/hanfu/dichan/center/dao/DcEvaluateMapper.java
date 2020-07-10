package com.hanfu.dichan.center.dao;

import com.hanfu.dichan.center.model.DcEvaluate;
import com.hanfu.dichan.center.model.DcEvaluateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DcEvaluateMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_evaluate
     *
     * @mbg.generated Fri Jul 10 16:00:27 CST 2020
     */
    long countByExample(DcEvaluateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_evaluate
     *
     * @mbg.generated Fri Jul 10 16:00:27 CST 2020
     */
    int deleteByExample(DcEvaluateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_evaluate
     *
     * @mbg.generated Fri Jul 10 16:00:27 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_evaluate
     *
     * @mbg.generated Fri Jul 10 16:00:27 CST 2020
     */
    int insert(DcEvaluate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_evaluate
     *
     * @mbg.generated Fri Jul 10 16:00:27 CST 2020
     */
    int insertSelective(DcEvaluate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_evaluate
     *
     * @mbg.generated Fri Jul 10 16:00:27 CST 2020
     */
    List<DcEvaluate> selectByExample(DcEvaluateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_evaluate
     *
     * @mbg.generated Fri Jul 10 16:00:27 CST 2020
     */
    DcEvaluate selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_evaluate
     *
     * @mbg.generated Fri Jul 10 16:00:27 CST 2020
     */
    int updateByExampleSelective(@Param("record") DcEvaluate record, @Param("example") DcEvaluateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_evaluate
     *
     * @mbg.generated Fri Jul 10 16:00:27 CST 2020
     */
    int updateByExample(@Param("record") DcEvaluate record, @Param("example") DcEvaluateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_evaluate
     *
     * @mbg.generated Fri Jul 10 16:00:27 CST 2020
     */
    int updateByPrimaryKeySelective(DcEvaluate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_evaluate
     *
     * @mbg.generated Fri Jul 10 16:00:27 CST 2020
     */
    int updateByPrimaryKey(DcEvaluate record);
}