package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.GoodsSpec;
import com.hanfu.product.center.model.GoodsSpecExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodsSpecMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_spec
     *
     * @mbg.generated Mon Oct 07 02:29:24 CST 2019
     */
    long countByExample(GoodsSpecExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_spec
     *
     * @mbg.generated Mon Oct 07 02:29:24 CST 2019
     */
    int deleteByExample(GoodsSpecExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_spec
     *
     * @mbg.generated Mon Oct 07 02:29:24 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_spec
     *
     * @mbg.generated Mon Oct 07 02:29:24 CST 2019
     */
    int insert(GoodsSpec record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_spec
     *
     * @mbg.generated Mon Oct 07 02:29:24 CST 2019
     */
    int insertSelective(GoodsSpec record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_spec
     *
     * @mbg.generated Mon Oct 07 02:29:24 CST 2019
     */
    List<GoodsSpec> selectByExample(GoodsSpecExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_spec
     *
     * @mbg.generated Mon Oct 07 02:29:24 CST 2019
     */
    GoodsSpec selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_spec
     *
     * @mbg.generated Mon Oct 07 02:29:24 CST 2019
     */
    int updateByExampleSelective(@Param("record") GoodsSpec record, @Param("example") GoodsSpecExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_spec
     *
     * @mbg.generated Mon Oct 07 02:29:24 CST 2019
     */
    int updateByExample(@Param("record") GoodsSpec record, @Param("example") GoodsSpecExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_spec
     *
     * @mbg.generated Mon Oct 07 02:29:24 CST 2019
     */
    int updateByPrimaryKeySelective(GoodsSpec record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_spec
     *
     * @mbg.generated Mon Oct 07 02:29:24 CST 2019
     */
    int updateByPrimaryKey(GoodsSpec record);
}