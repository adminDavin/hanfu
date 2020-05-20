package com.hanfu.user.center.dao;

import com.hanfu.user.center.model.AccountModel;
import com.hanfu.user.center.model.AccountModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AccountModelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account_model
     *
     * @mbg.generated Wed May 20 09:39:53 CST 2020
     */
    long countByExample(AccountModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account_model
     *
     * @mbg.generated Wed May 20 09:39:53 CST 2020
     */
    int deleteByExample(AccountModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account_model
     *
     * @mbg.generated Wed May 20 09:39:53 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account_model
     *
     * @mbg.generated Wed May 20 09:39:53 CST 2020
     */
    int insert(AccountModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account_model
     *
     * @mbg.generated Wed May 20 09:39:53 CST 2020
     */
    int insertSelective(AccountModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account_model
     *
     * @mbg.generated Wed May 20 09:39:53 CST 2020
     */
    List<AccountModel> selectByExample(AccountModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account_model
     *
     * @mbg.generated Wed May 20 09:39:53 CST 2020
     */
    AccountModel selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account_model
     *
     * @mbg.generated Wed May 20 09:39:53 CST 2020
     */
    int updateByExampleSelective(@Param("record") AccountModel record, @Param("example") AccountModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account_model
     *
     * @mbg.generated Wed May 20 09:39:53 CST 2020
     */
    int updateByExample(@Param("record") AccountModel record, @Param("example") AccountModelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account_model
     *
     * @mbg.generated Wed May 20 09:39:53 CST 2020
     */
    int updateByPrimaryKeySelective(AccountModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account_model
     *
     * @mbg.generated Wed May 20 09:39:53 CST 2020
     */
    int updateByPrimaryKey(AccountModel record);
}