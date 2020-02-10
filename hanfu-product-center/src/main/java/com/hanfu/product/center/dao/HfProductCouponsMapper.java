package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.HfProductCoupons;
import com.hanfu.product.center.model.HfProductCouponsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HfProductCouponsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_product_coupons
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    long countByExample(HfProductCouponsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_product_coupons
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    int deleteByExample(HfProductCouponsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_product_coupons
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_product_coupons
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    int insert(HfProductCoupons record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_product_coupons
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    int insertSelective(HfProductCoupons record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_product_coupons
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    List<HfProductCoupons> selectByExample(HfProductCouponsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_product_coupons
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    HfProductCoupons selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_product_coupons
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    int updateByExampleSelective(@Param("record") HfProductCoupons record, @Param("example") HfProductCouponsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_product_coupons
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    int updateByExample(@Param("record") HfProductCoupons record, @Param("example") HfProductCouponsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_product_coupons
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    int updateByPrimaryKeySelective(HfProductCoupons record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_product_coupons
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    int updateByPrimaryKey(HfProductCoupons record);
}