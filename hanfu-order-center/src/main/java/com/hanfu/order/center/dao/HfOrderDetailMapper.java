package com.hanfu.order.center.dao;

import com.hanfu.order.center.model.HfOrderDetail;
import com.hanfu.order.center.model.HfOrderDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HfOrderDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_detail
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    long countByExample(HfOrderDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_detail
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    int deleteByExample(HfOrderDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_detail
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_detail
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    int insert(HfOrderDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_detail
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    int insertSelective(HfOrderDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_detail
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    List<HfOrderDetail> selectByExample(HfOrderDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_detail
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    HfOrderDetail selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_detail
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    int updateByExampleSelective(@Param("record") HfOrderDetail record, @Param("example") HfOrderDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_detail
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    int updateByExample(@Param("record") HfOrderDetail record, @Param("example") HfOrderDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_detail
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    int updateByPrimaryKeySelective(HfOrderDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_detail
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    int updateByPrimaryKey(HfOrderDetail record);
}