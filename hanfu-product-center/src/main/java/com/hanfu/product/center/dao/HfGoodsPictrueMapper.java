package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.HfGoodsPictrue;
import com.hanfu.product.center.model.HfGoodsPictrueExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface HfGoodsPictrueMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_pictrue
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    long countByExample(HfGoodsPictrueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_pictrue
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    int deleteByExample(HfGoodsPictrueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_pictrue
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_pictrue
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    int insert(HfGoodsPictrue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_pictrue
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    int insertSelective(HfGoodsPictrue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_pictrue
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    List<HfGoodsPictrue> selectByExample(HfGoodsPictrueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_pictrue
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    HfGoodsPictrue selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_pictrue
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    int updateByExampleSelective(@Param("record") HfGoodsPictrue record, @Param("example") HfGoodsPictrueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_pictrue
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    int updateByExample(@Param("record") HfGoodsPictrue record, @Param("example") HfGoodsPictrueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_pictrue
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    int updateByPrimaryKeySelective(HfGoodsPictrue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_pictrue
     *
     * @mbg.generated Wed Oct 23 07:58:46 CST 2019
     */
    int updateByPrimaryKey(HfGoodsPictrue record);
}