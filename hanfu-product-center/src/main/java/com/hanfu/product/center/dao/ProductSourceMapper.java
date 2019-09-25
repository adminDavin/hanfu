package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.ProductSource;
import com.hanfu.product.center.model.ProductSourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductSourceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_source
     *
     * @mbg.generated Wed Sep 25 09:59:01 CST 2019
     */
    long countByExample(ProductSourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_source
     *
     * @mbg.generated Wed Sep 25 09:59:01 CST 2019
     */
    int deleteByExample(ProductSourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_source
     *
     * @mbg.generated Wed Sep 25 09:59:01 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_source
     *
     * @mbg.generated Wed Sep 25 09:59:01 CST 2019
     */
    int insert(ProductSource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_source
     *
     * @mbg.generated Wed Sep 25 09:59:01 CST 2019
     */
    int insertSelective(ProductSource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_source
     *
     * @mbg.generated Wed Sep 25 09:59:01 CST 2019
     */
    List<ProductSource> selectByExample(ProductSourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_source
     *
     * @mbg.generated Wed Sep 25 09:59:01 CST 2019
     */
    ProductSource selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_source
     *
     * @mbg.generated Wed Sep 25 09:59:01 CST 2019
     */
    int updateByExampleSelective(@Param("record") ProductSource record, @Param("example") ProductSourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_source
     *
     * @mbg.generated Wed Sep 25 09:59:01 CST 2019
     */
    int updateByExample(@Param("record") ProductSource record, @Param("example") ProductSourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_source
     *
     * @mbg.generated Wed Sep 25 09:59:01 CST 2019
     */
    int updateByPrimaryKeySelective(ProductSource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_source
     *
     * @mbg.generated Wed Sep 25 09:59:01 CST 2019
     */
    int updateByPrimaryKey(ProductSource record);
}