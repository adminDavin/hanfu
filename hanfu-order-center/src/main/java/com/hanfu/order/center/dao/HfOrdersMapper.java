package com.hanfu.order.center.dao;

import com.hanfu.order.center.model.HfOrders;
import com.hanfu.order.center.model.HfOrdersExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HfOrdersMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_orders
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    long countByExample(HfOrdersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_orders
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    int deleteByExample(HfOrdersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_orders
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_orders
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    int insert(HfOrders record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_orders
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    int insertSelective(HfOrders record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_orders
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    List<HfOrders> selectByExample(HfOrdersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_orders
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    HfOrders selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_orders
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    int updateByExampleSelective(@Param("record") HfOrders record, @Param("example") HfOrdersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_orders
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    int updateByExample(@Param("record") HfOrders record, @Param("example") HfOrdersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_orders
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    int updateByPrimaryKeySelective(HfOrders record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_orders
     *
     * @mbg.generated Sat Feb 01 13:21:26 CST 2020
     */
    int updateByPrimaryKey(HfOrders record);
}