package com.hanfu.product.center.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HfStonePictureExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hf_stone_picture
     *
     * @mbg.generated Fri May 08 11:34:44 CST 2020
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hf_stone_picture
     *
     * @mbg.generated Fri May 08 11:34:44 CST 2020
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hf_stone_picture
     *
     * @mbg.generated Fri May 08 11:34:44 CST 2020
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_stone_picture
     *
     * @mbg.generated Fri May 08 11:34:44 CST 2020
     */
    public HfStonePictureExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_stone_picture
     *
     * @mbg.generated Fri May 08 11:34:44 CST 2020
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_stone_picture
     *
     * @mbg.generated Fri May 08 11:34:44 CST 2020
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_stone_picture
     *
     * @mbg.generated Fri May 08 11:34:44 CST 2020
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_stone_picture
     *
     * @mbg.generated Fri May 08 11:34:44 CST 2020
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_stone_picture
     *
     * @mbg.generated Fri May 08 11:34:44 CST 2020
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_stone_picture
     *
     * @mbg.generated Fri May 08 11:34:44 CST 2020
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_stone_picture
     *
     * @mbg.generated Fri May 08 11:34:44 CST 2020
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_stone_picture
     *
     * @mbg.generated Fri May 08 11:34:44 CST 2020
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_stone_picture
     *
     * @mbg.generated Fri May 08 11:34:44 CST 2020
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_stone_picture
     *
     * @mbg.generated Fri May 08 11:34:44 CST 2020
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table hf_stone_picture
     *
     * @mbg.generated Fri May 08 11:34:44 CST 2020
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andHfNameIsNull() {
            addCriterion("hf_name is null");
            return (Criteria) this;
        }

        public Criteria andHfNameIsNotNull() {
            addCriterion("hf_name is not null");
            return (Criteria) this;
        }

        public Criteria andHfNameEqualTo(String value) {
            addCriterion("hf_name =", value, "hfName");
            return (Criteria) this;
        }

        public Criteria andHfNameNotEqualTo(String value) {
            addCriterion("hf_name <>", value, "hfName");
            return (Criteria) this;
        }

        public Criteria andHfNameGreaterThan(String value) {
            addCriterion("hf_name >", value, "hfName");
            return (Criteria) this;
        }

        public Criteria andHfNameGreaterThanOrEqualTo(String value) {
            addCriterion("hf_name >=", value, "hfName");
            return (Criteria) this;
        }

        public Criteria andHfNameLessThan(String value) {
            addCriterion("hf_name <", value, "hfName");
            return (Criteria) this;
        }

        public Criteria andHfNameLessThanOrEqualTo(String value) {
            addCriterion("hf_name <=", value, "hfName");
            return (Criteria) this;
        }

        public Criteria andHfNameLike(String value) {
            addCriterion("hf_name like", value, "hfName");
            return (Criteria) this;
        }

        public Criteria andHfNameNotLike(String value) {
            addCriterion("hf_name not like", value, "hfName");
            return (Criteria) this;
        }

        public Criteria andHfNameIn(List<String> values) {
            addCriterion("hf_name in", values, "hfName");
            return (Criteria) this;
        }

        public Criteria andHfNameNotIn(List<String> values) {
            addCriterion("hf_name not in", values, "hfName");
            return (Criteria) this;
        }

        public Criteria andHfNameBetween(String value1, String value2) {
            addCriterion("hf_name between", value1, value2, "hfName");
            return (Criteria) this;
        }

        public Criteria andHfNameNotBetween(String value1, String value2) {
            addCriterion("hf_name not between", value1, value2, "hfName");
            return (Criteria) this;
        }

        public Criteria andHfDescIsNull() {
            addCriterion("hf_desc is null");
            return (Criteria) this;
        }

        public Criteria andHfDescIsNotNull() {
            addCriterion("hf_desc is not null");
            return (Criteria) this;
        }

        public Criteria andHfDescEqualTo(String value) {
            addCriterion("hf_desc =", value, "hfDesc");
            return (Criteria) this;
        }

        public Criteria andHfDescNotEqualTo(String value) {
            addCriterion("hf_desc <>", value, "hfDesc");
            return (Criteria) this;
        }

        public Criteria andHfDescGreaterThan(String value) {
            addCriterion("hf_desc >", value, "hfDesc");
            return (Criteria) this;
        }

        public Criteria andHfDescGreaterThanOrEqualTo(String value) {
            addCriterion("hf_desc >=", value, "hfDesc");
            return (Criteria) this;
        }

        public Criteria andHfDescLessThan(String value) {
            addCriterion("hf_desc <", value, "hfDesc");
            return (Criteria) this;
        }

        public Criteria andHfDescLessThanOrEqualTo(String value) {
            addCriterion("hf_desc <=", value, "hfDesc");
            return (Criteria) this;
        }

        public Criteria andHfDescLike(String value) {
            addCriterion("hf_desc like", value, "hfDesc");
            return (Criteria) this;
        }

        public Criteria andHfDescNotLike(String value) {
            addCriterion("hf_desc not like", value, "hfDesc");
            return (Criteria) this;
        }

        public Criteria andHfDescIn(List<String> values) {
            addCriterion("hf_desc in", values, "hfDesc");
            return (Criteria) this;
        }

        public Criteria andHfDescNotIn(List<String> values) {
            addCriterion("hf_desc not in", values, "hfDesc");
            return (Criteria) this;
        }

        public Criteria andHfDescBetween(String value1, String value2) {
            addCriterion("hf_desc between", value1, value2, "hfDesc");
            return (Criteria) this;
        }

        public Criteria andHfDescNotBetween(String value1, String value2) {
            addCriterion("hf_desc not between", value1, value2, "hfDesc");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andStoneIdIsNull() {
            addCriterion("stone_id is null");
            return (Criteria) this;
        }

        public Criteria andStoneIdIsNotNull() {
            addCriterion("stone_id is not null");
            return (Criteria) this;
        }

        public Criteria andStoneIdEqualTo(Integer value) {
            addCriterion("stone_id =", value, "stoneId");
            return (Criteria) this;
        }

        public Criteria andStoneIdNotEqualTo(Integer value) {
            addCriterion("stone_id <>", value, "stoneId");
            return (Criteria) this;
        }

        public Criteria andStoneIdGreaterThan(Integer value) {
            addCriterion("stone_id >", value, "stoneId");
            return (Criteria) this;
        }

        public Criteria andStoneIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("stone_id >=", value, "stoneId");
            return (Criteria) this;
        }

        public Criteria andStoneIdLessThan(Integer value) {
            addCriterion("stone_id <", value, "stoneId");
            return (Criteria) this;
        }

        public Criteria andStoneIdLessThanOrEqualTo(Integer value) {
            addCriterion("stone_id <=", value, "stoneId");
            return (Criteria) this;
        }

        public Criteria andStoneIdIn(List<Integer> values) {
            addCriterion("stone_id in", values, "stoneId");
            return (Criteria) this;
        }

        public Criteria andStoneIdNotIn(List<Integer> values) {
            addCriterion("stone_id not in", values, "stoneId");
            return (Criteria) this;
        }

        public Criteria andStoneIdBetween(Integer value1, Integer value2) {
            addCriterion("stone_id between", value1, value2, "stoneId");
            return (Criteria) this;
        }

        public Criteria andStoneIdNotBetween(Integer value1, Integer value2) {
            addCriterion("stone_id not between", value1, value2, "stoneId");
            return (Criteria) this;
        }

        public Criteria andFileIdIsNull() {
            addCriterion("file_id is null");
            return (Criteria) this;
        }

        public Criteria andFileIdIsNotNull() {
            addCriterion("file_id is not null");
            return (Criteria) this;
        }

        public Criteria andFileIdEqualTo(Integer value) {
            addCriterion("file_id =", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotEqualTo(Integer value) {
            addCriterion("file_id <>", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdGreaterThan(Integer value) {
            addCriterion("file_id >", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("file_id >=", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdLessThan(Integer value) {
            addCriterion("file_id <", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdLessThanOrEqualTo(Integer value) {
            addCriterion("file_id <=", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdIn(List<Integer> values) {
            addCriterion("file_id in", values, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotIn(List<Integer> values) {
            addCriterion("file_id not in", values, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdBetween(Integer value1, Integer value2) {
            addCriterion("file_id between", value1, value2, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotBetween(Integer value1, Integer value2) {
            addCriterion("file_id not between", value1, value2, "fileId");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(LocalDateTime value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(LocalDateTime value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(LocalDateTime value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<LocalDateTime> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<LocalDateTime> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNull() {
            addCriterion("modify_time is null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNotNull() {
            addCriterion("modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeEqualTo(LocalDateTime value) {
            addCriterion("modify_time =", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotEqualTo(LocalDateTime value) {
            addCriterion("modify_time <>", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThan(LocalDateTime value) {
            addCriterion("modify_time >", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("modify_time >=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThan(LocalDateTime value) {
            addCriterion("modify_time <", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("modify_time <=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIn(List<LocalDateTime> values) {
            addCriterion("modify_time in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotIn(List<LocalDateTime> values) {
            addCriterion("modify_time not in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("modify_time between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("modify_time not between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNull() {
            addCriterion("is_deleted is null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNotNull() {
            addCriterion("is_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedEqualTo(Byte value) {
            addCriterion("is_deleted =", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotEqualTo(Byte value) {
            addCriterion("is_deleted <>", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThan(Byte value) {
            addCriterion("is_deleted >", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_deleted >=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThan(Byte value) {
            addCriterion("is_deleted <", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThanOrEqualTo(Byte value) {
            addCriterion("is_deleted <=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIn(List<Byte> values) {
            addCriterion("is_deleted in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotIn(List<Byte> values) {
            addCriterion("is_deleted not in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedBetween(Byte value1, Byte value2) {
            addCriterion("is_deleted between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotBetween(Byte value1, Byte value2) {
            addCriterion("is_deleted not between", value1, value2, "isDeleted");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table hf_stone_picture
     *
     * @mbg.generated do_not_delete_during_merge Fri May 08 11:34:44 CST 2020
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table hf_stone_picture
     *
     * @mbg.generated Fri May 08 11:34:44 CST 2020
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}