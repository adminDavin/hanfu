package com.hanfu.user.center.Jurisdiction.dao;

import java.util.List;

import com.hanfu.user.center.Jurisdiction.model.DepartmentPersonnel;
import com.hanfu.user.center.Jurisdiction.model.DepartmentPersonnelExample;
import org.apache.ibatis.annotations.Param;

public interface DepartmentPersonnelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table department_personnel
     *
     * @mbg.generated Tue May 26 11:27:10 CST 2020
     */
    long countByExample(DepartmentPersonnelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table department_personnel
     *
     * @mbg.generated Tue May 26 11:27:10 CST 2020
     */
    int deleteByExample(DepartmentPersonnelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table department_personnel
     *
     * @mbg.generated Tue May 26 11:27:10 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table department_personnel
     *
     * @mbg.generated Tue May 26 11:27:10 CST 2020
     */
    int insert(DepartmentPersonnel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table department_personnel
     *
     * @mbg.generated Tue May 26 11:27:10 CST 2020
     */
    int insertSelective(DepartmentPersonnel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table department_personnel
     *
     * @mbg.generated Tue May 26 11:27:10 CST 2020
     */
    List<DepartmentPersonnel> selectByExample(DepartmentPersonnelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table department_personnel
     *
     * @mbg.generated Tue May 26 11:27:10 CST 2020
     */
    DepartmentPersonnel selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table department_personnel
     *
     * @mbg.generated Tue May 26 11:27:10 CST 2020
     */
    int updateByExampleSelective(@Param("record") DepartmentPersonnel record, @Param("example") DepartmentPersonnelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table department_personnel
     *
     * @mbg.generated Tue May 26 11:27:10 CST 2020
     */
    int updateByExample(@Param("record") DepartmentPersonnel record, @Param("example") DepartmentPersonnelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table department_personnel
     *
     * @mbg.generated Tue May 26 11:27:10 CST 2020
     */
    int updateByPrimaryKeySelective(DepartmentPersonnel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table department_personnel
     *
     * @mbg.generated Tue May 26 11:27:10 CST 2020
     */
    int updateByPrimaryKey(DepartmentPersonnel record);
}