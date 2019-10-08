package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.HfGoods;
import com.hanfu.product.center.model.HfGoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HfGoodsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods
     *
     * @mbg.generated Mon Oct 07 02:29:24 CST 2019
     */
    long countByExample(HfGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods
     *
     * @mbg.generated Mon Oct 07 02:29:24 CST 2019
     */
    int deleteByExample(HfGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods
     *
     * @mbg.generated Mon Oct 07 02:29:24 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods
     *
     * @mbg.generated Mon Oct 07 02:29:24 CST 2019
     */
    int insert(HfGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods
     *
     * @mbg.generated Mon Oct 07 02:29:24 CST 2019
     */
    int insertSelective(HfGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods
     *
     * @mbg.generated Mon Oct 07 02:29:24 CST 2019
     */
    List<HfGoods> selectByExample(HfGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods
     *
     * @mbg.generated Mon Oct 07 02:29:24 CST 2019
     */
    HfGoods selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods
     *
     * @mbg.generated Mon Oct 07 02:29:24 CST 2019
     */
    int updateByExampleSelective(@Param("record") HfGoods record, @Param("example") HfGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods
     *
     * @mbg.generated Mon Oct 07 02:29:24 CST 2019
     */
    int updateByExample(@Param("record") HfGoods record, @Param("example") HfGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods
     *
     * @mbg.generated Mon Oct 07 02:29:24 CST 2019
     */
    int updateByPrimaryKeySelective(HfGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods
     *
     * @mbg.generated Mon Oct 07 02:29:24 CST 2019
     */
    int updateByPrimaryKey(HfGoods record);
}