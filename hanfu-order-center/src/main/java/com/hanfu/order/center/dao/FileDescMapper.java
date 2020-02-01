package com.hanfu.order.center.dao;

import com.hanfu.order.center.model.FileDesc;
import com.hanfu.order.center.model.FileDescExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FileDescMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_desc
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    long countByExample(FileDescExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_desc
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    int deleteByExample(FileDescExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_desc
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_desc
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    int insert(FileDesc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_desc
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    int insertSelective(FileDesc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_desc
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    List<FileDesc> selectByExample(FileDescExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_desc
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    FileDesc selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_desc
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    int updateByExampleSelective(@Param("record") FileDesc record, @Param("example") FileDescExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_desc
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    int updateByExample(@Param("record") FileDesc record, @Param("example") FileDescExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_desc
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    int updateByPrimaryKeySelective(FileDesc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_desc
     *
     * @mbg.generated Sat Feb 01 09:17:37 CST 2020
     */
    int updateByPrimaryKey(FileDesc record);
}