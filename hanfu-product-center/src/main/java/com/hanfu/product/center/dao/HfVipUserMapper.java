package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.HfVipUser;
import com.hanfu.product.center.model.HfVipUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HfVipUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip_user
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    long countByExample(HfVipUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip_user
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    int deleteByExample(HfVipUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip_user
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip_user
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    int insert(HfVipUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip_user
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    int insertSelective(HfVipUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip_user
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    List<HfVipUser> selectByExample(HfVipUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip_user
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    HfVipUser selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip_user
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    int updateByExampleSelective(@Param("record") HfVipUser record, @Param("example") HfVipUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip_user
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    int updateByExample(@Param("record") HfVipUser record, @Param("example") HfVipUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip_user
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    int updateByPrimaryKeySelective(HfVipUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip_user
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    int updateByPrimaryKey(HfVipUser record);
}