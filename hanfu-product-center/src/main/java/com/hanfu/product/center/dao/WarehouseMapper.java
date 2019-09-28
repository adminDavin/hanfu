package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.Warehouse;
import com.hanfu.product.center.model.WarehouseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WarehouseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_warehouse
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    long countByExample(WarehouseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_warehouse
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    int deleteByExample(WarehouseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_warehouse
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_warehouse
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    int insert(Warehouse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_warehouse
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    int insertSelective(Warehouse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_warehouse
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    List<Warehouse> selectByExample(WarehouseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_warehouse
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    Warehouse selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_warehouse
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    int updateByExampleSelective(@Param("record") Warehouse record, @Param("example") WarehouseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_warehouse
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    int updateByExample(@Param("record") Warehouse record, @Param("example") WarehouseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_warehouse
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    int updateByPrimaryKeySelective(Warehouse record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_warehouse
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    int updateByPrimaryKey(Warehouse record);
}