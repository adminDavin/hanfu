package com.hanfu.order.center.model;

import com.hanfu.order.center.manual.model.DetailRequest;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HfOrderDetail implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_detail.id
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_detail.order_id
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    private Integer orderId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_detail.resp_id
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    private Integer respId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_detail.hf_status
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    private String hfStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_detail.goods_id
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    private Integer goodsId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_detail.hf_tax
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    private Integer hfTax;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_detail.sell_price
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    private Integer sellPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_detail.actual_price
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    private Integer actualPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_detail.quantity
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    private Integer quantity;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_detail.taking_type
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    private String takingType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_detail.freight
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    private Integer freight;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_detail.hf_desc
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    private String hfDesc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_detail.stone_id
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    private Integer stoneId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_detail.create_time
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_detail.modify_time
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_detail.last_modifier
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    private String lastModifier;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_detail.id_deleted
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    private Byte idDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hf_order_detail
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_detail.id
     *
     * @return the value of hf_order_detail.id
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_detail.id
     *
     * @param id the value for hf_order_detail.id
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_detail.order_id
     *
     * @return the value of hf_order_detail.order_id
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_detail.order_id
     *
     * @param orderId the value for hf_order_detail.order_id
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_detail.resp_id
     *
     * @return the value of hf_order_detail.resp_id
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public Integer getRespId() {
        return respId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_detail.resp_id
     *
     * @param respId the value for hf_order_detail.resp_id
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public void setRespId(Integer respId) {
        this.respId = respId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_detail.hf_status
     *
     * @return the value of hf_order_detail.hf_status
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public String getHfStatus() {
        return hfStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_detail.hf_status
     *
     * @param hfStatus the value for hf_order_detail.hf_status
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public void setHfStatus(String hfStatus) {
        this.hfStatus = hfStatus == null ? null : hfStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_detail.goods_id
     *
     * @return the value of hf_order_detail.goods_id
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public Integer getGoodsId() {
        return goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_detail.goods_id
     *
     * @param goodsId the value for hf_order_detail.goods_id
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_detail.hf_tax
     *
     * @return the value of hf_order_detail.hf_tax
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public Integer getHfTax() {
        return hfTax;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_detail.hf_tax
     *
     * @param hfTax the value for hf_order_detail.hf_tax
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public void setHfTax(Integer hfTax) {
        this.hfTax = hfTax;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_detail.sell_price
     *
     * @return the value of hf_order_detail.sell_price
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public Integer getSellPrice() {
        return sellPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_detail.sell_price
     *
     * @param sellPrice the value for hf_order_detail.sell_price
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public void setSellPrice(Integer sellPrice) {
        this.sellPrice = sellPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_detail.actual_price
     *
     * @return the value of hf_order_detail.actual_price
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public Integer getActualPrice() {
        return actualPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_detail.actual_price
     *
     * @param actualPrice the value for hf_order_detail.actual_price
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public void setActualPrice(Integer actualPrice) {
        this.actualPrice = actualPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_detail.quantity
     *
     * @return the value of hf_order_detail.quantity
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_detail.quantity
     *
     * @param quantity the value for hf_order_detail.quantity
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_detail.taking_type
     *
     * @return the value of hf_order_detail.taking_type
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public String getTakingType() {
        return takingType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_detail.taking_type
     *
     * @param takingType the value for hf_order_detail.taking_type
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public void setTakingType(String takingType) {
        this.takingType = takingType == null ? null : takingType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_detail.freight
     *
     * @return the value of hf_order_detail.freight
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public Integer getFreight() {
        return freight;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_detail.freight
     *
     * @param freight the value for hf_order_detail.freight
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public void setFreight(Integer freight) {
        this.freight = freight;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_detail.hf_desc
     *
     * @return the value of hf_order_detail.hf_desc
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public String getHfDesc() {
        return hfDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_detail.hf_desc
     *
     * @param hfDesc the value for hf_order_detail.hf_desc
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public void setHfDesc(String hfDesc) {
        this.hfDesc = hfDesc == null ? null : hfDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_detail.stone_id
     *
     * @return the value of hf_order_detail.stone_id
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public Integer getStoneId() {
        return stoneId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_detail.stone_id
     *
     * @param stoneId the value for hf_order_detail.stone_id
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public void setStoneId(Integer stoneId) {
        this.stoneId = stoneId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_detail.create_time
     *
     * @return the value of hf_order_detail.create_time
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_detail.create_time
     *
     * @param createTime the value for hf_order_detail.create_time
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_detail.modify_time
     *
     * @return the value of hf_order_detail.modify_time
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_detail.modify_time
     *
     * @param modifyTime the value for hf_order_detail.modify_time
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_detail.last_modifier
     *
     * @return the value of hf_order_detail.last_modifier
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public String getLastModifier() {
        return lastModifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_detail.last_modifier
     *
     * @param lastModifier the value for hf_order_detail.last_modifier
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier == null ? null : lastModifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_detail.id_deleted
     *
     * @return the value of hf_order_detail.id_deleted
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public Byte getIdDeleted() {
        return idDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_detail.id_deleted
     *
     * @param idDeleted the value for hf_order_detail.id_deleted
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    public void setIdDeleted(Byte idDeleted) {
        this.idDeleted = idDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_detail
     *
     * @mbg.generated Tue Apr 07 10:16:51 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderId=").append(orderId);
        sb.append(", respId=").append(respId);
        sb.append(", hfStatus=").append(hfStatus);
        sb.append(", goodsId=").append(goodsId);
        sb.append(", hfTax=").append(hfTax);
        sb.append(", sellPrice=").append(sellPrice);
        sb.append(", actualPrice=").append(actualPrice);
        sb.append(", quantity=").append(quantity);
        sb.append(", takingType=").append(takingType);
        sb.append(", freight=").append(freight);
        sb.append(", hfDesc=").append(hfDesc);
        sb.append(", stoneId=").append(stoneId);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", lastModifier=").append(lastModifier);
        sb.append(", idDeleted=").append(idDeleted);
        sb.append("]");
        return sb.toString();
    }
}