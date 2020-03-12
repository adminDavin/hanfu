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
     * @mbg.generated Mon Feb 10 21:14:04 CST 2020
     */
    long countByExample(HfGoodsPictrueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_pictrue
     *
     * @mbg.generated Mon Feb 10 21:14:04 CST 2020
     */
    int deleteByExample(HfGoodsPictrueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_pictrue
     *
     * @mbg.generated Mon Feb 10 21:14:04 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_pictrue
     *
     * @mbg.generated Mon Feb 10 21:14:04 CST 2020
     */
    int insert(HfGoodsPictrue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_pictrue
     *
     * @mbg.generated Mon Feb 10 21:14:04 CST 2020
     */
    int insertSelective(HfGoodsPictrue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_pictrue
     *
     * @mbg.generated Mon Feb 10 21:14:04 CST 2020
     */
    List<HfGoodsPictrue> selectByExample(HfGoodsPictrueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_pictrue
     *
     * @mbg.generated Mon Feb 10 21:14:04 CST 2020
     */
    HfGoodsPictrue selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_pictrue
     *
     * @mbg.generated Mon Feb 10 21:14:04 CST 2020
     */
    int updateByExampleSelective(@Param("record") HfGoodsPictrue record, @Param("example") HfGoodsPictrueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_pictrue
     *
     * @mbg.generated Mon Feb 10 21:14:04 CST 2020
     */
    int updateByExample(@Param("record") HfGoodsPictrue record, @Param("example") HfGoodsPictrueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_pictrue
     *
     * @mbg.generated Mon Feb 10 21:14:04 CST 2020
     */
    int updateByPrimaryKeySelective(HfGoodsPictrue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods_pictrue
     *
     * @mbg.generated Mon Feb 10 21:14:04 CST 2020
     */
    int updateByPrimaryKey(HfGoodsPictrue record);
}