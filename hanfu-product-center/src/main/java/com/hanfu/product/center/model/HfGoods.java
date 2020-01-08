package com.hanfu.product.center.model;



import java.io.Serializable;

import java.time.LocalDateTime;



public class HfGoods implements Serializable {

    /**
     *
     * This field was generated by MyBatis Generator.

     * This field corresponds to the database column hf_goods.id

     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    private Integer id;



    /**
     *
     * This field was generated by MyBatis Generator.

     * This field corresponds to the database column hf_goods.hf_name

     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    private String hfName;



    /**
     *
     * This field was generated by MyBatis Generator.

     * This field corresponds to the database column hf_goods.instance_id

     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    private Integer instanceId;



    /**
     *
     * This field was generated by MyBatis Generator.

     * This field corresponds to the database column hf_goods.product_id

     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    private Integer productId;



    /**
     *
     * This field was generated by MyBatis Generator.

     * This field corresponds to the database column hf_goods.category_id

     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    private Integer categoryId;



    /**
     *
     * This field was generated by MyBatis Generator.

     * This field corresponds to the database column hf_goods.stone_id

     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    private Integer stoneId;



    /**
     *
     * This field was generated by MyBatis Generator.

     * This field corresponds to the database column hf_goods.boss_id

     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    private Integer bossId;



    /**
     *
     * This field was generated by MyBatis Generator.

     * This field corresponds to the database column hf_goods.brand_id

     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    private Integer brandId;



    /**
     *
     * This field was generated by MyBatis Generator.

     * This field corresponds to the database column hf_goods.resp_id

     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    private Integer respId;



    /**
     *
     * This field was generated by MyBatis Generator.

     * This field corresponds to the database column hf_goods.price_id

     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    private Integer priceId;



    /**
     *
     * This field was generated by MyBatis Generator.

     * This field corresponds to the database column hf_goods.goods_desc

     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    private String goodsDesc;



    /**
     *
     * This field was generated by MyBatis Generator.

     * This field corresponds to the database column hf_goods.create_time

     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    private LocalDateTime createTime;



    /**
     *
     * This field was generated by MyBatis Generator.

     * This field corresponds to the database column hf_goods.modify_time

     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    private LocalDateTime modifyTime;



    /**
     *
     * This field was generated by MyBatis Generator.

     * This field corresponds to the database column hf_goods.is_deleted

     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    private Short isDeleted;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_goods.cancel_id
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    private Integer cancelId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_goods.claim
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    private Integer claim;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_goods.frames
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    private Integer frames;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_goods.member
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    private Integer member;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_goods.evaluate
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    private String evaluate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hf_goods
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    private static final long serialVersionUID = 1L;



    /**

     * This method was generated by MyBatis Generator.

     * This method returns the value of the database column hf_goods.id

     *

     * @return the value of hf_goods.id
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    public Integer getId() {

        return id;

    }



    /**

     * This method was generated by MyBatis Generator.

     * This method sets the value of the database column hf_goods.id

     *

     * @param id the value for hf_goods.id
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    public void setId(Integer id) {

        this.id = id;

    }



    /**

     * This method was generated by MyBatis Generator.

     * This method returns the value of the database column hf_goods.hf_name

     *

     * @return the value of hf_goods.hf_name
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    public String getHfName() {

        return hfName;

    }



    /**

     * This method was generated by MyBatis Generator.

     * This method sets the value of the database column hf_goods.hf_name

     *

     * @param hfName the value for hf_goods.hf_name
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    public void setHfName(String hfName) {

        this.hfName = hfName == null ? null : hfName.trim();

    }



    /**

     * This method was generated by MyBatis Generator.

     * This method returns the value of the database column hf_goods.instance_id

     *

     * @return the value of hf_goods.instance_id
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    public Integer getInstanceId() {

        return instanceId;

    }



    /**

     * This method was generated by MyBatis Generator.

     * This method sets the value of the database column hf_goods.instance_id

     *

     * @param instanceId the value for hf_goods.instance_id
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    public void setInstanceId(Integer instanceId) {

        this.instanceId = instanceId;

    }



    /**

     * This method was generated by MyBatis Generator.

     * This method returns the value of the database column hf_goods.product_id

     *

     * @return the value of hf_goods.product_id
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    public Integer getProductId() {

        return productId;

    }



    /**

     * This method was generated by MyBatis Generator.

     * This method sets the value of the database column hf_goods.product_id

     *

     * @param productId the value for hf_goods.product_id
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    public void setProductId(Integer productId) {

        this.productId = productId;

    }



    /**

     * This method was generated by MyBatis Generator.

     * This method returns the value of the database column hf_goods.category_id

     *

     * @return the value of hf_goods.category_id
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    public Integer getCategoryId() {

        return categoryId;

    }



    /**

     * This method was generated by MyBatis Generator.

     * This method sets the value of the database column hf_goods.category_id

     *

     * @param categoryId the value for hf_goods.category_id
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    public void setCategoryId(Integer categoryId) {

        this.categoryId = categoryId;

    }



    /**

     * This method was generated by MyBatis Generator.

     * This method returns the value of the database column hf_goods.stone_id

     *

     * @return the value of hf_goods.stone_id
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    public Integer getStoneId() {

        return stoneId;

    }



    /**

     * This method was generated by MyBatis Generator.

     * This method sets the value of the database column hf_goods.stone_id

     *

     * @param stoneId the value for hf_goods.stone_id
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    public void setStoneId(Integer stoneId) {

        this.stoneId = stoneId;

    }



    /**

     * This method was generated by MyBatis Generator.

     * This method returns the value of the database column hf_goods.boss_id

     *

     * @return the value of hf_goods.boss_id
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    public Integer getBossId() {

        return bossId;

    }



    /**

     * This method was generated by MyBatis Generator.

     * This method sets the value of the database column hf_goods.boss_id

     *

     * @param bossId the value for hf_goods.boss_id
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    public void setBossId(Integer bossId) {

        this.bossId = bossId;

    }



    /**

     * This method was generated by MyBatis Generator.

     * This method returns the value of the database column hf_goods.brand_id

     *

     * @return the value of hf_goods.brand_id
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    public Integer getBrandId() {

        return brandId;

    }



    /**

     * This method was generated by MyBatis Generator.

     * This method sets the value of the database column hf_goods.brand_id

     *

     * @param brandId the value for hf_goods.brand_id
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    public void setBrandId(Integer brandId) {

        this.brandId = brandId;

    }



    /**

     * This method was generated by MyBatis Generator.

     * This method returns the value of the database column hf_goods.resp_id

     *

     * @return the value of hf_goods.resp_id
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    public Integer getRespId() {

        return respId;

    }



    /**

     * This method was generated by MyBatis Generator.

     * This method sets the value of the database column hf_goods.resp_id

     *

     * @param respId the value for hf_goods.resp_id
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    public void setRespId(Integer respId) {

        this.respId = respId;

    }



    /**

     * This method was generated by MyBatis Generator.

     * This method returns the value of the database column hf_goods.price_id

     *

     * @return the value of hf_goods.price_id
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    public Integer getPriceId() {

        return priceId;

    }



    /**

     * This method was generated by MyBatis Generator.

     * This method sets the value of the database column hf_goods.price_id

     *

     * @param priceId the value for hf_goods.price_id
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    public void setPriceId(Integer priceId) {

        this.priceId = priceId;

    }



    /**

     * This method was generated by MyBatis Generator.

     * This method returns the value of the database column hf_goods.goods_desc

     *

     * @return the value of hf_goods.goods_desc
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    public String getGoodsDesc() {

        return goodsDesc;

    }



    /**

     * This method was generated by MyBatis Generator.

     * This method sets the value of the database column hf_goods.goods_desc

     *

     * @param goodsDesc the value for hf_goods.goods_desc
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    public void setGoodsDesc(String goodsDesc) {

        this.goodsDesc = goodsDesc == null ? null : goodsDesc.trim();

    }



    /**

     * This method was generated by MyBatis Generator.

     * This method returns the value of the database column hf_goods.create_time

     *

     * @return the value of hf_goods.create_time
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    public LocalDateTime getCreateTime() {

        return createTime;

    }



    /**

     * This method was generated by MyBatis Generator.

     * This method sets the value of the database column hf_goods.create_time

     *

     * @param createTime the value for hf_goods.create_time
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    public void setCreateTime(LocalDateTime createTime) {

        this.createTime = createTime;

    }



    /**

     * This method was generated by MyBatis Generator.

     * This method returns the value of the database column hf_goods.modify_time

     *

     * @return the value of hf_goods.modify_time
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    public LocalDateTime getModifyTime() {

        return modifyTime;

    }



    /**

     * This method was generated by MyBatis Generator.

     * This method sets the value of the database column hf_goods.modify_time

     *

     * @param modifyTime the value for hf_goods.modify_time
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    public void setModifyTime(LocalDateTime modifyTime) {

        this.modifyTime = modifyTime;

    }



    /**

     * This method was generated by MyBatis Generator.

     * This method returns the value of the database column hf_goods.is_deleted

     *

     * @return the value of hf_goods.is_deleted
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    public Short getIsDeleted() {

        return isDeleted;

    }



    /**

     * This method was generated by MyBatis Generator.

     * This method sets the value of the database column hf_goods.is_deleted

     *

     * @param isDeleted the value for hf_goods.is_deleted
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    public void setIsDeleted(Short isDeleted) {

        this.isDeleted = isDeleted;

    }



    /**

     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_goods.cancel_id
     *
     * @return the value of hf_goods.cancel_id
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    public Integer getCancelId() {
        return cancelId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_goods.cancel_id
     *
     * @param cancelId the value for hf_goods.cancel_id
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    public void setCancelId(Integer cancelId) {
        this.cancelId = cancelId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_goods.claim
     *
     * @return the value of hf_goods.claim
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    public Integer getClaim() {
        return claim;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_goods.claim
     *
     * @param claim the value for hf_goods.claim
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    public void setClaim(Integer claim) {
        this.claim = claim;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_goods.frames
     *
     * @return the value of hf_goods.frames
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    public Integer getFrames() {
        return frames;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_goods.frames
     *
     * @param frames the value for hf_goods.frames
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    public void setFrames(Integer frames) {
        this.frames = frames;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_goods.member
     *
     * @return the value of hf_goods.member
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    public Integer getMember() {
        return member;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_goods.member
     *
     * @param member the value for hf_goods.member
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    public void setMember(Integer member) {
        this.member = member;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_goods.evaluate
     *
     * @return the value of hf_goods.evaluate
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    public String getEvaluate() {
        return evaluate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_goods.evaluate
     *
     * @param evaluate the value for hf_goods.evaluate
     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */
    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate == null ? null : evaluate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_goods

     *
     * @mbg.generated Wed Jan 08 13:02:42 CST 2020
     */

    @Override

    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append(getClass().getSimpleName());

        sb.append(" [");

        sb.append("Hash = ").append(hashCode());

        sb.append(", id=").append(id);

        sb.append(", hfName=").append(hfName);

        sb.append(", instanceId=").append(instanceId);

        sb.append(", productId=").append(productId);

        sb.append(", categoryId=").append(categoryId);

        sb.append(", stoneId=").append(stoneId);

        sb.append(", bossId=").append(bossId);

        sb.append(", brandId=").append(brandId);

        sb.append(", respId=").append(respId);

        sb.append(", priceId=").append(priceId);

        sb.append(", goodsDesc=").append(goodsDesc);

        sb.append(", createTime=").append(createTime);

        sb.append(", modifyTime=").append(modifyTime);

        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", cancelId=").append(cancelId);
        sb.append(", claim=").append(claim);
        sb.append(", frames=").append(frames);
        sb.append(", member=").append(member);
        sb.append(", evaluate=").append(evaluate);
        sb.append("]");

        return sb.toString();

    }

}