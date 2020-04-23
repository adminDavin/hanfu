package com.hanfu.order.center.dao;

import com.hanfu.order.center.model.Discover;
import com.hanfu.order.center.model.DiscoverExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DiscoverMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discover
     *
     * @mbg.generated Thu Apr 23 11:46:07 CST 2020
     */
    long countByExample(DiscoverExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discover
     *
     * @mbg.generated Thu Apr 23 11:46:07 CST 2020
     */
    int deleteByExample(DiscoverExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discover
     *
     * @mbg.generated Thu Apr 23 11:46:07 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discover
     *
     * @mbg.generated Thu Apr 23 11:46:07 CST 2020
     */
    int insert(Discover record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discover
     *
     * @mbg.generated Thu Apr 23 11:46:07 CST 2020
     */
    int insertSelective(Discover record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discover
     *
     * @mbg.generated Thu Apr 23 11:46:07 CST 2020
     */
    List<Discover> selectByExample(DiscoverExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discover
     *
     * @mbg.generated Thu Apr 23 11:46:07 CST 2020
     */
    Discover selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discover
     *
     * @mbg.generated Thu Apr 23 11:46:07 CST 2020
     */
    int updateByExampleSelective(@Param("record") Discover record, @Param("example") DiscoverExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discover
     *
     * @mbg.generated Thu Apr 23 11:46:07 CST 2020
     */
    int updateByExample(@Param("record") Discover record, @Param("example") DiscoverExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discover
     *
     * @mbg.generated Thu Apr 23 11:46:07 CST 2020
     */
    int updateByPrimaryKeySelective(Discover record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discover
     *
     * @mbg.generated Thu Apr 23 11:46:07 CST 2020
     */
    int updateByPrimaryKey(Discover record);
}