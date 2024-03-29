package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.Roles;
import com.hanfu.product.center.model.RolesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RolesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roles
     *
     * @mbg.generated Fri Aug 28 14:22:51 CST 2020
     */
    long countByExample(RolesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roles
     *
     * @mbg.generated Fri Aug 28 14:22:51 CST 2020
     */
    int deleteByExample(RolesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roles
     *
     * @mbg.generated Fri Aug 28 14:22:51 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roles
     *
     * @mbg.generated Fri Aug 28 14:22:51 CST 2020
     */
    int insert(Roles record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roles
     *
     * @mbg.generated Fri Aug 28 14:22:51 CST 2020
     */
    int insertSelective(Roles record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roles
     *
     * @mbg.generated Fri Aug 28 14:22:51 CST 2020
     */
    List<Roles> selectByExample(RolesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roles
     *
     * @mbg.generated Fri Aug 28 14:22:51 CST 2020
     */
    Roles selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roles
     *
     * @mbg.generated Fri Aug 28 14:22:51 CST 2020
     */
    int updateByExampleSelective(@Param("record") Roles record, @Param("example") RolesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roles
     *
     * @mbg.generated Fri Aug 28 14:22:51 CST 2020
     */
    int updateByExample(@Param("record") Roles record, @Param("example") RolesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roles
     *
     * @mbg.generated Fri Aug 28 14:22:51 CST 2020
     */
    int updateByPrimaryKeySelective(Roles record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roles
     *
     * @mbg.generated Fri Aug 28 14:22:51 CST 2020
     */
    int updateByPrimaryKey(Roles record);
}