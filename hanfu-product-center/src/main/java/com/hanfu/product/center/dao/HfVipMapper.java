package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.HfVip;
import com.hanfu.product.center.model.HfVipExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HfVipMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    long countByExample(HfVipExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    int deleteByExample(HfVipExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    int insert(HfVip record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    int insertSelective(HfVip record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    List<HfVip> selectByExample(HfVipExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    HfVip selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    int updateByExampleSelective(@Param("record") HfVip record, @Param("example") HfVipExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    int updateByExample(@Param("record") HfVip record, @Param("example") HfVipExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    int updateByPrimaryKeySelective(HfVip record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    int updateByPrimaryKey(HfVip record);
}