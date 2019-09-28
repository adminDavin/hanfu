package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.HfResp;
import com.hanfu.product.center.model.HfRespExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HfRespMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_resp
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    long countByExample(HfRespExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_resp
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    int deleteByExample(HfRespExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_resp
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_resp
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    int insert(HfResp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_resp
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    int insertSelective(HfResp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_resp
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    List<HfResp> selectByExample(HfRespExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_resp
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    HfResp selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_resp
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    int updateByExampleSelective(@Param("record") HfResp record, @Param("example") HfRespExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_resp
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    int updateByExample(@Param("record") HfResp record, @Param("example") HfRespExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_resp
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    int updateByPrimaryKeySelective(HfResp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_resp
     *
     * @mbg.generated Sat Sep 28 07:18:53 CST 2019
     */
    int updateByPrimaryKey(HfResp record);
}