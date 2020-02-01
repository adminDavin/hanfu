package com.hanfu.order.center.dao;

import com.hanfu.order.center.model.HfOrderLogistics;
import com.hanfu.order.center.model.HfOrderLogisticsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HfOrderLogisticsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_logistics
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    long countByExample(HfOrderLogisticsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_logistics
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    int deleteByExample(HfOrderLogisticsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_logistics
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_logistics
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    int insert(HfOrderLogistics record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_logistics
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    int insertSelective(HfOrderLogistics record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_logistics
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    List<HfOrderLogistics> selectByExample(HfOrderLogisticsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_logistics
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    HfOrderLogistics selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_logistics
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    int updateByExampleSelective(@Param("record") HfOrderLogistics record, @Param("example") HfOrderLogisticsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_logistics
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    int updateByExample(@Param("record") HfOrderLogistics record, @Param("example") HfOrderLogisticsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_logistics
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    int updateByPrimaryKeySelective(HfOrderLogistics record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_logistics
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    int updateByPrimaryKey(HfOrderLogistics record);
}