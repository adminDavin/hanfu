package com.hanfu.order.center.dao;

import com.hanfu.order.center.model.hfOrderAddress;
import com.hanfu.order.center.model.hfOrderAddressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface hfOrderAddressMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_address
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    long countByExample(hfOrderAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_address
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    int deleteByExample(hfOrderAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_address
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_address
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    int insert(hfOrderAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_address
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    int insertSelective(hfOrderAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_address
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    List<hfOrderAddress> selectByExample(hfOrderAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_address
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    hfOrderAddress selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_address
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    int updateByExampleSelective(@Param("record") hfOrderAddress record, @Param("example") hfOrderAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_address
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    int updateByExample(@Param("record") hfOrderAddress record, @Param("example") hfOrderAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_address
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    int updateByPrimaryKeySelective(hfOrderAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_address
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    int updateByPrimaryKey(hfOrderAddress record);
}