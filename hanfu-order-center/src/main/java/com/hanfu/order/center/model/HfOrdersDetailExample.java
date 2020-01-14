package com.hanfu.order.center.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HfOrdersDetailExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hf_orders_detail
     *
     * @mbg.generated Wed Oct 30 06:29:10 CST 2019
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hf_orders_detail
     *
     * @mbg.generated Wed Oct 30 06:29:10 CST 2019
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hf_orders_detail
     *
     * @mbg.generated Wed Oct 30 06:29:10 CST 2019
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_orders_detail
     *
     * @mbg.generated Wed Oct 30 06:29:10 CST 2019
     */
    public HfOrdersDetailExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_orders_detail
     *
     * @mbg.generated Wed Oct 30 06:29:10 CST 2019
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_orders_detail
     *
     * @mbg.generated Wed Oct 30 06:29:10 CST 2019
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_orders_detail
     *
     * @mbg.generated Wed Oct 30 06:29:10 CST 2019
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_orders_detail
     *
     * @mbg.generated Wed Oct 30 06:29:10 CST 2019
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_orders_detail
     *
     * @mbg.generated Wed Oct 30 06:29:10 CST 2019
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_orders_detail
     *
     * @mbg.generated Wed Oct 30 06:29:10 CST 2019
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_orders_detail
     *
     * @mbg.generated Wed Oct 30 06:29:10 CST 2019
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_orders_detail
     *
     * @mbg.generated Wed Oct 30 06:29:10 CST 2019
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
     * This method corresponds to the database table hf_orders_detail
     *
     * @mbg.generated Wed Oct 30 06:29:10 CST 2019
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_orders_detail
     *
     * @mbg.generated Wed Oct 30 06:29:10 CST 2019
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table hf_orders_detail
     *
     * @mbg.generated Wed Oct 30 06:29:10 CST 2019
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

        public Criteria andOrdersIdIsNull() {
            addCriterion("orders_id is null");
            return (Criteria) this;
        }

        public Criteria andOrdersIdIsNotNull() {
            addCriterion("orders_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrdersIdEqualTo(Integer value) {
            addCriterion("orders_id =", value, "ordersId");
            return (Criteria) this;
        }

        public Criteria andOrdersIdNotEqualTo(Integer value) {
            addCriterion("orders_id <>", value, "ordersId");
            return (Criteria) this;
        }

        public Criteria andOrdersIdGreaterThan(Integer value) {
            addCriterion("orders_id >", value, "ordersId");
            return (Criteria) this;
        }

        public Criteria andOrdersIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("orders_id >=", value, "ordersId");
            return (Criteria) this;
        }

        public Criteria andOrdersIdLessThan(Integer value) {
            addCriterion("orders_id <", value, "ordersId");
            return (Criteria) this;
        }

        public Criteria andOrdersIdLessThanOrEqualTo(Integer value) {
            addCriterion("orders_id <=", value, "ordersId");
            return (Criteria) this;
        }

        public Criteria andOrdersIdIn(List<Integer> values) {
            addCriterion("orders_id in", values, "ordersId");
            return (Criteria) this;
        }

        public Criteria andOrdersIdNotIn(List<Integer> values) {
            addCriterion("orders_id not in", values, "ordersId");
            return (Criteria) this;
        }

        public Criteria andOrdersIdBetween(Integer value1, Integer value2) {
            addCriterion("orders_id between", value1, value2, "ordersId");
            return (Criteria) this;
        }

        public Criteria andOrdersIdNotBetween(Integer value1, Integer value2) {
            addCriterion("orders_id not between", value1, value2, "ordersId");
            return (Criteria) this;
        }

        public Criteria andRespIdIsNull() {
            addCriterion("resp_id is null");
            return (Criteria) this;
        }

        public Criteria andRespIdIsNotNull() {
            addCriterion("resp_id is not null");
            return (Criteria) this;
        }

        public Criteria andRespIdEqualTo(Integer value) {
            addCriterion("resp_id =", value, "respId");
            return (Criteria) this;
        }

        public Criteria andRespIdNotEqualTo(Integer value) {
            addCriterion("resp_id <>", value, "respId");
            return (Criteria) this;
        }

        public Criteria andRespIdGreaterThan(Integer value) {
            addCriterion("resp_id >", value, "respId");
            return (Criteria) this;
        }

        public Criteria andRespIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("resp_id >=", value, "respId");
            return (Criteria) this;
        }

        public Criteria andRespIdLessThan(Integer value) {
            addCriterion("resp_id <", value, "respId");
            return (Criteria) this;
        }

        public Criteria andRespIdLessThanOrEqualTo(Integer value) {
            addCriterion("resp_id <=", value, "respId");
            return (Criteria) this;
        }

        public Criteria andRespIdIn(List<Integer> values) {
            addCriterion("resp_id in", values, "respId");
            return (Criteria) this;
        }

        public Criteria andRespIdNotIn(List<Integer> values) {
            addCriterion("resp_id not in", values, "respId");
            return (Criteria) this;
        }

        public Criteria andRespIdBetween(Integer value1, Integer value2) {
            addCriterion("resp_id between", value1, value2, "respId");
            return (Criteria) this;
        }

        public Criteria andRespIdNotBetween(Integer value1, Integer value2) {
            addCriterion("resp_id not between", value1, value2, "respId");
            return (Criteria) this;
        }

        public Criteria andOrderDetailStatusIsNull() {
            addCriterion("order_detail_status is null");
            return (Criteria) this;
        }

        public Criteria andOrderDetailStatusIsNotNull() {
            addCriterion("order_detail_status is not null");
            return (Criteria) this;
        }

        public Criteria andOrderDetailStatusEqualTo(String value) {
            addCriterion("order_detail_status =", value, "orderDetailStatus");
            return (Criteria) this;
        }

        public Criteria andOrderDetailStatusNotEqualTo(String value) {
            addCriterion("order_detail_status <>", value, "orderDetailStatus");
            return (Criteria) this;
        }

        public Criteria andOrderDetailStatusGreaterThan(String value) {
            addCriterion("order_detail_status >", value, "orderDetailStatus");
            return (Criteria) this;
        }

        public Criteria andOrderDetailStatusGreaterThanOrEqualTo(String value) {
            addCriterion("order_detail_status >=", value, "orderDetailStatus");
            return (Criteria) this;
        }

        public Criteria andOrderDetailStatusLessThan(String value) {
            addCriterion("order_detail_status <", value, "orderDetailStatus");
            return (Criteria) this;
        }

        public Criteria andOrderDetailStatusLessThanOrEqualTo(String value) {
            addCriterion("order_detail_status <=", value, "orderDetailStatus");
            return (Criteria) this;
        }

        public Criteria andOrderDetailStatusLike(String value) {
            addCriterion("order_detail_status like", value, "orderDetailStatus");
            return (Criteria) this;
        }

        public Criteria andOrderDetailStatusNotLike(String value) {
            addCriterion("order_detail_status not like", value, "orderDetailStatus");
            return (Criteria) this;
        }

        public Criteria andOrderDetailStatusIn(List<String> values) {
            addCriterion("order_detail_status in", values, "orderDetailStatus");
            return (Criteria) this;
        }

        public Criteria andOrderDetailStatusNotIn(List<String> values) {
            addCriterion("order_detail_status not in", values, "orderDetailStatus");
            return (Criteria) this;
        }

        public Criteria andOrderDetailStatusBetween(String value1, String value2) {
            addCriterion("order_detail_status between", value1, value2, "orderDetailStatus");
            return (Criteria) this;
        }

        public Criteria andOrderDetailStatusNotBetween(String value1, String value2) {
            addCriterion("order_detail_status not between", value1, value2, "orderDetailStatus");
            return (Criteria) this;
        }

        public Criteria andGoogsIdIsNull() {
            addCriterion("googs_id is null");
            return (Criteria) this;
        }

        public Criteria andGoogsIdIsNotNull() {
            addCriterion("googs_id is not null");
            return (Criteria) this;
        }

        public Criteria andGoogsIdEqualTo(Integer value) {
            addCriterion("googs_id =", value, "googsId");
            return (Criteria) this;
        }

        public Criteria andGoogsIdNotEqualTo(Integer value) {
            addCriterion("googs_id <>", value, "googsId");
            return (Criteria) this;
        }

        public Criteria andGoogsIdGreaterThan(Integer value) {
            addCriterion("googs_id >", value, "googsId");
            return (Criteria) this;
        }

        public Criteria andGoogsIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("googs_id >=", value, "googsId");
            return (Criteria) this;
        }

        public Criteria andGoogsIdLessThan(Integer value) {
            addCriterion("googs_id <", value, "googsId");
            return (Criteria) this;
        }

        public Criteria andGoogsIdLessThanOrEqualTo(Integer value) {
            addCriterion("googs_id <=", value, "googsId");
            return (Criteria) this;
        }

        public Criteria andGoogsIdIn(List<Integer> values) {
            addCriterion("googs_id in", values, "googsId");
            return (Criteria) this;
        }

        public Criteria andGoogsIdNotIn(List<Integer> values) {
            addCriterion("googs_id not in", values, "googsId");
            return (Criteria) this;
        }

        public Criteria andGoogsIdBetween(Integer value1, Integer value2) {
            addCriterion("googs_id between", value1, value2, "googsId");
            return (Criteria) this;
        }

        public Criteria andGoogsIdNotBetween(Integer value1, Integer value2) {
            addCriterion("googs_id not between", value1, value2, "googsId");
            return (Criteria) this;
        }

        public Criteria andHfTaxIsNull() {
            addCriterion("hf_tax is null");
            return (Criteria) this;
        }

        public Criteria andHfTaxIsNotNull() {
            addCriterion("hf_tax is not null");
            return (Criteria) this;
        }

        public Criteria andHfTaxEqualTo(Integer value) {
            addCriterion("hf_tax =", value, "hfTax");
            return (Criteria) this;
        }

        public Criteria andHfTaxNotEqualTo(Integer value) {
            addCriterion("hf_tax <>", value, "hfTax");
            return (Criteria) this;
        }

        public Criteria andHfTaxGreaterThan(Integer value) {
            addCriterion("hf_tax >", value, "hfTax");
            return (Criteria) this;
        }

        public Criteria andHfTaxGreaterThanOrEqualTo(Integer value) {
            addCriterion("hf_tax >=", value, "hfTax");
            return (Criteria) this;
        }

        public Criteria andHfTaxLessThan(Integer value) {
            addCriterion("hf_tax <", value, "hfTax");
            return (Criteria) this;
        }

        public Criteria andHfTaxLessThanOrEqualTo(Integer value) {
            addCriterion("hf_tax <=", value, "hfTax");
            return (Criteria) this;
        }

        public Criteria andHfTaxIn(List<Integer> values) {
            addCriterion("hf_tax in", values, "hfTax");
            return (Criteria) this;
        }

        public Criteria andHfTaxNotIn(List<Integer> values) {
            addCriterion("hf_tax not in", values, "hfTax");
            return (Criteria) this;
        }

        public Criteria andHfTaxBetween(Integer value1, Integer value2) {
            addCriterion("hf_tax between", value1, value2, "hfTax");
            return (Criteria) this;
        }

        public Criteria andHfTaxNotBetween(Integer value1, Integer value2) {
            addCriterion("hf_tax not between", value1, value2, "hfTax");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceIsNull() {
            addCriterion("purchase_price is null");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceIsNotNull() {
            addCriterion("purchase_price is not null");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceEqualTo(Integer value) {
            addCriterion("purchase_price =", value, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceNotEqualTo(Integer value) {
            addCriterion("purchase_price <>", value, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceGreaterThan(Integer value) {
            addCriterion("purchase_price >", value, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("purchase_price >=", value, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceLessThan(Integer value) {
            addCriterion("purchase_price <", value, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceLessThanOrEqualTo(Integer value) {
            addCriterion("purchase_price <=", value, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceIn(List<Integer> values) {
            addCriterion("purchase_price in", values, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceNotIn(List<Integer> values) {
            addCriterion("purchase_price not in", values, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceBetween(Integer value1, Integer value2) {
            addCriterion("purchase_price between", value1, value2, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceNotBetween(Integer value1, Integer value2) {
            addCriterion("purchase_price not between", value1, value2, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchaseQuantityIsNull() {
            addCriterion("purchase_quantity is null");
            return (Criteria) this;
        }

        public Criteria andPurchaseQuantityIsNotNull() {
            addCriterion("purchase_quantity is not null");
            return (Criteria) this;
        }

        public Criteria andPurchaseQuantityEqualTo(Integer value) {
            addCriterion("purchase_quantity =", value, "purchaseQuantity");
            return (Criteria) this;
        }

        public Criteria andPurchaseQuantityNotEqualTo(Integer value) {
            addCriterion("purchase_quantity <>", value, "purchaseQuantity");
            return (Criteria) this;
        }

        public Criteria andPurchaseQuantityGreaterThan(Integer value) {
            addCriterion("purchase_quantity >", value, "purchaseQuantity");
            return (Criteria) this;
        }

        public Criteria andPurchaseQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("purchase_quantity >=", value, "purchaseQuantity");
            return (Criteria) this;
        }

        public Criteria andPurchaseQuantityLessThan(Integer value) {
            addCriterion("purchase_quantity <", value, "purchaseQuantity");
            return (Criteria) this;
        }

        public Criteria andPurchaseQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("purchase_quantity <=", value, "purchaseQuantity");
            return (Criteria) this;
        }

        public Criteria andPurchaseQuantityIn(List<Integer> values) {
            addCriterion("purchase_quantity in", values, "purchaseQuantity");
            return (Criteria) this;
        }

        public Criteria andPurchaseQuantityNotIn(List<Integer> values) {
            addCriterion("purchase_quantity not in", values, "purchaseQuantity");
            return (Criteria) this;
        }

        public Criteria andPurchaseQuantityBetween(Integer value1, Integer value2) {
            addCriterion("purchase_quantity between", value1, value2, "purchaseQuantity");
            return (Criteria) this;
        }

        public Criteria andPurchaseQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("purchase_quantity not between", value1, value2, "purchaseQuantity");
            return (Criteria) this;
        }

        public Criteria andDistributionIsNull() {
            addCriterion("Distribution is null");
            return (Criteria) this;
        }

        public Criteria andDistributionIsNotNull() {
            addCriterion("Distribution is not null");
            return (Criteria) this;
        }

        public Criteria andDistributionEqualTo(String value) {
            addCriterion("Distribution =", value, "distribution");
            return (Criteria) this;
        }

        public Criteria andDistributionNotEqualTo(String value) {
            addCriterion("Distribution <>", value, "distribution");
            return (Criteria) this;
        }

        public Criteria andDistributionGreaterThan(String value) {
            addCriterion("Distribution >", value, "distribution");
            return (Criteria) this;
        }

        public Criteria andDistributionGreaterThanOrEqualTo(String value) {
            addCriterion("Distribution >=", value, "distribution");
            return (Criteria) this;
        }

        public Criteria andDistributionLessThan(String value) {
            addCriterion("Distribution <", value, "distribution");
            return (Criteria) this;
        }

        public Criteria andDistributionLessThanOrEqualTo(String value) {
            addCriterion("Distribution <=", value, "distribution");
            return (Criteria) this;
        }

        public Criteria andDistributionLike(String value) {
            addCriterion("Distribution like", value, "distribution");
            return (Criteria) this;
        }

        public Criteria andDistributionNotLike(String value) {
            addCriterion("Distribution not like", value, "distribution");
            return (Criteria) this;
        }

        public Criteria andDistributionIn(List<String> values) {
            addCriterion("Distribution in", values, "distribution");
            return (Criteria) this;
        }

        public Criteria andDistributionNotIn(List<String> values) {
            addCriterion("Distribution not in", values, "distribution");
            return (Criteria) this;
        }

        public Criteria andDistributionBetween(String value1, String value2) {
            addCriterion("Distribution between", value1, value2, "distribution");
            return (Criteria) this;
        }

        public Criteria andDistributionNotBetween(String value1, String value2) {
            addCriterion("Distribution not between", value1, value2, "distribution");
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

        public Criteria andLastModifierIsNull() {
            addCriterion("last_modifier is null");
            return (Criteria) this;
        }

        public Criteria andLastModifierIsNotNull() {
            addCriterion("last_modifier is not null");
            return (Criteria) this;
        }

        public Criteria andLastModifierEqualTo(String value) {
            addCriterion("last_modifier =", value, "lastModifier");
            return (Criteria) this;
        }

        public Criteria andLastModifierNotEqualTo(String value) {
            addCriterion("last_modifier <>", value, "lastModifier");
            return (Criteria) this;
        }

        public Criteria andLastModifierGreaterThan(String value) {
            addCriterion("last_modifier >", value, "lastModifier");
            return (Criteria) this;
        }

        public Criteria andLastModifierGreaterThanOrEqualTo(String value) {
            addCriterion("last_modifier >=", value, "lastModifier");
            return (Criteria) this;
        }

        public Criteria andLastModifierLessThan(String value) {
            addCriterion("last_modifier <", value, "lastModifier");
            return (Criteria) this;
        }

        public Criteria andLastModifierLessThanOrEqualTo(String value) {
            addCriterion("last_modifier <=", value, "lastModifier");
            return (Criteria) this;
        }

        public Criteria andLastModifierLike(String value) {
            addCriterion("last_modifier like", value, "lastModifier");
            return (Criteria) this;
        }

        public Criteria andLastModifierNotLike(String value) {
            addCriterion("last_modifier not like", value, "lastModifier");
            return (Criteria) this;
        }

        public Criteria andLastModifierIn(List<String> values) {
            addCriterion("last_modifier in", values, "lastModifier");
            return (Criteria) this;
        }

        public Criteria andLastModifierNotIn(List<String> values) {
            addCriterion("last_modifier not in", values, "lastModifier");
            return (Criteria) this;
        }

        public Criteria andLastModifierBetween(String value1, String value2) {
            addCriterion("last_modifier between", value1, value2, "lastModifier");
            return (Criteria) this;
        }

        public Criteria andLastModifierNotBetween(String value1, String value2) {
            addCriterion("last_modifier not between", value1, value2, "lastModifier");
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
     * This class corresponds to the database table hf_orders_detail
     *
     * @mbg.generated do_not_delete_during_merge Wed Oct 30 06:29:10 CST 2019
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table hf_orders_detail
     *
     * @mbg.generated Wed Oct 30 06:29:10 CST 2019
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