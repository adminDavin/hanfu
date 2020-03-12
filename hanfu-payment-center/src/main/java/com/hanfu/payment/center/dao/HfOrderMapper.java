package com.hanfu.payment.center.dao;

import com.hanfu.payment.center.model.HfOrder;
import com.hanfu.payment.center.model.HfOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HfOrderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order
     *
     * @mbg.generated Mon Feb 10 20:33:43 CST 2020
     */
    long countByExample(HfOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order
     *
     * @mbg.generated Mon Feb 10 20:33:43 CST 2020
     */
    int deleteByExample(HfOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order
     *
     * @mbg.generated Mon Feb 10 20:33:43 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order
     *
     * @mbg.generated Mon Feb 10 20:33:43 CST 2020
     */
    int insert(HfOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order
     *
     * @mbg.generated Mon Feb 10 20:33:43 CST 2020
     */
    int insertSelective(HfOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order
     *
     * @mbg.generated Mon Feb 10 20:33:43 CST 2020
     */
    List<HfOrder> selectByExample(HfOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order
     *
     * @mbg.generated Mon Feb 10 20:33:43 CST 2020
     */
    HfOrder selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order
     *
     * @mbg.generated Mon Feb 10 20:33:43 CST 2020
     */
    int updateByExampleSelective(@Param("record") HfOrder record, @Param("example") HfOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order
     *
     * @mbg.generated Mon Feb 10 20:33:43 CST 2020
     */
    int updateByExample(@Param("record") HfOrder record, @Param("example") HfOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order
     *
     * @mbg.generated Mon Feb 10 20:33:43 CST 2020
     */
    int updateByPrimaryKeySelective(HfOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order
     *
     * @mbg.generated Mon Feb 10 20:33:43 CST 2020
     */
    int updateByPrimaryKey(HfOrder record);
}