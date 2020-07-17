package com.hanfu.dichan.center.dao;

import com.hanfu.dichan.center.model.DcRichText;
import com.hanfu.dichan.center.model.DcRichTextExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DcRichTextMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_rich_text
     *
     * @mbg.generated Fri Jul 17 17:00:12 CST 2020
     */
    long countByExample(DcRichTextExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_rich_text
     *
     * @mbg.generated Fri Jul 17 17:00:12 CST 2020
     */
    int deleteByExample(DcRichTextExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_rich_text
     *
     * @mbg.generated Fri Jul 17 17:00:12 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_rich_text
     *
     * @mbg.generated Fri Jul 17 17:00:12 CST 2020
     */
    int insert(DcRichText record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_rich_text
     *
     * @mbg.generated Fri Jul 17 17:00:12 CST 2020
     */
    int insertSelective(DcRichText record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_rich_text
     *
     * @mbg.generated Fri Jul 17 17:00:12 CST 2020
     */
    List<DcRichText> selectByExample(DcRichTextExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_rich_text
     *
     * @mbg.generated Fri Jul 17 17:00:12 CST 2020
     */
    DcRichText selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_rich_text
     *
     * @mbg.generated Fri Jul 17 17:00:12 CST 2020
     */
    int updateByExampleSelective(@Param("record") DcRichText record, @Param("example") DcRichTextExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_rich_text
     *
     * @mbg.generated Fri Jul 17 17:00:12 CST 2020
     */
    int updateByExample(@Param("record") DcRichText record, @Param("example") DcRichTextExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_rich_text
     *
     * @mbg.generated Fri Jul 17 17:00:12 CST 2020
     */
    int updateByPrimaryKeySelective(DcRichText record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_rich_text
     *
     * @mbg.generated Fri Jul 17 17:00:12 CST 2020
     */
    int updateByPrimaryKey(DcRichText record);
}