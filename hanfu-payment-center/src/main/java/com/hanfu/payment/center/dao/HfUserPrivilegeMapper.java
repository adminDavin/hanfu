package com.hanfu.payment.center.dao;

import com.hanfu.payment.center.model.HfUserPrivilege;
import com.hanfu.payment.center.model.HfUserPrivilegeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HfUserPrivilegeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_user_privilege
     *
     * @mbg.generated Mon Feb 10 20:33:43 CST 2020
     */
    long countByExample(HfUserPrivilegeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_user_privilege
     *
     * @mbg.generated Mon Feb 10 20:33:43 CST 2020
     */
    int deleteByExample(HfUserPrivilegeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_user_privilege
     *
     * @mbg.generated Mon Feb 10 20:33:43 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_user_privilege
     *
     * @mbg.generated Mon Feb 10 20:33:43 CST 2020
     */
    int insert(HfUserPrivilege record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_user_privilege
     *
     * @mbg.generated Mon Feb 10 20:33:43 CST 2020
     */
    int insertSelective(HfUserPrivilege record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_user_privilege
     *
     * @mbg.generated Mon Feb 10 20:33:43 CST 2020
     */
    List<HfUserPrivilege> selectByExample(HfUserPrivilegeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_user_privilege
     *
     * @mbg.generated Mon Feb 10 20:33:43 CST 2020
     */
    HfUserPrivilege selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_user_privilege
     *
     * @mbg.generated Mon Feb 10 20:33:43 CST 2020
     */
    int updateByExampleSelective(@Param("record") HfUserPrivilege record, @Param("example") HfUserPrivilegeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_user_privilege
     *
     * @mbg.generated Mon Feb 10 20:33:43 CST 2020
     */
    int updateByExample(@Param("record") HfUserPrivilege record, @Param("example") HfUserPrivilegeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_user_privilege
     *
     * @mbg.generated Mon Feb 10 20:33:43 CST 2020
     */
    int updateByPrimaryKeySelective(HfUserPrivilege record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_user_privilege
     *
     * @mbg.generated Mon Feb 10 20:33:43 CST 2020
     */
    int updateByPrimaryKey(HfUserPrivilege record);
}