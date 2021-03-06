package com.hanfu.dichan.center.dao;

import com.hanfu.dichan.center.model.DcPraiseRecord;
import com.hanfu.dichan.center.model.DcPraiseRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DcPraiseRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_praise_record
     *
     * @mbg.generated Mon Jul 13 17:45:58 CST 2020
     */
    long countByExample(DcPraiseRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_praise_record
     *
     * @mbg.generated Mon Jul 13 17:45:58 CST 2020
     */
    int deleteByExample(DcPraiseRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_praise_record
     *
     * @mbg.generated Mon Jul 13 17:45:58 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_praise_record
     *
     * @mbg.generated Mon Jul 13 17:45:58 CST 2020
     */
    int insert(DcPraiseRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_praise_record
     *
     * @mbg.generated Mon Jul 13 17:45:58 CST 2020
     */
    int insertSelective(DcPraiseRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_praise_record
     *
     * @mbg.generated Mon Jul 13 17:45:58 CST 2020
     */
    List<DcPraiseRecord> selectByExample(DcPraiseRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_praise_record
     *
     * @mbg.generated Mon Jul 13 17:45:58 CST 2020
     */
    DcPraiseRecord selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_praise_record
     *
     * @mbg.generated Mon Jul 13 17:45:58 CST 2020
     */
    int updateByExampleSelective(@Param("record") DcPraiseRecord record, @Param("example") DcPraiseRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_praise_record
     *
     * @mbg.generated Mon Jul 13 17:45:58 CST 2020
     */
    int updateByExample(@Param("record") DcPraiseRecord record, @Param("example") DcPraiseRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_praise_record
     *
     * @mbg.generated Mon Jul 13 17:45:58 CST 2020
     */
    int updateByPrimaryKeySelective(DcPraiseRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_praise_record
     *
     * @mbg.generated Mon Jul 13 17:45:58 CST 2020
     */
    int updateByPrimaryKey(DcPraiseRecord record);
}