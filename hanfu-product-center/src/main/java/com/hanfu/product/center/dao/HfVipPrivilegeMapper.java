package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.HfVipPrivilege;
import com.hanfu.product.center.model.HfVipPrivilegeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HfVipPrivilegeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip_privilege
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    long countByExample(HfVipPrivilegeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip_privilege
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    int deleteByExample(HfVipPrivilegeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip_privilege
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip_privilege
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    int insert(HfVipPrivilege record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip_privilege
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    int insertSelective(HfVipPrivilege record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip_privilege
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    List<HfVipPrivilege> selectByExample(HfVipPrivilegeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip_privilege
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    HfVipPrivilege selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip_privilege
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    int updateByExampleSelective(@Param("record") HfVipPrivilege record, @Param("example") HfVipPrivilegeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip_privilege
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    int updateByExample(@Param("record") HfVipPrivilege record, @Param("example") HfVipPrivilegeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip_privilege
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    int updateByPrimaryKeySelective(HfVipPrivilege record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip_privilege
     *
     * @mbg.generated Tue Dec 08 16:23:54 CST 2020
     */
    int updateByPrimaryKey(HfVipPrivilege record);
}