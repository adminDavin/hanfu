package com.hanfu.product.center.request;


import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class DeleteGoodsPictrueRequest extends CommonRequest {
    @ApiModelProperty(required = true, value = "物品id, 即对应到商铺商品的某一种种类")
    private Integer goodsId;
    @ApiModelProperty(required = false, value = "要删除的图片id")
    private Integer fileId;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

}
