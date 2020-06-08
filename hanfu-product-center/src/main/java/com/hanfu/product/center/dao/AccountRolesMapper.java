package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.AccountRoles;
import com.hanfu.product.center.model.AccountRolesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AccountRolesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account_roles
     *
     * @mbg.generated Mon Jun 08 14:48:57 CST 2020
     */
    long countByExample(AccountRolesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account_roles
     *
     * @mbg.generated Mon Jun 08 14:48:57 CST 2020
     */
    int deleteByExample(AccountRolesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account_roles
     *
     * @mbg.generated Mon Jun 08 14:48:57 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account_roles
     *
     * @mbg.generated Mon Jun 08 14:48:57 CST 2020
     */
    int insert(AccountRoles record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account_roles
     *
     * @mbg.generated Mon Jun 08 14:48:57 CST 2020
     */
    int insertSelective(AccountRoles record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account_roles
     *
     * @mbg.generated Mon Jun 08 14:48:57 CST 2020
     */
    List<AccountRoles> selectByExample(AccountRolesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account_roles
     *
     * @mbg.generated Mon Jun 08 14:48:57 CST 2020
     */
    AccountRoles selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account_roles
     *
     * @mbg.generated Mon Jun 08 14:48:57 CST 2020
     */
    int updateByExampleSelective(@Param("record") AccountRoles record, @Param("example") AccountRolesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account_roles
     *
     * @mbg.generated Mon Jun 08 14:48:57 CST 2020
     */
    int updateByExample(@Param("record") AccountRoles record, @Param("example") AccountRolesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account_roles
     *
     * @mbg.generated Mon Jun 08 14:48:57 CST 2020
     */
    int updateByPrimaryKeySelective(AccountRoles record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account_roles
     *
     * @mbg.generated Mon Jun 08 14:48:57 CST 2020
     */
    int updateByPrimaryKey(AccountRoles record);
}