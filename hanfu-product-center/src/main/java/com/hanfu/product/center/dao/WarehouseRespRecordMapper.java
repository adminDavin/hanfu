package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.WarehouseRespRecord;
import com.hanfu.product.center.model.WarehouseRespRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WarehouseRespRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warehouse_resp_record
     *
     * @mbg.generated Thu May 07 09:56:19 CST 2020
     */
    long countByExample(WarehouseRespRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warehouse_resp_record
     *
     * @mbg.generated Thu May 07 09:56:19 CST 2020
     */
    int deleteByExample(WarehouseRespRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warehouse_resp_record
     *
     * @mbg.generated Thu May 07 09:56:19 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warehouse_resp_record
     *
     * @mbg.generated Thu May 07 09:56:19 CST 2020
     */
    int insert(WarehouseRespRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warehouse_resp_record
     *
     * @mbg.generated Thu May 07 09:56:19 CST 2020
     */
    int insertSelective(WarehouseRespRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warehouse_resp_record
     *
     * @mbg.generated Thu May 07 09:56:19 CST 2020
     */
    List<WarehouseRespRecord> selectByExample(WarehouseRespRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warehouse_resp_record
     *
     * @mbg.generated Thu May 07 09:56:19 CST 2020
     */
    WarehouseRespRecord selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warehouse_resp_record
     *
     * @mbg.generated Thu May 07 09:56:19 CST 2020
     */
    int updateByExampleSelective(@Param("record") WarehouseRespRecord record, @Param("example") WarehouseRespRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warehouse_resp_record
     *
     * @mbg.generated Thu May 07 09:56:19 CST 2020
     */
    int updateByExample(@Param("record") WarehouseRespRecord record, @Param("example") WarehouseRespRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warehouse_resp_record
     *
     * @mbg.generated Thu May 07 09:56:19 CST 2020
     */
    int updateByPrimaryKeySelective(WarehouseRespRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warehouse_resp_record
     *
     * @mbg.generated Thu May 07 09:56:19 CST 2020
     */
    int updateByPrimaryKey(WarehouseRespRecord record);
}