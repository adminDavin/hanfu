package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.HfVisitsRecord;
import com.hanfu.product.center.model.HfVisitsRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HfVisitsRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_visits_record
     *
     * @mbg.generated Tue Jun 23 09:29:13 CST 2020
     */
    long countByExample(HfVisitsRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_visits_record
     *
     * @mbg.generated Tue Jun 23 09:29:13 CST 2020
     */
    int deleteByExample(HfVisitsRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_visits_record
     *
     * @mbg.generated Tue Jun 23 09:29:13 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_visits_record
     *
     * @mbg.generated Tue Jun 23 09:29:13 CST 2020
     */
    int insert(HfVisitsRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_visits_record
     *
     * @mbg.generated Tue Jun 23 09:29:13 CST 2020
     */
    int insertSelective(HfVisitsRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_visits_record
     *
     * @mbg.generated Tue Jun 23 09:29:13 CST 2020
     */
    List<HfVisitsRecord> selectByExample(HfVisitsRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_visits_record
     *
     * @mbg.generated Tue Jun 23 09:29:13 CST 2020
     */
    HfVisitsRecord selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_visits_record
     *
     * @mbg.generated Tue Jun 23 09:29:13 CST 2020
     */
    int updateByExampleSelective(@Param("record") HfVisitsRecord record, @Param("example") HfVisitsRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_visits_record
     *
     * @mbg.generated Tue Jun 23 09:29:13 CST 2020
     */
    int updateByExample(@Param("record") HfVisitsRecord record, @Param("example") HfVisitsRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_visits_record
     *
     * @mbg.generated Tue Jun 23 09:29:13 CST 2020
     */
    int updateByPrimaryKeySelective(HfVisitsRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_visits_record
     *
     * @mbg.generated Tue Jun 23 09:29:13 CST 2020
     */
    int updateByPrimaryKey(HfVisitsRecord record);
}