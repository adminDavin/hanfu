package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.SeniorityInstance;
import com.hanfu.product.center.model.SeniorityInstanceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SeniorityInstanceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seniority_instance
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    long countByExample(SeniorityInstanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seniority_instance
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    int deleteByExample(SeniorityInstanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seniority_instance
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seniority_instance
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    int insert(SeniorityInstance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seniority_instance
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    int insertSelective(SeniorityInstance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seniority_instance
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    List<SeniorityInstance> selectByExample(SeniorityInstanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seniority_instance
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    SeniorityInstance selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seniority_instance
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    int updateByExampleSelective(@Param("record") SeniorityInstance record, @Param("example") SeniorityInstanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seniority_instance
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    int updateByExample(@Param("record") SeniorityInstance record, @Param("example") SeniorityInstanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seniority_instance
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    int updateByPrimaryKeySelective(SeniorityInstance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table seniority_instance
     *
     * @mbg.generated Thu Jan 09 14:56:41 CST 2020
     */
    int updateByPrimaryKey(SeniorityInstance record);
}