package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.WarehouseApplyGood;
import com.hanfu.product.center.model.WarehouseApplyGoodExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WarehouseApplyGoodMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warehouse_apply_good
     *
     * @mbg.generated Wed May 13 16:08:19 CST 2020
     */
    long countByExample(WarehouseApplyGoodExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warehouse_apply_good
     *
     * @mbg.generated Wed May 13 16:08:19 CST 2020
     */
    int deleteByExample(WarehouseApplyGoodExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warehouse_apply_good
     *
     * @mbg.generated Wed May 13 16:08:19 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warehouse_apply_good
     *
     * @mbg.generated Wed May 13 16:08:19 CST 2020
     */
    int insert(WarehouseApplyGood record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warehouse_apply_good
     *
     * @mbg.generated Wed May 13 16:08:19 CST 2020
     */
    int insertSelective(WarehouseApplyGood record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warehouse_apply_good
     *
     * @mbg.generated Wed May 13 16:08:19 CST 2020
     */
    List<WarehouseApplyGood> selectByExample(WarehouseApplyGoodExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warehouse_apply_good
     *
     * @mbg.generated Wed May 13 16:08:19 CST 2020
     */
    WarehouseApplyGood selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warehouse_apply_good
     *
     * @mbg.generated Wed May 13 16:08:19 CST 2020
     */
    int updateByExampleSelective(@Param("record") WarehouseApplyGood record, @Param("example") WarehouseApplyGoodExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warehouse_apply_good
     *
     * @mbg.generated Wed May 13 16:08:19 CST 2020
     */
    int updateByExample(@Param("record") WarehouseApplyGood record, @Param("example") WarehouseApplyGoodExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warehouse_apply_good
     *
     * @mbg.generated Wed May 13 16:08:19 CST 2020
     */
    int updateByPrimaryKeySelective(WarehouseApplyGood record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warehouse_apply_good
     *
     * @mbg.generated Wed May 13 16:08:19 CST 2020
     */
    int updateByPrimaryKey(WarehouseApplyGood record);
}