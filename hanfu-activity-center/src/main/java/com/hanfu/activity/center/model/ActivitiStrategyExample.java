package com.hanfu.activity.center.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ActivitiStrategyExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table activiti_strategy
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table activiti_strategy
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table activiti_strategy
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_strategy
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    public ActivitiStrategyExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_strategy
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_strategy
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_strategy
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_strategy
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_strategy
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_strategy
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_strategy
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_strategy
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
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
     * This method corresponds to the database table activiti_strategy
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_strategy
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table activiti_strategy
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
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

        public Criteria andStrategyNameIsNull() {
            addCriterion("strategy_name is null");
            return (Criteria) this;
        }

        public Criteria andStrategyNameIsNotNull() {
            addCriterion("strategy_name is not null");
            return (Criteria) this;
        }

        public Criteria andStrategyNameEqualTo(String value) {
            addCriterion("strategy_name =", value, "strategyName");
            return (Criteria) this;
        }

        public Criteria andStrategyNameNotEqualTo(String value) {
            addCriterion("strategy_name <>", value, "strategyName");
            return (Criteria) this;
        }

        public Criteria andStrategyNameGreaterThan(String value) {
            addCriterion("strategy_name >", value, "strategyName");
            return (Criteria) this;
        }

        public Criteria andStrategyNameGreaterThanOrEqualTo(String value) {
            addCriterion("strategy_name >=", value, "strategyName");
            return (Criteria) this;
        }

        public Criteria andStrategyNameLessThan(String value) {
            addCriterion("strategy_name <", value, "strategyName");
            return (Criteria) this;
        }

        public Criteria andStrategyNameLessThanOrEqualTo(String value) {
            addCriterion("strategy_name <=", value, "strategyName");
            return (Criteria) this;
        }

        public Criteria andStrategyNameLike(String value) {
            addCriterion("strategy_name like", value, "strategyName");
            return (Criteria) this;
        }

        public Criteria andStrategyNameNotLike(String value) {
            addCriterion("strategy_name not like", value, "strategyName");
            return (Criteria) this;
        }

        public Criteria andStrategyNameIn(List<String> values) {
            addCriterion("strategy_name in", values, "strategyName");
            return (Criteria) this;
        }

        public Criteria andStrategyNameNotIn(List<String> values) {
            addCriterion("strategy_name not in", values, "strategyName");
            return (Criteria) this;
        }

        public Criteria andStrategyNameBetween(String value1, String value2) {
            addCriterion("strategy_name between", value1, value2, "strategyName");
            return (Criteria) this;
        }

        public Criteria andStrategyNameNotBetween(String value1, String value2) {
            addCriterion("strategy_name not between", value1, value2, "strategyName");
            return (Criteria) this;
        }

        public Criteria andStrategyDescIsNull() {
            addCriterion("strategy_desc is null");
            return (Criteria) this;
        }

        public Criteria andStrategyDescIsNotNull() {
            addCriterion("strategy_desc is not null");
            return (Criteria) this;
        }

        public Criteria andStrategyDescEqualTo(String value) {
            addCriterion("strategy_desc =", value, "strategyDesc");
            return (Criteria) this;
        }

        public Criteria andStrategyDescNotEqualTo(String value) {
            addCriterion("strategy_desc <>", value, "strategyDesc");
            return (Criteria) this;
        }

        public Criteria andStrategyDescGreaterThan(String value) {
            addCriterion("strategy_desc >", value, "strategyDesc");
            return (Criteria) this;
        }

        public Criteria andStrategyDescGreaterThanOrEqualTo(String value) {
            addCriterion("strategy_desc >=", value, "strategyDesc");
            return (Criteria) this;
        }

        public Criteria andStrategyDescLessThan(String value) {
            addCriterion("strategy_desc <", value, "strategyDesc");
            return (Criteria) this;
        }

        public Criteria andStrategyDescLessThanOrEqualTo(String value) {
            addCriterion("strategy_desc <=", value, "strategyDesc");
            return (Criteria) this;
        }

        public Criteria andStrategyDescLike(String value) {
            addCriterion("strategy_desc like", value, "strategyDesc");
            return (Criteria) this;
        }

        public Criteria andStrategyDescNotLike(String value) {
            addCriterion("strategy_desc not like", value, "strategyDesc");
            return (Criteria) this;
        }

        public Criteria andStrategyDescIn(List<String> values) {
            addCriterion("strategy_desc in", values, "strategyDesc");
            return (Criteria) this;
        }

        public Criteria andStrategyDescNotIn(List<String> values) {
            addCriterion("strategy_desc not in", values, "strategyDesc");
            return (Criteria) this;
        }

        public Criteria andStrategyDescBetween(String value1, String value2) {
            addCriterion("strategy_desc between", value1, value2, "strategyDesc");
            return (Criteria) this;
        }

        public Criteria andStrategyDescNotBetween(String value1, String value2) {
            addCriterion("strategy_desc not between", value1, value2, "strategyDesc");
            return (Criteria) this;
        }

        public Criteria andStrategyTypeIsNull() {
            addCriterion("strategy_type is null");
            return (Criteria) this;
        }

        public Criteria andStrategyTypeIsNotNull() {
            addCriterion("strategy_type is not null");
            return (Criteria) this;
        }

        public Criteria andStrategyTypeEqualTo(String value) {
            addCriterion("strategy_type =", value, "strategyType");
            return (Criteria) this;
        }

        public Criteria andStrategyTypeNotEqualTo(String value) {
            addCriterion("strategy_type <>", value, "strategyType");
            return (Criteria) this;
        }

        public Criteria andStrategyTypeGreaterThan(String value) {
            addCriterion("strategy_type >", value, "strategyType");
            return (Criteria) this;
        }

        public Criteria andStrategyTypeGreaterThanOrEqualTo(String value) {
            addCriterion("strategy_type >=", value, "strategyType");
            return (Criteria) this;
        }

        public Criteria andStrategyTypeLessThan(String value) {
            addCriterion("strategy_type <", value, "strategyType");
            return (Criteria) this;
        }

        public Criteria andStrategyTypeLessThanOrEqualTo(String value) {
            addCriterion("strategy_type <=", value, "strategyType");
            return (Criteria) this;
        }

        public Criteria andStrategyTypeLike(String value) {
            addCriterion("strategy_type like", value, "strategyType");
            return (Criteria) this;
        }

        public Criteria andStrategyTypeNotLike(String value) {
            addCriterion("strategy_type not like", value, "strategyType");
            return (Criteria) this;
        }

        public Criteria andStrategyTypeIn(List<String> values) {
            addCriterion("strategy_type in", values, "strategyType");
            return (Criteria) this;
        }

        public Criteria andStrategyTypeNotIn(List<String> values) {
            addCriterion("strategy_type not in", values, "strategyType");
            return (Criteria) this;
        }

        public Criteria andStrategyTypeBetween(String value1, String value2) {
            addCriterion("strategy_type between", value1, value2, "strategyType");
            return (Criteria) this;
        }

        public Criteria andStrategyTypeNotBetween(String value1, String value2) {
            addCriterion("strategy_type not between", value1, value2, "strategyType");
            return (Criteria) this;
        }

        public Criteria andStrategyStatusIsNull() {
            addCriterion("strategy_status is null");
            return (Criteria) this;
        }

        public Criteria andStrategyStatusIsNotNull() {
            addCriterion("strategy_status is not null");
            return (Criteria) this;
        }

        public Criteria andStrategyStatusEqualTo(String value) {
            addCriterion("strategy_status =", value, "strategyStatus");
            return (Criteria) this;
        }

        public Criteria andStrategyStatusNotEqualTo(String value) {
            addCriterion("strategy_status <>", value, "strategyStatus");
            return (Criteria) this;
        }

        public Criteria andStrategyStatusGreaterThan(String value) {
            addCriterion("strategy_status >", value, "strategyStatus");
            return (Criteria) this;
        }

        public Criteria andStrategyStatusGreaterThanOrEqualTo(String value) {
            addCriterion("strategy_status >=", value, "strategyStatus");
            return (Criteria) this;
        }

        public Criteria andStrategyStatusLessThan(String value) {
            addCriterion("strategy_status <", value, "strategyStatus");
            return (Criteria) this;
        }

        public Criteria andStrategyStatusLessThanOrEqualTo(String value) {
            addCriterion("strategy_status <=", value, "strategyStatus");
            return (Criteria) this;
        }

        public Criteria andStrategyStatusLike(String value) {
            addCriterion("strategy_status like", value, "strategyStatus");
            return (Criteria) this;
        }

        public Criteria andStrategyStatusNotLike(String value) {
            addCriterion("strategy_status not like", value, "strategyStatus");
            return (Criteria) this;
        }

        public Criteria andStrategyStatusIn(List<String> values) {
            addCriterion("strategy_status in", values, "strategyStatus");
            return (Criteria) this;
        }

        public Criteria andStrategyStatusNotIn(List<String> values) {
            addCriterion("strategy_status not in", values, "strategyStatus");
            return (Criteria) this;
        }

        public Criteria andStrategyStatusBetween(String value1, String value2) {
            addCriterion("strategy_status between", value1, value2, "strategyStatus");
            return (Criteria) this;
        }

        public Criteria andStrategyStatusNotBetween(String value1, String value2) {
            addCriterion("strategy_status not between", value1, value2, "strategyStatus");
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

        public Criteria andIsDeletedEqualTo(Short value) {
            addCriterion("is_deleted =", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotEqualTo(Short value) {
            addCriterion("is_deleted <>", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThan(Short value) {
            addCriterion("is_deleted >", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThanOrEqualTo(Short value) {
            addCriterion("is_deleted >=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThan(Short value) {
            addCriterion("is_deleted <", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThanOrEqualTo(Short value) {
            addCriterion("is_deleted <=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIn(List<Short> values) {
            addCriterion("is_deleted in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotIn(List<Short> values) {
            addCriterion("is_deleted not in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedBetween(Short value1, Short value2) {
            addCriterion("is_deleted between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotBetween(Short value1, Short value2) {
            addCriterion("is_deleted not between", value1, value2, "isDeleted");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table activiti_strategy
     *
     * @mbg.generated do_not_delete_during_merge Fri Nov 15 06:59:35 CST 2019
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table activiti_strategy
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
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