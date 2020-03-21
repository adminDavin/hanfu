package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.DistributionProduct;
import com.hanfu.product.center.model.DistributionProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DistributionProductMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table distribution_product
     *
     * @mbg.generated Sat Mar 21 19:25:16 CST 2020
     */
    long countByExample(DistributionProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table distribution_product
     *
     * @mbg.generated Sat Mar 21 19:25:16 CST 2020
     */
    int deleteByExample(DistributionProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table distribution_product
     *
     * @mbg.generated Sat Mar 21 19:25:16 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table distribution_product
     *
     * @mbg.generated Sat Mar 21 19:25:16 CST 2020
     */
    int insert(DistributionProduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table distribution_product
     *
     * @mbg.generated Sat Mar 21 19:25:16 CST 2020
     */
    int insertSelective(DistributionProduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table distribution_product
     *
     * @mbg.generated Sat Mar 21 19:25:16 CST 2020
     */
    List<DistributionProduct> selectByExample(DistributionProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table distribution_product
     *
     * @mbg.generated Sat Mar 21 19:25:16 CST 2020
     */
    DistributionProduct selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table distribution_product
     *
     * @mbg.generated Sat Mar 21 19:25:16 CST 2020
     */
    int updateByExampleSelective(@Param("record") DistributionProduct record, @Param("example") DistributionProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table distribution_product
     *
     * @mbg.generated Sat Mar 21 19:25:16 CST 2020
     */
    int updateByExample(@Param("record") DistributionProduct record, @Param("example") DistributionProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table distribution_product
     *
     * @mbg.generated Sat Mar 21 19:25:16 CST 2020
     */
    int updateByPrimaryKeySelective(DistributionProduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table distribution_product
     *
     * @mbg.generated Sat Mar 21 19:25:16 CST 2020
     */
    int updateByPrimaryKey(DistributionProduct record);
}