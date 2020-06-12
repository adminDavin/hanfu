package com.hanfu.user.center.dao;

import com.hanfu.user.center.model.HfOrder;
import com.hanfu.user.center.model.HfOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HfOrderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order
     *
     * @mbg.generated Fri Jun 12 14:42:03 CST 2020
     */
    long countByExample(HfOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order
     *
     * @mbg.generated Fri Jun 12 14:42:03 CST 2020
     */
    int deleteByExample(HfOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order
     *
     * @mbg.generated Fri Jun 12 14:42:03 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order
     *
     * @mbg.generated Fri Jun 12 14:42:03 CST 2020
     */
    int insert(HfOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order
     *
     * @mbg.generated Fri Jun 12 14:42:03 CST 2020
     */
    int insertSelective(HfOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order
     *
     * @mbg.generated Fri Jun 12 14:42:03 CST 2020
     */
    List<HfOrder> selectByExample(HfOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order
     *
     * @mbg.generated Fri Jun 12 14:42:03 CST 2020
     */
    HfOrder selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order
     *
     * @mbg.generated Fri Jun 12 14:42:03 CST 2020
     */
    int updateByExampleSelective(@Param("record") HfOrder record, @Param("example") HfOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order
     *
     * @mbg.generated Fri Jun 12 14:42:03 CST 2020
     */
    int updateByExample(@Param("record") HfOrder record, @Param("example") HfOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order
     *
     * @mbg.generated Fri Jun 12 14:42:03 CST 2020
     */
    int updateByPrimaryKeySelective(HfOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order
     *
     * @mbg.generated Fri Jun 12 14:42:03 CST 2020
     */
    int updateByPrimaryKey(HfOrder record);
}