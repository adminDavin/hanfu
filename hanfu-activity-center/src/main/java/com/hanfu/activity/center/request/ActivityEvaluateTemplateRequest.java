package com.hanfu.activity.center.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
@ApiModel
public class ActivityEvaluateTemplateRequest extends CommonRequest {
    @ApiModelProperty(required = false, value = "模板id")
    private Integer id;
    @ApiModelProperty(required = false, value = "暂时存放活动id")
    private Integer parentTemplateId;
    @ApiModelProperty(required = false, value = "评价类型名")
    private String evaluateType;
    @ApiModelProperty(required = false, value = "评价内容")
    private String evaluateContent;
    @ApiModelProperty(required = false, value = "评价权重")
    private String evaluateWeight;
    @ApiModelProperty(required = false, value = "评论")
    private String remarks;
    @ApiModelProperty(required = false, value = "类型")
    private Short isDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentTemplateId() {
        return parentTemplateId;
    }

    public void setParentTemplateId(Integer parentTemplateId) {
        this.parentTemplateId = parentTemplateId;
    }

    public String getEvaluateType() {
        return evaluateType;
    }

    public void setEvaluateType(String evaluateType) {
        this.evaluateType = evaluateType;
    }

    public String getEvaluateContent() {
        return evaluateContent;
    }

    public void setEvaluateContent(String evaluateContent) {
        this.evaluateContent = evaluateContent;
    }

    public String getEvaluateWeight() {
        return evaluateWeight;
    }

    public void setEvaluateWeight(String evaluateWeight) {
        this.evaluateWeight = evaluateWeight;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }
}
