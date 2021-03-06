package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.HfEvaluateCollect;
import com.hanfu.product.center.model.HfEvaluateCollectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HfEvaluateCollectMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_evaluate_collect
     *
     * @mbg.generated Mon Jul 27 10:25:46 CST 2020
     */
    long countByExample(HfEvaluateCollectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_evaluate_collect
     *
     * @mbg.generated Mon Jul 27 10:25:46 CST 2020
     */
    int deleteByExample(HfEvaluateCollectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_evaluate_collect
     *
     * @mbg.generated Mon Jul 27 10:25:46 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_evaluate_collect
     *
     * @mbg.generated Mon Jul 27 10:25:46 CST 2020
     */
    int insert(HfEvaluateCollect record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_evaluate_collect
     *
     * @mbg.generated Mon Jul 27 10:25:46 CST 2020
     */
    int insertSelective(HfEvaluateCollect record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_evaluate_collect
     *
     * @mbg.generated Mon Jul 27 10:25:46 CST 2020
     */
    List<HfEvaluateCollect> selectByExample(HfEvaluateCollectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_evaluate_collect
     *
     * @mbg.generated Mon Jul 27 10:25:46 CST 2020
     */
    HfEvaluateCollect selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_evaluate_collect
     *
     * @mbg.generated Mon Jul 27 10:25:46 CST 2020
     */
    int updateByExampleSelective(@Param("record") HfEvaluateCollect record, @Param("example") HfEvaluateCollectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_evaluate_collect
     *
     * @mbg.generated Mon Jul 27 10:25:46 CST 2020
     */
    int updateByExample(@Param("record") HfEvaluateCollect record, @Param("example") HfEvaluateCollectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_evaluate_collect
     *
     * @mbg.generated Mon Jul 27 10:25:46 CST 2020
     */
    int updateByPrimaryKeySelective(HfEvaluateCollect record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_evaluate_collect
     *
     * @mbg.generated Mon Jul 27 10:25:46 CST 2020
     */
    int updateByPrimaryKey(HfEvaluateCollect record);
}