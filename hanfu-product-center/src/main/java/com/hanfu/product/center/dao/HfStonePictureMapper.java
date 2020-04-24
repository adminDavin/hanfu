package com.hanfu.product.center.dao;

import com.hanfu.product.center.model.HfStonePicture;
import com.hanfu.product.center.model.HfStonePictureExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HfStonePictureMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_stone_picture
     *
     * @mbg.generated Fri Apr 24 15:46:53 CST 2020
     */
    long countByExample(HfStonePictureExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_stone_picture
     *
     * @mbg.generated Fri Apr 24 15:46:53 CST 2020
     */
    int deleteByExample(HfStonePictureExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_stone_picture
     *
     * @mbg.generated Fri Apr 24 15:46:53 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_stone_picture
     *
     * @mbg.generated Fri Apr 24 15:46:53 CST 2020
     */
    int insert(HfStonePicture record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_stone_picture
     *
     * @mbg.generated Fri Apr 24 15:46:53 CST 2020
     */
    int insertSelective(HfStonePicture record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_stone_picture
     *
     * @mbg.generated Fri Apr 24 15:46:53 CST 2020
     */
    List<HfStonePicture> selectByExample(HfStonePictureExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_stone_picture
     *
     * @mbg.generated Fri Apr 24 15:46:53 CST 2020
     */
    HfStonePicture selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_stone_picture
     *
     * @mbg.generated Fri Apr 24 15:46:53 CST 2020
     */
    int updateByExampleSelective(@Param("record") HfStonePicture record, @Param("example") HfStonePictureExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_stone_picture
     *
     * @mbg.generated Fri Apr 24 15:46:53 CST 2020
     */
    int updateByExample(@Param("record") HfStonePicture record, @Param("example") HfStonePictureExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_stone_picture
     *
     * @mbg.generated Fri Apr 24 15:46:53 CST 2020
     */
    int updateByPrimaryKeySelective(HfStonePicture record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_stone_picture
     *
     * @mbg.generated Fri Apr 24 15:46:53 CST 2020
     */
    int updateByPrimaryKey(HfStonePicture record);
}